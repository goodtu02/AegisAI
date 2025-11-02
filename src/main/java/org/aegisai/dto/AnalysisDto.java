package org.aegisai.dto;

import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

public class AnalysisDto {

    private Integer analysis_id;

    private Integer user_id;

    private String status; //PENDING, PROCESSING, COMPLETED, FAILED

    private String error_message;

    private Integer high_vul_count;

    private Integer medium_vul_count;

    private Integer low_vul_count;

    private LocalDateTime submitted_at;

    private LocalDateTime completed_at;

}

