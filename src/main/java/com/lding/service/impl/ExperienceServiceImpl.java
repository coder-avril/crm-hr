package com.lding.service.impl;

import com.lding.dao.ExperienceDao;
import com.lding.domain.Experience;
import com.lding.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    @Autowired
    private ExperienceDao dao;

    @Override
    public List<Experience> getAll(Integer userId) {
        return this.dao.getAll(userId);
    }

    @Override
    public boolean save(Experience experience) {
        int count = 0;
        if (experience.getId() == null) {
            count = this.dao.insert(experience);
        } else {
            count = this.dao.update(experience);
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
