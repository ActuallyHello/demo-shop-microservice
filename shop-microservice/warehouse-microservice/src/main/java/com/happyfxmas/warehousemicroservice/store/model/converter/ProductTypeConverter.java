package com.happyfxmas.warehousemicroservice.store.model.converter;

import com.happyfxmas.warehousemicroservice.store.model.enums.ProductType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProductTypeConverter implements AttributeConverter<ProductType, String> {
    @Override
    public String convertToDatabaseColumn(ProductType productType) {
        if (productType == null) {
            return null;
        }
        return productType.getType();
    }

    @Override
    public ProductType convertToEntityAttribute(String productType) {
        if (productType == null) {
            return null;
        }
        return Stream.of(ProductType.values())
                .filter(pt -> pt.getType().equals(productType))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
