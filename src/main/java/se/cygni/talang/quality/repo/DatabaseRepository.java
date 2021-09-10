package se.cygni.talang.quality.repo;

import se.cygni.talang.quality.domain.Brand;
import se.cygni.talang.quality.domain.Dealer;
import se.cygni.talang.quality.domain.Vehicle;

import java.util.List;

@org.springframework.stereotype.Repository
public class DatabaseRepository implements Repository {

    @Override
    public Vehicle getVehicleByRegistration(String registration) {
        return null;
    }

    @Override
    public Dealer getDealerByOrgNumber(String orgNumber) {
        return null;
    }

    @Override
    public List<Brand> getApprovedBrands(String ownerOrgNumber) {
        return null;
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {

    }

    @Override
    public void saveDealer(Dealer dealer) {

    }
}
