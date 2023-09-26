package com.project.springapistudy.menu.entity;

import com.project.springapistudy.common.jpa.BaseEntity;
import com.project.springapistudy.menu.domain.MenuType;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "MENU")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {
    @Id
    @Comment("메뉴 ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MENU_ID", nullable = false)
    private Long menuId;

    @Comment("메뉴 종류")
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private MenuType type;

    @NotEmpty
    @Comment("메뉴 명")
    @Column(name = "NAME", nullable = false, length = 32)
    private String name;

    @Comment("사용여부")
    @Column(name = "USE_YN")
    private String useYn;

    public void changeName(String name) {
        if (!ObjectUtils.isEmpty(name)) {
            this.name = name;
        }
    }

    public void changeType(MenuType menuType) {
        if (!ObjectUtils.isEmpty(menuType)) {
            this.type = menuType;
        }
    }

    public void remove() {
        this.useYn = "N";
    }
}
