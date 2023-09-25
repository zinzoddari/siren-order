package com.project.springapistudy.menu.service;

import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.dto.MenuSaveRequest;
import com.project.springapistudy.menu.entity.Menu;
import com.project.springapistudy.menu.repository.MenuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
            MenuSaveRequest request = MenuSaveRequest.builder()
                    .type(MenuType.BEVERAGE)
                    .name("얼음 뺀 아이스 아메리카노")
                    .useYn("Y")
                    .build();

            Menu expectedMenu = request.toEntity();

            given(menuRepository.save(any(Menu.class))).willReturn(expectedMenu);

            //when & then
            //TODO: 조회 기능 만들어지면 결과 검증으로 수정 필요
            assertDoesNotThrow(() -> menuService.registerMenu(request));
        }
    }
}