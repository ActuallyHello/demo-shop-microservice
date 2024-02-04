package com.happyfxmas.ordermicroservice.service.communication;

import com.happyfxmas.ordermicroservice.exception.communication.ProductsDoesNotExistException;
import com.happyfxmas.ordermicroservice.service.communication.dto.ProductDTO;
import com.happyfxmas.ordermicroservice.store.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Slf4j
@Service
public class WarehouseCommunication {

    private final RestTemplate restTemplate;

    @Value("${WAREHOUSE_BASE_URL}")
    private static String WAREHOUSE_BASE_URL;
    private static final String PRODUCT_ID = "/products/{productId}";

    public void  checkForUnavailableProductsInItems(List<Item> items) {
        var futures = items.stream()
                .map(item -> receiveProduct(item.getProductId()))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<UUID> unavailableProductIds = new ArrayList<>();
        futures.forEach(future -> {
            try {
                var result = future.get();
                if (!result.getIsFound()) {
                    unavailableProductIds.add(result.getProductId());
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("ERROR WHEN PROCESSING AVAILABLE ITEMS", e);
                Thread.currentThread().interrupt();
                throw new RuntimeException(e.getMessage(), e);
            }
        });
        if (!unavailableProductIds.isEmpty()) {
            throw new ProductsDoesNotExistException("These products was not found!", unavailableProductIds);
        }
    }

    private CompletableFuture<ProductResponseWrapper> receiveProduct(UUID productId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("CHECK PRODUCT WITH ID={}", productId);
                var productDTO = restTemplate.getForObject(
                        WAREHOUSE_BASE_URL + PRODUCT_ID,
                        ProductDTO.class,
                        productId
                );
                return ProductResponseWrapper.builder()
                        .productId(productId)
                        .productDTO(productDTO)
                        .isFound(productDTO != null)
                        .build();
            } catch (HttpClientErrorException.NotFound exception) {
                log.warn("PRODUCT WITH ID={} WAS NOT FOUND!", productId);
                return ProductResponseWrapper.builder()
                        .productId(productId)
                        .isFound(false)
                        .build();
            }
        });
    }

    @AllArgsConstructor
    @Builder
    @Getter
    private static class ProductResponseWrapper {
        private UUID productId;
        private ProductDTO productDTO;
        private Boolean isFound;
    }
}
