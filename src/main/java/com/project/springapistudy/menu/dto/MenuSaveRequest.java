package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.project.springapistudy.menu.domain.MenuValidateMessage.MENU_TYPE_IS_NOT_NULL;
import static com.project.springapistudy.menu.domain.MenuValidateMessage.NAME_IS_NOT_NULL;
import static com.project.springapistudy.menu.domain.MenuValidateMessage.NAME_SIZE_INVALID;
import static com.project.springapistudy.menu.domain.MenuValidateMessage.USE_YN_IS_NOT_NULL;

@Getter
@AllArgsConstructor
public class MenuSaveRequest {
    @NotNull(message = MENU_TYPE_IS_NOT_NULL)
    private MenuType type;

    @Size(max = 32, message = NAME_SIZE_INVALID)
    @NotEmpty(message = NAME_IS_NOT_NULL)
    private String name;

    @NotEmpty(message = USE_YN_IS_NOT_NULL)
    private String useYn;

    public static MenuSaveRequest create(MenuType type, String name, String useYn) {
        return new MenuSaveRequest(type, name, useYn);
    }

    public Menu toEntity() {
        return Menu.createMenu(this.type, this.name, this.useYn);
    }
}
