package se.cygni.talang.quality.repo;

import se.cygni.talang.quality.domain.Brand;
import se.cygni.talang.quality.domain.Dealer;
import se.cygni.talang.quality.domain.Vehicle;

import java.util.List;

public interface Repository {

    Vehicle getVehicleByRegistration(String registration);

    Dealer getDealerByOrgNumber(String orgNumber);

    List<Brand> getApprovedBrands(String ownerOrgNumber);

    void saveVehicle(Vehicle vehicle);

    void saveDealer(Dealer dealer);
}
