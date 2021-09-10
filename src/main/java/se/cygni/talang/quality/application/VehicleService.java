package se.cygni.talang.quality.application;

import org.springframework.stereotype.Component;
import se.cygni.talang.quality.exceptions.NotFoundException;
import se.cygni.talang.quality.domain.Brand;
import se.cygni.talang.quality.domain.Dealer;
import se.cygni.talang.quality.domain.Vehicle;
import se.cygni.talang.quality.repo.Repository;

import java.util.List;

@Component
public class VehicleService {

    Repository repo;

    public VehicleService(Repository repo) {
        this.repo = repo;
    }

    public void assignOwner(String ownerOrgNumber, String registration) {
        Vehicle vehicle = getVehicleIfExists(registration);
        Dealer newOwner = getDealerIfExists(ownerOrgNumber);
        List<Brand> approvedBrands = repo.getApprovedBrands(ownerOrgNumber);

        vehicle.assignOwner(newOwner, approvedBrands);

        repo.saveDealer(newOwner);
        repo.saveVehicle(vehicle);
    }

    private Dealer getDealerIfExists(String orgNumber) {
        Dealer dealer = repo.getDealerByOrgNumber(orgNumber);
        if (dealer == null) {
            throw new NotFoundException("No dealer found for org number " + orgNumber);
        }
        return dealer;
    }

    private Vehicle getVehicleIfExists(String registration) {
        Vehicle vehicle = repo.getVehicleByRegistration(registration);
        if (vehicle == null) {
            throw new NotFoundException("No vehicle found for registration " + registration);
        }
        return vehicle;
    }
}
