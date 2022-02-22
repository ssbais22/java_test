package com.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tariff")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Tariff {
    private static final long serialVersionUID = 20000L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "rate")
    private int rate;
    @Column(name = "discount")
    private int discount;

    @OneToMany(mappedBy = "tariff", targetEntity = Vehicle.class)
    @JsonIgnore
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "tariff", targetEntity = Shipment.class)
    private Set<Shipment> shipments;

    @Override
    public String toString() {
        return this.name;
    }

}
