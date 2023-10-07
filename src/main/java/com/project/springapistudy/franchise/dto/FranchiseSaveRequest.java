package com.project.springapistudy.franchise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

    @NotBlank(message = USE_YN_IS_NOT_NULL)
    private String useYn;

    private FranchiseSaveRequest(String name, String englishName, String useYn) {
        this.name = name;
        this.englishName = englishName;
        this.useYn = useYn;
    }

    public static FranchiseSaveRequest create(String name, String englishName, String useYn) {
        return new FranchiseSaveRequest(name, englishName, useYn);
    }
}
