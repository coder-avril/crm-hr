package com.lding.domain;

import lombok.Data;

@Data
public class Education extends DateDto{
    private Integer userId;
    private String type;
    private String name;
    private String intro;
}
