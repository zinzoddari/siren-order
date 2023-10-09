package com.project.springapistudy.franchise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.project.springapistudy.franchise.domain.FranchiseValidateMessage.ENGLISH_NAME_SIZE_INVALID;
import static com.project.springapistudy.franchise.domain.FranchiseValidateMessage.NAME_IS_NOT_NULL;
import static com.project.springapistudy.franchise.domain.FranchiseValidateMessage.NAME_SIZE_INVALID;

@Getter
@AllArgsConstructor
public class FranchiseUpdateRequest {
    @NotBlank(message = NAME_IS_NOT_NULL)
    @Size(max = 32, message = NAME_SIZE_INVALID)
    private String name;

    @Size(max = 64, message = ENGLISH_NAME_SIZE_INVALID)
    private String englishName;

    public static FranchiseUpdateRequest create(String name, String englishName) {
        return new FranchiseUpdateRequest(name, englishName);
    }
}
