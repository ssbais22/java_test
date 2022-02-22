package com.test.scm;

import com.test.entity.Shipment;
import com.test.entity.Tariff;
import com.test.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ShipmentApplicationTests {
    final String baseURL = "http://localhost:9085/";

    private Shipment buildShipment(String name, int weight, int id) {
        Shipment shipment = new Shipment();
        shipment.setName(name);
        shipment.setWeight(weight);
        shipment.setId(id);
        return shipment;
    }

    private Vehicle buildVehicle(String name, int weight, int id) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(name);
        vehicle.setWeight(weight);
        vehicle.setId(id);
        return vehicle;
    }

    private Tariff buildTariff(String name, int rate, int id) {
        Tariff tariff = new Tariff();
        tariff.setName(name);
        tariff.setRate(rate);
        tariff.setId(id);
        return tariff;
    }

    @Test
    void testShipment() {
        Shipment shipment = buildShipment("SH_01", 200, 22);
        HttpEntity<Shipment> request = new HttpEntity<>(shipment);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Shipment> postForEntity = template.postForEntity(baseURL + "shipment/create/", request, Shipment.class);
        Assert.isTrue(postForEntity.getBody().getId() > 0, "Tariff test ");
        Assert.isTrue(postForEntity.getStatusCodeValue() == HttpStatus.OK.value(), "Status match");
        template.delete(baseURL + "shipment/delete/" + postForEntity.getBody().getId());
    }

    @Test
    void testVehicle() {
        Vehicle vehicle = buildVehicle("VH_01", 800, 22);
        HttpEntity<Vehicle> request = new HttpEntity<>(vehicle);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Vehicle> postForEntity = template.postForEntity(baseURL + "vehicle/create/", request, Vehicle.class);
        Assert.isTrue(postForEntity.getBody().getId() > 0, "Tariff test ");
        Assert.isTrue(postForEntity.getStatusCodeValue() == HttpStatus.OK.value(), "Status match");
        template.delete(baseURL + "vehicle/delete/" + postForEntity.getBody().getId());
    }

    @Test
    void testTariff() {
        Tariff tariff = buildTariff("TF_01", 20, 22);
        HttpEntity<Tariff> request = new HttpEntity<>(tariff);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Tariff> postForEntity = template.postForEntity(baseURL + "tariff/create/", request, Tariff.class);
        Assert.isTrue(postForEntity.getBody().getId() > 0, "Tariff test ");
        Assert.isTrue(postForEntity.getStatusCodeValue() == HttpStatus.OK.value(), "Status match");
        template.delete(baseURL + "tariff/delete/" + postForEntity.getBody().getId());
    }

    @Test
    void testSearchShipment() {
        Shipment shipment = buildShipment("SH_01", 200, 22);
        HttpEntity<Shipment> request = new HttpEntity<>(shipment);
        RestTemplate template = new RestTemplate();
        ResponseEntity<Shipment> postForEntity = template.postForEntity(baseURL + "shipment/create/", request, Shipment.class);
        Assert.isTrue(postForEntity.getBody().getId() > 0, "Tariff test ");
        Assert.isTrue(postForEntity.getStatusCodeValue() == HttpStatus.OK.value(), "Status match");
        postForEntity = template.postForEntity(baseURL + "shipment/create/", request, Shipment.class);
        template.delete(baseURL + "shipment/delete/" + postForEntity.getBody().getId());
    }

}
