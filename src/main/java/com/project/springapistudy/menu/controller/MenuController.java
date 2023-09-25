package com.project.springapistudy.menu.controller;

import com.project.springapistudy.menu.dto.MenuSaveRequest;
import com.project.springapistudy.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<Void> registerMenu(@RequestBody @Valid MenuSaveRequest request) {
        Long response = menuService.registerMenu(request);

        return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{menuId}")
                    .buildAndExpand(response)
                    .toUri())
                .build();
    }
}
