package com.test.repo;

import com.test.entity.Shipment;

public interface ShipmentRepositoryCustom {
    Shipment findByName(String name);
}
