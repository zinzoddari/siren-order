package com.project.springapistudy.menu.controller;

import com.project.springapistudy.menu.dto.MenuResponse;
import com.project.springapistudy.menu.dto.MenuSaveRequest;
import com.project.springapistudy.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/{menuId}")
    public MenuResponse findMenuId(@PathVariable @Min(1) long menuId) {
        return menuService.findMenu(menuId);
    }

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
