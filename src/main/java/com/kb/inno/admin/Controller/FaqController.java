/**
 * 파일명     : FaqController.java
 * 화면명     : FAQ 관리
 * 설명       : FAQ 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.12
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.Service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {
    // 서비스 연결
    private final FaqService faqService;
    
    // 공통 경로 설정
    @Value("/faq")
    private String directory;
    
    // FAQ 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(Model model, @PathVariable int menuId,
                             @RequestParam(value = "ctgry" , required = false) int ctgry,
                             @RequestParam(value = "type", required = false, defaultValue = "") String type,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        faqService.selectList(menuId, ctgry, model, type, keyword, page);
        return directory + "/faq";
    }

    // FAQ 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, @RequestParam(value = "ctgry", required = false) int ctgry, Model model) {
        Map<String, Object> result = faqService.selectCategory(ctgry);
        model.addAttribute("menuId", menuId);
        model.addAttribute("ctgry_sn", result.get("CTGRY_SN"));
        model.addAttribute("ctgry_nm", result.get("CTGRY_NM"));
        return directory + "/faq_insert";
    }

    // FAQ 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, FaqDTO faqDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = faqService.insert(faqDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + faqDTO.getMenu_id() + "?ctgry=" + faqDTO.getFaq_ctgry_sn();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/faq";
        }
    }

    // FAQ 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int faq_sn, @RequestParam int ctgry, Model model) {
        FaqDTO faq = faqService.select(faq_sn);
        model.addAttribute("faq", faq);
        model.addAttribute("menuId", menuId);
        model.addAttribute("ctgry", ctgry);
        return directory + "/faq_update";
    }

    // FAQ 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, FaqDTO faqDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = faqService.update(faqDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
             return "redirect:" + directory + "/list/" + faqDTO.getMenu_id() + "?ctgry=" + faqDTO.getFaq_ctgry_sn();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/faq";
        }
    }

    // FAQ 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("faq_sn") int faq_sn) {
        faqService.delete(faq_sn);
        return "success";
    }
}
