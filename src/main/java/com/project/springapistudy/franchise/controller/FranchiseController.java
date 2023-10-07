package com.project.springapistudy.franchise.controller;

import com.project.springapistudy.franchise.dto.FranchiseResponse;
import com.project.springapistudy.franchise.service.FranchiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/franchise")
public class FranchiseController {
    private final FranchiseService franchiseService;

    @GetMapping("/{franchiseId}")
    public FranchiseResponse findProductId(@PathVariable @Min(1) long franchiseId) {
        return franchiseService.findFranchise(franchiseId);
    }
}
