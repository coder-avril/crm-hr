package com.lding.service;

import com.lding.domain.Experience;
import java.util.List;

public interface ExperienceService {
    List<Experience> getAll(Integer userId);

    boolean save(Experience experience);

    boolean remove(List<Integer> ids);
}
