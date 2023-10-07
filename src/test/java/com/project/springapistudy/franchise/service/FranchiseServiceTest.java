package com.project.springapistudy.franchise.service;

import com.project.springapistudy.franchise.dto.FranchiseResponse;
import com.project.springapistudy.franchise.entity.Franchise;
import com.project.springapistudy.franchise.repository.FranchiseRepository;
import com.project.springapistudy.product.domain.NotFoundException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.SoftAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {
    @InjectMocks
    private FranchiseService franchiseService;

    @Mock
    private FranchiseRepository franchiseRepository;

    @Nested
    @DisplayName("프랜차이즈 단건 조회")
    class findFranchise {
        @Test
        @DisplayName("조회 성공")
        void success() {
            //given
            final Long franchiseId = -1L;
            final Franchise expectedFranchise = new Franchise(franchiseId, "name", "englishName", "Y");

            given(franchiseRepository.findById(franchiseId)).willReturn(Optional.of(expectedFranchise));

            //when
            FranchiseResponse response = franchiseService.findFranchise(franchiseId);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.getFranchiseId()).isEqualTo(franchiseId);
                softAssertions.assertThat(response.getEnglishName()).isEqualTo(expectedFranchise.getEnglishName());
                softAssertions.assertThat(response.getName()).isEqualTo(expectedFranchise.getName());
            });
        }

        @Test
        @DisplayName("없는 값 조회시 오류")
        void notFoundException() {
            //given
            final Long franchiseId = -1L;

            given(franchiseRepository.findById(franchiseId)).willThrow(NotFoundException.class);

            //when & then
            assertThrows(NotFoundException.class, () -> franchiseService.findFranchise(franchiseId));
        }
    }
}