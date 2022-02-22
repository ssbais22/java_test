package com.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vehicle")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Vehicle {
    private static final long serialVersionUID = 30000L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private int weight;

    @ManyToOne(targetEntity = Tariff.class)
    @JoinColumn(name = "tariff_id", nullable = true)
    private Tariff tariff;

    @OneToMany(mappedBy = "vehicle", targetEntity = Shipment.class)
    @JsonIgnore
    private Set<Shipment> shipments;

    @Override
    public String toString() {
        return this.name;
    }
}
