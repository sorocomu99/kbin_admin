/**
 * 파일명     : PortfolioController.java
 * 화면명     : 포트폴리오 관리
 * 설명       : 포트폴리오 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.12.31
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.HistoryDTO;
import com.kb.inno.admin.DTO.PortfolioDTO;
import com.kb.inno.admin.DTO.SurveyDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.admin.Service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    // 디렉터리 공통
    @Value("/portfolio")
    private String directory;
    
    // Service 연결
    private final PortfolioService portfolioService;

    /**
     * 포트폴리오 년도 리스트 조회
     * @param menuId
     * @param model
     * @return
     */
    @GetMapping("/list/{menuId}")
    public String portYearList(@PathVariable int menuId, Model model) {
        //List<HistoryDTO> selectList = historyService.selectList();
        portfolioService.selectListYear(menuId, model);
        //model.addAttribute("selectList", selectList);
        //model.addAttribute("menuId", menuId);
        return directory + "/portfolio_year";
    }

    /**
     * 포트폴리오 년도 등록페이지 이동
     * @param menuId
     * @param model
     * @return
     */
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/portfolio_year_insert";
    }

    /**
     * 포트폴리오 년도 등록
     * @param portfolioDTO
     * @param redirectAttributes
     * @param request
     * @return
     */
    @PostMapping("/yearInsert")
    public String yearInsert(PortfolioDTO portfolioDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //로그인 세션 정보 가져오기
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        resultMap = portfolioService.yearInsert(portfolioDTO, loginId);

        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + portfolioDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/portfolio_year_insert";
        }
    }

    /**
     * 포트폴리오 년도 상세
     * @param menuId
     * @param port_yr
     * @param model
     * @return
     */
    @PostMapping("/yearDetail")
    public String detail(@RequestParam int menuId, @RequestParam String port_yr, Model model) {
        portfolioService.selectYearDetail(port_yr, model);

        model.addAttribute("menuId", menuId);
        model.addAttribute("port_yr", port_yr);

        return directory + "/portfolio_year_update";
    }

    /**
     * 포트폴리오 년도 수정
     * @param redirectAttributes
     * @param portfolioDTO
     * @param request
     * @return
     */
    @PostMapping("/yearUpdate")
    public String yearUpdate(RedirectAttributes redirectAttributes, PortfolioDTO portfolioDTO, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        resultMap = portfolioService.yearUpdate(portfolioDTO, loginId);

        //정상 등록되면 리스트 페이지로 이동
        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + portfolioDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/portfolio_year_update";
        }
    }

    /**
     * 포트폴리오 년도 삭제
     * @param port_yr
     * @return
     */
    @ResponseBody
    @RequestMapping("/portYrDelete")
    public String portYrDelete(@RequestParam("port_yr") String port_yr) {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setPort_yr(port_yr);
        portfolioService.portYrDelete(portfolioDTO);
        return "success";
    }

    /**
     * 년도별 포트폴리오 리스트
     * @param menuId
     * @param port_yr
     * @param model
     * @param portfolioDTO
     * @return
     */
    //@GetMapping("/portList/{menuId}/port_yr/{port_yr}")
    @GetMapping("/portList/{menuId}")
    public String portList(@PathVariable int menuId, @RequestParam String port_yr, Model model, PortfolioDTO portfolioDTO,
                           @RequestParam(value = "type", required = false, defaultValue = "") String type,
                           @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        //List<HistoryDTO> selectList = historyService.selectList();
        //portfolioDTO.setPort_yr(port_yr);
        portfolioService.selectPortList(menuId, model, port_yr, type, keyword, page);
        //model.addAttribute("selectList", selectList);
        //model.addAttribute("menuId", menuId);
        return directory + "/portfolio";
    }

    /**
     * 년도별 포트폴리오 등록 페이지 이동
     * @param menuId
     * @param model
     * @param port_yr
     * @return
     */
    @RequestMapping("/portInsert/{menuId}")
    public String portInsert(@PathVariable int menuId, Model model, @RequestParam String port_yr) {
        model.addAttribute("menuId", menuId);
        model.addAttribute("port_yr", port_yr);
        return directory + "/portfolio_insert";
    }

    /**
     * 년도별 포트폴리오 등록
     * @param portfolioDTO
     * @param redirectAttributes
     * @param request
     * @return
     */
    @PostMapping("/portListInsert")
    public String portListInsert(PortfolioDTO portfolioDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //로그인 세션 정보 가져오기
        int loginId = 0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            loginId = (int) session.getAttribute("mngrSn");
        }

        resultMap = portfolioService.portListInsert(portfolioDTO, loginId);

        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/portList/" + portfolioDTO.getMenu_id()+"?port_yr="+portfolioDTO.getPort_yr();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/portfolio_insert";
        }
    }

    /**
     * 년도별 포트폴리오 상세
     * @param menuId
     * @param port_sn
     * @param port_yr
     * @param model
     * @return
     */
    @RequestMapping("/portListDet")
    public String portListDet(@RequestParam int menuId, @RequestParam int port_sn, @RequestParam String port_yr, Model model) {
        portfolioService.portListDet(port_sn, port_yr, model);
        //model.addAttribute("portListDet", portListDet);
        model.addAttribute("menuId", menuId);
        model.addAttribute("port_yr", port_yr);
        model.addAttribute("port_sn", port_sn);
        return directory + "/portfolio_update";
    }

    /**
     * 년도별 포트폴리오 수정
     * @param portfolioDTO
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequestMapping("/portListUpdate")
    public String portListUpdate(PortfolioDTO portfolioDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        HttpSession session = request.getSession(false);
        int loginId = (int) session.getAttribute("mngrSn");

        resultMap = portfolioService.portListUpdate(portfolioDTO, loginId);

        //정상 등록되면 리스트 페이지로 이동
        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/portList/" + portfolioDTO.getMenu_id()+"?port_yr="+portfolioDTO.getPort_yr();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/portfolio_update";
        }
    }

    /**
     * 년도별 포트폴리오 삭제
     * @param port_yr
     * @param menuId
     * @param port_sn
     * @return
     */
    @ResponseBody
    @RequestMapping("/portListDel")
    public String portListDel(@RequestParam("port_yr") String port_yr, @RequestParam("menuId") int menuId, @RequestParam("port_sn") int port_sn) {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setPort_sn(port_sn);
        portfolioDTO.setPort_yr(port_yr);
        portfolioDTO.setMenu_id(menuId);
        portfolioService.portListDel(portfolioDTO);
        return "success";
    }
}

