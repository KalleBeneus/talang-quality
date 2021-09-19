package se.cygni.talang.quality.repo;

import se.cygni.talang.quality.model.Brand;
import se.cygni.talang.quality.model.Customer;
import se.cygni.talang.quality.model.Vehicle;

import java.util.List;

public interface Repository {

    Vehicle getVehicleByRegistration(String registration);

    Customer getCustomerByOrgNumber(String orgNumber);

    List<Brand> getPremiumBrands();

    void saveVehicle(Vehicle vehicle);

    void saveCustomer(Customer customer);
}
