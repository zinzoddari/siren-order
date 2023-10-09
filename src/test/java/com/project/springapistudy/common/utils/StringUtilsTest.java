package com.project.springapistudy.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Nested
    @DisplayName("문자열이 비어있지 않는지 확인")
    class isNotEmpty {
        @Test
        @DisplayName("문자열이 비어있지 않다면 true")
        void isTrue() {
            //given
            final String input = "문자열";

            //when
            boolean result = StringUtils.isNotEmpty(input);

            //then
            assertThat(result).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        @NullSource
        @DisplayName("문자열이 비어있다면 false")
        void isFalse(String input) {
            //when
            boolean result = StringUtils.isNotEmpty(input);

            //then
            assertThat(result).isFalse();
        }
    }
}