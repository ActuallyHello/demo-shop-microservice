package com.happyfxmas.warehousemicroservice.api.controller;

import com.happyfxmas.warehousemicroservice.api.dto.request.SupplierRequestDTO;
import com.happyfxmas.warehousemicroservice.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<?> createSupplier(@RequestBody @Valid SupplierRequestDTO supplierRequestDTO) {
        var supplier = supplierService.create(supplierRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("ID", supplier.getId()));
    }
}
