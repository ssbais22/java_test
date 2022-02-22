package com.test.repo;

import com.test.entity.Vehicle;

public interface VehicleRepositoryCustom {
    Vehicle findByName(String name);
}
