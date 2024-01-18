package com.happyfxmas.warehousemicroservice.store.repository;

import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
public class SupplierRepositoryTest {

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    public void supplierRepositorySave_givenSupplier_thenReturnSavedObject() {
        //given
        String companyName = "test";
        String phone = "test-phone";
        String email = "test-email@test.com";
        Supplier supplier = Supplier.builder()
                .companyName(companyName)
                .phone(phone)
                .email(email)
                .build();

        // when
        Supplier actual = supplierRepository.save(supplier);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getCompanyName()).isEqualTo(companyName);
        assertThat(actual.getCreatedAt()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNotNull();
    }

    @Test
    public void supplierRepositorySave_givenTwoSameSuppliers_thenThrowRuntime() {
        //given
        String companyName = "test";
        String phone = "test-phone";
        String email = "test-email@test.com";
        Supplier supplier = Supplier.builder()
                .companyName(companyName)
                .phone(phone)
                .email(email)
                .build();
        Supplier supplier1 = Supplier.builder()
                .companyName(companyName)
                .phone(phone)
                .email(email)
                .build();

        // when
        Exception actual = assertThrows(
                Exception.class,
                () -> {
                    supplierRepository.saveAndFlush(supplier);
                    supplierRepository.saveAndFlush(supplier1);
                },
                "Expected to throw RuntimeException but it didn't"
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void supplierRepositoryFindById_givenSupplierId_thenReturnSupplier() {
        // given
        String companyName = "test";
        String phone = "test-phone";
        String email = "test-email@test.com";
        Supplier supplier = Supplier.builder()
                .companyName(companyName)
                .phone(phone)
                .email(email)
                .build();
        supplier = supplierRepository.saveAndFlush(supplier);

        // when
        Optional<Supplier> actual = supplierRepository.findById(supplier.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getId()).isEqualByComparingTo(supplier.getId());
        assertThat(actual.get().getCompanyName()).isEqualTo(supplier.getCompanyName());
    }

    @Test
    public void supplierRepositoryDeleteById_givenSupplierId_thenDeleteSupplier() {
        // given
        String companyName = "test";
        String phone = "test-phone";
        String email = "test-email@test.com";
        Supplier supplier = Supplier.builder()
                .companyName(companyName)
                .phone(phone)
                .email(email)
                .build();
        supplier = supplierRepository.saveAndFlush(supplier);

        // when
        supplierRepository.delete(supplier);
        supplierRepository.flush();
        Optional<Supplier> actual = supplierRepository.findById(supplier.getId());

        // then
        assertThat(actual.isEmpty()).isTrue();
    }
}
