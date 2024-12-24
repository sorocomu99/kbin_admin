/**
 * 파일명     : PopupController.java
 * 화면명     : 팝업 관리
 * 설명       : 팝업 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.MenuDTO;
import com.kb.inno.admin.DTO.PopupDTO;
import com.kb.inno.admin.Service.MenuService;
import com.kb.inno.admin.Service.PopupService;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
//import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/popup")
public class PopupController {
    
    // 서비스 연결
    private final PopupService popupService;

    // 메뉴 서비스 연결
    private final MenuService menuService;

    // 디렉터리 공통
    @Value("/popup")
    private String directory;

    // 팝업 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(@PathVariable int menuId, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        popupService.selectList(model, page, menuId);
        return directory + "/popup";
    }
    
    // 팝업 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/popup_insert";
    }

    // 팝업 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int popup_sn, Model model) {
        PopupDTO popup = popupService.select(popup_sn);
        model.addAttribute("popup", popup);
        model.addAttribute("menuId", menuId);
        return directory + "/popup_update";
    }

    // 이미지 업로드
    @ResponseBody
    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        FileUploader fileUploader = new FileUploader();
        return ResponseEntity.ok(fileUploader.summernoteInsertImage(file));
    }

    // 팝업 미리보기
    @PostMapping("/preview")
    public String preview(PopupDTO popupDTO, Model model) {
        List<PopupDTO> selectList = popupService.selectListAll(popupDTO);
        model.addAttribute("selectList", selectList);
        model.addAttribute("popup", popupDTO);
        return directory + "/popup_preview";
    }

    // 팝업 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, PopupDTO popupDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = popupService.insert(popupDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + popupDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/popup_insert";
        }
    }

    // 팝업 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, PopupDTO popupDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = popupService.update(popupDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + popupDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/popup_update";
        }
    }

    // 팝업 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("popupId") int popupId) {
        popupService.delete(popupId);
        return "success";
    }
}
