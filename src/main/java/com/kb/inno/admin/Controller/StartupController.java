package com.kb.inno.admin.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kb.inno.admin.DTO.StartupDTO;
import com.kb.inno.admin.Service.StartupService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/startup")
public class StartupController {
    
    // 서비스 연결
    private final StartupService startupService;
    
    // 공통 경로 설정
    @Value("/startup")
    private String directory;

    /**
     * KB 스타터스 조회
     * @param menuId
     * @param model
     * @param type
     * @param keyword
     * @param page
     * @return
     */
    @GetMapping("/list/{menuId}")
    public String selectList(@PathVariable int menuId, Model model,
            				 @RequestParam(value = "type", required = false, defaultValue = "") String type,
            				 @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            				 @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
    	startupService.selectList(menuId, model, type, keyword, page);
        return directory + "/startup";
    }

    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/startup_insert";
    }
}
