package com.project.springapistudy.product.service;

import com.project.springapistudy.product.domain.ProductType;
import com.project.springapistudy.product.domain.NotFoundException;
import com.project.springapistudy.product.dto.ProductResponse;
import com.project.springapistudy.product.dto.ProductSaveRequest;
import com.project.springapistudy.product.dto.ProductUpdateRequest;
import com.project.springapistudy.product.entity.Product;
import com.project.springapistudy.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("상품 등록 검증")
    class registerProduct {
        @Test
        @DisplayName("상품 등록 성공")
        void success() {
            //given
            ProductSaveRequest request = ProductSaveRequest.create(ProductType.BEVERAGE, "얼음 뺀 아이스 아메리카노", "Y");

            Product expectedProduct = request.toEntity();

            given(productRepository.save(any(Product.class))).willReturn(expectedProduct);

            //when & then
            assertDoesNotThrow(() -> productService.registerProduct(request));
        }
    }

    @Nested
    @DisplayName("상품 단건 조회")
    class findProduct {
        @Test
        @DisplayName("올바른 productId로 조회시 성공")
        void success() {
            //given
            final Long productId = 1L;

            final Product expetcedProduct = new Product(productId, ProductType.BEVERAGE, "name", "Y");

            given(productRepository.findById(productId)).willReturn(Optional.of(expetcedProduct));

            //when
            ProductResponse response = productService.findProduct(productId);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getProductId()).isEqualTo(productId);
                softAssertions.assertThat(response.getName()).isEqualTo(expetcedProduct.getName());
                softAssertions.assertThat(response.getType()).isEqualTo(expetcedProduct.getType());
            });
        }

        @Test
        @DisplayName("존재하지 않는 productId로 조회시 오류 출력")
        void notFound() {
            //given
            final Long productId = -1L;

            given(productRepository.findById(productId)).willReturn(Optional.empty());

            //when & then
            assertThrows(NotFoundException.class, () -> productService.findProduct(productId));
        }
    }

    @Nested
    @DisplayName("상품 수정")
    class modifyProduct {
        @Test
        @DisplayName("상품 수정 성공")
        void success() {
            //given
            final Long productId = 1L;

            final Product expetcedProduct = new Product(productId, ProductType.BEVERAGE, "name", "Y");

            final ProductUpdateRequest request = ProductUpdateRequest.create(ProductType.DESSERT, "댕장꿍");

            given(productRepository.findById(productId)).willReturn(Optional.ofNullable(expetcedProduct));

            //when & then
            assertDoesNotThrow(() -> productService.modifyProduct(productId, request));
        }

        @Test
        @DisplayName("존재하지 않는 productId 수정 시도 시 오류")
        void invalidProduct() {
            //given
            final Long productId = -1L;

            final ProductUpdateRequest request = ProductUpdateRequest.create(ProductType.DESSERT, "댕장꿍");

            given(productRepository.findById(productId)).willThrow(NotFoundException.class);

            //when & then
            assertThrows(NotFoundException.class, () -> productService.modifyProduct(productId, request));
        }
    }

    @Nested
    @DisplayName("상품 단건 삭제")
    class removeProduct {
        @Test
        @DisplayName("상품 단건 삭제 성공")
        void success() {
            //given
            final Long productId = 1L;

            final Product expetcedProduct = new Product(productId, ProductType.BEVERAGE, "name", "Y");

            given(productRepository.findById(productId)).willReturn(Optional.ofNullable(expetcedProduct));

            //when
            productService.removeProduct(productId);

            //then
            assertThat(expetcedProduct.getUseYn()).isEqualTo("N");
        }

        @Test
        @DisplayName("없는 상품 단건 삭제시 실패")
        void notFound() {
            //given
            final Long productId = -1L;

            given(productRepository.findById(productId)).willThrow(NotFoundException.class);

            //when & then
            assertThrows(NotFoundException.class, () -> productService.removeProduct(productId));
        }
    }
}
