package se.cygni.talang.quality.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.cygni.talang.quality.application.VehicleService;
import se.cygni.talang.quality.exceptions.NotAllowedException;
import se.cygni.talang.quality.exceptions.NotFoundException;

@RestController
public class Endpoints {

    private VehicleService vehicleService;

    public Endpoints(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(path = "/assign")
    public void assignOwner() {
        try {

            vehicleService.assignOwner("123-456789", "ABC123");

        } catch (NotFoundException | NotAllowedException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
