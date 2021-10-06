package com.lding.service;

import com.lding.domain.Skill;
import java.util.List;

public interface SkillService {
    List<Skill> getAll(Integer userId);

    boolean save(Skill skill);

    boolean remove(List<Integer> ids);
}
