package com.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "shipment")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Shipment {
    private static final long serialVersionUID = 10000L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private int weight;
    @Column(name = "cost")
    private int cost;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = true)
    @JsonIgnore
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "tariff_id", nullable = true)
    @JsonIgnore
    private Tariff tariff;

    @Override
    public String toString() {
        return this.name;
    }

}
