package com.project.springapistudy.product.controller;

import com.project.springapistudy.product.dto.ProductResponse;
import com.project.springapistudy.product.dto.ProductSaveRequest;
import com.project.springapistudy.product.dto.ProductUpdateRequest;
import com.project.springapistudy.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductResponse findProductId(@PathVariable @Min(1) long productId) {
        return productService.findProduct(productId);
    }

    @PostMapping
    public ResponseEntity<Void> registerProduct(@RequestBody @Valid ProductSaveRequest request) {
        Long response = productService.registerProduct(request);

        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{productId}")
                        .buildAndExpand(response)
                        .toUri())
                .build();
    }

    @PutMapping("/{productId}")
    public void modifyProduct(@PathVariable @Min(1) long productId, @RequestBody @Valid ProductUpdateRequest request) {
        productService.modifyProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    public void removeProduct(@PathVariable @Min(1) long productId) {
        productService.removeProduct(productId);
    }
}
