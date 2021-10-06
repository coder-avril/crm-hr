package com.lding.domain;

import lombok.Data;
import java.util.Date;

@Data
public class DateDto extends BaseDto {
    private Date beginDay;
    private Date endDay;
}
