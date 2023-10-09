package com.project.springapistudy.franchise.service;

import com.project.springapistudy.franchise.domain.DuplicateException;
import com.project.springapistudy.franchise.dto.FranchiseResponse;
import com.project.springapistudy.franchise.dto.FranchiseSaveRequest;
import com.project.springapistudy.franchise.dto.FranchiseUpdateRequest;
import com.project.springapistudy.franchise.entity.Franchise;
import com.project.springapistudy.franchise.repository.FranchiseRepository;
import com.project.springapistudy.product.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FranchiseService {
    private final FranchiseRepository franchiseRepository;

    @Transactional(readOnly = true)
    public FranchiseResponse findFranchise(Long franchiseId) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(NotFoundException::new);

        if(franchise.isNotUse()) {
            franchise = null;
        }

        return FranchiseResponse.fromEntity(franchise);
    }

    @Transactional
    public Long registerFranchise(FranchiseSaveRequest request) {
        franchiseRepository.findByName(request.getName()).ifPresent(franchise -> {
            throw new DuplicateException();
        });

        return franchiseRepository.save(request.toEntity())
                .getId();
    }

    @Transactional
    public void modifyFranchise(long franchiseId, FranchiseUpdateRequest request) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(NotFoundException::new);

        franchiseRepository.findByName(request.getName()).ifPresent(f -> {
            throw new DuplicateException();
        });

        franchise.modifyName(request.getName());
        franchise.modifyEnglishName(request.getEnglishName());
    }
}
