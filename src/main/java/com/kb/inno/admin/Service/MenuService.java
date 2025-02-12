/**
 * 파일명     : MenuService.java
 * 화면명     : 메뉴 관리
 * 설명       : 메뉴 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.MenuDAO;
import com.kb.inno.admin.DTO.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    // DAO 연결
    private final MenuDAO menuDAO;

    // 메뉴 조회
    public List<MenuDTO> selectListConfig() {
        return menuDAO.selectListConfig();
    }

    // 메뉴 조회
    public List<MenuDTO> selectList() {
        return menuDAO.selectList();
    }

    // 메뉴 상세 조회
    public MenuDTO select(int menu_sn) {
        return menuDAO.select(menu_sn);
    }

    // 메뉴 수정
    public int update(MenuDTO menuDTO) {
        return menuDAO.update(menuDTO);
    }

    public void getPreview(MenuDTO menuDTO, Model model) {
        List<MenuDTO> selectList = this.selectList();
        List<MenuDTO> updatedList = new ArrayList<>();

        if (menuDTO.getMenu_sn() != 0) {
            for (MenuDTO menu : selectList) {
                if (menu.getMenu_sn() == menuDTO.getMenu_sn()) {
                    menu.setMenu_nm(menuDTO.getMenu_nm());
                    menu.setUse_yn(menuDTO.getUse_yn());
                }
            }
        }

        for (MenuDTO menu : selectList) {
            if (menu.getMenu_depth() == 1) {
                boolean hasActiveSubMenu = false;
                for (MenuDTO subMenu : selectList) {
                    if (subMenu.getMenu_depth() == 2 && subMenu.getMenu_up_sn() == menu.getMenu_sn() && "Y".equals(subMenu.getUse_yn())) {
                        hasActiveSubMenu = true;
                        break;
                    }
                }
                if (hasActiveSubMenu) {
                    updatedList.add(menu);
                }
            } else if (menu.getMenu_depth() == 2 && "Y".equals(menu.getUse_yn())) {
                updatedList.add(menu);
            }
        }
        model.addAttribute("selectList", updatedList);
    }
}
