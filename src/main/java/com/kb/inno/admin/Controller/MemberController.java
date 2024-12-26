/**
 * 파일명     : MemberController.java
 * 화면명     : 관리자 계정 관리
 * 설명       : 관리자 계정 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.24
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.MemberDTO;
import com.kb.inno.admin.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // 디렉터리 공통
    @Value("/member")
    private String directory;
    
    // Service 연결
    private final MemberService memberService;

    /**
     * @param menuId
     * @param model
     * @return
     * 관리자 리스트 조회
     */
    @GetMapping("/list/{menuId}")
    public String adminList(@PathVariable int menuId, Model model) {
        List<MemberDTO> selectList = memberService.selectList();
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        return directory + "/member";
    }

    // 관리자 등록 페이지 이동
    @GetMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        // 수정 페이지와 같이 쓰고 있으므로 빈 DTO를 만들어 보내어 사용
        model.addAttribute("member", new MemberDTO());
        model.addAttribute("menuId", menuId);
        return directory + "/member_insert";
    }

    // 관리자 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, MemberDTO memberDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = memberService.insert(memberDTO, loginId);

        // 결과 메시지
        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료 되었습니다.");
            return "redirect:" + directory + "/list/" + memberDTO.getMenu_id();
        } else if(result == 2) {
            redirectAttributes.addFlashAttribute("msg", "이미 존재하는 아이디입니다.");
            return directory + "/member_insert";
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패하였습니다.");
            return directory + "/member_insert";
        }
    }

    // 관리자 수정 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int mngr_sn, Model model) {
        MemberDTO member = memberService.detail(mngr_sn);
        model.addAttribute("member", member);
        model.addAttribute("menuId", menuId);
        return directory + "/member_update";
    }

    // 관리자 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, MemberDTO memberDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = memberService.update(memberDTO, loginId);

        // 결과 메시지
        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료 되었습니다.");
            return "redirect:" + directory + "/list/" + memberDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패하였습니다.");
            return directory + "/member_insert";
        }
    }

    // 관리자 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("mngr_sn") int mngr_sn) {
        memberService.delete(mngr_sn);
        return "success";
    }
}

