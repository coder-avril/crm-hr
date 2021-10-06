package com.lding.domain;

import lombok.Data;
import java.util.Date;

@Data
public class ContactRequest{
    private Integer userId;
    private Date beginDay;
    private Date endDay;
    /** 页码：第几页 */
    private Integer pageNo;
    /** 每页的大小：每一页显示20个 */
    private Integer pageSize;
    /** 关键字 */
    private String keyword;
}
