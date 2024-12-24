package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.FaqCategoryDTO;
import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.NoticeDTO;
import com.kb.inno.admin.Service.FaqCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faq/category")
public class FaqCategoryController {
    // 서비스 연결
    private final FaqCategoryService faqCategoryService;

    // 공통 경로 설정
    @Value("/faq")
    private String directory;

    // FAQ 카테고리 카테고리 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(Model model, @PathVariable int menuId,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        faqCategoryService.selectList(menuId, model, page);
        return directory + "/faq_category";
    }

    // FAQ 카테고리 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/faq_category_insert";
    }

    // FAQ 카테고리 미리보기 페이지 이동
    @PostMapping("/preview")
    public String preview(FaqCategoryDTO faqCategory, Model model) {
        faqCategoryService.preview(model, faqCategory);
        return directory + "/faq_category_preview";
    }

    // FAQ 카테고리 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, FaqCategoryDTO faqCategoryDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = faqCategoryService.insert(faqCategoryDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/category/list/" + faqCategoryDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/faq_category_insert";
        }
    }

    // FAQ 카테고리 상세 조회
    @PostMapping("/detail")
    public String detail (@RequestParam int menuId, @RequestParam int ctgry_sn, Model model) {
        FaqCategoryDTO category = faqCategoryService.select(ctgry_sn);
        model.addAttribute("category", category);
        model.addAttribute("menuId", menuId);
        return directory + "/faq_category_update";
    }

    // FAQ 카테고리 수정
    @PostMapping("/update")
    public String update (RedirectAttributes redirectAttributes, FaqCategoryDTO faqCategoryDTO, HttpServletRequest request) {
        // 로그인한 아이디 가져오기
        // 현재 세션 확인
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        int result = faqCategoryService.update(faqCategoryDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/category/list/" + faqCategoryDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/faq_category_update";
        }
    }

    // FAQ 카테고리 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete (@RequestParam("ctgry_sn") int ctgry_sn) {
        faqCategoryService.delete(ctgry_sn);
        return "success";
    }
}
