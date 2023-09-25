package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import org.junit.jupiter.api.BeforeAll;
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

class MenuSaveRequestTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Nested
    @DisplayName("메뉴 종류 유효성 검증")
    class validateMenuType {
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"SNACK", "과자"})
        @DisplayName("존재하지 않는 메뉴 종류 입력시 유효성 검증 실패")
        void invalidMenuType(String menuType) {
            //when
            MenuSaveRequest request = MenuSaveRequest.builder()
                    .type(MenuType.from(menuType))
                    .name("name")
                    .useYn("Y")
                    .build();

            Set<ConstraintViolation<MenuSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<MenuSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("type");
            });
        }
    }

    @Nested
    @DisplayName("메뉴명 유효성 검증")
    class validateName {
        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("name이 빈값 혹은 null 값으로 입력시 유효성 검증 실패")
        void nameIsNull(String name) {
            //when
            MenuSaveRequest request = MenuSaveRequest.builder()
                    .type(MenuType.BEVERAGE)
                    .name(name)
                    .useYn("Y")
                    .build();

            Set<ConstraintViolation<MenuSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<MenuSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("name");
            });
        }

        @Test
        @DisplayName("메뉴명으로 32자 넘는 입력값이 들어올 경우 유효성 검증 실패")
        void invalidNameLength() {
            //given
            final String name = "가나다라마바사아자차카파타하가나다라마바사아자차카파타하가나다라마바사아자차카파타하";

            //when
            MenuSaveRequest request = MenuSaveRequest.builder()
                    .type(MenuType.BEVERAGE)
                    .name(name)
                    .useYn("Y")
                    .build();

            Set<ConstraintViolation<MenuSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<MenuSaveRequest> result = violations.iterator().next();

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
            MenuSaveRequest request = MenuSaveRequest.builder()
                    .type(MenuType.BEVERAGE)
                    .name("name")
                    .useYn(useYn)
                    .build();

            Set<ConstraintViolation<MenuSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<MenuSaveRequest> result = violations.iterator().next();

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
        final MenuType menuType = MenuType.BEVERAGE;
        final String name = "아메리카노";
        final String useYn = "Y";

        //when
        MenuSaveRequest request = MenuSaveRequest.builder()
                .type(menuType)
                .name(name)
                .useYn(useYn)
                .build();

        Set<ConstraintViolation<MenuSaveRequest>> violations = validator.validate(request);

        //then
        assertThat(violations).size().isZero();
    }
}