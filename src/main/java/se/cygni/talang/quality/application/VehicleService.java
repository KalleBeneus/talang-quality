package se.cygni.talang.quality.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.cygni.talang.quality.exceptions.NotAllowedException;
import se.cygni.talang.quality.exceptions.NotFoundException;
import se.cygni.talang.quality.model.Brand;
import se.cygni.talang.quality.model.Customer;
import se.cygni.talang.quality.model.Vehicle;
import se.cygni.talang.quality.repo.Repository;

import java.util.List;
import java.util.Objects;

@Component
public class VehicleService {
    Logger log = LoggerFactory.getLogger(VehicleService.class);

    Repository repo;

    public VehicleService(Repository repo) {
        this.repo = repo;
    }

    public void assignOwner(String ownerOrgNumber, String vehicleRegistration) {
        Vehicle vehicle = getVehicleIfExists(vehicleRegistration);
        if (Objects.equals(vehicle.getOwner(), ownerOrgNumber)) {
            log.info("No update needed");
            return;
        }
        if (vehicle.getOwner() != null) {
            throw new NotAllowedException("Only vehicles without current assignment can be updated");
        }

        Customer newOwner = getCustomerIfExists(ownerOrgNumber);
        vehicle.setOwner(newOwner.getOrganisationNumber());

        List<Brand> premiumBrands = repo.getPremiumBrands();
        if (premiumBrands.contains(vehicle.getBrand())) {
            newOwner.setPremiumCustomer(true);
            repo.saveCustomer(newOwner);
        }

        repo.saveVehicle(vehicle);
        log.info("Vehicle updated successfully");
    }

    private Vehicle getVehicleIfExists(String registration) {
        Vehicle vehicle = repo.getVehicleByRegistration(registration);
        if (vehicle == null) {
            throw new NotFoundException("No vehicle found for registration " + registration);
        }
        return vehicle;
    }

    private Customer getCustomerIfExists(String orgNumber) {
        Customer customer = repo.getCustomerByOrgNumber(orgNumber);
        if (customer == null) {
            throw new NotFoundException("No customer found for org number " + orgNumber);
        }
        return customer;
    }
}
