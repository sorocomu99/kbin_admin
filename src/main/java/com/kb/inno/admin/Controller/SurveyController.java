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

import com.kb.inno.admin.DAO.KbStartersSurvey;
import com.kb.inno.admin.DTO.*;
import com.kb.inno.admin.Service.SurveyService;
import com.kb.inno.common.FilePathUtil;
import com.kb.inno.common.Pagination;
import com.kb.inno.common.PropertiesValue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {
    // 디렉터리 공통
    @Value("/survey")
    private String directory;

    //Service 연결
    private final SurveyService surveyService;

    @GetMapping("/banner")
    public ResponseEntity<Resource> getImage(String filename) throws IOException {
        // TODO : 파일 경로를 설정해주세요 또는 기존 파일경로를 설정했던 방식으로 사용해주세요
        //String filePath = "/Users/johuiyang/Documents/web/uploads/kbinno/" + filename;
        //String filePath = "D:/fsfile/dev_kbinnovation/" + filename;
        //String filePath = "/fsfile/kbinnovation/" + filename;
        String filePath = FilePathUtil.getSavePath(PropertiesValue.profilesActive);

        // 파일이 존재하는지 확인
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 파일을 Resource로 감싸서 반환
        Resource resource = new FileSystemResource(file);

        // 이미지 파일의 헤더 설정
        String contentType = "image/jpeg";
        if (filename.toLowerCase().endsWith(".png")) {
            contentType = "image/png";
        } else if (filename.toLowerCase().endsWith(".gif")) {
            contentType = "image/gif";
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @GetMapping("/list/{menuId}")
    public ModelAndView surveyList(@PathVariable int menuId, @ModelAttribute SearchDTO searchDTO) {
        ModelAndView mv = new ModelAndView("survey/survey");
        searchDTO.setDelete_yn("N");
        int totalCount = surveyService.getSurveyListCnt(searchDTO);
        List<KbStartersSurveyDTO> surveyDTOList = surveyService.getSurveyList(searchDTO);
        Pagination pagination = new Pagination(totalCount, searchDTO.getStart(), 10, 10);
        mv.addObject("surveyList", surveyDTOList);
        mv.addObject("pagination", pagination);
        mv.addObject("totalCount", totalCount);
        mv.addObject("menuId", menuId);

        mv.addObject("search", searchDTO);
        return mv;
    }

    @PostMapping("/question/save")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> insertSurvey(@RequestBody KbStartersSurveyRequestDTO requestBody, HttpServletRequest request) {
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        return ResponseEntity.ok(surveyService.saveSurveyQuestion(requestBody, loginId));
    }

    @PostMapping("/preview")
    public ModelAndView preview(KbStartersSurveyRequestDTO data) {
        ModelAndView mv = new ModelAndView("survey/survey_preview");
        mv.addObject("survey", data);
        KbStartersSurveyDTO surveyDTO = surveyService.getSurvey(data.getData().get(0).getSurvey_no());
        mv.addObject("surveyDefaultData", surveyDTO);

        mv.addObject("newLineChar", "\n");
        return mv;
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
        return "/survey/survey_insert";
    }

    @PostMapping("/insert")
    public ModelAndView insert(HttpServletRequest request, KbStartersSurveyDTO survey, MultipartFile surveyBannerFile, int menuId) {
        ModelAndView mv = new ModelAndView("redirect:/survey/list/" + menuId);

        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        Map<String, Object> result = surveyService.saveSurvey(survey, surveyBannerFile, loginId);
        if(!result.get("result").equals("success")){
            mv.setViewName("alertBack");
            mv.addObject("message", result.get("message"));
        }
        return mv;
    }

    @PostMapping("/surverCopy")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> surverCopy(int surveyNo, HttpServletRequest request) {
        //로그인 세션 정보 가져오기
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        Map<String, Object> resultMap = surveyService.copySurvey(surveyNo, loginId);

        return ResponseEntity.ok(resultMap);
    }

    @RequestMapping("/manageSurvey/{menuId}")
    public ModelAndView manageSurvey(int surveyNo, @PathVariable int menuId) {
        ModelAndView mv = new ModelAndView("survey/survey_question_ins");
        mv.addObject("questions", surveyService.getQuestionList(surveyNo));
        mv.addObject("menuId", menuId);
        mv.addObject("surveyNo", surveyNo);
        return mv;
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


    @GetMapping("/guide/{menuId}")
    public ModelAndView guide(@PathVariable int menuId, int surveyNo) {
        ModelAndView mv = new ModelAndView("survey/survey_guide");
        KbStartersSurveyDTO surveyDTO = surveyService.getSurveyInfo(surveyNo);
        mv.addObject("menuId", menuId);
        mv.addObject("surveyNo", surveyNo);
        mv.addObject("surveyInfo", surveyDTO);
        return mv;
    }

    @PostMapping("/guideIns")
    public ModelAndView guideIns(KbStartersSurveyDTO surveyDTO, int menuId, HttpServletRequest request) {
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        Map<String, Object> resultMap = surveyService.saveSurveyInfo(surveyDTO, loginId);

        ModelAndView mv = new ModelAndView("redirect:/survey/list/" + menuId);
        if(!resultMap.get("result").equals("success")){
            mv.setViewName("alertBack");
            mv.addObject("message", resultMap.get("message"));
        }

        return mv;
    }

    @PostMapping("/guidePreview")
    public ModelAndView guidePreview(KbStartersSurveyDTO surveyDTO) {
        ModelAndView mv = new ModelAndView("survey/survey_guide_preview");
        mv.addObject("info", surveyDTO);
        return mv;
    }

    @ResponseBody
    @PostMapping("/exmnDelete")
    public String exmnDelete(@RequestParam("srvy_sn") int srvy_sn) {
        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setSrvy_sn(srvy_sn);
        surveyService.exmnDelete(surveyDTO);
        return "success";
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteSurvey(int survey_no) {
        Map<String, Object> result = surveyService.deleteSurvey(survey_no);
        return ResponseEntity.ok(result);
    }
}
