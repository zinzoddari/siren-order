package com.project.springapistudy.menu.service;

import com.project.springapistudy.menu.domain.NotFoundException;
import com.project.springapistudy.menu.dto.MenuResponse;
import com.project.springapistudy.menu.dto.MenuSaveRequest;
import com.project.springapistudy.menu.dto.MenuUpdateRequest;
import com.project.springapistudy.menu.entity.Menu;
import com.project.springapistudy.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public MenuResponse findMenu(long menuId) {
        //TODO: useYn이 N이면 조회가 되지 않는 요건이 필요할 것 같음
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundException::new);

        return MenuResponse.fromEntity(menu);
    }

    @Transactional
    public Long registerMenu(MenuSaveRequest request) {
        return menuRepository.save(request.toEntity()).getMenuId();
    }

    @Transactional
    public void modifyMenu(Long menuId, MenuUpdateRequest request) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundException::new);

        menu.changeName(request.getName());
        menu.changeType(request.getType());
    }

    @Transactional
    public void removeMenu(long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundException::new);

        menu.remove();
    }
}
