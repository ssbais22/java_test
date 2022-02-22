package com.test.services;

import com.test.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {
    Vehicle save(Vehicle vehicle);

    void delete(int id);

    List<Vehicle> findAll();

    Vehicle findByName(String name);
}

