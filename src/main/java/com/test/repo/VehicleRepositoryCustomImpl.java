package com.test.repo;

import com.test.entity.Vehicle;
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
public class VehicleRepositoryCustomImpl implements VehicleRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    public Vehicle findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
        Root<Vehicle> vehicle = cq.from(Vehicle.class);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(cb.equal(vehicle.get("name"), name));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        List<Vehicle> vehicles = entityManager.createQuery(cq).getResultList();
        return (vehicles != null && vehicles.size() > 0) ? vehicles.get(0) : null;
    }
}
