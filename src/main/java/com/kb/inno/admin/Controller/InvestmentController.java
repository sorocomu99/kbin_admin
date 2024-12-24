/**
 * 파일명     : InvestmentController.java
 * 화면명     : 국내 프로그램 - 투자 그래프 관리
 * 설명       : 투자 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.InvestmentDTO;
import com.kb.inno.admin.DTO.ResultDTO;
import com.kb.inno.admin.Service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/investment")
public class InvestmentController {

    // 서비스 연결
    private final InvestmentService investmentService;

    // 공통 경로 설정
    @Value("/investment")
    private String directory;

    // 국내 프로그램 - 투자 그래프 조회
    @RequestMapping("/info/{menuId}")
    public String select(@PathVariable int menuId, Model model) {
        InvestmentDTO investment = investmentService.select();
        model.addAttribute("investment", investment);
        model.addAttribute("menuId", menuId);
        return directory + "/investment";
    }

    // 국내 프로그램 - 투자 그래프 미리보기
    @PostMapping("/preview")
    public String preview(InvestmentDTO investmentDTO, Model model) {
        investmentService.preview(investmentDTO, model);
        return directory + "/investment_preview";
    }

    // 국내 프로그램 - 투자 그래프 저장
    @PostMapping("/save")
    public String save(RedirectAttributes redirectAttributes, InvestmentDTO investmentDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result;

        // 추가, 수정 처리
        if (investmentDTO.getGraph_sn() == 0) {
            result = investmentService.insert(investmentDTO, loginId);
        } else {
            result = investmentService.update(investmentDTO, loginId);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "저장이 완료되었습니다.");
            return "redirect:" + directory + "/info/" + investmentDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "저장이 실패했습니다.");
            return directory + "/investment";
        }
    }
}
