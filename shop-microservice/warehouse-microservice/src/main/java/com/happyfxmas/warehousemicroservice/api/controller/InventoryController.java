package com.happyfxmas.warehousemicroservice.api.controller;

import com.happyfxmas.warehousemicroservice.api.dto.InventoryDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.InventoryUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.api.mapper.InventoryDTOMapper;
import com.happyfxmas.warehousemicroservice.service.InventoryService;
import com.happyfxmas.warehousemicroservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/inventories")
public class InventoryController {

    private final ProductService productService;
    private final InventoryService inventoryService;

    private final static String BY_ID = "/{id}";
    private final static String BY_PRODUCT_ID = "/products/{productId}";

    @GetMapping(BY_ID)
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable UUID id) {
        var inventory = inventoryService.getById(id);
        return ResponseEntity.ok(InventoryDTOMapper.makeDTO(inventory));
    }

    @GetMapping(BY_PRODUCT_ID)
    public ResponseEntity<InventoryDTO> getInventoryByProductId(@PathVariable UUID productId) {
        var product = productService.getByIdWithInventory(productId);
        return ResponseEntity.ok(InventoryDTOMapper.makeDTO(product.getInventory()));
    }

    @PostMapping
    public ResponseEntity<?> createInventory(@RequestBody @Valid InventoryRequestDTO inventoryRequestDTO) {
        var product = productService.getById(UUID.fromString(inventoryRequestDTO.getProductId()));
        var inventory = InventoryDTOMapper.makeModel(inventoryRequestDTO, product);
        inventory = inventoryService.create(inventory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("ID", inventory.getId()));

    }

    @PatchMapping(BY_ID)
    public ResponseEntity<InventoryDTO> updateInventoryById(@PathVariable UUID id,
                                                            @RequestBody @Valid InventoryUpdateRequestDTO inventoryUpdateRequestDTO) {
        var oldInventory = inventoryService.getById(id);
        var newInventory = InventoryDTOMapper.makeModel(inventoryUpdateRequestDTO, oldInventory);
        newInventory = inventoryService.update(newInventory);
        return ResponseEntity.ok(InventoryDTOMapper.makeDTO(newInventory));
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<?> deleteInventoryById(@PathVariable UUID id) {
        var inventory = inventoryService.getById(id);
        inventoryService.delete(inventory);
        return ResponseEntity.noContent().build();
    }
}
