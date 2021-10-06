package com.lding.dao;

import com.lding.domain.Award;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AwardDao {
    @Select("SELECT * FROM awards WHERE user_id = #{userId}")
    List<Award> getAll(Integer userId);

    @Insert("INSERT INTO awards(user_id, type, name, intro, picture) VALUES (#{userId}, #{type}, #{name}, #{intro}, #{picture})")
    int insert(Award award);

    @Update("UPDATE awards SET user_id = #{userId}, type = #{type}, name = #{name}, intro = #{intro}, picture = #{picture} WHERE id = #{id}")
    int update(Award award);

    int deleteLists(List<Integer> ids);
}
