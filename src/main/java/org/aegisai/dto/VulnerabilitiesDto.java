package org.aegisai.dto;

import jakarta.persistence.*;
import lombok.Getter;
import org.aegisai.entity.Analysis;

public class VulnerabilitiesDto {

    //private Integer vulnerabilityId;

    //private Analysis analysis;
    @Getter
    private String message;
    @Getter
    private Integer lineNumber;
    @Getter
    private String codeSnippet;
    @Getter
    private String severity; // "Critical", "High", "Medium", "Low"
    @Getter
    private String cweLink;
}
