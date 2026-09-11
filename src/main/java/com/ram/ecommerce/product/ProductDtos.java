package com.ram.ecommerce.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public final class ProductDtos {
    private ProductDtos() {}

    public record CreateRequest(
            @NotBlank @Size(max = 64) String sku,
            @NotBlank @Size(max = 200) String name,
            @Size(max = 1000) String description,
            @DecimalMin("0.00") BigDecimal price,
            @Min(0) int inventoryQuantity) {}

    public record UpdateRequest(
            @NotBlank @Size(max = 200) String name,
            @Size(max = 1000) String description,
            @DecimalMin("0.00") BigDecimal price) {}

    public record Response(
            UUID id,
            String sku,
            String name,
            String description,
            BigDecimal price,
            int inventoryQuantity,
            long version) {}
}
