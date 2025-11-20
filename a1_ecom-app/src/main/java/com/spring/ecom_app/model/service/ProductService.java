package com.spring.ecom_app.model.service;

import com.spring.ecom_app.model.dto.ProductRequest;
import com.spring.ecom_app.model.dto.ProductResponse;
import com.spring.ecom_app.model.entity.Product;
import com.spring.ecom_app.model.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public  ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllproducts() {
        return this.productRepository.findByActiveTrue().stream().map(p -> mapToProductResponse(p)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> searchProducts(String keyword) {
        return this.productRepository.searchProducts(keyword).stream().map(p -> mapToProductResponse(p)).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = this.productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    @Transactional
    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return this.productRepository.findById(id).map(p_db -> {
            updateProductFromRequest(p_db, productRequest);
            Product savedProduct = this.productRepository.save(p_db);
            return mapToProductResponse(savedProduct);
        });
    }

    @Transactional
    public Boolean deleteProduct(Long id) {
       return this.productRepository.findById(id).map(p -> {
         p.setActive(Boolean.FALSE);
         this.productRepository.save(p);
         return true;
       }).orElse(Boolean.FALSE);
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setActive(productRequest.getActive());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setActive(product.getActive());
        productResponse.setCategory(product.getCategory());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setStockQuantity(product.getStockQuantity());
        return productResponse;
    }

}
