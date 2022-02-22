package com.test.controller;

import com.test.entity.Shipment;
import com.test.entity.Tariff;
import com.test.entity.Vehicle;
import com.test.models.AssignTariffRequest;
import com.test.models.AssignVehicleRequest;
import com.test.models.AutoAssignRequest;
import com.test.models.Response;
import com.test.services.ShipmentService;
import com.test.services.TariffService;
import com.test.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final VehicleService vehicleService;
    private final TariffService tariffService;

    public ShipmentController(ShipmentService shipmentService, VehicleService vehicleService, TariffService tariffService) {
        this.shipmentService = shipmentService;
        this.vehicleService = vehicleService;
        this.tariffService = tariffService;
    }

    @GetMapping("/shipments/")
    // return all the shipments
    public ResponseEntity<List<Shipment>> findAll() {
        List<Shipment> shipments = shipmentService.findAll();
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @GetMapping("/shipments/top/")
    //Return the shipment with most cost if there is any.
    public ResponseEntity findCostlyShipment() {
        List<Shipment> shipments = shipmentService.findAll();
        Optional<Shipment> shipment = shipments.stream().sorted(Comparator.comparingInt(Shipment::getCost)).reduce((first, second) -> second).stream().findFirst();
        return new ResponseEntity(shipment, HttpStatus.OK);
    }

    @GetMapping("/shipments/{name}")
    //Find the shipment with matching name, exact match only.
    public ResponseEntity findByName(@PathVariable String name) {
        Response response = new Response();
        Shipment shipment = shipmentService.findByName(name);
        if (shipment == null) {
            response.setStatus("failure");
            response.setMessage("Shipment with this name does not exit");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(shipment, HttpStatus.OK);
    }

    @PostMapping("/shipment/create/")
    // Create shipment with basic validation.
    public ResponseEntity<?> createShipment(@RequestBody Shipment shipment) {
        Response response = new Response();
        if (isValidShipment(shipment)) {
            response.setStatus("failure");
            response.setMessage("Missing shipment name or invalid weight ");
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Shipment currentShipment = shipmentService.findByName(shipment.getName());
        //Check for existing shipment if there is one already .
        if (shipment.getId() == null && currentShipment != null) {
            response.setStatus("failure");
            response.setMessage("Shipment with this name already exist");
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Create or update the shipment.
        shipment = shipmentService.save(shipment);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }

    @DeleteMapping("/shipment/delete/{id}")
    //Delete the shipment.
    public ResponseEntity<String> deleteShipment(@PathVariable int id) {
        try {
            shipmentService.delete(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("error while delete the shipment.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/shipment/assign/vehicle")
    //Assign a vehicle to a shipment if it passes basic validations.
    public ResponseEntity<Response> assignVehicle(@RequestBody AssignVehicleRequest request) {
        Response response = new Response();
        response.setStatus("success");
        Shipment shipment = shipmentService.findByName(request.getShipmentName());
        Vehicle vehicle = vehicleService.findByName(request.getVehicleName());
        if (shipment == null) {
            response.setStatus("failure");
            response.setMessage("Shipment does not exist");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (vehicle == null) {
            response.setStatus("failure");
            response.setMessage("Vehicle does not exist");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (vehicle.getWeight() >= shipment.getWeight()) {
            shipment.setVehicle(vehicle);
            shipmentService.save(shipment);
        } else {
            response.setStatus("failure");
            response.setMessage("Vehicle does not have enough capacity");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/shipment/assign/auto/")
    //Assign a vehicle and tariff to a shipment with some basic validations. Also ca
    public ResponseEntity<Response> autoAssign(@RequestBody AutoAssignRequest request) {
        Response response = new Response();
        response.setStatus("success");
        Shipment shipment = shipmentService.findByName(request.getShipmentName());
        //Get all the tariffs where ataleast
        Optional<Tariff> tariff = tariffService.findAll().stream().filter(tariff1 ->
                tariff1.getVehicles().size() > 0).min(Comparator.comparingInt(o -> (shipment.getWeight() * o.getRate() - o.getDiscount())));
        if (tariff.get() == null) {
            response.setStatus("failure");
            response.setMessage("Not suitable tariff found");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //Assign vehicle and tariff.
        shipment.setVehicle(tariff.get().getVehicles().stream().findFirst().get());
        shipment.setTariff(tariff.get());
        //Calculate the cost
        shipment.setCost(shipment.getWeight() * tariff.get().getRate() - tariff.get().getDiscount());
        shipmentService.save(shipment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/shipment/assign/tariff")
    // assign tariff to a shipment if tariff is applicable to the vehicle assigned to the shipment.
    public ResponseEntity<Response> assignVehicle(@RequestBody AssignTariffRequest request) {
        Response response = new Response();
        response.setStatus("success");
        if (request.getShipmentName() == null || request.getTariffName() == null) {
            response.setStatus("failure");
            response.setMessage("Shipment and Tariff does not exist");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Shipment shipment = shipmentService.findByName(request.getShipmentName());
        Tariff tariff = tariffService.findByName(request.getTariffName());
        if (shipment == null) {
            response.setStatus("failure");
            response.setMessage("Shipment does not exist");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (tariff == null) {
            response.setStatus("failure");
            response.setMessage("Tariff does not exist");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (shipment.getVehicle() == null) {
            response.setStatus("failure");
            response.setMessage("Vehicle is not assigned to shipment yet");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Set<Vehicle> vehicles = tariff.getVehicles();
        //Check if vehicle on the shipment has the tariff associated with it .
        boolean isTariffApplicableToVehicle = tariff.getVehicles().size() > 0 && vehicles.stream().anyMatch(vehicle ->
                vehicle.getId().intValue() == shipment.getVehicle().getId().intValue());
        if (isTariffApplicableToVehicle) {
            shipment.setTariff(tariff);
            //Calculate the cost
            shipment.setCost(tariff.getRate() * shipment.getWeight() - tariff.getDiscount());
        } else {
            response.setStatus("failure");
            response.setMessage("Tariff is not applicable to assigned vehicle");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        shipmentService.save(shipment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean isValidShipment(Shipment shipment) {
        return (shipment.getWeight() <= 0 || shipment.getName() == null);
    }
}
