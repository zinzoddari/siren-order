package com.project.springapistudy.franchise.service;

import com.project.springapistudy.franchise.dto.FranchiseResponse;
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

        return FranchiseResponse.fromEntity(franchise);
    }
}
