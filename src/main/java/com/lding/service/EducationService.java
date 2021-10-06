package com.lding.service;

import com.lding.domain.Education;
import java.util.List;

public interface EducationService {
    List<Education> getAll(Integer userId);

    boolean save(Education education);

    boolean remove(List<Integer> ids);
}
