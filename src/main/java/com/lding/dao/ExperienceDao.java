package com.lding.dao;

import com.lding.domain.Experience;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ExperienceDao {
    @Select("SELECT * FROM experience WHERE user_id = #{userId}")
    List<Experience> getAll(Integer userId);

    @Insert("INSERT INTO experience(user_id, begin_day, end_day, position_name, company_name, intro) VALUES (#{userId}, #{beginDay}, #{endDay}, #{positionName}, #{companyName}, #{intro})")
    int insert(Experience experience);

    @Update("UPDATE experience SET user_id = #{userId}, begin_day = #{beginDay}, end_day = #{endDay}, position_name = #{positionName}, company_name = #{companyName}, intro = #{intro} WHERE id = #{id}")
    int update(Experience experience);

    int deleteLists(List<Integer> ids);
}
