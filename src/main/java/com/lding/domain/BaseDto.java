package com.lding.domain;

import lombok.Data;
import java.util.Date;

@Data
public class BaseDto {
    private Integer id;
    private Date createdTime;
}
