package org.aegisai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class AnalysisDto {

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String fixedcode;

}

