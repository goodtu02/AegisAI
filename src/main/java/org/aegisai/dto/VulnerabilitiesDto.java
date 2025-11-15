package org.aegisai.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VulnerabilitiesDto {

    //private Integer vulnerabilityId;

    //private Analysis analysis;
    private String message;
    private Integer lineNumber;
    private String codeSnippet;
    private String severity; // "Critical", "High", "Medium", "Low"
    private String cweLink;
}
