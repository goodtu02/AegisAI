package org.aegisai.service;

import org.aegisai.dto.AnalysisDto;
import org.aegisai.dto.VulnerabilitiesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ApiService {

    private final WebClient webClient;

    @Autowired
    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://38b4f941-fec7-45cf-8e5e-0bbf1bf2336d.mock.pstmn.io")
                .build();
    }

    public List<VulnerabilitiesDto> request(AnalysisDto analysisDto) {

        return webClient.post()
                //.uri("/scan")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(analysisDto)
                .retrieve() // 응답 수신
                .bodyToFlux(VulnerabilitiesDto.class)
                .collectList().block();
    }

}
