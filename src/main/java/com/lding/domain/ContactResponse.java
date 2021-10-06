package com.lding.domain;

import lombok.Data;
import java.util.List;

@Data
public class ContactResponse {
    /** 联络信息 */
    private List<ContactMe> contacts = null;

    /** 总数量和总页数 */
    private Integer totalCount;
    private Integer totalPages;

    /** 检索情报 */
    private ContactRequest searchInfo;
}
