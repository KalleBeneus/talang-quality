package se.cygni.talang.quality.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.cygni.talang.quality.exceptions.NotAllowedException;
import se.cygni.talang.quality.domain.Brand;
import se.cygni.talang.quality.domain.Dealer;
import se.cygni.talang.quality.domain.Vehicle;
import se.cygni.talang.quality.repo.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VehicleServiceTestWithFake {

    public static final Brand NON_ALLOWED_BRAND = Brand.TOYOTA;
    public static final Brand ALLOWED_PREMIUM_BRAND = Brand.LAMBORGHINI;
    public static final Brand ALLOWED_NORMAL_BRAND = Brand.VOLVO;

    VehicleService sut; // System Under Test
    private RepositoryFake repo;

    @BeforeEach
    void setUp() {
        repo = new RepositoryFake();
        sut = new VehicleService(repo);
    }

    @Test
    void assignOwner_vehicleIsAlreadyAssignedToNewOwner_noEffect() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setOwner("123-456789");
        repo.saveVehicle(expectedVehicle);
        Dealer expectedDealer = new Dealer("123-456789");
        repo.saveDealer(expectedDealer);
        // Act
        sut.assignOwner("123-456789", "ABC123");
        // Assert
        assertThat(repo.getVehicleByRegistration("ABC123")).isEqualTo(expectedVehicle);
    }

    @Test
    void assignOwner_vehicleIsAlreadyAssignedToDifferentOwner_notAllowed() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setOwner("other-owner");
        repo.saveVehicle(expectedVehicle);
        Dealer expectedDealer = new Dealer("123-456789");
        repo.saveDealer(expectedDealer);
        // Act & Assert
        assertThrows(NotAllowedException.class, () ->
                sut.assignOwner("123-456789", "ABC123"));
    }

    @Test
    void assignOwner_vehicleBrandIsNotApproved_notAllowed() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(NON_ALLOWED_BRAND);
        repo.saveVehicle(expectedVehicle);
        Dealer expectedDealer = new Dealer("123-456789");
        repo.saveDealer(expectedDealer);
        // Act & Assert
        assertThrows(NotAllowedException.class, () ->
                sut.assignOwner("123-456789", "ABC123"));
    }

    @Test
    void assignOwner_normalVehicleAssignedToNonPremiumDealer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(ALLOWED_NORMAL_BRAND);
        Dealer expectedDealer = new Dealer("123-456789");

        repo.saveVehicle(expectedVehicle);
        repo.saveDealer(expectedDealer);

        // Act
        sut.assignOwner("123-456789", "ABC123");

        // Assert
        expectedVehicle.setOwner("123-456789");
        assertThat(repo.getVehicleByRegistration("ABC123")).isEqualTo(expectedVehicle);
        assertThat(repo.getDealerByOrgNumber("123-456789")).isEqualTo(expectedDealer);
    }

    @Test
    void assignOwner_premiumVehicleAssignedToNonPremiumDealer_vehicleAndDealerIsUpdated() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(ALLOWED_PREMIUM_BRAND);
        Dealer expectedDealer = new Dealer("123-456789");
        expectedDealer.setPremiumCustomer(false);

        repo.saveVehicle(expectedVehicle);
        repo.saveDealer(expectedDealer);

        // Act
        sut.assignOwner("123-456789", "ABC123");

        // Assert
        expectedVehicle.setOwner("123-456789");
        expectedDealer.setPremiumCustomer(true);
        assertThat(repo.getVehicleByRegistration("ABC123")).isEqualTo(expectedVehicle);
        assertThat(repo.getDealerByOrgNumber("123-456789")).isEqualTo(expectedDealer);
    }

    @Test
    void assignOwner_normalVehicleAssignedToPremiumDealer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(ALLOWED_NORMAL_BRAND);
        Dealer expectedDealer = new Dealer("123-456789");
        expectedDealer.setPremiumCustomer(true);

        repo.saveVehicle(expectedVehicle);
        repo.saveDealer(expectedDealer);

        // Act
        sut.assignOwner("123-456789", "ABC123");

        // Assert
        expectedVehicle.setOwner("123-456789");
        assertThat(repo.getVehicleByRegistration("ABC123")).isEqualTo(expectedVehicle);
        assertThat(repo.getDealerByOrgNumber("123-456789")).isEqualTo(expectedDealer);
    }

    @Test
    void assignOwner_premiumVehicleAssignedToPremiumDealer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(ALLOWED_PREMIUM_BRAND);
        Dealer expectedDealer = new Dealer("123-456789");
        expectedDealer.setPremiumCustomer(true);

        repo.saveVehicle(expectedVehicle);
        repo.saveDealer(expectedDealer);

        // Act
        sut.assignOwner("123-456789", "ABC123");

        // Assert
        expectedVehicle.setOwner("123-456789");
        assertThat(repo.getVehicleByRegistration("ABC123")).isEqualTo(expectedVehicle);
        assertThat(repo.getDealerByOrgNumber("123-456789")).isEqualTo(expectedDealer);
    }

    public class RepositoryFake implements Repository {
        Map<String, Vehicle> vehicleMap = new HashMap<>();
        Map<String, Dealer> dealerMap = new HashMap<>();

        @Override
        public Vehicle getVehicleByRegistration(String registration) {
            return vehicleMap.get(registration);
        }

        @Override
        public Dealer getDealerByOrgNumber(String orgNumber) {
            return dealerMap.get(orgNumber);
        }

        @Override
        public List<Brand> getApprovedBrands(String ownerOrgNumber) {
            return List.of(ALLOWED_NORMAL_BRAND, ALLOWED_PREMIUM_BRAND);
        }

        @Override
        public void saveVehicle(Vehicle vehicle) {
            vehicleMap.put(vehicle.getRegistration(), vehicle);
        }

        @Override
        public void saveDealer(Dealer dealer) {
            dealerMap.put(dealer.getOrganisationNumber(), dealer);
        }
    }
}