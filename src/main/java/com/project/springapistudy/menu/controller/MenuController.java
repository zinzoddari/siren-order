package com.project.springapistudy.menu.controller;

import com.project.springapistudy.menu.dto.MenuResponse;
import com.project.springapistudy.menu.dto.MenuSaveRequest;
import com.project.springapistudy.menu.dto.MenuUpdateRequest;
import com.project.springapistudy.menu.service.MenuService;
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

    @PutMapping("/{menuId}")
    public void modifyMenu(@PathVariable @Min(1) long menuId, @RequestBody @Valid MenuUpdateRequest request) {
        menuService.modifyMenu(menuId, request);
    }

    @DeleteMapping("/{menuId}")
    public void removeMenu(@PathVariable @Min(1) long menuId) {
        menuService.removeMenu(menuId);
    }
}
