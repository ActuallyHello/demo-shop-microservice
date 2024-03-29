package com.happyfxmas.warehousemicroservice.api.controller;

import com.happyfxmas.warehousemicroservice.api.dto.SupplierDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.response.SupplierWithProductDTO;
import com.happyfxmas.warehousemicroservice.api.mapper.SupplierMapper;
import com.happyfxmas.warehousemicroservice.api.mapper.SupplierWithProductMapper;
import com.happyfxmas.warehousemicroservice.exception.response.SupplierNotFoundException;
import com.happyfxmas.warehousemicroservice.exception.response.SupplierServerException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierDeleteException;
import com.happyfxmas.warehousemicroservice.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    private static final String BY_ID = "/{id}";
    private static final String WITH_PRODUCTS = "/products";

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 50,
                Sort.by("companyName")
        );
        var suppliers = supplierService.getAll(pageable);
        var supplierDTOs = suppliers.stream()
                .map(SupplierMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(supplierDTOs);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable UUID id) {
        var supplierDTO = supplierService.getById(id)
                .map(SupplierMapper::makeDTO)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with id=%s was not found".formatted(id)));
        return ResponseEntity.ok(supplierDTO);
    }

    @GetMapping(BY_ID + WITH_PRODUCTS)
    public ResponseEntity<SupplierWithProductDTO> getSupplierWithProductsById(@PathVariable UUID id) {
        var supplierWithProductDTO = supplierService.getByIdWithProducts(id)
                .map(supplier -> SupplierWithProductMapper.makeDTO(supplier, supplier.getProducts()))
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with id=%s was not found".formatted(id)));
        return ResponseEntity.ok(supplierWithProductDTO);
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@RequestBody @Valid SupplierRequestDTO supplierRequestDTO) {
        try {
            var supplier = supplierService.create(supplierRequestDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("ID", supplier.getId()));
        } catch (SupplierCreationException exception) {
            throw new SupplierServerException(exception.getMessage(), exception);
        }
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<SupplierDTO> updateSupplierById(@PathVariable UUID id,
                                                      @RequestBody @Valid SupplierUpdateRequestDTO supplierUpdateRequestDTO) {
        var supplier = supplierService.getById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with id=%s was not found".formatted(id)));
        try {
            supplier = supplierService.update(supplierUpdateRequestDTO, supplier);
            return ResponseEntity.ok(SupplierMapper.makeDTO(supplier));
        } catch (SupplierCreationException exception) {
            throw new SupplierServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<?> deleteSupplierById(@PathVariable UUID id) {
        var supplier = supplierService.getById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with id=%s was not found".formatted(id)));
        try {
            supplierService.delete(supplier);
            return ResponseEntity.noContent().build();
        } catch (SupplierDeleteException exception) {
            throw new SupplierServerException(exception.getMessage(), exception);
        }
    }
}
