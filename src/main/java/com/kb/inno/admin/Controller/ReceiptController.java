package com.kb.inno.admin.Controller;


import com.kb.inno.admin.DAO.KbStartersSurvey;
import com.kb.inno.admin.DTO.*;
import com.kb.inno.admin.Service.SurveyService;
import com.kb.inno.common.MailUtil;
import com.kb.inno.common.Pagination;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kb.inno.admin.Service.ReceiptService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class ReceiptController {
    // 디렉터리 공통
    @Value("/receipt")
    private String directory;

    //Service 연결
    private final ReceiptService receiptService;

    private final SurveyService surveyService;


    @ResponseBody
    @PostMapping("/receiptDelete")
    public String receiptDelete(@RequestParam("srvy_sn") int srvy_sn) {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        receiptDTO.setSrvy_sn(srvy_sn);
        receiptService.receiptDelete(receiptDTO);
        return "success";
    }

    @ResponseBody
    @PostMapping("/deleteCancel")
    public String deleteCancel(@RequestParam("srvy_sn") int srvy_sn) {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        receiptDTO.setSrvy_sn(srvy_sn);
        receiptService.deleteCancel(receiptDTO);
        return "success";
    }

    @ResponseBody
    @PostMapping("/tempDelete")
    public ResponseEntity<Map<String, Object>> tempDelete(@RequestParam("survey_no") int surveyNo, @RequestParam("delete_yn") String deleteYn) {
        return ResponseEntity.ok(surveyService.surveyTempDelete(surveyNo, deleteYn));
    }

    @ResponseBody
    @PostMapping("/updateAlert")
    public String updateAlert(@RequestParam("stts") String stts,
                              @RequestParam("conm") String conm) {
        ReceiptListDTO receiptListDTO = new ReceiptListDTO();
        receiptListDTO.setPrgrs_stts(stts);
        receiptListDTO.setConm(conm);

        System.out.println("/updateAlert : prgrs_stts [" + stts + "]  conm [" + conm + "]");
        System.out.println("/updateAlert : prgrs_stts [" + receiptListDTO.getPrgrs_stts() + "]  conm [" + receiptListDTO.getConm() + "]");

        receiptService.updateAlert(receiptListDTO);
        return "success";
    }

    @PostMapping("/updateApplyStatus")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateApplyStatus(int applyNo, String status) {
        return ResponseEntity.ok(surveyService.updateApplyStatus(applyNo, status));
    }

    @PostMapping("/updateStatusList")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateStatusList(String status, @RequestParam(name = "applyNos[]") List<Integer> applyNos) {
        return ResponseEntity.ok(surveyService.updateStatusList(applyNos, status));
    }

    @GetMapping("/list/{menuId}")
    public ModelAndView receiptMainList(@PathVariable int menuId, SearchDTO search) {
        ModelAndView mv = new ModelAndView("receipt/receipt");

        search.setDelete_yn("N");

        List<KbStartersSurveyDTO> surveyList = surveyService.getSurveyList(search);
        int totalCount = surveyService.getSurveyListCnt(search);

        Pagination pagination = new Pagination(totalCount, search.getStart(), 10, 10);

        mv.addObject("surveyList", surveyList);
        mv.addObject("pagination", pagination);
        mv.addObject("totalCount", totalCount);
        mv.addObject("menuId", menuId);

        return mv;
    }

    @GetMapping("/receiptList/{menuId}")
    public ModelAndView receiptList(@PathVariable int menuId, int surveyNo, SearchDTO searchDTO) {
        ModelAndView mv = new ModelAndView("receipt/receipt_list");
        mv.addObject("menuId", menuId);

        List<KbStartersApplyDTO> applyList = surveyService.getApplyList(surveyNo, searchDTO);
        mv.addObject("applyList", applyList);

        mv.addObject("search", searchDTO);
        mv.addObject("surveyNo", surveyNo);

        List<KbStartersQuestionDTO> questionList = surveyService.getQuestionList(surveyNo);
        mv.addObject("questionList", questionList);

        int colCount = 0;
        for (KbStartersQuestionDTO question : questionList) {
            if (question.getQuestion_type_no() != 4) {
                colCount++;
            }
        }

        mv.addObject("colCount", colCount);

        return mv;
    }

    @GetMapping("/downloadUserApplyFile")
    public ResponseEntity<Resource> downloadUserApplyFile(int applyAnswerNo) {
        KbStartersApplyAnswerDTO applyAnswer = surveyService.getApplyAnswer(applyAnswerNo);

        if (applyAnswer.getAnswer_filename() == null || applyAnswer.getAnswer_filename().equals("")) {
            return ResponseEntity.notFound().build();
        }

        // TODO : 파일 경로를 설정해주세요 또는 기존 파일경로를 설정했던 방식으로 사용해주세요
        String filePath = "/Users/johuiyang/Documents/web/uploads/kbinno/" + applyAnswer.getAnswer_filename();

        // 파일이 존재하는지 확인
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 파일을 Resource로 감싸서 반환
        Resource resource = new FileSystemResource(file);

        String decodedFileName = new String(Base64.decodeBase64(applyAnswer.getAnswer_original_filename()));

        try {
            decodedFileName = URLEncoder.encode(decodedFileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        decodedFileName = decodedFileName.replaceAll("\\+", "%20");

        String contentDisposition = "attachment; filename=\"" + decodedFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/receiptTrash/{menuId}")
    public ModelAndView receiptTrashList(SearchDTO searchDTO, @PathVariable int menuId) {
        searchDTO.setDelete_yn("Y");
        List<KbStartersSurveyDTO> surveyList = surveyService.getSurveyList(searchDTO);
        int totalCount = surveyService.getSurveyListCnt(searchDTO);

        ModelAndView mv = new ModelAndView("receipt/receipt_trash");
        mv.addObject("menuId", menuId);
        mv.addObject("surveyList", surveyList);
        mv.addObject("totalCount", totalCount);

        Pagination pagination = new Pagination(totalCount, searchDTO.getStart(), 10, 10);
        mv.addObject("pagination", pagination);

        return mv;
    }


    @RequestMapping("/sendMail/{menuId}")
    public ModelAndView sendMail(@RequestParam(value = "receivers", required = false) List<String> receivers) {
        ModelAndView mv = new ModelAndView("receipt/receipt_mail");
        mv.addObject("receivers", receivers);
        return mv;
    }

    @PostMapping("/sendMail")
    public ModelAndView sendMail(@RequestParam("receivers[]") List<String> receivers, String subject, String content, MultipartFile attachment, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("alert");
        MailUtil mailUtil = new MailUtil();
        if(mailUtil.sendMail(receivers, subject, content, attachment)){
            String referer = request.getHeader("Referer");
            mv.addObject("message", "메일 전송이 완료되었습니다.");
            mv.addObject("url", referer);
            return mv;
        }
        else{
            mv.setViewName("alertBack");
            mv.addObject("message", "메일 전송에 실패하였습니다.");
            return mv;
        }
    }



    @PostMapping("/deleteApply")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteApply(Integer applyNo) {
        return ResponseEntity.ok(surveyService.deleteApply(applyNo));
    }

    @PostMapping("/deleteApplyList")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteApplyList(@RequestParam(name = "applyNos[]") List<Integer> applyNos) {
        return ResponseEntity.ok(surveyService.deleteApplyList(applyNos));
    }


}
