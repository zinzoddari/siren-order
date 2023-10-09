package com.project.springapistudy.franchise.dto;

import com.project.springapistudy.common.domain.Flag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class FranchiseSaveRequestTest {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    @Nested
    @DisplayName("프랜차이즈명 유효성 검사")
    class validateName {
        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " "})
        @DisplayName("프랜차이즈명이 null 혹은 빈값이면 오류")
        void invalidName(String name) {
            //given
            FranchiseSaveRequest request = FranchiseSaveRequest.create(name, "english", Flag.Y);

            //when
            Set<ConstraintViolation<FranchiseSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<FranchiseSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("name");
            });
        }

        @Test
        @DisplayName("프랜차이즈명이 32자 이상일 때 오류")
        void invalidNameSize() {
            //given
            final String name = "가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마";
            final FranchiseSaveRequest request = FranchiseSaveRequest.create(name, "english", Flag.Y);

            //when
            Set<ConstraintViolation<FranchiseSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<FranchiseSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isEqualTo(1);
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("name");
            });
        }
    }

    @Nested
    @DisplayName("영어명 유효성 검사")
    class validateEnglishName {
        @Test
        @DisplayName("영어명이 64자 이상일 때 오류")
        void invalidEnglishNameSize() {
            //given
            final String englishName = "ABCDEFABCDEFABCDEFABCDEFABCDEFABCDEFABCDEFABCDEFABCDEFABCDEFABCDEF";
            final FranchiseSaveRequest request = FranchiseSaveRequest.create("이름", englishName, Flag.Y);

            //when
            Set<ConstraintViolation<FranchiseSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<FranchiseSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isEqualTo(1);
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("englishName");
            });
        }
    }

    @Nested
    @DisplayName("사용여부 유효성 검사")
    class validateUseYn {
        @ParameterizedTest
        @NullSource
        @DisplayName("사용여부가 null 혹은 빈값이면 오류")
        void invalidUseYn(Flag useYn) {
            //given
            FranchiseSaveRequest request = FranchiseSaveRequest.create("이름", "english", useYn);

            //when
            Set<ConstraintViolation<FranchiseSaveRequest>> violations = validator.validate(request);
            ConstraintViolation<FranchiseSaveRequest> result = violations.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isEqualTo(1);
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("useYn");
            });
        }
    }

    @Test
    @DisplayName("유효성 검증에 통과")
    void success() {
        //given
        final String name = "스벅타스";
        final String englishName = "buckStarts";
        final Flag useYn = Flag.Y;

        //when
        FranchiseSaveRequest result = FranchiseSaveRequest.create(name, englishName, useYn);

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getName()).isEqualTo(name);
            softAssertions.assertThat(result.getEnglishName()).isEqualTo(englishName);
            softAssertions.assertThat(result.getUseYn()).isEqualTo(useYn);
        });
    }
}