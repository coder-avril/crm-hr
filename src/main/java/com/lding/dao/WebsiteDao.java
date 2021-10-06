package com.lding.dao;

import com.lding.domain.Website;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface WebsiteDao {
    @Select("SELECT * FROM web_site WHERE user_id = #{userId}")
    Website getAll(Integer userId);

    @Insert("INSERT INTO web_site(user_id, link) VALUES (#{userId}, #{link})")
    int insert(Website website);

    @Update("UPDATE web_site SET user_id = #{userId}, link = #{link} WHERE id = #{id}")
    int update(Website website);

    @Delete("DELETE FROM web_site WHERE id = #{id}")
    int delete(Integer id);
}
