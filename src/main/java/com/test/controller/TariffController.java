package com.test.controller;

import com.test.entity.Tariff;
import com.test.entity.Vehicle;
import com.test.models.AddVehicleRequest;
import com.test.models.Response;
import com.test.services.TariffService;
import com.test.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TariffController {
    private final TariffService tariffService;
    private final VehicleService vehicleService;

    public TariffController(TariffService tariffService, VehicleService vehicleService) {
        this.tariffService = tariffService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/tariffs/")
    //Get all the tariffs
    public ResponseEntity<List<Tariff>> findAll() {
        List<Tariff> tariffs = tariffService.findAll();
        return new ResponseEntity<>(tariffs, HttpStatus.OK);
    }

    @GetMapping("/tariffs/{name}")
    // Get the tariff by name , exact match only.
    public ResponseEntity<List<Tariff>> findByName(@PathVariable String name) {
        Response response = new Response();
        Tariff tariff = tariffService.findByName(name);
        if (tariff == null) {
            response.setStatus("failure");
            response.setMessage("Tariff with this name does not exit");
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(tariff, HttpStatus.OK);
    }

    @PostMapping("/tariff/create/")
    //Create a tariff
    public ResponseEntity<Tariff> create(@RequestBody Tariff tariff) {
        Response response = new Response();
        if (isValidTariff(tariff)) {
            tariff = tariffService.save(tariff);
        } else {
            response.setStatus("failure");
            response.setMessage("Missing shipment name or invalid weight ");
            new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }

    @DeleteMapping("/tariff/delete/{id}")
    // Delete the tariff
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            tariffService.delete(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        } catch (Exception e) {
            tariffService.delete(id);
            return new ResponseEntity<>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tariff/add/")
    // Add vehicle to tariff. Call it separately for each vehicle the tariff needs to be added.
    public ResponseEntity<?> assignVehicle(@RequestBody AddVehicleRequest request) {
        Response response = new Response();
        response.setStatus("success");
        Tariff tariff = tariffService.findByName(request.getTariffName());
        Vehicle vehicle = vehicleService.findByName(request.getVehicleName());
        if (tariff == null || vehicle == null) {
            response.setStatus("failure");
            response.setMessage("Vehicle or Tariff does not exist");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        vehicle.setTariff(tariff);
        vehicleService.save(vehicle);
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }

    private boolean isValidTariff(Tariff tariff) {
        return tariff.getName() != null && tariff.getRate() > 0;
    }
}
