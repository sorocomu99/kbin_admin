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

        if(menuDTO.getMenu_sn() != 0) {
            for(int i = 0; i < selectList.size(); i++) {
                if(selectList.get(i).getMenu_sn() == menuDTO.getMenu_sn()){
                    selectList.get(i).setMenu_nm(menuDTO.getMenu_nm());
                    selectList.get(i).setUse_yn(menuDTO.getUse_yn());
                }
            }
        }
        model.addAttribute("selectList", selectList);
    }
}
