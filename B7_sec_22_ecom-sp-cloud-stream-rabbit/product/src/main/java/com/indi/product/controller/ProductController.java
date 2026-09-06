package com.indi.product.controller;


import com.indi.product.model.dto.ProductRequest;
import com.indi.product.model.dto.ProductResponse;
import com.indi.product.model.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //246
    @GetMapping("/simulate")    //----> /api/products/simulate?fail=true
    public ResponseEntity <String> simulateFail(@RequestParam(defaultValue = "false") boolean fail) {
        if(fail) {
            throw new RuntimeException("simulated failure for testing");
        }
        return ResponseEntity.ok("product servce is ok");
    }


    @GetMapping
    public ResponseEntity <List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(this.productService.getAllproducts());
    }
    @GetMapping("/{id}")
    public ResponseEntity <ProductResponse> getProductById(@PathVariable String id) {
        return this.productService.getProductById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public ResponseEntity <List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(this.productService.searchProducts(keyword));
    }
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return  new ResponseEntity<ProductResponse>(this.productService.createProduct(productRequest), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductRequest productRequest) {
        return this.productService.updateProduct(id, productRequest).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id) {
        Boolean deleted = this.productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
