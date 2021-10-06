package com.lding.domain;

import lombok.Data;

@Data
public class Award extends BaseDto{
    private Integer userId;
    private String type;
    private String name;
    private String intro;
    private String picture;
}
