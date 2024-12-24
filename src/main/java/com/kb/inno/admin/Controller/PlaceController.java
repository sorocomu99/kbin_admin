/**
 * 파일명     : PlaceController.java
 * 화면명     : 육성공간 관리
 * 설명       : 육성공간 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.06
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.PlaceDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.admin.Service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    // 서비스 연결
    private final PlaceService placeService;

    // 공통 경로 설정
    @Value("/place")
    private String directory;

    // 육성공간 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(@PathVariable int menuId, Model model) {
        List<PlaceDTO> selectList = placeService.selectList();
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        return directory + "/place";
    }

    // 육성공간 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/place_insert";
    }

    // 육성공간 리스트 조회 (미리보기용)
    @PostMapping("/preview")
    public String preview(PlaceDTO placeDTO, Model model) {
        placeService.selectListAll(model, placeDTO);
        return directory + "/place_preview";
    }

    // 육성공간 상세 페이지 이동
    @PostMapping("/detail")
    public String update(@RequestParam int menuId, @RequestParam int plc_sn, Model model) {
        PlaceDTO place = placeService.select(plc_sn);
        model.addAttribute("place", place);
        model.addAttribute("menuId", menuId);
        return directory + "/place_update";
    }

    // 육성공간 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, PlaceDTO placeDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = placeService.insert(placeDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + placeDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/place_insert";
        }
    }

    // 육성공간 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, PlaceDTO placeDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = placeService.update(placeDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + placeDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return "redirect:" + directory + "/place_update";
        }
    }

    // 육성공간 삭제
    @PostMapping("/delete")
    public String delete(@RequestParam("plc_sn") int plc_sn) {
        placeService.delete(plc_sn);
        return directory + "/place";
    }
}
