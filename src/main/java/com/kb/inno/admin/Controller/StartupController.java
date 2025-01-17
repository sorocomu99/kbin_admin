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
     * @param menuId
     * @param model
     * @return
     * KB 스타터스 조회
     */
    @GetMapping("/list/{menuId}")
    public String selectList(@PathVariable int menuId, Model model,
            				 @RequestParam(value = "type", required = false, defaultValue = "") String type,
            				 @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            				 @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
    	startupService.selectList(menuId, model, type, keyword, page);
        return directory + "/startup";
    }
    
    /**
    // KB 스타터스 조회
    @RequestMapping("/info")
    public String select(Model model) {
    	List<StartersDTO> result = startersService.select();
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
	
	*/
	
	
	
	
	
}
