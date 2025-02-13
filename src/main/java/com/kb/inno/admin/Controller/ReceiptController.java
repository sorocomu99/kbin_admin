package com.kb.inno.admin.Controller;


import com.kb.inno.admin.DAO.KbStartersSurvey;
import com.kb.inno.admin.DTO.*;
import com.kb.inno.admin.Service.SurveyService;
import com.kb.inno.common.*;
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
import org.springframework.web.bind.annotation.*;

import com.kb.inno.admin.Service.ReceiptService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

        mv.addObject("search", search);

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

        List<KbStartersApplyDTO> statusList = receiptService.getSurveyApplyStatusList(surveyNo);
        mv.addObject("statusList", statusList);

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
        //String filePath = "/Users/johuiyang/Documents/web/uploads/kbinno/" + applyAnswer.getAnswer_filename();

        String filePath = applyAnswer.getAnswer_file_path() + File.separator + applyAnswer.getAnswer_filename();

        // 운영은 sftp 로 다운로드 및 저장
        if(CommonUtil.isProd(PropertiesValue.profilesActive)) {
            String savePath = FilePathUtil.getSavePath(PropertiesValue.profilesActive)
                    + File.separator + "download"
                    + File.separator + "apply"
                    + File.separator + DateUtil.getToDay("yyyyMMdd");

            File directory = new File(savePath);

            if(!directory.exists()) {
                directory.mkdirs();
            }

            // 다운로드 파일 경로. /download/apply/yyyyMMdd/파일명
            filePath = savePath + File.separator + applyAnswer.getAnswer_filename();

            String remoteFilePath = applyAnswer.getAnswer_file_path().concat(File.separator).concat(applyAnswer.getAnswer_filename());

            try{
                SFTPDownloader downloader = new SFTPDownloader("10.170.6.13", 22, "wasadm", "Kb!wasadm77");
                if(downloader.isServerReachable(downloader.getHost(), downloader.getPort(), 2000)) {
                    downloader.downloadFile(remoteFilePath, filePath);
                }
            }catch (Exception ignored) {
            }

            try{
                SFTPDownloader downloader = new SFTPDownloader("10.170.6.14", 22, "wasadm", "Kb!wasadm77");
                if(downloader.isServerReachable(downloader.getHost(), downloader.getPort(), 2000)) {
                    downloader.downloadFile(remoteFilePath, filePath);
                }

            }catch (Exception ignored) {
            }
        }

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

        mv.addObject("search", searchDTO);

        return mv;
    }


    @RequestMapping("/sendMail/{menuId}")
    public ModelAndView sendMail(@RequestParam(value = "receivers", required = false) List<String> receivers) {
        ModelAndView mv = new ModelAndView("receipt/receipt_mail");
        mv.addObject("receivers", receivers);
        return mv;
    }

    @PostMapping("/sendMail")
    public ModelAndView sendMail(@RequestParam("receivers[]") List<String> receivers, String sender, String subject, String content, MultipartFile attachment, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("alert");
        MailUtil mailUtil = new MailUtil();

        if(mailUtil.sendMail(sender, receivers, subject, content, attachment)){
            String referer = request.getHeader("Referer");

            HttpSession session = request.getSession(false);
            int loginId = (int) session.getAttribute("mngrSn");

            //receivers 사이즈는 mailUtil.sendMail 에서 이미 처리함
            SendMailDTO sendMailDTO = new SendMailDTO();
            sendMailDTO.setSend_ymd(DateUtil.getToDay("yyyyMMdd"));
            sendMailDTO.setMail_rcvr(receivers.get(0));
            sendMailDTO.setMail_ttl(subject);
            sendMailDTO.setMail_cn(content);
            sendMailDTO.setFrst_rgtr(loginId);
            sendMailDTO.setLast_mdfr(loginId);

            receiptService.insertSendMailInfo(sendMailDTO, receivers);
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

    @PostMapping("/uploadXlsForApplyStatus")
    public ResponseEntity<Map<String, Object>> uploadXlsForApplyStatus(@RequestParam("file") MultipartFile file, @RequestParam("surveyNo") Integer surveyNo) {
        Map<String, Object> result = new HashMap<>();

        try {
            if(surveyNo == null) {
                result.put("result", "fail");
                return ResponseEntity.ok(result);
            }

            if (file.isEmpty()) {
                result.put("result", "1");
                return ResponseEntity.ok(result);
            }

            String fileName = file.getOriginalFilename();

            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                result.put("result", "2");
                return ResponseEntity.ok(result);
            }
            boolean retVal = receiptService.uploadXlsForApplyStatus(file, surveyNo);

            result.put("result", retVal ? "6" : "fail");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "fail");
            return ResponseEntity.status(500).body(result);
        }
    }

    @RequestMapping("/apply/status/{menuId}/{surveyNo}")
    public ModelAndView applyStatus(@PathVariable int menuId, @PathVariable int surveyNo) {
        List<KbStartersApplyDTO> statusList = receiptService.getSurveyApplyStatusList(surveyNo);

        ModelAndView mv = new ModelAndView("receipt/apply_status");
        mv.addObject("menuId", menuId);
        mv.addObject("surveyNo", surveyNo);
        mv.addObject("statusList", statusList);
        return mv;
    }

    @PostMapping("/saveApplyStatusList")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveApplyStatusList(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        return ResponseEntity.ok(receiptService.saveApplyStatusList(requestData, loginId));
    }
}
