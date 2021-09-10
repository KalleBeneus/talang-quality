package se.cygni.talang.quality.repo;

import se.cygni.talang.quality.model.Brand;
import se.cygni.talang.quality.model.Dealer;
import se.cygni.talang.quality.model.Vehicle;

import java.util.List;

public interface Repository {

    Vehicle getVehicleByRegistration(String registration);

    Dealer getDealerByOrgNumber(String orgNumber);

    List<Brand> getPremiumBrands();

    void saveVehicle(Vehicle vehicle);

    void saveDealer(Dealer dealer);
}
