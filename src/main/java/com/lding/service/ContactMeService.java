package com.lding.service;

import com.lding.domain.ContactMe;
import com.lding.domain.ContactRequest;
import com.lding.domain.ContactResponse;

public interface ContactMeService {
    ContactResponse getAll(ContactRequest request);

    boolean save(ContactMe contactMe);
}
