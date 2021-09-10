package se.cygni.talang.quality.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import se.cygni.talang.quality.exceptions.NotAllowedException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    public static final Brand NON_ALLOWED_BRAND = Brand.TOYOTA;
    public static final Brand ALLOWED_PREMIUM_BRAND = Brand.LAMBORGHINI;
    public static final Brand ALLOWED_NORMAL_BRAND = Brand.VOLVO;
    public static final List<Brand> defaultApprovedBrands = List.of(Brand.VOLVO, Brand.LAMBORGHINI);

    @Test
    void assignOwner_vehicleIsAlreadyAssignedToNewOwner_noEffect() {
        // Arrange
        Vehicle sut = new Vehicle("ABC123");
        sut.setOwner("123-456789");
        Dealer dealer = new Dealer("123-456789");
        // Act
        sut.assignOwner(dealer, defaultApprovedBrands);
        // Assert
        assertThat(sut.getOwner()).isEqualTo("123-456789");
    }

    @Test
    void assignOwner_vehicleIsAlreadyAssignedToDifferentOwner_notAllowed() {
        // Arrange
        Vehicle sut = new Vehicle("ABC123");
        sut.setOwner("other-owner");
        Dealer dealer = new Dealer("123-456789");
        // Act & Assert
        assertThrows(NotAllowedException.class, () ->
                sut.assignOwner(dealer, defaultApprovedBrands));
    }

    @Test
    void assignOwner_vehicleBrandIsNotApproved_notAllowed() {
        // Arrange
        Vehicle sut = new Vehicle("ABC123");
        sut.setBrand(NON_ALLOWED_BRAND);
        Dealer dealer = new Dealer("123-456789");
        // Act & Assert
        assertThrows(NotAllowedException.class, () ->
                sut.assignOwner(dealer, defaultApprovedBrands));
    }

    @Test
    void assignOwner_normalVehicleAssignedToNonPremiumDealer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle sut = new Vehicle("ABC123");
        sut.setBrand(ALLOWED_NORMAL_BRAND);
        Dealer dealer = new Dealer("123-456789");

        // Act
        sut.assignOwner(dealer, defaultApprovedBrands);

        // Assert
        assertThat(sut.getOwner()).isEqualTo("123-456789");
        assertThat(dealer.isPremiumCustomer()).isFalse();
    }

    @Test
    void assignOwner_premiumVehicleAssignedToNonPremiumDealer_vehicleAndDealerIsUpdated() {
        // Arrange
        Vehicle sut = new Vehicle("ABC123");
        sut.setBrand(ALLOWED_PREMIUM_BRAND);
        Dealer dealer = new Dealer("123-456789");
        dealer.setPremiumCustomer(false);

        // Act
        sut.assignOwner(dealer, defaultApprovedBrands);

        // Assert
        assertThat(sut.getOwner()).isEqualTo("123-456789");
        assertThat(dealer.isPremiumCustomer()).isTrue();
    }

    @Test
    void assignOwner_normalVehicleAssignedToPremiumDealer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle sut = new Vehicle("ABC123");
        sut.setBrand(ALLOWED_NORMAL_BRAND);
        Dealer dealer = new Dealer("123-456789");
        dealer.setPremiumCustomer(true);

        // Act
        sut.assignOwner(dealer, defaultApprovedBrands);

        // Assert
        assertThat(sut.getOwner()).isEqualTo("123-456789");
        assertThat(dealer.isPremiumCustomer()).isTrue();
    }

    @Test
    void assignOwner_premiumVehicleAssignedToPremiumDealer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle sut = new Vehicle("ABC123");
        sut.setBrand(ALLOWED_PREMIUM_BRAND);
        Dealer dealer = new Dealer("123-456789");
        dealer.setPremiumCustomer(true);

        // Act
        sut.assignOwner(dealer, defaultApprovedBrands);

        // Assert
        assertThat(sut.getOwner()).isEqualTo("123-456789");
        assertThat(dealer.isPremiumCustomer()).isTrue();
    }

    // More compact alternative to last 4 tests

    @ParameterizedTest
    @CsvSource({
            "LAMBORGHINI, true, true",
            "VOLVO, true, true",
            "LAMBORGHINI, false, true",
            "VOLVO, false, false"
    })
    void assignOwner_premiumCustomerUpdated(Brand vehicleBrand, boolean isPremiumNow, boolean expectedPremiumValue) {
        // Arrange
        Vehicle sut = new Vehicle("ABC123");
        sut.setBrand(vehicleBrand);
        Dealer dealer = new Dealer("123-456789");
        dealer.setPremiumCustomer(isPremiumNow);

        // Act
        sut.assignOwner(dealer, defaultApprovedBrands);

        // Assert
        assertThat(sut.getOwner()).isEqualTo("123-456789");
        assertThat(dealer.isPremiumCustomer()).isEqualTo(expectedPremiumValue);
    }
}