package com.project.springapistudy.franchise.service;

import com.project.springapistudy.common.domain.Flag;
import com.project.springapistudy.franchise.domain.DuplicateException;
import com.project.springapistudy.franchise.dto.FranchiseResponse;
import com.project.springapistudy.franchise.dto.FranchiseSaveRequest;
import com.project.springapistudy.franchise.dto.FranchiseUpdateRequest;
import com.project.springapistudy.franchise.entity.Franchise;
import com.project.springapistudy.franchise.repository.FranchiseRepository;
import com.project.springapistudy.product.domain.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

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
            final Franchise expectedFranchise = new Franchise(franchiseId, "name", "englishName", Flag.Y);

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

    @Nested
    @DisplayName("프랜차이즈 등록")
    class registerFranchise {
        @Test
        @DisplayName("정상 등록 성공")
        void success() {
            //given
            final String name = "카페";
            final String englishName = null;
            final Flag useYn = Flag.Y;

            final FranchiseSaveRequest request = FranchiseSaveRequest.create(name, englishName, useYn);

            given(franchiseRepository.save(any(Franchise.class)))
                    .willReturn(new Franchise(1L, name, englishName, useYn));

            //when
            Long id = franchiseService.registerFranchise(request);

            //then
            assertThat(id).isNotZero();
        }

        @Test
        @DisplayName("이름 중복일 경우 중복 오류")
        void duplicateName() {
            //given
            final String name = "카페";
            final String englishName = null;
            final Flag useYn = Flag.Y;

            final FranchiseSaveRequest request = FranchiseSaveRequest.create(name, englishName, useYn);

            given(franchiseRepository.findByName(name)).willThrow(DuplicateException.class);

            //when & then
            assertThrows(DuplicateException.class, () -> franchiseService.registerFranchise(request));
        }
    }

    @Nested
    @DisplayName("프랜차이즈 수정")
    class modifyFranchise {
        @Test
        @DisplayName("수정에 성공")
        void success() {
            //given
            final Franchise mockFranchise = mock(Franchise.class);

            final long franchiseId = 1L;
            final String name = "수정할 이름";
            final String englishName = null;
            final FranchiseUpdateRequest request = FranchiseUpdateRequest.create(name, englishName);

            given(franchiseRepository.findById(franchiseId)).willReturn(Optional.of(mockFranchise));
            given(franchiseRepository.findByName(name)).willReturn(Optional.empty());

            //when & then
            assertDoesNotThrow(() -> franchiseService.modifyFranchise(franchiseId, request));

            then(mockFranchise).should().modifyName(name);
            then(mockFranchise).should().modifyEnglishName(englishName);
        }

        @Test
        @DisplayName("수정하고자 하는 프랜차이즈를 찾지 못해 오류")
        void notFound() {
            //given
            final long franchiseId = -1L;
            final FranchiseUpdateRequest request = FranchiseUpdateRequest.create("name", "englishName");

            given(franchiseRepository.findById(franchiseId)).willThrow(NotFoundException.class);

            //when & then
            assertThrows(NotFoundException.class, () -> franchiseService.modifyFranchise(franchiseId, request));
        }

        @Test
        @DisplayName("수정하고자 하는 프랜차이즈 명이 중복이라 오류")
        void duplicateName() {
            //given
            final long franchiseId = -1L;
            final String name = "변경될 이름";
            final FranchiseUpdateRequest request = FranchiseUpdateRequest.create(name, "englishName");

            given(franchiseRepository.findById(franchiseId)).willReturn(Optional.of(new Franchise(null, null, Flag.Y)));
            given(franchiseRepository.findByName(name)).willReturn(Optional.of(new Franchise(name, null, Flag.Y)));

            //when & then
            assertThrows(DuplicateException.class, () -> franchiseService.modifyFranchise(franchiseId, request));
        }
    }
}