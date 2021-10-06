package com.lding.dao;

import com.lding.domain.Skill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

public interface SkillDao {
    @Select("SELECT * FROM skill WHERE user_id = #{userId}")
    List<Skill> getAll(Integer userId);

    @Insert("INSERT INTO skill(user_id, name, level) VALUES (#{userId}, #{name}, #{level})")
    boolean insert(Skill skill);

    @Update("UPDATE skill SET user_id = #{userId}, name = #{name}, level = #{level} WHERE id = #{id}")
    boolean update(Skill skill);

    boolean deleteLists(List<Integer> ids);
}
