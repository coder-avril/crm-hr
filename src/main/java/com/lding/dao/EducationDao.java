package com.lding.dao;

import com.lding.domain.Education;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EducationDao {
    List<Education> getAll(Integer userId);

    @Update("UPDATE education SET user_id = #{userId}, name = #{name}, begin_day = #{beginDay}, end_day = #{endDay}, type = #{type}, intro = #{intro} WHERE id = #{id}")
    int update(Education education);

    @Insert("INSERT INTO education(user_id, name, begin_day, end_day, type, intro) VALUES (#{userId}, #{name}, #{beginDay}, #{endDay}, #{type}, #{intro})")
    int insert(Education education);

    int deleteLists(List<Integer> ids);
}
