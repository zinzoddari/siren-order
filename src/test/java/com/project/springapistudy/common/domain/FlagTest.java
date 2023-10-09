package com.project.springapistudy.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class FlagTest {

    @Nested
    @DisplayName("input값으로 flag 생성")
    class from {
        @ParameterizedTest
        @ValueSource(strings = {"가나다", "123", "", " "})
        @NullSource
        @DisplayName("flag에 없는 값일 경우 null처리")
        void invalidFlag(String input) {
            //when
            Flag result = Flag.from(input);

            //then
            assertThat(result).isNull();
        }

        @ParameterizedTest
        @ValueSource(strings = {"Y", "N"})
        @DisplayName("flag에 있는 값 넣어 Flag 생성 성공")
        void validFlag(String input) {
            //when
            Flag result = Flag.from(input);

            //then
            assertThat(result).isEqualTo(Flag.valueOf(input));
        }
    }

    @Nested
    @DisplayName("isY 검증")
    class isY {
        @Test
        @DisplayName("flag가 N일때 true")
        void flagIsTrue() {
            //given
            final Flag flag = Flag.Y;

            //when
            boolean result = flag.isY();

            //then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("flag가 Y일때 true")
        void flagIsFalse() {
            //given
            final Flag flag = Flag.N;

            //when
            boolean result = flag.isY();

            //then
            assertThat(result).isFalse();
        }
    }
}