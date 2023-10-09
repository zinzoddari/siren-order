package com.project.springapistudy.product.dto;

import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.product.domain.ProductType;
import com.project.springapistudy.product.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponse {
    private Long productId;
    private ProductType type;
    private String name;
    private Flag useYn;

    public static ProductResponse fromEntity(Product product) {
        if(ObjectUtils.isEmpty(product)) {
            return null;
        }

        return new ProductResponse(product.getProductId(), product.getType(), product.getName(), product.getUseYn());
    }
}
