package com.test.services;

import com.test.entity.Vehicle;
import com.test.repo.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(@Autowired VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void delete(int id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle findByName(String name) {
        return vehicleRepository.findByName(name);
    }
}
