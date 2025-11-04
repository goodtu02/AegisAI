package org.aegisai.repository;

import org.aegisai.constant.AnalysisStatus;
import org.aegisai.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {

    // 상태별 분석 조회 (Enum 사용)
    List<Analysis> findByStatus(AnalysisStatus status);

    // 대기 중인 분석만 조회
    @Query("SELECT a FROM Analysis a WHERE a.status = 'PENDING'")
    List<Analysis> findPendingAnalyses();

    // 처리 중인 분석만 조회
    @Query("SELECT a FROM Analysis a WHERE a.status = 'PROCESSING'")
    List<Analysis> findProcessingAnalyses();

    // 완료된 분석만 조회
    @Query("SELECT a FROM Analysis a WHERE a.status = 'COMPLETED'")
    List<Analysis> findCompletedAnalyses();

    // 실패한 분석만 조회
    @Query("SELECT a FROM Analysis a WHERE a.status = 'FAILED'")
    List<Analysis> findFailedAnalyses();

    // 특정 기간 내 분석 조회
    List<Analysis> findBySubmittedAtBetween(LocalDateTime start, LocalDateTime end);

    // 최근 N개 분석 조회 (모든 사용자)
    List<Analysis> findTop10ByOrderBySubmittedAtDesc();

    // 분석 ID로 상세 조회 (Vulnerability도 함께 가져오기)
    @Query("SELECT a FROM Analysis a LEFT JOIN FETCH a.vulnerabilities WHERE a.analysisId = :analysisId")
    Optional<Analysis> findByIdWithVulnerabilities(@Param("analysisId") Integer analysisId);

    // 전체 분석 개수
    long count();

    // 상태별 분석 개수
    long countByStatus(AnalysisStatus status);

    // 최신순으로 모든 분석 조회
    List<Analysis> findAllByOrderBySubmittedAtDesc();

    // 특정 날짜 이후 분석 조회
    List<Analysis> findBySubmittedAtAfter(LocalDateTime date);

    // 특정 날짜 이전 분석 조회
    List<Analysis> findBySubmittedAtBefore(LocalDateTime date);
}