package com.project.springapistudy.menu.repository;

import com.project.springapistudy.common.jpa.JpaConfig;
import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.entity.Menu;
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
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Nested
    @DisplayName("메뉴명에 대한 유효성 검증")
    class validateName {
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"가나다라마바사아자차카파타하가나다라마바사아자차카파타하가나다라마바사아자차카파타하"})
        @DisplayName("메뉴명이 null 혹은 32자가 넘었을 경우 등록 실패")
        void invalidName(String name) {
            //given
            Menu menu = Menu.builder()
                    .type(MenuType.BEVERAGE)
                    .name(name)
                    .useYn("Y")
                    .build();

            //when
            menuRepository.save(menu);

            //then
            assertThrows(Exception.class, () -> menuRepository.flush());
        }
    }

    @Test
    @DisplayName("menu 저장 성공")
    void saveSuccess() {
        //given
        final MenuType menuType = MenuType.BEVERAGE;
        final String name = "아메리카노";
        final String useYn = "Y";

        Menu menu = Menu.builder()
                .type(menuType)
                .name(name)
                .useYn(useYn)
                .build();

        //when
        Menu result = menuRepository.save(menu);
        menuRepository.flush();

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getMenuId()).isNotZero().isNotNull();
            softAssertions.assertThat(result.getType()).isEqualTo(menuType);
            softAssertions.assertThat(result.getName()).isEqualTo(name);
            softAssertions.assertThat(result.getUseYn()).isEqualTo(useYn);
        });
    }
}