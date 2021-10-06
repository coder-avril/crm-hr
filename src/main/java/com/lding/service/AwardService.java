package com.lding.service;

import com.lding.domain.Award;
import java.util.List;

public interface AwardService {
    List<Award> getAll(Integer userId);

    boolean save(Award award);

    boolean remove(List<Integer> ids);
}
