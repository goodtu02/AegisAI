package org.aegisai.dto;

import jakarta.persistence.*;
import org.aegisai.entity.Analysis;

public class VulnerabilitiesDto {

    private Integer vulnerabilityId;

    private Analysis analysis;

    private String message;

    private Integer lineNumber;

    private String codeSnippet;

    private String severity; // "High", "Medium", "Low"

    private String cweLink;
}
