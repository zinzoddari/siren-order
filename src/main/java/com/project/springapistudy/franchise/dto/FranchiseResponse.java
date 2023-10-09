package com.project.springapistudy.franchise.dto;

import com.project.springapistudy.franchise.entity.Franchise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
@AllArgsConstructor
public class FranchiseResponse {
    private Long franchiseId;
    private String name;
    private String englishName;

    public static FranchiseResponse fromEntity(Franchise franchise) {
        if(ObjectUtils.isEmpty(franchise)) {
            return null;
        }

        return new FranchiseResponse(franchise.getId(), franchise.getName(), franchise.getEnglishName());
    }
}