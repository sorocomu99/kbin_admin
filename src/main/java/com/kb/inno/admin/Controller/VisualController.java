/**
 * 파일명     : VisualController.java
 * 화면명     : 메인 비주얼 관리
 * 설명       : 메인 비주얼 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.PopupDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.admin.Service.VisualService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/visual")
public class VisualController {
    
    // 서비스 연결
    private final VisualService visualService;
    
    // 메인 디렉토리 설정
    @Value("/visual")
    private String directory;

    /**
     * 메인 비주얼 미리보기
     * @param params
     * @param model
     * @return
     */
    @GetMapping("/preview")
    @ResponseBody
    public String preview(@RequestParam Map<String, String> params, Model model) {
        return params.toString();
    }
    
    // 메인 비주얼 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String visualList(@PathVariable int menuId, Model model) {
        List<VisualDTO> selectList = visualService.selectList();
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        return directory + "/visual";
    }

    // 메인 비주얼 리스트 조회 (미리보기용)
    @PostMapping("/preview")
    public String preview(VisualDTO visualDTO, Model model) {
        visualService.selectListAll(model, visualDTO);
        return directory + "/visual_preview";
    }

    // 메인 비주얼 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/visual_insert";
    }

    // 메인 비주얼 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int main_sn, Model model) {
        VisualDTO visual = visualService.select(main_sn);
        model.addAttribute("visual", visual);
        model.addAttribute("menuId", menuId);
        return directory + "/visual_update";
    }

    // 메인 비주얼 등록
    @PostMapping("/insert")
    public String insert(VisualDTO visualDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = visualService.insert(visualDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + visualDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/visual_insert";
        }
    }

    // 메인 비주얼 수정
    @PostMapping("/update")
    public String update(VisualDTO visualDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = visualService.update(visualDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + visualDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/visual_update";
        }
    }

    // 메인 비주얼 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("main_sn") int main_sn) {
        visualService.delete(main_sn);
        return "success";
    }
}
