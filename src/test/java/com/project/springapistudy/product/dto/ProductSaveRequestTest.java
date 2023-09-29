package com.project.springapistudy.product.dto;

import com.project.springapistudy.product.domain.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ProductSaveRequestTest {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    @Nested
    @DisplayName("상품 종류 유효성 검증")
    class validateProductType {
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"SNACK", "과자"})
        @DisplayName("존재하지 않는 상품 종류 입력시 유효성 검증 실패")
        void invalidProductType(String productType) {
            //when
            ProductSaveRequest request = ProductSaveRequest.create(ProductType.from(productType), "name", "Y");

            Set<ConstraintViolation<ProductSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<ProductSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("type");
            });
        }
    }

    @Nested
    @DisplayName("상품명 유효성 검증")
    class validateName {
        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("name이 빈값 혹은 null 값으로 입력시 유효성 검증 실패")
        void nameIsNull(String name) {
            //when
            ProductSaveRequest request = ProductSaveRequest.create(ProductType.BEVERAGE, name, "Y");

            Set<ConstraintViolation<ProductSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<ProductSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("name");
            });
        }

        @Test
        @DisplayName("상품명으로 32자 넘는 입력값이 들어올 경우 유효성 검증 실패")
        void invalidNameLength() {
            //given
            final String name = "가나다라마바사아자차카파타하가나다라마바사아자차카파타하가나다라마바사아자차카파타하";

            //when
            ProductSaveRequest request = ProductSaveRequest.create(ProductType.BEVERAGE, name, "Y");

            Set<ConstraintViolation<ProductSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<ProductSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("name");
            });
        }
    }

    @Nested
    @DisplayName("사용여부 유효성 검증")
    class validateUseYn {
        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("사용여부가 null 혹은 빈 값이면 유효성 검증 실패")
        void useYnIsNotNull(String useYn) {
            //when
            ProductSaveRequest request = ProductSaveRequest.create(ProductType.BEVERAGE, "name", useYn);

            Set<ConstraintViolation<ProductSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<ProductSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("useYn");
            });
        }
    }

    @Test
    @DisplayName("유효성 검증에 통과")
    void validDto() {
        //given
        final ProductType productType = ProductType.BEVERAGE;
        final String name = "아메리카노";
        final String useYn = "Y";

        //when
        ProductSaveRequest request = ProductSaveRequest.create(productType, name, useYn);

        Set<ConstraintViolation<ProductSaveRequest>> violations = validator.validate(request);

        //then
        assertThat(violations).size().isZero();
    }
}
