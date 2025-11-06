package org.aegisai.service;

import org.aegisai.constant.AnalysisStatus;
import org.aegisai.constant.SeverityStatus;
import org.aegisai.dto.AnalysisDto;
import org.aegisai.dto.VulnerabilitiesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiService {
    private WebClient webClient_model1;
    private WebClient webClient_model2;
    private WebClient webClient_model3;
    private GeminiService geminiService;
    private final AnalysisRepository analysisRepository;
    private final VulnerabilityRepository vulnerabilityRepository;

    @Autowired
    public ApiService(WebClient.Builder webClientBuilder,
                      AnalysisRepository analysisRepository,
                                  Repository vulnerabilityRepository,
                      GeminiService geminiService) {
        WebClient webClient_model1 = webClientBuilder //codebert
                .baseUrl("https://api-inference.huggingface.co/models/mrm8488/codebert-base-finetuned-detect-insecure-code")
                .build();
        WebClient webClient_model2 = webClientBuilder //code t5
                .baseUrl("http://34.47.124.100:8000")
                .build();
        this.analysisRepository = analysisRepository;
        this.vulnerabilityRepository = vulnerabilityRepository;
        this.geminiService = geminiService;

    }

    public Integer requestModel1(AnalysisDto analysisDto){
        //vulnerable status generate
        return webClient_model1.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(analysisDto)
                .retrieve()
                .bodyToMono(Integer.class) // Dto 목록이 아닌 Integer로 직접 받음
                .block();
    }
    public String requestModel2(AnalysisDto analysisDto){
        //fixed code generate
        return webClient_model2.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(analysisDto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public String requestModel3(AnalysisDto analysisDto){
        //judgement reason generate for vulnerable status
        String prompt = "다음 코드에 대한 판정 이유를 생성해 주세요:";

        // 2. 요청 본문을 Map으로 만듭니다.
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("analysis_data", analysisDto);
        return webClient_model3.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public String requestModel3_1(AnalysisDto analysisDto){
        //judgement reason generate for code fix
        String prompt = "기존코드 : AnalysisDto. R";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("analysis_data", analysisDto);
        return webClient_model3.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<VulnerabilitiesDto> requestModel4(AnalysisDto analysisDto) {
        try {
            // 1. 프롬프트 생성
            String prompt = String.format(
                    "당신은 Java 보안 전문가입니다.\n" +
                            "제공된 'Before' 코드의 모든 보안 취약점과 'After' 코드가 이 문제들을 어떻게 해결했는지 분석해주세요.\n\n" +
                            "## [Before] 취약한 코드:\n```java\n%s\n```\n\n" +
                            "## [After] 수정된 코드:\n```java\n%s\n```\n\n" +
                            "발견된 모든 취약점에 대해 다음 JSON 배열 형식으로 정확하게 응답해주세요:\n" +
                            "[\n" +
                            "  {\n" +
                            "    \"message\": \"보안 취약점과 해결 방법에 대한 명확하고 간결한 설명 (200자 이내)\",\n" +
                            "    \"lineNumber\": 문제가 발생한 라인 번호 (정수),\n" +
                            "    \"codeSnippet\": \"취약한 코드의 핵심 부분 (한 줄)\",\n" +
                            "    \"severity\": \"Critical\", \"High\", \"Medium\", \"Low\" 중 하나,\n" +
                            "    \"cweLink\": \"https://cwe.mitre.org/data/definitions/XXX.html\" 형식의 CWE 링크\n" +
                            "  }\n" +
                            "]\n\n" +
                            "주의사항:\n" +
                            "- 반드시 JSON 배열 형태로 응답하세요\n" +
                            "- 취약점이 여러 개라면 배열에 모두 포함하세요\n" +
                            "- 취약점이 하나만 있어도 배열 형태 [ {...} ]로 응답하세요\n" +
                            "- JSON만 응답하고 다른 텍스트는 포함하지 마세요\n" +
                            "- 마크다운 코드 블록(```)을 사용하지 마세요",
                    analysisDto.getInputcode(),
                    analysisDto.getFixedcode()
            );

            // 2. Gemini API를 통해 취약점 분석 수행
            List<VulnerabilitiesDto> vulnerabilities = geminiService.analyzeVulnerabilities(
                    analysisDto.getInputcode(),
                    analysisDto.getFixedcode()
            );

            // 3. 결과 로깅 (선택사항)
            System.out.println("발견된 취약점 수: " + vulnerabilities.size());

            return vulnerabilities;

        } catch (Exception e) {
            e.printStackTrace();
            // 에러 발생 시 빈 리스트 반환
            return new ArrayList<>();
        }
    }

    @Transactional // 트랜잭션 필수
    public List<VulnerabilitiesDto> entityService(List<VulnerabilitiesDto> vulnerabilities, AnalysisDto analysisDto) {
        
        // 1. 외부 API에서 취약점 데이터 가져오기

        // 2. Analysis 엔티티 생성 및 저장
        Analysis analysis = Analysis.builder()
                .status(AnalysisStatus.COMPLETED)
                .highVulCount(0)
                .mediumVulCount(0)
                .lowVulCount(0)
                .build();
        
        Analysis savedAnalysis = analysisRepository.save(analysis);
        System.out.println("Analysis 저장 완료: ID = " + savedAnalysis.getAnalysisId());
        
        // 3. Vulnerability 엔티티 변환 및 저장
        if (vulnerabilities != null && !vulnerabilities.isEmpty()) {
            List<Vulnerability> vulEntities = vulnerabilities.stream()
                    .map(dto -> {
                        // String severity를 Enum으로 변환
                        SeverityStatus severityEnum = convertToSeverityEnum(dto.getSeverity());
                        
                        return Vulnerability.builder()
                                .analysis(savedAnalysis)
                                .message(dto.getMessage())
                                .lineNumber(dto.getLineNumber())
                                .codeSnippet(dto.getCodeSnippet())
                                .severity(severityEnum) // Enum 사용
                                .cweLink(dto.getCweLink())
                                .build();
                    })
                    .collect(Collectors.toList());
            
            vulnerabilityRepository.saveAll(vulEntities);
            System.out.println("Vulnerability " + vulEntities.size() + "개 저장 완료");
            
            // 4. 심각도별 카운트 업데이트
            long highCount = vulEntities.stream()
                    .filter(v -> v.getSeverity() == SeverityStatus.HIGH)
                    .count();
            long mediumCount = vulEntities.stream()
                    .filter(v -> v.getSeverity() == SeverityStatus.MEDIUM)
                    .count();
            long lowCount = vulEntities.stream()
                    .filter(v -> v.getSeverity() == SeverityStatus.LOW)
                    .count();
            
            savedAnalysis.completeAnalysis((int) highCount, (int) mediumCount, (int) lowCount);
            analysisRepository.save(savedAnalysis);
            System.out.println("Analysis 카운트 업데이트 완료");
        }
        return vulnerabilities;
    }
    
    // String을 SeverityStatus Enum으로 변환하는 헬퍼 메서드
    private SeverityStatus convertToSeverityEnum(String severity) {
        if (severity == null) {
            return SeverityStatus.LOW; // 기본값
        }
        
        switch (severity.toUpperCase()) {
            case "CRITICAL":
                return SeverityStatus.CRITICAL;
            case "HIGH":
                return SeverityStatus.HIGH;
            case "MEDIUM":
                return SeverityStatus.MEDIUM;
            case "LOW":
                return SeverityStatus.LOW;
            default:
                System.out.println("⚠️  알 수 없는 severity 값: " + severity + " → LOW로 변환");
                return SeverityStatus.LOW;
        }
    }
}