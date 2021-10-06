package com.lding.service.impl;

import com.lding.dao.SkillDao;
import com.lding.domain.Skill;
import com.lding.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillDao dao;

    @Override
    public List<Skill> getAll(Integer userId) {
        return this.dao.getAll(userId);
    }

    @Override
    public boolean save(Skill skill) {
        boolean result = false;
        if (skill.getId() != null) {
            result = this.dao.update(skill);
        } else {
            result = this.dao.insert(skill);
        }
        return result;
    }

    @Override
    public boolean remove(List<Integer> ids) {
        boolean result = false;
        if (ids != null) {
            result = this.dao.deleteLists(ids);
        }
        return result;
    }
}
