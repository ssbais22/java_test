package com.test.repo;

import com.test.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Integer>, TariffRepositoryCustom {
}
