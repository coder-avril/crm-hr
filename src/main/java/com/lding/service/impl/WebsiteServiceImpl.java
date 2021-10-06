package com.lding.service.impl;

import com.lding.dao.WebsiteDao;
import com.lding.domain.Website;
import com.lding.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WebsiteServiceImpl implements WebsiteService {
    @Autowired
    private WebsiteDao dao;

    @Override
    public Website getAll(Integer userId) {
        return this.dao.getAll(userId);
    }

    @Override
    public boolean save(Website website) {
        int count = 0;
        if (website.getId() == null) {
            count = this.dao.insert(website);
        } else {
            count = this.dao.update(website);
        }
        return count > 0;
    }

    @Override
    public boolean remove(Integer id) {
        int count = 0;
        if (id != null) {
            count = this.dao.delete(id);
        }
        return count > 0;
    }
}
