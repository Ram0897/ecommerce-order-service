package com.ram.ecommerce.product;

import com.ram.ecommerce.common.ResourceConflictException;
import com.ram.ecommerce.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductDtos.Response create(ProductDtos.CreateRequest request) {
        if (repository.existsBySku(request.sku())) {
            throw new ResourceConflictException("SKU already exists: " + request.sku());
        }
        Product product = new Product(null, request.sku(), request.name(), request.description(), request.price(), request.inventoryQuantity());
        return toResponse(repository.save(product));
    }

    @Transactional(readOnly = true)
    public ProductDtos.Response get(UUID id) {
        return toResponse(find(id));
    }

    @Transactional(readOnly = true)
    public Page<ProductDtos.Response> list(String search, Pageable pageable) {
        Page<Product> products = search == null || search.isBlank()
                ? repository.findAll(pageable)
                : repository.findByNameContainingIgnoreCase(search.trim(), pageable);
        return products.map(this::toResponse);
    }

    public ProductDtos.Response update(UUID id, ProductDtos.UpdateRequest request) {
        Product product = find(id);
        product.changeDetails(request.name(), request.description(), request.price());
        return toResponse(product);
    }

    public void delete(UUID id) {
        repository.delete(find(id));
    }

    private Product find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    private ProductDtos.Response toResponse(Product p) {
        return new ProductDtos.Response(p.getId(), p.getSku(), p.getName(), p.getDescription(), p.getPrice(), p.getInventoryQuantity(), p.getVersion());
    }
}
