/**
 * 파일명     : HubController.java
 * 화면명     : HUB 센터 소식 관리
 * 설명       : HUB 센터 소식 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.14
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.HubDTO;
import com.kb.inno.admin.DTO.NoticeDTO;
import com.kb.inno.admin.Service.HubService;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hub")
public class HubController {

    // 서비스 연결
    private final HubService hubService;

    // 공통 경로 설정
    @Value("/hub")
    private String directory;

    // HUB 센터 소식 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(Model model, @PathVariable int menuId,
                             @RequestParam(value = "type", required = false, defaultValue = "") String type,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        hubService.selectList(menuId, model, type, keyword, page);
        return directory + "/hub";
    }

    // 공지사항 미리보기 페이지 이동
    @PostMapping("/preview")
    public String preview(HubDTO hub, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        hubService.preview(model, hub, loginId);

        return directory + "/hub_preview";
    }

    // HUB 센터 소식 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/hub_insert";
    }

    // 이미지 업로드
    @ResponseBody
    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        FileUploader fileUploader = new FileUploader();
        return ResponseEntity.ok(fileUploader.summernoteInsertImage(file));
    }

    // HUB 센터 소식 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, HubDTO hubDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = hubService.insert(hubDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + hubDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/notice_insert";
        }
    }

    // HUB 센터 소식 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int hub_sn, Model model) {
        HubDTO hub = hubService.select(hub_sn);
        model.addAttribute("hub", hub);
        model.addAttribute("menuId", menuId);
        return directory + "/hub_update";
    }

    // HUB 센터 소식 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, HubDTO hubDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        // 만약 del_yn이 null이면 N 값 강제 대입
        if(hubDTO.getDel_yn() == null || hubDTO.getDel_yn().equals("")) {
            hubDTO.setDel_yn("N");
        }

        int result = hubService.update(hubDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + hubDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/notice_update";
        }
    }

    // HUB 센터 소식 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("hub_sn") int hub_sn) {
        hubService.delete(hub_sn);
        return "success";
    }
}
