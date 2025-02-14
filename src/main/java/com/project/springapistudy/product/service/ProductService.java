package com.project.springapistudy.product.service;

import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.product.domain.NotFoundException;
import com.project.springapistudy.product.dto.ProductResponse;
import com.project.springapistudy.product.dto.ProductSaveRequest;
import com.project.springapistudy.product.dto.ProductUpdateRequest;
import com.project.springapistudy.product.entity.Product;
import com.project.springapistudy.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductResponse findProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);

        if(product.isNotUse()) {
            product = null;
        }

        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public Long registerProduct(ProductSaveRequest request) {
        return productRepository.save(request.toEntity()).getProductId();
    }

    @Transactional
    public void modifyProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);

        product.changeName(request.getName());
        product.changeType(request.getType());
    }

    @Transactional
    public void removeProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);

        product.remove();
    }
}
