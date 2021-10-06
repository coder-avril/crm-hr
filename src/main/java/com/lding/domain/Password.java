package com.lding.domain;

import lombok.Data;

@Data
public class Password {
    private String oldPassword;
    private String newPassword;
}
