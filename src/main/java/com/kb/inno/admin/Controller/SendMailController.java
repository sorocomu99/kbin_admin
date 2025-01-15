/**
 * 파일명     : SendMailController.java
 * 화면명     : 보낸 메일함
 * 설명       : 보낸 메일함 조회 및 삭제 처리
 * 최초개발일  : 2025.01.14
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.Service.SendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class SendMailController {

    // 디렉터리 공통
    @Value("/sendmail")
    private String directory;
    
    // Service 연결
    private final SendMailService sendMailService;

    /**
     * 보낸 메일 리스트 조회
     * @param menuId
     * @param model
     * @param type
     * @param keyword
     * @param page
     * @return
     */
    @GetMapping("/list/{menuId}")
    public String mailList(@PathVariable int menuId, Model model,
                           @RequestParam(value = "type", required = false, defaultValue = "") String type,
                           @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        sendMailService.selectListMail(menuId, model, type, keyword, page);
        model.addAttribute("menuId", menuId);
        return directory + "/mail";
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam String send_ymd, @RequestParam int send_mail_sn, Model model) {
        sendMailService.selectMailDetail(send_ymd, send_mail_sn, model);
        //model.addAttribute("portListDet", portListDet);
        model.addAttribute("menuId", menuId);
        model.addAttribute("send_ymd", send_ymd);
        model.addAttribute("send_mail_sn", send_mail_sn);
        return directory + "/mail_detail";
    }
}

