package com.lding.domain;

import lombok.Data;

@Data
public class Skill extends BaseDto{
    private Integer userId;
    private String name;
    private Integer level;
}
