package com.project.springapistudy.franchise.entity;

import com.project.springapistudy.common.jpa.BaseEntity;
import com.project.springapistudy.common.utils.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Table(name = "FRANCHISE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Franchise extends BaseEntity {
    @Id
    @Comment("프랜차이즈 ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FRANCHISE_ID", nullable = false)
    private Long id;

    @NotEmpty
    @Comment("프랜차이즈 명")
    @Column(name = "NAME", nullable = false, length = 64)
    private String name;

    @Comment("영문명")
    @Column(name = "ENGLISH_NAME")
    private String englishName;

    @Comment("사용여부")
    @Column(name = "USE_YN")
    private String useYn;

    public Franchise(String name, String englishName, String useYn) {
        this.name = name;
        this.englishName = englishName;
        this.useYn = useYn;
    }

    public static Franchise create(String name, String englishName, String useYn) {
        return new Franchise(name, englishName, useYn);
    }

    public void modifyName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            this.name = name;
        }
    }

    public void modifyEnglishName(String englishName) {
        if (StringUtils.isNotEmpty(englishName)) {
            this.englishName = englishName;
        }
    }

    public void modifyUseYn(String useYn) {
        if (StringUtils.isNotEmpty(useYn)) {
            this.useYn = useYn;
        }
    }
}
