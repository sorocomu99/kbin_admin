/**
 * 파일명     : InterchangeController.java
 * 화면명     : 글로벌 – 현지교류 지원 관리
 * 설명       : 글로벌 – 현지교류 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.11
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.InterchangeDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.admin.Service.InterchangeService;
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
@RequestMapping("/interchange")
public class InterchangeController {

    // 서비스 연결
    private final InterchangeService interchangeService;

    // 공통 경로 설정
    @Value("/interchange")
    private String directory;

    // 글로벌 – 현지교류 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String list(@PathVariable int menuId, Model model) {
        List<InterchangeDTO> selectList = interchangeService.selectList();
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        return directory + "/interchange";
    }

    // 글로벌 – 현지교류 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/interchange_insert";
    }

    // 글로벌 – 현지교류 리스트 조회 (미리보기용)
    @PostMapping("/preview")
    public String preview(InterchangeDTO interchangeDTO, Model model) {
        interchangeService.selectListAll(model, interchangeDTO);
        return directory + "/interchange_preview";
    }

    // 글로벌 – 현지교류 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes,InterchangeDTO interchangeDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = interchangeService.insert(interchangeDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + interchangeDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/interchange_insert";
        }
    }

    // 글로벌 – 현지교류 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int exch_sn, Model model) {
        InterchangeDTO interchange = interchangeService.select(exch_sn);
        model.addAttribute("interchange", interchange);
        model.addAttribute("menuId", menuId);
        return directory + "/interchange_update";
    }

    // 글로벌 – 현지교류 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes,InterchangeDTO interchangeDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = interchangeService.update(interchangeDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + interchangeDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return "redirect:" + directory + "/place_update";
        }
    }

    // 글로벌 – 현지교류 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("exch_sn") int exch_sn) {
        interchangeService.delete(exch_sn);
        return "success";
    }
}
