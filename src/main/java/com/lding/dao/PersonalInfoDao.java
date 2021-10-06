package com.lding.dao;

import com.lding.domain.PersonalInfo;
import org.apache.ibatis.annotations.Select;

public interface PersonalInfoDao {
    @Select("SELECT * FROM personal_info WHERE id = #{userId}")
    PersonalInfo get(Integer userId);

    @Select("SELECT * FROM personal_info WHERE email = #{email} AND password = #{password}")
    PersonalInfo getByUser(PersonalInfo user);

    boolean save(PersonalInfo user);
}
