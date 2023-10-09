package com.project.springapistudy.product.dto;

import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.product.domain.ProductType;
import com.project.springapistudy.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private ProductType type;
    private String name;
    private Flag useYn;

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(product.getProductId(), product.getType(), product.getName(), product.getUseYn());
    }
}
