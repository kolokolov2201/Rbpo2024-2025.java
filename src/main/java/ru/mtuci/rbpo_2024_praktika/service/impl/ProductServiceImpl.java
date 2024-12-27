package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.model.Product;
import ru.mtuci.rbpo_2024_praktika.repository.ProductRepository;
import ru.mtuci.rbpo_2024_praktika.request.ProductRequest;
import ru.mtuci.rbpo_2024_praktika.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setIsBlocked(productRequest.getIsBlocked());
        return productRepository.save(product);
    }
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Продукт не найден по id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}