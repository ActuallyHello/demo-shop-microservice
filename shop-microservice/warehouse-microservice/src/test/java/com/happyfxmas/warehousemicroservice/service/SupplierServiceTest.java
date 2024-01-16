package com.happyfxmas.warehousemicroservice.service;

import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierRequestDTO;
import com.happyfxmas.warehousemicroservice.service.impl.SupplierServiceImpl;
import com.happyfxmas.warehousemicroservice.store.model.Supplier;
import com.happyfxmas.warehousemicroservice.store.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    public void supplierServiceSave_givenSupplierRequestDTO_thenSaveSupplier() {
        // given
        String companyName = "test";
        String phone = "test-phone";
        String email = "test-email@test.com";
        SupplierRequestDTO supplierRequestDTO = new SupplierRequestDTO(
                companyName,
                phone,
                email
        );
        Supplier supplier = Supplier.builder()
                .id(UUID.randomUUID())
                .companyName(companyName)
                .phone(phone)
                .email(email)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        when(supplierRepository.saveAndFlush(Mockito.any(Supplier.class))).thenReturn(supplier);

        // when
        Supplier actual = supplierService.create(supplierRequestDTO);

        // then
        assertThat(actual.getId()).isEqualByComparingTo(supplier.getId());
        assertThat(actual.getCompanyName()).isEqualTo(supplier.getCompanyName());
        assertThat(actual.getCreatedAt()).isEqualTo(supplier.getCreatedAt());
    }

    @Test
    public void supplierServiceFindById_givenSupplierId_thenReturnSupplier() {
        // given
        String companyName = "test";
        String phone = "test-phone";
        String email = "test-email@test.com";
        Supplier supplier = Supplier.builder()
                .id(UUID.randomUUID())
                .companyName(companyName)
                .phone(phone)
                .email(email)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        when(supplierRepository.findById(supplier.getId())).thenReturn(Optional.of(supplier));

        // when
        Optional<Supplier> actual = supplierService.getById(supplier.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getId()).isEqualByComparingTo(supplier.getId());
        assertThat(actual.get().getCompanyName()).isEqualTo(supplier.getCompanyName());
        assertThat(actual.get().getCreatedAt()).isEqualTo(supplier.getCreatedAt());
    }

    @Test
    public void supplierServiceFindById_givenNotExistedSupplierId_thenReturnOptionalEmpty() {
        // given
        UUID id = UUID.randomUUID();
        when(supplierRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Optional<Supplier> actual = supplierService.getById(id);

        // then
        assertThat(actual.isEmpty()).isTrue();
    }
}
