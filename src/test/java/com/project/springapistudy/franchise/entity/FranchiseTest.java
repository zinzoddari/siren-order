package com.project.springapistudy.franchise.entity;

import com.project.springapistudy.common.domain.Flag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class FranchiseTest {

    @Nested
    @DisplayName("프랜차이즈 이름 변경")
    class modifyName {
        @Test
        @DisplayName("프랜차이즈 이름 변경 성공")
        void success() {
            //given
            final String name = "name";
            final String englishName = "englishName";
            final Flag useYn = Flag.Y;

            final Franchise franchise = new Franchise(1L, name, englishName, useYn);

            //when
            franchise.modifyName("name 수정");

            //then
            assertThat(franchise.getName()).isNotEqualTo(name);
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " "})
        @DisplayName("프랜차이즈 이름 변경시 null 혹은 빈값일 때 변경되지 않음")
        void notChange(String inputName) {
            //given
            final String name = "name";
            final String englishName = "englishName";
            final Flag useYn = Flag.Y;

            final Franchise franchise = new Franchise(1L, name, englishName, useYn);

            //when
            franchise.modifyName(inputName);

            //then
            assertThat(franchise.getName()).isEqualTo(name);
        }
    }

    @Nested
    @DisplayName("프랜차이즈 영어 이름 변경")
    class modifyEnglishName {
        @Test
        @DisplayName("영어 이름 변경 성공")
        void success() {
            //given
            final String name = "name";
            final String englishName = "englishName";
            final Flag useYn = Flag.Y;

            final Franchise franchise = new Franchise(1L, name, englishName, useYn);

            //when
            franchise.modifyEnglishName("englishName 수정");

            //then
            assertThat(franchise.getEnglishName()).isNotEqualTo(englishName);
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " "})
        @DisplayName("영어 이름이 비어있는 상태라면 변경되지 않음")
        void notChange(String inputEnglishName) {
            //given
            final String name = "name";
            final String englishName = "englishName";
            final Flag useYn = Flag.Y;

            final Franchise franchise = new Franchise(1L, name, englishName, useYn);

            //when
            franchise.modifyEnglishName(inputEnglishName);

            //then
            assertThat(franchise.getEnglishName()).isEqualTo(englishName);
        }
    }

    @Nested
    @DisplayName("프랜차이즈 사용여부 이름 변경")
    class modifyUseYn {
        @Test
        @DisplayName("사용여부 변경 성공")
        void success() {
            //given
            final String name = "name";
            final String englishName = "englishName";
            final Flag useYn = Flag.Y;

            final Franchise franchise = new Franchise(1L, name, englishName, useYn);

            //when
            franchise.modifyUseYn(Flag.N);

            //then
            assertThat(franchise.getUseYn()).isNotEqualTo(useYn);
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " "})
        @DisplayName("사용여부가 비어있는 상태라면 변경되지 않음")
        void notChange(String inputUseYn) {
            //given
            final String name = "name";
            final String englishName = "englishName";
            final Flag useYn = Flag.Y;

            final Franchise franchise = new Franchise(1L, name, englishName, useYn);

            //when
            franchise.modifyEnglishName(inputUseYn);

            //then
            assertThat(franchise.getEnglishName()).isEqualTo(englishName);
        }
    }
}