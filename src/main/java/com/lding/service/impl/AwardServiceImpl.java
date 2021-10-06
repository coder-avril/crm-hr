package com.lding.service.impl;

import com.lding.dao.AwardDao;
import com.lding.domain.Award;
import com.lding.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AwardServiceImpl implements AwardService {
    @Autowired
    private AwardDao dao;

    @Override
    public List<Award> getAll(Integer userId) {
        return this.dao.getAll(userId);
    }

    @Override
    public boolean save(Award award) {
        int count = 0;
        if (award.getId() == null) {
            count = this.dao.insert(award);
        } else {
            count = this.dao.update(award);
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
