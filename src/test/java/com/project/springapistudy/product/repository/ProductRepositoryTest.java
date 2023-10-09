package com.project.springapistudy.product.repository;

import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.common.jpa.JpaConfig;
import com.project.springapistudy.product.domain.ProductType;
import com.project.springapistudy.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(JpaConfig.class)
@ExtendWith(SpringExtension.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("상품명에 대한 유효성 검증")
    class validateName {
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"가나다라마바사아자차카파타하가나다라마바사아자차카파타하가나다라마바사아자차카파타하"})
        @DisplayName("상품명이 null 혹은 32자가 넘었을 경우 등록 실패")
        void invalidName(String name) {
            //given
            Product product = Product.create(ProductType.BEVERAGE, name, Flag.Y);

            //when
            productRepository.save(product);

            //then
            assertThrows(Exception.class, () -> productRepository.flush());
        }
    }

    @Test
    @DisplayName("product 저장 성공")
    void saveSuccess() {
        //given
        final ProductType productType = ProductType.BEVERAGE;
        final String name = "아메리카노";
        final Flag useYn = Flag.Y;

        Product product = Product.create(productType, name, Flag.Y);

        //when
        Product result = productRepository.save(product);
        productRepository.flush();

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getProductId()).isNotZero().isNotNull();
            softAssertions.assertThat(result.getType()).isEqualTo(productType);
            softAssertions.assertThat(result.getName()).isEqualTo(name);
            softAssertions.assertThat(result.getUseYn()).isEqualTo(useYn);
        });
    }
}
