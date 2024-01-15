package com.happyfxmas.warehousemicroservice.api.controller;

import com.happyfxmas.warehousemicroservice.api.dto.ProductDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.request.ProductUpdateRequestDTO;
import com.happyfxmas.warehousemicroservice.api.dto.response.ProductWithSupplierDTO;
import com.happyfxmas.warehousemicroservice.api.mapper.ProductMapper;
import com.happyfxmas.warehousemicroservice.api.mapper.ProductWithSupplierMapper;
import com.happyfxmas.warehousemicroservice.exception.response.ProductNotFoundException;
import com.happyfxmas.warehousemicroservice.exception.response.ProductServerException;
import com.happyfxmas.warehousemicroservice.exception.response.SupplierNotFoundException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductDeleteException;
import com.happyfxmas.warehousemicroservice.service.ProductService;
import com.happyfxmas.warehousemicroservice.service.SupplierService;
import com.happyfxmas.warehousemicroservice.store.model.enums.ProductType;
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
    private static final String WITH_SUPPLIERS = "/suppliers";

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
                .map(ProductMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        var product = productService.getById(UUID.fromString(id))
                .orElseThrow(() -> new ProductNotFoundException("Product with id=%s does not exist!".formatted(id)));
        return ResponseEntity.ok(ProductMapper.makeDTO(product));
    }

    @GetMapping(BY_ID + WITH_SUPPLIERS)
    public ResponseEntity<ProductWithSupplierDTO> getProductWithSupplierById(@PathVariable String id) {
        var product = productService.getByIdWithSupplier(UUID.fromString(id))
                .orElseThrow(() -> new ProductNotFoundException("Product with id=%s does not exist!".formatted(id)));
        return ResponseEntity.ok(ProductWithSupplierMapper.makeDTO(product, product.getSupplier()));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        var supplier = supplierService.getById(UUID.fromString(productRequestDTO.getSupplierId()))
                .orElseThrow(() -> new SupplierNotFoundException(
                        "Supplier with id=%s was not found!".formatted(productRequestDTO.getSupplierId())));
        try {
            var product = productService.create(productRequestDTO, supplier);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("ID", product.getId()));
        } catch (ProductCreationException exception) {
            throw new ProductServerException(exception.getMessage(), exception);
        }
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable String id,
                                                  @RequestBody @Valid ProductUpdateRequestDTO productUpdateRequestDTO) {
        var product = productService.getByIdWithSupplier(UUID.fromString(id))
                .orElseThrow(() -> new ProductNotFoundException("Product with id=%s does not exist!".formatted(id)));
        if (productUpdateRequestDTO.getSupplierId() != null) {
            var supplier = supplierService.getById(UUID.fromString(productUpdateRequestDTO.getSupplierId()))
                    .orElseThrow(() -> new SupplierNotFoundException(
                            "Supplier with id=%s was not found!".formatted(productUpdateRequestDTO.getSupplierId())));
            product.setSupplier(supplier);
        }
        if (productUpdateRequestDTO.getTitle() != null) {
            product.setTitle(productUpdateRequestDTO.getTitle());
        }
        if (productUpdateRequestDTO.getDescription() != null) {
            product.setDescription(productUpdateRequestDTO.getDescription());
        }
        if (productUpdateRequestDTO.getType() != null) {
            product.setType(ProductType.valueOf(productUpdateRequestDTO.getType()));
        }
        try {
            product = productService.update(product);
            return ResponseEntity.ok(ProductMapper.makeDTO(product));
        } catch (ProductCreationException exception) {
            throw new ProductServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<?> deleteProductById(@PathVariable String id) {
        var product = productService.getByIdWithSupplier(UUID.fromString(id))
                .orElseThrow(() -> new ProductNotFoundException("Product with id=%s does not exist!".formatted(id)));
        try {
            productService.delete(product);
            return ResponseEntity.noContent().build();
        } catch (ProductDeleteException exception) {
            throw new ProductServerException(exception.getMessage(), exception);
        }
    }
}
