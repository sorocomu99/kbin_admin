package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.MemberDTO;
import com.kb.inno.admin.DTO.NoticeDTO;
import com.kb.inno.admin.Service.MemberService;
import com.kb.inno.admin.Service.NoticeService;
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
import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    // 서비스 연결
    private final NoticeService noticeService;

    // 공통 경로 설정
    @Value("/notice")
    public String directory;

    // 공지사항 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(@PathVariable int menuId, Model model,
                             @RequestParam(value = "type", required = false, defaultValue = "") String type,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        noticeService.selectList(menuId, model, type, keyword, page);
        return directory + "/notice";
    }

    // 공지사항 미리보기 페이지 이동
    @PostMapping("/preview")
    public String preview(NoticeDTO notice, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        noticeService.preview(model, notice, loginId);

        return directory + "/notice_preview";
    }

    // 공지사항 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/notice_insert";
    }

    // 이미지 업로드
    @ResponseBody
    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        FileUploader fileUploader = new FileUploader();
        return ResponseEntity.ok(fileUploader.summernoteInsertImage(file));
    }

    // 공지사항 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, NoticeDTO noticeDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        // 만약 ntc_yn이 null이면 N 값 강제 대입
        if(noticeDTO.getNtc_yn() == null || noticeDTO.getNtc_yn().equals("")) {
            noticeDTO.setNtc_yn("N");
        }

        int result = noticeService.insert(noticeDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + noticeDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/notice_insert";
        }
    }

    // 공지사항 수정 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int ntc_sn, Model model) {
        NoticeDTO notice = noticeService.select(ntc_sn);
        model.addAttribute("notice", notice);
        model.addAttribute("menuId", menuId);
        return directory + "/notice_update";
    }

    // 공지사항 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, NoticeDTO noticeDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        // 만약 ntc_yn이 null이면 N 값 강제 대입
        if(noticeDTO.getNtc_yn() == null || noticeDTO.getNtc_yn().equals("")) {
            noticeDTO.setNtc_yn("N");
        }

        // 만약 del_yn이 null이면 N 값 강제 대입
        if(noticeDTO.getDel_yn() == null || noticeDTO.getDel_yn().equals("")) {
            noticeDTO.setDel_yn("N");
        }

        int result = noticeService.update(noticeDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + noticeDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/notice_update";
        }
    }
    
    // 공지사항 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("ntc_sn") int ntc_sn) {
        noticeService.delete(ntc_sn);
        return "success";
    }
}
