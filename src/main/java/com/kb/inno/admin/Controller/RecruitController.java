/**
 * 파일명     : RecruitController.java
 * 화면명     : 국내 프로그램 - 채용 지원 관리
 * 설명       : 국내 프로그램 - 채용 지원 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.RecruitDTO;
import com.kb.inno.admin.DTO.ResultDTO;
import com.kb.inno.admin.Service.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recruit")
public class RecruitController {

    // 서비스 연결
    private final RecruitService recruitService;

    // 공통 경로 설정
    @Value("/recruit")
    private String directory;
    
    // 국내 프로그램 - 채용 지원 조회
    @RequestMapping("/info/{menuId}")
    public String select(@PathVariable int menuId, Model model) {
        RecruitDTO recruit = recruitService.select();
        model.addAttribute("recruit", recruit);
        model.addAttribute("menuId", menuId);
        return directory + "/recruit";
    }

    // 국내 프로그램 - 채용 지원 미리보기
    @PostMapping("/preview")
    public String preview(RecruitDTO recruitDTO, Model model) {
        BigDecimal amount = BigDecimal.valueOf(recruitDTO.getSprt_amt());
        DecimalFormat df = new DecimalFormat("####.###");
        recruitDTO.setRet_sprt_amt(df.format(amount));
        model.addAttribute("recruit", recruitDTO);
        return directory + "/recruit_preview";
    }

    // 국내 프로그램 - 채용 지원 저장
    @PostMapping("/save")
    public String save(RedirectAttributes redirectAttributes, RecruitDTO recruitDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result;

        // 추가, 수정 처리
        if(recruitDTO.getEmploy_sn() == 0) {
            result = recruitService.insert(recruitDTO, loginId);
        } else {
            result = recruitService.update(recruitDTO, loginId);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "저장이 완료 되었습니다.");
            return "redirect:" + directory + "/info/" + recruitDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "저장이 실패했습니다.");
            return directory + "/recruit";
        }
    }
}
