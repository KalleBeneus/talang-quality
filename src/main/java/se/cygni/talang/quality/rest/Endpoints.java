package se.cygni.talang.quality.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.cygni.talang.quality.application.VehicleService;

import java.util.Map;

@RestController
public class Endpoints {

    private final VehicleService vehicleService;

    public Endpoints(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping(path = "/assign",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void assignOwner(@RequestBody Map<String, String> input) {
        vehicleService.assignOwner(input.get("owner"), input.get("vehicle"));
    }

}
