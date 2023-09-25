package com.project.springapistudy.menu.dto;

import com.project.springapistudy.menu.domain.MenuType;
import com.project.springapistudy.menu.entity.Menu;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.project.springapistudy.menu.domain.MenuValidateMessage.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuSaveRequest {
    @NotNull(message = MENU_TYPE_IS_NOT_NULL)
    private MenuType type;

    @Size(max = 32, message = NAME_SIZE_INVALID)
    @NotEmpty(message = NAME_IS_NOT_NULL)
    private String name;

    @NotEmpty(message = USE_YN_IS_NOT_NULL)
    private String useYn;

    public Menu toEntity() {
        return Menu.builder()
                .name(this.name)
                .type(this.type)
                .useYn(this.useYn)
                .build();
    }
}
