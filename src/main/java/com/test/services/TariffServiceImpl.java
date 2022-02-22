package com.test.services;

import com.test.entity.Tariff;
import com.test.repo.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {
    final TariffRepository tariffRepository;

    public TariffServiceImpl(@Autowired TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public Tariff save(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    public void delete(int id) {
        tariffRepository.deleteById(id);
    }

    public List<Tariff> findAll() {
        return tariffRepository.findAll();
    }

    public Tariff findByName(String name) {
        return tariffRepository.findByName(name);
    }
}
