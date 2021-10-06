package com.lding.service;

import com.lding.domain.Website;
import java.util.List;

public interface WebsiteService {
    Website getAll(Integer userId);

    boolean save(Website website);

    boolean remove(Integer id);
}
