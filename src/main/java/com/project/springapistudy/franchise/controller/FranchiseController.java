package com.project.springapistudy.franchise.controller;

import com.project.springapistudy.franchise.dto.FranchiseResponse;
import com.project.springapistudy.franchise.dto.FranchiseSaveRequest;
import com.project.springapistudy.franchise.dto.FranchiseUpdateRequest;
import com.project.springapistudy.franchise.service.FranchiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/franchise")
class FranchiseController {
    private final FranchiseService franchiseService;

    @GetMapping("/{franchiseId}")
    public FranchiseResponse findFranchiseId(@PathVariable @Min(1) long franchiseId) {
        return franchiseService.findFranchise(franchiseId);
    }

    @PostMapping
    public ResponseEntity<Void> registerFranchise(@RequestBody @Valid FranchiseSaveRequest request) {
        Long response = franchiseService.registerFranchise(request);

        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{franchiseId}")
                        .buildAndExpand(response)
                        .toUri())
                .build();
    }

    @PutMapping("/{franchiseId}")
    public void modifyFranchise(@PathVariable @Min(1) long franchiseId
            , @RequestBody @Valid FranchiseUpdateRequest request) {
        franchiseService.modifyFranchise(franchiseId, request);
    }

    @DeleteMapping("/{franchiseId}")
    public void removeFranchise(@PathVariable @Min(1) long franchiseId) {
        franchiseService.removeFranchise(franchiseId);
    }
}
