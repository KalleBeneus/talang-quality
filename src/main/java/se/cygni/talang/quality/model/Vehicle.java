package se.cygni.talang.quality.model;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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
