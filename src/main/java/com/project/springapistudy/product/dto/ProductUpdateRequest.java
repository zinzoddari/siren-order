package com.project.springapistudy.product.dto;

import com.project.springapistudy.product.domain.ProductType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.project.springapistudy.product.domain.ProductValidateMessage.PRODUCT_TYPE_IS_NOT_NULL;
import static com.project.springapistudy.product.domain.ProductValidateMessage.PRODUCT_IS_NOT_NULL;
import static com.project.springapistudy.product.domain.ProductValidateMessage.PRODUCT_SIZE_INVALID;

@Getter
public class ProductUpdateRequest {
    @NotNull(message = PRODUCT_TYPE_IS_NOT_NULL)
    private ProductType type;

    @Size(max = 32, message = PRODUCT_SIZE_INVALID)
    @NotBlank(message = PRODUCT_IS_NOT_NULL)
    private String name;

    public ProductUpdateRequest(ProductType type, String name) {
        this.type = type;
        this.name = name;
    }

    public static ProductUpdateRequest create(ProductType type, String name) {
        return new ProductUpdateRequest(type, name);
    }
}
