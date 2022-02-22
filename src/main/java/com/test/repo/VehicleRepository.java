package com.test.repo;

import com.test.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>, VehicleRepositoryCustom {
}
