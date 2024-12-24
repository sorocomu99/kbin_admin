/**
 * 파일명     : PromoteController.java
 * 화면명     : 국내 프로그램 - 육성 그래프 관리
 * 설명       : 국내 프로그램 - 육성 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.PromoteDTO;
import com.kb.inno.admin.DTO.ResultDTO;
import com.kb.inno.admin.Service.PromoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/promote")
public class PromoteController {

    // 서비스 연결
    private final PromoteService promoteService;

    // 공통 경로 설정
    @Value("/promote")
    private String directory;

    // 국내 프로그램 - 육성 그래프 조회
    @GetMapping("/info/{menuId}")
    public String select(@PathVariable int menuId, Model model) {
        PromoteDTO promote = promoteService.select();
        model.addAttribute("promote", promote);
        model.addAttribute("menuId", menuId);
        return directory + "/promote";
    }

    // 육성 현황 미리보기
    @PostMapping("/preview")
    public String preview(PromoteDTO promoteDTO, Model model) {
        promoteService.preview(promoteDTO, model);
        return directory + "/promote_preview";
    }

    // 국내 프로그램 - 육성 그래프 저장
    @PostMapping("/save")
    public String save(RedirectAttributes redirectAttributes, PromoteDTO promoteDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result;

        // 추가, 수정 처리
        if (promoteDTO.getGraph_sn() == 0) {
            result = promoteService.insert(promoteDTO, loginId);
        } else {
            result = promoteService.update(promoteDTO, loginId);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "저장이 완료되었습니다.");
            return "redirect:" + directory + "/info/" + promoteDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "저장이 실패했습니다.");
            return directory + "/promote/" + promoteDTO.getMenu_id();
        }
    }
}
