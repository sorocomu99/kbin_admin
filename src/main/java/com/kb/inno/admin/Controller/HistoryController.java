/**
 * 파일명     : HistoryController.java
 * 화면명     : 연혁 관리
 * 설명       : 연혁 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.12.31
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.HistoryDTO;
import com.kb.inno.admin.Service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    // 디렉터리 공통
    @Value("/history")
    private String directory;
    
    // Service 연결
    private final HistoryService historyService;

    /**
     * @param menuId
     * @param model
     * @return
     * 관리자 리스트 조회
     */
    @GetMapping("/list/{menuId}")
    public String historyList(@PathVariable int menuId, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        //List<HistoryDTO> selectList = historyService.selectList();
        historyService.selectList(menuId, page, model);
        //model.addAttribute("selectList", selectList);
        //model.addAttribute("menuId", menuId);
        return directory + "/history";
    }

    /**
     * 연혁 등록 페이지 이동
     * @param menuId
     * @param model
     * @return
     */
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        historyService.selectList(menuId, 1, model);

        model.addAttribute("menuId", menuId);
        return directory + "/history_insert";
    }

    /**
     * 연혁 등록하기
     * @param historyDTO
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequestMapping("/insert")
    public String insert(HistoryDTO historyDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //public HashMap<String, Object> insert(HistoryDTO historyDTO, @RequestParam Map<String, Object> params, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //System.out.println("historyDTO=================="+historyDTO);

        //로그인 세션 정보 가져오기
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        //DB에 저장할 최종값을 담을 Map
        HashMap<String, Object> resMap = new HashMap<>();

        //년도 세팅
        resMap.put("hstry_yr", historyDTO.getHstry_yr());

        //항목 1 세팅
        resMap.put("main_input1", historyDTO.getMain_input1());
        List<String> subInputList1 = new ArrayList<>();
        if (historyDTO.getSub_input1() == null || historyDTO.getSub_input1().isEmpty()) {
        } else {
            subInputList1 = Arrays.asList(historyDTO.getSub_input1().split(","));
            //System.out.println("subInputList1===================="+subInputList1);
            resMap.put("subInputList1", subInputList1);
        }

        //항목 2 세팅
        if (historyDTO.getMain_input2() == null || historyDTO.getMain_input2().isEmpty()) {
        } else {
            resMap.put("main_input2", historyDTO.getMain_input2());
        }
        List<String> subInputList2 = new ArrayList<>();
        if (historyDTO.getSub_input2() == null || historyDTO.getSub_input2().isEmpty()) {
        } else {
            subInputList2 = Arrays.asList(historyDTO.getSub_input2().split(","));
            resMap.put("subInputList2", subInputList2);
        }

        //항목 3 세팅
        if (historyDTO.getMain_input3() == null || historyDTO.getMain_input3().isEmpty()) {
        } else {
            resMap.put("main_input3", historyDTO.getMain_input3());
        }
        List<String> subInputList3 = new ArrayList<>();
        if (historyDTO.getSub_input3() == null || historyDTO.getSub_input3().isEmpty()) {
        } else {
            subInputList3 = Arrays.asList(historyDTO.getSub_input3()
                                                    .split(","));
            resMap.put("subInputList3", subInputList3);
        }

        //항목 4 세팅
        if (historyDTO.getMain_input4() == null || historyDTO.getMain_input4()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input4", historyDTO.getMain_input4());
        }
        List<String> subInputList4 = new ArrayList<>();
        if (historyDTO.getSub_input4() == null || historyDTO.getSub_input4()
                                                            .isEmpty()) {
        } else {
            subInputList4 = Arrays.asList(historyDTO.getSub_input4()
                                                    .split(","));
            resMap.put("subInputList4", subInputList4);
        }

        //항목 5 세팅
        if (historyDTO.getMain_input5() == null || historyDTO.getMain_input5()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input5", historyDTO.getMain_input5());
        }
        List<String> subInputList5 = new ArrayList<>();
        if (historyDTO.getSub_input5() == null || historyDTO.getSub_input5()
                                                            .isEmpty()) {
        } else {
            subInputList5 = Arrays.asList(historyDTO.getSub_input5()
                                                    .split(","));
            resMap.put("subInputList5", subInputList5);
        }

        //항목 6 세팅
        if (historyDTO.getMain_input6() == null || historyDTO.getMain_input6()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input6", historyDTO.getMain_input6());
        }
        List<String> subInputList6 = new ArrayList<>();
        if (historyDTO.getSub_input6() == null || historyDTO.getSub_input6()
                                                            .isEmpty()) {
        } else {
            subInputList6 = Arrays.asList(historyDTO.getSub_input6()
                                                    .split(","));
            resMap.put("subInputList6", subInputList6);
        }

        //항목 7 세팅
        if (historyDTO.getMain_input7() == null || historyDTO.getMain_input7()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input7", historyDTO.getMain_input7());
        }
        List<String> subInputList7 = new ArrayList<>();
        if (historyDTO.getSub_input7() == null || historyDTO.getSub_input7()
                                                            .isEmpty()) {
        } else {
            subInputList7 = Arrays.asList(historyDTO.getSub_input7()
                                                    .split(","));
            resMap.put("subInputList7", subInputList7);
        }

        //항목 8 세팅
        if (historyDTO.getMain_input8() == null || historyDTO.getMain_input8()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input8", historyDTO.getMain_input8());
        }
        List<String> subInputList8 = new ArrayList<>();
        if (historyDTO.getSub_input8() == null || historyDTO.getSub_input8()
                                                            .isEmpty()) {
        } else {
            subInputList8 = Arrays.asList(historyDTO.getSub_input8()
                                                    .split(","));
            resMap.put("subInputList8", subInputList8);
        }

        //항목 9 세팅
        if (historyDTO.getMain_input9() == null || historyDTO.getMain_input9()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input9", historyDTO.getMain_input9());
        }
        List<String> subInputList9 = new ArrayList<>();
        if (historyDTO.getSub_input9() == null || historyDTO.getSub_input9()
                                                            .isEmpty()) {
        } else {
            subInputList9 = Arrays.asList(historyDTO.getSub_input9()
                                                    .split(","));
            resMap.put("subInputList9", subInputList9);
        }

        //항목 10 세팅
        if (historyDTO.getMain_input10() == null || historyDTO.getMain_input10()
                                                              .isEmpty()) {
        } else {
            resMap.put("main_input10", historyDTO.getMain_input10());
        }
        List<String> subInputList10 = new ArrayList<>();
        if (historyDTO.getSub_input10() == null || historyDTO.getSub_input10()
                                                             .isEmpty()) {
        } else {
            subInputList10 = Arrays.asList(historyDTO.getSub_input10()
                                                     .split(","));
            resMap.put("subInputList10", subInputList10);
        }

        try {
            resultMap = historyService.insert(historyDTO, resMap, loginId);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "연혁 등록 중 오류가 발생했습니다.");
            return directory + "/history_insert";
        }

        //정상 등록되면 리스트 페이지로 이동
        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + historyDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/history_insert";
        }

        /*
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + historyDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록에 실패하였습니다.");
            return directory + "/history_insert";
        }
        */
    }

    /**
     * 현혁 수정(상세) 페이지 이동
     * @param menuId
     * @param hstry_yr
     * @param model
     * @return
     */
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam String hstry_yr, Model model) {
        historyService.selectDetail(hstry_yr, model);
        historyService.selectList(menuId, 1, model);

        model.addAttribute("menuId", menuId);
        model.addAttribute("hstry_yr", hstry_yr);

        return directory + "/history_update";
    }

    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, HistoryDTO historyDTO, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        //DB에 저장할 최종값을 담을 Map
        HashMap<String, Object> resMap = new HashMap<>();

        //년도 세팅
        resMap.put("hstry_yr", historyDTO.getHstry_yr());

        //항목 1 세팅
        resMap.put("main_input1", historyDTO.getMain_input1());
        List<String> subInputList1 = new ArrayList<>();
        if (historyDTO.getSub_input1() == null || historyDTO.getSub_input1()
                                                            .isEmpty()) {
        } else {
            subInputList1 = Arrays.asList(historyDTO.getSub_input1()
                                                    .split(","));
            //System.out.println("subInputList1===================="+subInputList1);
            resMap.put("subInputList1", subInputList1);
        }

        //항목 2 세팅
        if (historyDTO.getMain_input2() == null || historyDTO.getMain_input2()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input2", historyDTO.getMain_input2());
        }
        List<String> subInputList2 = new ArrayList<>();
        if (historyDTO.getSub_input2() == null || historyDTO.getSub_input2()
                                                            .isEmpty()) {
        } else {
            subInputList2 = Arrays.asList(historyDTO.getSub_input2()
                                                    .split(","));
            resMap.put("subInputList2", subInputList2);
        }

        //항목 3 세팅
        if (historyDTO.getMain_input3() == null || historyDTO.getMain_input3()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input3", historyDTO.getMain_input3());
        }
        List<String> subInputList3 = new ArrayList<>();
        if (historyDTO.getSub_input3() == null || historyDTO.getSub_input3()
                                                            .isEmpty()) {
        } else {
            subInputList3 = Arrays.asList(historyDTO.getSub_input3()
                                                    .split(","));
            resMap.put("subInputList3", subInputList3);
        }

        //항목 4 세팅
        if (historyDTO.getMain_input4() == null || historyDTO.getMain_input4()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input4", historyDTO.getMain_input4());
        }
        List<String> subInputList4 = new ArrayList<>();
        if (historyDTO.getSub_input4() == null || historyDTO.getSub_input4()
                                                            .isEmpty()) {
        } else {
            subInputList4 = Arrays.asList(historyDTO.getSub_input4()
                                                    .split(","));
            resMap.put("subInputList4", subInputList4);
        }

        //항목 5 세팅
        if (historyDTO.getMain_input5() == null || historyDTO.getMain_input5()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input5", historyDTO.getMain_input5());
        }
        List<String> subInputList5 = new ArrayList<>();
        if (historyDTO.getSub_input5() == null || historyDTO.getSub_input5()
                                                            .isEmpty()) {
        } else {
            subInputList5 = Arrays.asList(historyDTO.getSub_input5()
                                                    .split(","));
            resMap.put("subInputList5", subInputList5);
        }

        //항목 6 세팅
        if (historyDTO.getMain_input6() == null || historyDTO.getMain_input6()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input6", historyDTO.getMain_input6());
        }
        List<String> subInputList6 = new ArrayList<>();
        if (historyDTO.getSub_input6() == null || historyDTO.getSub_input6()
                                                            .isEmpty()) {
        } else {
            subInputList6 = Arrays.asList(historyDTO.getSub_input6()
                                                    .split(","));
            resMap.put("subInputList6", subInputList6);
        }

        //항목 7 세팅
        if (historyDTO.getMain_input7() == null || historyDTO.getMain_input7()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input7", historyDTO.getMain_input7());
        }
        List<String> subInputList7 = new ArrayList<>();
        if (historyDTO.getSub_input7() == null || historyDTO.getSub_input7()
                                                            .isEmpty()) {
        } else {
            subInputList7 = Arrays.asList(historyDTO.getSub_input7()
                                                    .split(","));
            resMap.put("subInputList7", subInputList7);
        }

        //항목 8 세팅
        if (historyDTO.getMain_input8() == null || historyDTO.getMain_input8()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input8", historyDTO.getMain_input8());
        }
        List<String> subInputList8 = new ArrayList<>();
        if (historyDTO.getSub_input8() == null || historyDTO.getSub_input8()
                                                            .isEmpty()) {
        } else {
            subInputList8 = Arrays.asList(historyDTO.getSub_input8()
                                                    .split(","));
            resMap.put("subInputList8", subInputList8);
        }

        //항목 9 세팅
        if (historyDTO.getMain_input9() == null || historyDTO.getMain_input9()
                                                             .isEmpty()) {
        } else {
            resMap.put("main_input9", historyDTO.getMain_input9());
        }
        List<String> subInputList9 = new ArrayList<>();
        if (historyDTO.getSub_input9() == null || historyDTO.getSub_input9()
                                                            .isEmpty()) {
        } else {
            subInputList9 = Arrays.asList(historyDTO.getSub_input9()
                                                    .split(","));
            resMap.put("subInputList9", subInputList9);
        }

        //항목 10 세팅
        if (historyDTO.getMain_input10() == null || historyDTO.getMain_input10()
                                                              .isEmpty()) {
        } else {
            resMap.put("main_input10", historyDTO.getMain_input10());
        }
        List<String> subInputList10 = new ArrayList<>();
        if (historyDTO.getSub_input10() == null || historyDTO.getSub_input10()
                                                             .isEmpty()) {
        } else {
            subInputList10 = Arrays.asList(historyDTO.getSub_input10()
                                                     .split(","));
            resMap.put("subInputList10", subInputList10);
        }

        resultMap = historyService.update(historyDTO, resMap, loginId);

        //정상 등록되면 리스트 페이지로 이동
        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + historyDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/history_update";
        }
    }

    /**
     * 연혁 삭제
     * @param hstry_yr
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("hstry_yr") String hstry_yr) {
        historyService.delete(hstry_yr);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/existYearData")
    public String existYearData (@RequestParam("hstryYr") String hstryYr) {
        return historyService.existYearData(hstryYr);
    }
}

