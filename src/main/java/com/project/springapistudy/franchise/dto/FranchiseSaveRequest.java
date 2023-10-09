package com.project.springapistudy.franchise.dto;

import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.franchise.entity.Franchise;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.project.springapistudy.franchise.domain.FranchiseValidateMessage.ENGLISH_NAME_SIZE_INVALID;
import static com.project.springapistudy.franchise.domain.FranchiseValidateMessage.NAME_IS_NOT_NULL;
import static com.project.springapistudy.franchise.domain.FranchiseValidateMessage.NAME_SIZE_INVALID;
import static com.project.springapistudy.franchise.domain.FranchiseValidateMessage.USE_YN_IS_NOT_NULL;

@Getter
public class FranchiseSaveRequest {
    @NotBlank(message = NAME_IS_NOT_NULL)
    @Size(max = 32, message = NAME_SIZE_INVALID)
    private String name;

    @Size(max = 64, message = ENGLISH_NAME_SIZE_INVALID)
    private String englishName;

    @NotNull(message = USE_YN_IS_NOT_NULL)
    private Flag useYn;

    private FranchiseSaveRequest(String name, String englishName, Flag useYn) {
        this.name = name;
        this.englishName = englishName;
        this.useYn = useYn;
    }

    public static FranchiseSaveRequest create(String name, String englishName, Flag useYn) {
        return new FranchiseSaveRequest(name, englishName, useYn);
    }

    public Franchise toEntity() {
        return Franchise.create(this.name, this.englishName, this.useYn);
    }
}
