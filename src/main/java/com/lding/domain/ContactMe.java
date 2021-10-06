package com.lding.domain;

import lombok.Data;

@Data
public class ContactMe extends BaseDto{
    private String name;
    private String userId;
    private String email;
    private String sendDate;
    private String subject;
    private String message;
}
