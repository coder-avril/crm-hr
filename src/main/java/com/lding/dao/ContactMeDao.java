package com.lding.dao;

import com.lding.domain.ContactMe;
import com.lding.domain.ContactRequest;
import org.apache.ibatis.annotations.Insert;
import java.util.List;

public interface ContactMeDao {
    List<ContactMe> getAll(ContactRequest request);

    @Insert("INSERT INTO contact_me(name, user_id, email, send_date, subject, message) VALUES (#{name} ,#{userId}, #{email}, #{sendDate}, #{subject}, #{message})")
    int insert(ContactMe contactMe);

}
