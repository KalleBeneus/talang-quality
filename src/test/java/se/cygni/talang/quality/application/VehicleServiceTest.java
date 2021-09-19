package se.cygni.talang.quality.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.cygni.talang.quality.exceptions.NotAllowedException;
import se.cygni.talang.quality.exceptions.NotFoundException;
import se.cygni.talang.quality.model.Brand;
import se.cygni.talang.quality.model.Customer;
import se.cygni.talang.quality.model.Vehicle;
import se.cygni.talang.quality.repo.DatabaseRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    DatabaseRepository repositoryMock;

    VehicleService sut; // System Under Test

    @BeforeEach
    void setUp() {
        sut = new VehicleService(repositoryMock);
    }

    @Test
    void assignOwner_vehicleIsAlreadyAssignedToNewOwner_noEffect() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setOwner("123-456789");
        Mockito.when(repositoryMock.getVehicleByRegistration("ABC123")).thenReturn(expectedVehicle);
        // Act
        sut.assignOwner("123-456789", "ABC123");
        // Assert
        Mockito.verify(repositoryMock, Mockito.never()).saveVehicle(expectedVehicle);
    }

    @Test
    void assignOwner_vehicleIsAlreadyAssignedToDifferentOwner_notAllowed() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setOwner("other-owner");
        Mockito.when(repositoryMock.getVehicleByRegistration("ABC123")).thenReturn(expectedVehicle);
        // Act & Assert
        Assertions.assertThrows(NotAllowedException.class, () ->
                sut.assignOwner("123-456789", "ABC123"));
    }

    @Test
    void assignOwner_vehicleNotFound_throwError() {
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () ->
                sut.assignOwner("123-456789", "ABC123"));
    }

    @Test
    void assignOwner_customerNotFound_throwError() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        Mockito.when(repositoryMock.getVehicleByRegistration("ABC123")).thenReturn(expectedVehicle);
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () ->
                sut.assignOwner("123-456789", "ABC123"));
    }

    @Test
    void assignOwner_normalVehicleAssignedToNonPremiumCustomer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(Brand.VOLVO);
        List<Brand> premiumBrands = List.of(Brand.LAMBORGHINI);
        Customer expectedCustomer = new Customer("123-456789");
        expectedCustomer.setPremiumCustomer(false);

        Mockito.when(repositoryMock.getVehicleByRegistration("ABC123")).thenReturn(expectedVehicle);
        Mockito.when(repositoryMock.getPremiumBrands()).thenReturn(premiumBrands);
        Mockito.when(repositoryMock.getCustomerByOrgNumber("123-456789")).thenReturn(expectedCustomer);

        // Act
        sut.assignOwner("123-456789", "ABC123");

        // Assert
        expectedVehicle.setOwner("123-456789");
        Mockito.verify(repositoryMock).saveVehicle(expectedVehicle);
        Mockito.verify(repositoryMock, Mockito.never()).saveCustomer(Mockito.any());
    }

    @Test
    void assignOwner_premiumVehicleAssignedToNonPremiumCustomer_vehicleAndCustomerIsUpdated() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(Brand.LAMBORGHINI);
        List<Brand> premiumBrands = List.of(Brand.LAMBORGHINI);
        Customer expectedCustomer = new Customer("123-456789");
        expectedCustomer.setPremiumCustomer(false);

        Mockito.when(repositoryMock.getVehicleByRegistration("ABC123")).thenReturn(expectedVehicle);
        Mockito.when(repositoryMock.getPremiumBrands()).thenReturn(premiumBrands);
        Mockito.when(repositoryMock.getCustomerByOrgNumber("123-456789")).thenReturn(expectedCustomer);

        // Act
        sut.assignOwner("123-456789", "ABC123");

        // Assert
        expectedVehicle.setOwner("123-456789");
        Mockito.verify(repositoryMock).saveVehicle(expectedVehicle);
        expectedCustomer.setPremiumCustomer(true);
        Mockito.verify(repositoryMock).saveCustomer(expectedCustomer);
    }

    @Test
    void assignOwner_normalVehicleAssignedToPremiumCustomer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(Brand.VOLVO);
        List<Brand> premiumBrands = List.of(Brand.LAMBORGHINI);
        Customer expectedCustomer = new Customer("123-456789");
        expectedCustomer.setPremiumCustomer(true);

        Mockito.when(repositoryMock.getVehicleByRegistration("ABC123")).thenReturn(expectedVehicle);
        Mockito.when(repositoryMock.getPremiumBrands()).thenReturn(premiumBrands);
        Mockito.when(repositoryMock.getCustomerByOrgNumber("123-456789")).thenReturn(expectedCustomer);

        // Act
        sut.assignOwner("123-456789", "ABC123");

        // Assert
        expectedVehicle.setOwner("123-456789");
        Mockito.verify(repositoryMock).saveVehicle(expectedVehicle);
        Mockito.verify(repositoryMock, Mockito.never()).saveCustomer(Mockito.any());
    }

    @Test
    void assignOwner_premiumVehicleAssignedToPremiumCustomer_onlyVehicleIsUpdated() {
        // Arrange
        Vehicle expectedVehicle = new Vehicle("ABC123");
        expectedVehicle.setBrand(Brand.LAMBORGHINI);
        List<Brand> premiumBrands = List.of(Brand.LAMBORGHINI);
        Customer expectedCustomer = new Customer("123-456789");
        expectedCustomer.setPremiumCustomer(true);

        Mockito.when(repositoryMock.getVehicleByRegistration("ABC123")).thenReturn(expectedVehicle);
        Mockito.when(repositoryMock.getPremiumBrands()).thenReturn(premiumBrands);
        Mockito.when(repositoryMock.getCustomerByOrgNumber("123-456789")).thenReturn(expectedCustomer);

        // Act
        sut.assignOwner("123-456789", "ABC123");

        // Assert
        expectedVehicle.setOwner("123-456789");
        Mockito.verify(repositoryMock).saveVehicle(expectedVehicle);
        Mockito.verify(repositoryMock).saveCustomer(expectedCustomer);
    }
}