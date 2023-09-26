package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuResponse {
    private Long menuId;
    private MenuType type;
    private String name;
    private String useYn;

    public static MenuResponse fromEntity(Menu menu) {
        return MenuResponse.builder()
                .menuId(menu.getMenuId())
                .type(menu.getType())
                .name(menu.getName())
                .useYn(menu.getUseYn())
                .build();
    }
}
