package com.bilalachraf.billingsupplierservice.feign;

import com.bilalachraf.billingsupplierservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {

    @GetMapping("/products/{id}")
    Product getProductById(@RequestHeader("Authorization") String token, @PathVariable Long id);


    @GetMapping("/products")
    PagedModel<Product> getPageProducts(
            @RequestHeader("Authorization") String token,
            @RequestParam(value="page") int page,
            @RequestParam(value="size") int size
            );

    @GetMapping("/api/products/random")
    List<Product> getRandomPageProducts();

    @PutMapping("/products/{id}")
    Product updateProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody Product product);
}
