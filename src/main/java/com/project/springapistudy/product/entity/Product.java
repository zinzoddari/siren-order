package com.project.springapistudy.product.entity;

import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.common.jpa.BaseEntity;
import com.project.springapistudy.product.domain.ProductType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "PRODUCT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Id
    @Comment("상품 ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Comment("상품 종류")
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @NotEmpty
    @Comment("상품 명")
    @Column(name = "NAME", nullable = false, length = 32)
    private String name;

    @Comment("사용여부")
    @Column(name = "USE_YN")
    @Enumerated(EnumType.STRING)
    private Flag useYn;

    public void changeName(String name) {
        if (!ObjectUtils.isEmpty(name)) {
            this.name = name;
        }
    }

    public void changeType(ProductType productType) {
        if (!ObjectUtils.isEmpty(productType)) {
            this.type = productType;
        }
    }

    public Product(ProductType type, String name, Flag useYn) {
        this.type = type;
        this.name = name;
        this.useYn = useYn;
    }

    public static Product create(ProductType type, String name, Flag useYn) {
        return new Product(type, name, useYn);
    }

    public boolean isNotUse() {
        return !this.useYn.isY();
    }

    public void remove() {
        this.useYn = Flag.N;
    }
}
