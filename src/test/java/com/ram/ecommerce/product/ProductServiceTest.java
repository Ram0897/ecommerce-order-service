package com.ram.ecommerce.product;

import com.ram.ecommerce.common.ResourceConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock ProductRepository repository;
    @InjectMocks ProductService service;

    @Test
    void createRejectsDuplicateSku() {
        ProductDtos.CreateRequest request = new ProductDtos.CreateRequest(
                "SKU-1", "Keyboard", "Mechanical keyboard", new BigDecimal("49.99"), 10);
        when(repository.existsBySku("SKU-1")).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> service.create(request));
    }

    @Test
    void getReturnsProductResponse() {
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "SKU-1", "Keyboard", null, new BigDecimal("49.99"), 10);
        when(repository.findById(id)).thenReturn(Optional.of(product));

        ProductDtos.Response response = service.get(id);

        assertEquals(id, response.id());
        assertEquals("SKU-1", response.sku());
        assertEquals(10, response.inventoryQuantity());
    }

    @Test
    void createPersistsNewProduct() {
        ProductDtos.CreateRequest request = new ProductDtos.CreateRequest(
                "SKU-2", "Mouse", null, new BigDecimal("19.99"), 20);
        when(repository.existsBySku("SKU-2")).thenReturn(false);
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductDtos.Response response = service.create(request);

        assertEquals("SKU-2", response.sku());
        assertEquals("Mouse", response.name());
        assertEquals(20, response.inventoryQuantity());
    }
}
