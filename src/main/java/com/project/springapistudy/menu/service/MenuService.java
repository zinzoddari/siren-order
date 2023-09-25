package com.project.springapistudy.menu.service;

import com.project.springapistudy.menu.dto.MenuSaveRequest;
import com.project.springapistudy.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public Long registerMenu(MenuSaveRequest request) {
        return menuRepository.save(request.toEntity()).getMenuId();
    }
}
