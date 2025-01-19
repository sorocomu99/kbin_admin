/**
 * 파일명     : SurveyController.java
 * 화면명     : 설문 관리
 * 설명       : 설문 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2025.01.06
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.SurveyDTO;
import com.kb.inno.admin.Service.SurveyService;
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
@RequestMapping("/survey")
public class SurveyController {
    // 디렉터리 공통
    @Value("/survey")
    private String directory;

    //Service 연결
    private final SurveyService surveyService;

    /**
     * 설문 조회
     * @param menuId
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/list/{menuId}")
    public String suveyList(@PathVariable int menuId, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        surveyService.selectList(menuId, page, model);

        return directory + "/survey";
    }

    /**
     * 설문 조사 등록 페이지 이동
     * @param menuId
     * @param model
     * @return
     */
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/survey_insert";
    }

    /**
     * 설문 조사 등록
     * @param surveyDTO
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequestMapping("/insert")
    public String insert(SurveyDTO surveyDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //로그인 세션 정보 가져오기
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        resultMap = surveyService.exmnInsert(surveyDTO, loginId);

        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + surveyDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/history_insert";
        }
    }

    /**
     * 지원서 설문 복사
     * @param surveyDTO
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequestMapping("/surverCopy")
    public String surverCopy(SurveyDTO surveyDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //로그인 세션 정보 가져오기
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        resultMap = surveyService.surverCopy(surveyDTO, loginId);

        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + surveyDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/history_insert";
        }
    }
    

    @RequestMapping("/qstnInsert/{menuId}")
    public String qstnInsert(@PathVariable int menuId, Model model,@RequestParam(value = "srvy_sn" , required = false, defaultValue = "1") int srvy_sn) {
        model.addAttribute("menuId", menuId);
        model.addAttribute("srvy_sn1", srvy_sn);
        return directory + "/survey_question_ins";
    }

    @RequestMapping("/qstnInsert")
    public String qstnInsert(SurveyDTO surveyDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //로그인 세션 정보 가져오기
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }
        
        System.out.println("===========================================================================================");
       	System.out.println("["+ surveyDTO +"]");
        System.out.println("===========================================================================================");
        
        //문항 등록
        resultMap = surveyService.qstnInsert(surveyDTO, loginId);

        
        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + surveyDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/history_insert";
        }
    }
    

    
    /**
     * 가이드 페이지 이동
     * @param surveyDTO
     * @param model
     * @return
     */
    @RequestMapping("/guide")
    public String guide(SurveyDTO surveyDTO, Model model) {
        surveyService.selectGuide(surveyDTO, model);
        model.addAttribute("menuId", surveyDTO.getMenu_id());
        model.addAttribute("srvy_sn", surveyDTO.getSrvy_sn());
        return directory + "/survey_guide";
    }

    @PostMapping("/guideIns")
    public String guideIns(SurveyDTO surveyDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        resultMap = surveyService.guideInsert(surveyDTO, loginId);

        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + surveyDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/survey_guide";
        }
    }

    @ResponseBody
    @PostMapping("/exmnDelete")
    public String exmnDelete(@RequestParam("srvy_sn") int srvy_sn) {
        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setSrvy_sn(srvy_sn);
        surveyService.exmnDelete(surveyDTO);
        return "success";
    }
}
