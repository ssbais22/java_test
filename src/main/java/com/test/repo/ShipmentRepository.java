package com.test.repo;

import com.test.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer>, ShipmentRepositoryCustom {

}
