package com.test.repo;

import com.test.entity.Shipment;
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
public class ShipmentRepositoryCustomImpl implements ShipmentRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    public Shipment findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shipment> cq = cb.createQuery(Shipment.class);
        Root<Shipment> shipment = cq.from(Shipment.class);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(cb.equal(shipment.get("name"), name));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        List<Shipment> shipments = entityManager.createQuery(cq).getResultList();
        return (shipments != null && shipments.size() > 0) ? shipments.get(0) : null;
    }
}
