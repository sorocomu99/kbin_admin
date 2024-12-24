/**
 * 파일명     : CooperationController.java
 * 화면명     : 협력 기관 관리
 * 설명       : 협력 기관 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.CooperationDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.admin.Service.CooperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cooperation")
public class CooperationController {

    // 서비스 연결
    private final CooperationService cooperationService;

    // 공통 경로 설정
    @Value("/cooperation")
    private String directory;

    // 협력 기관 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(@PathVariable int menuId, Model model) {
        List<CooperationDTO> selectList = cooperationService.selectList();
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        return directory + "/cooperation";
    }

    // 협력 기관 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/cooperation_insert";
    }

    // 협력 기관 리스트 조회 (미리보기용)
    @PostMapping("/preview")
    public String preview(CooperationDTO cooperationDTO, Model model) {
        cooperationService.selectListAll(model, cooperationDTO);
        return directory + "/cooperation_preview";
    }

    // 협력 기관 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int coope_sn, Model model) {
        CooperationDTO cooperation = cooperationService.select(coope_sn);
        model.addAttribute("cooperation", cooperation);
        model.addAttribute("menuId", menuId);
        return directory + "/cooperation_update";
    }

    // 협력 기관 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, CooperationDTO cooperationDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = cooperationService.insert(cooperationDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + cooperationDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/cooperation_insert";
        }
    }

    // 협력 기관 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, CooperationDTO cooperationDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = cooperationService.update(cooperationDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + cooperationDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/cooperation_update";
        }
    }

    // 협력 기관 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("coope_sn") int coope_sn) {
        cooperationService.delete(coope_sn);
        return "success";
    }
}
