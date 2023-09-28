package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.project.springapistudy.menu.domain.MenuValidateMessage.MENU_TYPE_IS_NOT_NULL;
import static com.project.springapistudy.menu.domain.MenuValidateMessage.NAME_IS_NOT_NULL;
import static com.project.springapistudy.menu.domain.MenuValidateMessage.NAME_SIZE_INVALID;

@Getter
public class MenuUpdateRequest {
    @NotNull(message = MENU_TYPE_IS_NOT_NULL)
    private MenuType type;

    @Size(max = 32, message = NAME_SIZE_INVALID)
    @NotEmpty(message = NAME_IS_NOT_NULL)
    private String name;

    public MenuUpdateRequest(MenuType type, String name) {
        this.type = type;
        this.name = name;
    }

    public static MenuUpdateRequest create(MenuType type, String name) {
        return new MenuUpdateRequest(type, name);
    }
}
