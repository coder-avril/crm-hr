package com.lding.service.impl;

import com.lding.dao.PersonalInfoDao;
import com.lding.domain.PersonalInfo;
import com.lding.service.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {
    @Autowired
    private PersonalInfoDao dao;

    @Override
    public PersonalInfo get(Integer userId) {
        return this.dao.get(userId);
    }

    @Override
    public PersonalInfo getByUser(PersonalInfo user) {
        return this.dao.getByUser(user);
    }

    @Override
    public boolean save(PersonalInfo user) {
        return this.dao.save(user);
    }
}
