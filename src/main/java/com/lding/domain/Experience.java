package com.lding.domain;

import lombok.Data;

@Data
public class Experience extends DateDto{
    private Integer userId;
    private String positionName;
    private String companyName;
    private String companyLink;
    private String intro;
}
