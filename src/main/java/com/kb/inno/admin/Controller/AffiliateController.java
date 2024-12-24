/**
 * 파일명     : AffiliateController.java
 * 화면명     : 국내 프로그램 - 제휴 사례 관리
 * 설명       : 국내 프로그램 - 제휴 사례 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.31
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.admin.Service.AffiliateService;
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
@RequestMapping("/affiliate")
public class AffiliateController {

    // 서비스 연결
    private final AffiliateService affiliateService;

    // 공통 경로 설정
    @Value("/affiliate")
    private String directory;

    // 국내 프로그램 - 제휴 사례 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(@PathVariable int menuId, Model model) {
        List<AffiliateDTO> selectList = affiliateService.selectList();
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        return directory + "/affiliate";
    }

    // 국내 프로그램 - 제휴 사례 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/affiliate_insert";
    }

    // 국내 프로그램 - 제휴 사례 리스트 조회 (미리보기용)
    @PostMapping("/preview")
    public String preview(AffiliateDTO affiliateDTO, Model model) {
        affiliateService.selectListAll(model, affiliateDTO);
        return directory + "/affiliate_preview";
    }
    
    // 국내 프로그램 - 제휴 사례 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int affiliate_sn, Model model) {
        AffiliateDTO affiliate = affiliateService.select(affiliate_sn);
        model.addAttribute("affiliate", affiliate);
        model.addAttribute("menuId", menuId);
        return directory + "/affiliate_update";
    }

    // 국내 프로그램 - 제휴 사례 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, AffiliateDTO affiliateDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = affiliateService.insert(affiliateDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + affiliateDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/affiliate_insert";
        }
    }

    // 국내 프로그램 - 제휴 사례 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, AffiliateDTO affiliateDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = affiliateService.update(affiliateDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + affiliateDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/affiliate_update";
        }
    }

    // 국내 프로그램 - 제휴 사례 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("affiliate_sn") int affiliate_sn) {
        affiliateService.delete(affiliate_sn);
        return "success";
    }
}
