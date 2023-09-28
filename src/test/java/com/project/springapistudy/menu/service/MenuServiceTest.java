package com.project.springapistudy.menu.service;

import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.domain.NotFoundException;
import com.project.springapistudy.menu.dto.MenuResponse;
import com.project.springapistudy.menu.dto.MenuSaveRequest;
import com.project.springapistudy.menu.dto.MenuUpdateRequest;
import com.project.springapistudy.menu.entity.Menu;
import com.project.springapistudy.menu.repository.MenuRepository;
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
class MenuServiceTest {
    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Nested
    @DisplayName("메뉴 등록 검증")
    class registerMenu {
        @Test
        @DisplayName("메뉴 등록 성공")
        void success() {
            //given
            MenuSaveRequest request = MenuSaveRequest.create(MenuType.BEVERAGE, "얼음 뺀 아이스 아메리카노", "Y");

            Menu expectedMenu = request.toEntity();

            given(menuRepository.save(any(Menu.class))).willReturn(expectedMenu);

            //when & then
            assertDoesNotThrow(() -> menuService.registerMenu(request));
        }
    }

    @Nested
    @DisplayName("메뉴 단건 조회")
    class findMenu {
        @Test
        @DisplayName("올바른 menuId로 조회시 성공")
        void success() {
            //given
            final Long menuId = 1L;

            final Menu expetcedMenu = new Menu(menuId, MenuType.BEVERAGE, "name", "Y");

            given(menuRepository.findById(menuId)).willReturn(Optional.of(expetcedMenu));

            //when
            MenuResponse response = menuService.findMenu(menuId);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getMenuId()).isEqualTo(menuId);
                softAssertions.assertThat(response.getName()).isEqualTo(expetcedMenu.getName());
                softAssertions.assertThat(response.getType()).isEqualTo(expetcedMenu.getType());
            });
        }

        @Test
        @DisplayName("존재하지 않는 menuId로 조회시 오류 출력")
        void notFound() {
            //given
            final Long menuId = -1L;

            given(menuRepository.findById(menuId)).willReturn(Optional.empty());

            //when & then
            assertThrows(NotFoundException.class, () -> menuService.findMenu(menuId));
        }
    }

    @Nested
    @DisplayName("메뉴 수정")
    class modifyMenu {
        @Test
        @DisplayName("메뉴 수정 성공")
        void success() {
            //given
            final Long menuId = 1L;

            final Menu expetcedMenu = new Menu(menuId, MenuType.BEVERAGE, "name", "Y");

            final MenuUpdateRequest request = MenuUpdateRequest.create(MenuType.DESSERT, "댕장꿍");

            given(menuRepository.findById(menuId)).willReturn(Optional.ofNullable(expetcedMenu));

            //when & then
            assertDoesNotThrow(() -> menuService.modifyMenu(menuId, request));
        }

        @Test
        @DisplayName("존재하지 않는 menuId 수정 시도 시 오류")
        void invalidMenu() {
            //given
            final Long menuId = -1L;

            final MenuUpdateRequest request = MenuUpdateRequest.create(MenuType.DESSERT, "댕장꿍");

            given(menuRepository.findById(menuId)).willThrow(NotFoundException.class);

            //when & then
            assertThrows(NotFoundException.class, () -> menuService.modifyMenu(menuId, request));
        }
    }

    @Nested
    @DisplayName("메뉴 단건 삭제")
    class removeMenu {
        @Test
        @DisplayName("메뉴 단건 삭제 성공")
        void success() {
            //given
            final Long menuId = 1L;

            final Menu expetcedMenu = new Menu(menuId, MenuType.BEVERAGE, "name", "Y");

            given(menuRepository.findById(menuId)).willReturn(Optional.ofNullable(expetcedMenu));

            //when
            menuService.removeMenu(menuId);

            //then
            assertThat(expetcedMenu.getUseYn()).isEqualTo("N");
        }

        @Test
        @DisplayName("없는 메뉴 단건 삭제시 실패")
        void notFound() {
            //given
            final Long menuId = -1L;

            given(menuRepository.findById(menuId)).willThrow(NotFoundException.class);

            //when & then
            assertThrows(NotFoundException.class, () -> menuService.removeMenu(menuId));
        }
    }
}
