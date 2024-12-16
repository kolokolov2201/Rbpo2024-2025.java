package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);
    Product createProduct(ru.mtuci.rbpo_2024_praktika.request.ProductRequest productRequest);
    void deleteProduct(Long id);
    List<Product> getAllProducts();
}