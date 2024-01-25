package com.happyfxmas.warehousemicroservice.api.controller;

import com.happyfxmas.warehousemicroservice.api.dto.ProductDTO;
import com.happyfxmas.warehousemicroservice.api.dto.response.ProductWithInventoryDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.response.ProductWithSupplierDTO;
import com.happyfxmas.warehousemicroservice.api.mapper.ProductDTOMapper;
import com.happyfxmas.warehousemicroservice.service.ProductService;
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
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/products")
public class ProductController {

    private final ProductService productService;
    private final SupplierService supplierService;

    private static final String BY_ID = "/{id}";
    private static final String WITH_SUPPLIER = "/suppliers";
    private static final String WITH_INVENTORY = "/inventories";

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam(required = false) Integer page,
                                                           @RequestParam(required = false) Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 50,
                Sort.by("type")
        );
        var products = productService.getAll(pageable);
        var productDTOs = products.stream()
                .map(ProductDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        var product = productService.getById(id);
        return ResponseEntity.ok(ProductDTOMapper.makeDTO(product));
    }

    @GetMapping(BY_ID + WITH_SUPPLIER)
    public ResponseEntity<ProductWithSupplierDTO> getProductWithSupplierById(@PathVariable UUID id) {
        var productWithSupplier = productService.getByIdWithSupplier(id);
        return ResponseEntity.ok(
                ProductDTOMapper.makeDTO(productWithSupplier, productWithSupplier.getSupplier()));
    }

    @GetMapping(BY_ID + WITH_INVENTORY)
    public ResponseEntity<ProductWithInventoryDTO> getProductWithInventoryById(@PathVariable UUID id) {
        var productWithInventory = productService.getByIdWithInventory(id);
        return ResponseEntity.ok(
                ProductDTOMapper.makeDTO(productWithInventory, productWithInventory.getInventory()));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        var supplier = supplierService.getById(UUID.fromString(productRequestDTO.getSupplierId()));
        var product = ProductDTOMapper.makeModel(productRequestDTO, supplier);
        product = productService.create(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("ID", product.getId()));
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable UUID id,
                                                        @RequestBody @Valid ProductUpdateRequestDTO productUpdateRequestDTO) {
        var oldProduct = productService.getByIdWithSupplier(id);
        var newProduct = ProductDTOMapper.makeModel(productUpdateRequestDTO, oldProduct);
        newProduct = productService.update(newProduct);
        return ResponseEntity.ok(ProductDTOMapper.makeDTO(newProduct));
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<?> deleteProductById(@PathVariable UUID id) {
        var product = productService.getById(id);
        productService.delete(product);
        return ResponseEntity.noContent().build();
    }
}
