/**
 * 파일명     : MenuController.java
 * 화면명     : 메뉴 관리
 * 설명       : 메뉴 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.MenuDTO;
import com.kb.inno.admin.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    // 서비스 연결
    private final MenuService menuService;

    // 공통 경로 설정
    @Value("/menu")
    private String directory;

    // 공통 메뉴 조회
    @RequestMapping("/")
    public ResponseEntity<List<MenuDTO>> index() {
        List<MenuDTO> selectList = menuService.selectListConfig();
        return ResponseEntity.ok().body(selectList);
    }
    
    // 메뉴 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String select(@PathVariable int menuId, Model model) {
        List<MenuDTO> selectList = menuService.selectList();
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        return directory + "/menu";
    }
    
    // 메뉴 상세 조회
    @PostMapping("/info")
    public ResponseEntity<MenuDTO> detail(@RequestParam("menu_sn") int menu_sn, Model model) {
        MenuDTO select = menuService.select(menu_sn);
        return ResponseEntity.ok().body(select);
    }

    // 메뉴 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, MenuDTO menuDTO) {
        int result = menuService.update(menuDTO);
        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "저장이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + menuDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "저장이 실패했습니다.");
            return directory + "/menu";
        }
    }
}
