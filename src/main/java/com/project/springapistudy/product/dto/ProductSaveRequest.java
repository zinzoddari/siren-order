package com.project.springapistudy.product.dto;

import com.project.springapistudy.product.domain.ProductType;
import com.project.springapistudy.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.project.springapistudy.product.domain.ProductValidateMessage.PRODUCT_TYPE_IS_NOT_NULL;
import static com.project.springapistudy.product.domain.ProductValidateMessage.PRODUCT_IS_NOT_NULL;
import static com.project.springapistudy.product.domain.ProductValidateMessage.PRODUCT_SIZE_INVALID;
import static com.project.springapistudy.product.domain.ProductValidateMessage.USE_YN_IS_NOT_NULL;

@Getter
@AllArgsConstructor
public class ProductSaveRequest {
    @NotNull(message = PRODUCT_TYPE_IS_NOT_NULL)
    private ProductType type;

    @Size(max = 32, message = PRODUCT_SIZE_INVALID)
    @NotBlank(message = PRODUCT_IS_NOT_NULL)
    private String name;

    @NotBlank(message = USE_YN_IS_NOT_NULL)
    private String useYn;

    public static ProductSaveRequest create(ProductType type, String name, String useYn) {
        return new ProductSaveRequest(type, name, useYn);
    }

    public Product toEntity() {
        return Product.create(this.type, this.name, this.useYn);
    }
}
