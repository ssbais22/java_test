package com.test.repo;

import com.test.entity.Tariff;

public interface TariffRepositoryCustom {
    Tariff findByName(String name);
}
