package se.cygni.talang.quality.application;

import org.springframework.stereotype.Component;
import se.cygni.talang.quality.exceptions.NotAllowedException;
import se.cygni.talang.quality.exceptions.NotFoundException;
import se.cygni.talang.quality.model.Brand;
import se.cygni.talang.quality.model.Dealer;
import se.cygni.talang.quality.model.Vehicle;
import se.cygni.talang.quality.repo.Repository;

import java.util.List;
import java.util.Objects;

@Component
public class VehicleService {

    Repository repo;

    public VehicleService(Repository repo) {
        this.repo = repo;
    }

    public void assignOwner(String ownerOrgNumber, String vehicleRegistration) {
        Vehicle vehicle = getVehicleIfExists(vehicleRegistration);
        if (Objects.equals(vehicle.getOwner(), ownerOrgNumber)) {
            return;
        }
        if (vehicle.getOwner() != null) {
            throw new NotAllowedException("Only vehicles without current assignment can be updated");
        }

        List<Brand> approvedBrands = repo.getApprovedBrands(ownerOrgNumber);
        if (!approvedBrands.contains(vehicle.getBrand())) {
            throw new NotAllowedException("The brand " + vehicle.getBrand() + " is not applicable for dealer " + ownerOrgNumber);
        }

        Dealer newOwner = getDealerIfExists(ownerOrgNumber);
        vehicle.setOwner(ownerOrgNumber);
        if (vehicle.getBrand() == Brand.LAMBORGHINI) {
            newOwner.setPremiumCustomer(true);
            repo.saveDealer(newOwner);
        }

        repo.saveVehicle(vehicle);

    }

    private Dealer getDealerIfExists(String orgNumber) {
        Dealer dealer = repo.getDealerByOrgNumber(orgNumber);
        if(dealer == null) {
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
