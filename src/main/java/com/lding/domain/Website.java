package com.lding.domain;

import lombok.Data;

@Data
public class Website extends BaseDto {
    private Integer userId;
    private String link;
}
