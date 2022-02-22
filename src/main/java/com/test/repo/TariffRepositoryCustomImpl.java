package com.test.repo;

import com.test.entity.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TariffRepositoryCustomImpl implements TariffRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    public Tariff findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tariff> cq = cb.createQuery(Tariff.class);
        Root<Tariff> tariff = cq.from(Tariff.class);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(cb.equal(tariff.get("name"), name));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        List<Tariff> tariffs = entityManager.createQuery(cq).getResultList();
        return (tariffs != null && tariffs.size() > 0) ? tariffs.get(0) : null;

    }
}
