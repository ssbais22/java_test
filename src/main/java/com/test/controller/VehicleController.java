package com.test.controller;

import com.test.entity.Vehicle;
import com.test.models.Response;
import com.test.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles/")
    // Get all the vehicles
    public ResponseEntity<List<Vehicle>> findAll() {
        List<Vehicle> vehicles = vehicleService.findAll();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/vehicles/{name}")
    // Get all the vehicles
    public ResponseEntity<List<Vehicle>> findByName(@PathVariable String name) {
        Response response = new Response();
        Vehicle vehicle = vehicleService.findByName(name);
        if (vehicle == null) {
            response.setStatus("failure");
            response.setMessage("Vehicle with this name does not exit");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(vehicle, HttpStatus.OK);
    }

    @PostMapping("/vehicle/create/")
    // Create a vehicle
    public ResponseEntity<?> create(@RequestBody Vehicle vehicle) {
        Response response = new Response();
        if (isValidVehicle(vehicle)) {
            vehicle = vehicleService.save(vehicle);
        } else {
            response.setStatus("failure");
            response.setMessage("Missing shipment name or invalid weight ");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @DeleteMapping("/vehicle/delete/{id}")
    // delete the vehicle
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            vehicleService.delete(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("error while delete the vehicle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidVehicle(Vehicle vehicle) {
        return !(vehicle.getWeight() <= 0 || vehicle.getName() == null);
    }
}
