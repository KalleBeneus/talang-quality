package se.cygni.talang.quality.domain;

import se.cygni.talang.quality.exceptions.NotAllowedException;

import java.util.List;
import java.util.Objects;

public class Vehicle {

    private final String registrationNumber;
    private String owner;
    private Brand brand;

    public Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistration() {
        return registrationNumber;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void assignOwner(Dealer newOwner, List<Brand> dealerApprovedBrands) {
        if (Objects.equals(owner, newOwner.getOrganisationNumber())) {
            return;
        }
        if (owner != null) {
            throw new NotAllowedException("Only vehicles without current assignment can be updated");
        }

        if (!dealerApprovedBrands.contains(brand)) {
            throw new NotAllowedException("The brand " + brand + " is not applicable for dealer " + newOwner.getOrganisationNumber());
        }

        owner = newOwner.getOrganisationNumber();
        if (brand == Brand.LAMBORGHINI) {
            newOwner.setPremiumCustomer(true);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(registrationNumber, vehicle.registrationNumber)
                && Objects.equals(owner, vehicle.owner)
                && brand == vehicle.brand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, owner, brand);
    }
}
