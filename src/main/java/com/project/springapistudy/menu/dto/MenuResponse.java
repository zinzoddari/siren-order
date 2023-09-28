package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuResponse {
    private Long menuId;
    private MenuType type;
    private String name;
    private String useYn;

    public static MenuResponse fromEntity(Menu menu) {
        return new MenuResponse(menu.getMenuId(), menu.getType(), menu.getName(), menu.getUseYn());
    }
}
