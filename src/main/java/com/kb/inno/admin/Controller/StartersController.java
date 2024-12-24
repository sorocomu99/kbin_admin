/**
 * 파일명     : StartersController.java
 * 화면명     : KB 스타터스 관리
 * 설명       : KB 스타터스 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.StartersDTO;
import com.kb.inno.admin.Service.StartersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/starters")
public class StartersController {
    
    // 서비스 연결
    private final StartersService startersService;
    
    // 공통 경로 설정
    @Value("/starters")
    private String directory;
    
    // KB 스타터스 조회
    @RequestMapping("/info")
    public String select(Model model) {
        StartersDTO result = startersService.select();
        model.addAttribute("result", result);
        return directory + "/starters";
    }

    // KB 스타터스 저장
    @PostMapping("/save")
    public String save(RedirectAttributes redirectAttributes, StartersDTO startersDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result;

        // 추가, 수정 처리
        if (startersDTO.getStar_sn() == 0) {
            result = startersService.insert(startersDTO, loginId);
        } else {
            result = startersService.update(startersDTO, loginId);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "저장이 완료되었습니다.");
            return "redirect:" + directory + "/info";
        } else {
            redirectAttributes.addFlashAttribute("msg", "저장이 실패했습니다.");
            return directory + "/starters";
        }
    }
}
