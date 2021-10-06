package com.lding.service.impl;

import com.github.pagehelper.PageHelper;
import com.lding.dao.ContactMeDao;
import com.lding.domain.ContactMe;
import com.lding.domain.ContactRequest;
import com.lding.domain.ContactResponse;
import com.lding.service.ContactMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactMeServiceImpl implements ContactMeService {
    @Autowired
    private ContactMeDao dao;

    @Override
    public ContactResponse getAll(ContactRequest request) {
        ContactResponse response = new ContactResponse();
        response.setSearchInfo(request);
        int totalCount = this.dao.getAll(request).size();
        int pageSize = request.getPageSize();
        // 根据公式计算总件数和页面个数
        response.setTotalCount(this.dao.getAll(request).size());
        response.setTotalPages((totalCount + pageSize -1) / pageSize);
        // 自动分页
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        response.setContacts(this.dao.getAll(request));
        return response;
    }

    @Override
    public boolean save(ContactMe contactMe) {
        int count = 0;
        if (contactMe.getId() == null) {
            count = this.dao.insert(contactMe);
        }
        return count > 0;
    }
}
