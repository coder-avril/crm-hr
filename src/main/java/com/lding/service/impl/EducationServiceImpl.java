package com.lding.service.impl;

import com.lding.dao.EducationDao;
import com.lding.domain.Education;
import com.lding.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {
    @Autowired
    private EducationDao dao;

    @Override
    public List<Education> getAll(Integer userId) {
        return this.dao.getAll(userId);
    }

    @Override
    public boolean save(Education education) {
        int count = 0;
        if (education.getId() == null) {
            count = this.dao.insert(education);
        } else {
            count = this.dao.update(education);
        }
        return count > 0;
    }

    @Override
    public boolean remove(List<Integer> ids) {
        int count = 0;
        if (ids != null) {
            count = this.dao.deleteLists(ids);
        }
        return count > 0;
    }
}
