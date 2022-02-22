package com.test.services;

import com.test.entity.Shipment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipmentService {
    Shipment save(Shipment shipment);

    void delete(int id);

    List<Shipment> findAll();

    Shipment findByName(String name);
}

