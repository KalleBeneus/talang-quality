package se.cygni.talang.quality.domain;

import java.util.Objects;

public class Dealer {

    private final String organisationNumber;
    private int ownedNumberOfVehicles;
    private boolean premiumCustomer;

    public Dealer(String organisationNumber) {
        this.organisationNumber = organisationNumber;
    }

    public String getOrganisationNumber() {
        return organisationNumber;
    }

    public int getOwnedNumberOfVehicles() {
        return ownedNumberOfVehicles;
    }

    public boolean isPremiumCustomer() {
        return premiumCustomer;
    }

    public void setPremiumCustomer(boolean premiumCustomer) {
        this.premiumCustomer = premiumCustomer;
    }

    public void changeNumberOfVehiclesBy(int delta) {
        ownedNumberOfVehicles += delta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dealer dealer = (Dealer) o;
        return ownedNumberOfVehicles == dealer.ownedNumberOfVehicles
                && premiumCustomer == dealer.premiumCustomer
                && Objects.equals(organisationNumber, dealer.organisationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisationNumber, ownedNumberOfVehicles, premiumCustomer);
    }
}
