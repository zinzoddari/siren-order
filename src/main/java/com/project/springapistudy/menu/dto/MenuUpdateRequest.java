package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.project.springapistudy.menu.domain.MenuValidateMessage.*;

@Getter
@Builder
public class MenuUpdateRequest {
    @NotNull(message = MENU_TYPE_IS_NOT_NULL)
    private MenuType type;

    @Size(max = 32, message = NAME_SIZE_INVALID)
    @NotEmpty(message = NAME_IS_NOT_NULL)
    private String name;
}
