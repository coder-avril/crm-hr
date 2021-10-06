package com.lding.service;

import com.lding.domain.PersonalInfo;

public interface PersonalInfoService {
    PersonalInfo get(Integer userId);

    PersonalInfo getByUser(PersonalInfo user);

    boolean save(PersonalInfo user);
}
