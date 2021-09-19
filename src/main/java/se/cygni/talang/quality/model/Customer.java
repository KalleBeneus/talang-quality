package se.cygni.talang.quality.model;

import java.util.Objects;

public class Customer {

    private final String organisationNumber;
    private boolean premiumCustomer;

    public Customer(String organisationNumber) {
        this.organisationNumber = organisationNumber;
    }

    public String getOrganisationNumber() {
        return organisationNumber;
    }

    public boolean isPremiumCustomer() {
        return premiumCustomer;
    }

    public void setPremiumCustomer(boolean premiumCustomer) {
        this.premiumCustomer = premiumCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return premiumCustomer == customer.premiumCustomer && Objects.equals(organisationNumber, customer.organisationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisationNumber, premiumCustomer);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "organisationNumber='" + organisationNumber + '\'' +
                ", premiumCustomer=" + premiumCustomer +
                '}';
    }
}
