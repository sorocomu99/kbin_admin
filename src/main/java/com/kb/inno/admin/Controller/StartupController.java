package com.kb.inno.admin.Controller;

import java.util.*;

import com.kb.inno.admin.DTO.StartersDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kb.inno.admin.DTO.StartupDTO;
import com.kb.inno.admin.Service.StartupService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    /**
     * 등록 페이지 이동
     * @param menuId
     * @param model
     * @return
     */
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/startup_insert";
    }

    @RequestMapping("/insert")
    public String insert(StartupDTO startupDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
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

        /* 사업 서비스 정보 시작 */
        //서비스명 리스트로 담기
        List<String> srvc_nm_List = new ArrayList<>();
        srvc_nm_List = Arrays.asList(startupDTO.getSrvc_nm().split(",", -1));
        resMap.put("srvc_nm_List", srvc_nm_List);

        //서비스 웹 링크 리스트로 담기
        List<String> web_srvc_link_List = new ArrayList<>();
        web_srvc_link_List = Arrays.asList(startupDTO.getWeb_srvc_link().split(",", -1));
        resMap.put("web_srvc_link_List", web_srvc_link_List);

        //구글앱 링크 리스트로 담기
        List<String> google_app_link_List = new ArrayList<>();
        google_app_link_List = Arrays.asList(startupDTO.getGoogle_app_link().split(",", -1));
        resMap.put("google_app_link_List", google_app_link_List);

        //아이폰 앱 링크 리스트로 담기
        List<String> apple_app_link_List = new ArrayList<>();
        apple_app_link_List = Arrays.asList(startupDTO.getApple_app_link().split(",", -1));
        resMap.put("apple_app_link_List", apple_app_link_List);
        /* 사업 서비스 정보 종료 */

        /* 투자정보 시작 */
        //투자일자 리스트로 담기
        List<String> invest_ymd_List = new ArrayList<>();
        System.out.println("startupDTO.getInvest_ymd()============"+startupDTO.getInvest_ymd());
        invest_ymd_List = Arrays.asList(startupDTO.getInvest_ymd().split(",", -1));
        resMap.put("invest_ymd_List", invest_ymd_List);

        //투자단계 리스트로 담기
        List<String> series_nm_List = new ArrayList<>();
        series_nm_List = Arrays.asList(startupDTO.getSeries_nm().split(",", -1));
        resMap.put("series_nm_List", series_nm_List);

        //투자금액 리스트로 담기
        List<String> invest_amt_List = new ArrayList<>();
        invest_amt_List = Arrays.asList(startupDTO.getInvest_amt().split(",", -1));
        resMap.put("invest_amt_List", invest_amt_List);

        //투자자 리스트로 담기
        List<String> investor_List = new ArrayList<>();
        investor_List = Arrays.asList(startupDTO.getInvestor().split(",", -1));
        resMap.put("investor_List", investor_List);
        /* 투자정보 종료 */

        /* 고용정보 시작 */
        //기준년월 리스트로 담기
        List<String> crtr_ym_List = new ArrayList<>();
        crtr_ym_List = Arrays.asList(startupDTO.getCrtr_ym().split(",", -1));
        resMap.put("crtr_ym_List", crtr_ym_List);

        //입사자 리스트에 담기
        List<String> jncmp_nocs_List = new ArrayList<>();
        jncmp_nocs_List = Arrays.asList(startupDTO.getJncmp_nocs().split(",", -1));
        resMap.put("jncmp_nocs_List", jncmp_nocs_List);
        //퇴사자 리스트에 담기
        List<String> rsgntn_nocs_List = new ArrayList<>();
        rsgntn_nocs_List = Arrays.asList(startupDTO.getRsgntn_nocs().split(",", -1));
        resMap.put("rsgntn_nocs_List", rsgntn_nocs_List);
        //근무자 리스트에 담기
        List<String> hdof_nocs_List = new ArrayList<>();
        hdof_nocs_List = Arrays.asList(startupDTO.getHdof_nocs().split(",", -1));
        resMap.put("hdof_nocs_List", hdof_nocs_List);
        /* 고용정보 종료 */

        /* 뉴스 정보 시작 */
        //뉴스제목 리스트로 담기
        List<String> news_ttl_List = new ArrayList<>();
        news_ttl_List = Arrays.asList(startupDTO.getNews_ttl().split(",", -1));
        resMap.put("news_ttl_List", news_ttl_List);
        //제공자 리스트에 담기
        List<String> provider_List = new ArrayList<>();
        provider_List = Arrays.asList(startupDTO.getProvider().split(",", -1));
        resMap.put("provider_List", provider_List);
        //뉴스 링크 리스트에 담기
        List<String> news_link_List = new ArrayList<>();
        news_link_List = Arrays.asList(startupDTO.getNews_link().split(",", -1));
        resMap.put("news_link_List", news_link_List);
        /* 뉴스 정보 종료 */
        
        //기업코드 생성 UUID 16자리 생성
        UUID uuid = UUID.randomUUID();
        String entCd = uuid.toString().replace("-", "").substring(0, 16);
        startupDTO.setEnt_cd(entCd);

        resultMap = startupService.insert(startupDTO, resMap, loginId);

        //정상 등록되면 리스트 페이지로 이동
        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + startupDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/statrup_insert";
        }
    }

    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam String ent_cd, Model model) {
        startupService.selectDetail(ent_cd, model, menuId);

        model.addAttribute("menuId", menuId);
        model.addAttribute("ent_cd", ent_cd);

        return directory + "/startup_update";
    }

    @RequestMapping("/update")
    public String update(StartupDTO startupDTO, RedirectAttributes redirectAttributes, HttpServletRequest request) {
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

        /* 사업 서비스 정보 시작 */
        //서비스명 리스트로 담기
        List<String> srvc_nm_List = new ArrayList<>();
        srvc_nm_List = Arrays.asList(startupDTO.getSrvc_nm().split(",", -1));
        resMap.put("srvc_nm_List", srvc_nm_List);

        //서비스 웹 링크 리스트로 담기
        List<String> web_srvc_link_List = new ArrayList<>();
        web_srvc_link_List = Arrays.asList(startupDTO.getWeb_srvc_link().split(",", -1));
        resMap.put("web_srvc_link_List", web_srvc_link_List);

        //구글앱 링크 리스트로 담기
        List<String> google_app_link_List = new ArrayList<>();
        google_app_link_List = Arrays.asList(startupDTO.getGoogle_app_link().split(",", -1));
        resMap.put("google_app_link_List", google_app_link_List);

        //아이폰 앱 링크 리스트로 담기
        List<String> apple_app_link_List = new ArrayList<>();
        apple_app_link_List = Arrays.asList(startupDTO.getApple_app_link().split(",", -1));
        resMap.put("apple_app_link_List", apple_app_link_List);
        /* 사업 서비스 정보 종료 */

        /* 투자정보 시작 */
        //투자일자 리스트로 담기
        List<String> invest_ymd_List = new ArrayList<>();
        System.out.println("startupDTO.getInvest_ymd()============"+startupDTO.getInvest_ymd());
        invest_ymd_List = Arrays.asList(startupDTO.getInvest_ymd().split(",", -1));
        resMap.put("invest_ymd_List", invest_ymd_List);

        //투자단계 리스트로 담기
        List<String> series_nm_List = new ArrayList<>();
        series_nm_List = Arrays.asList(startupDTO.getSeries_nm().split(",", -1));
        resMap.put("series_nm_List", series_nm_List);

        //투자금액 리스트로 담기
        List<String> invest_amt_List = new ArrayList<>();
        invest_amt_List = Arrays.asList(startupDTO.getInvest_amt().split(",", -1));
        resMap.put("invest_amt_List", invest_amt_List);

        //투자자 리스트로 담기
        List<String> investor_List = new ArrayList<>();
        investor_List = Arrays.asList(startupDTO.getInvestor().split(",", -1));
        resMap.put("investor_List", investor_List);
        /* 투자정보 종료 */

        /* 고용정보 시작 */
        //기준년월 리스트로 담기
        List<String> crtr_ym_List = new ArrayList<>();
        crtr_ym_List = Arrays.asList(startupDTO.getCrtr_ym().split(",", -1));
        resMap.put("crtr_ym_List", crtr_ym_List);
        //입사자 리스트에 담기
        List<String> jncmp_nocs_List = new ArrayList<>();
        jncmp_nocs_List = Arrays.asList(startupDTO.getJncmp_nocs().split(",", -1));
        resMap.put("jncmp_nocs_List", jncmp_nocs_List);
        //퇴사자 리스트에 담기
        List<String> rsgntn_nocs_List = new ArrayList<>();
        rsgntn_nocs_List = Arrays.asList(startupDTO.getRsgntn_nocs().split(",", -1));
        resMap.put("rsgntn_nocs_List", rsgntn_nocs_List);
        //근무자 리스트에 담기
        List<String> hdof_nocs_List = new ArrayList<>();
        hdof_nocs_List = Arrays.asList(startupDTO.getHdof_nocs().split(",", -1));
        resMap.put("hdof_nocs_List", hdof_nocs_List);
        /* 고용정보 종료 */

        /* 뉴스 정보 시작 */
        //뉴스제목 리스트로 담기
        List<String> news_ttl_List = new ArrayList<>();
        news_ttl_List = Arrays.asList(startupDTO.getNews_ttl().split(",", -1));
        resMap.put("news_ttl_List", news_ttl_List);
        //제공자 리스트에 담기
        List<String> provider_List = new ArrayList<>();
        provider_List = Arrays.asList(startupDTO.getProvider().split(",", -1));
        resMap.put("provider_List", provider_List);
        //뉴스 링크 리스트에 담기
        List<String> news_link_List = new ArrayList<>();
        news_link_List = Arrays.asList(startupDTO.getNews_link().split(",", -1));
        resMap.put("news_link_List", news_link_List);
        /* 뉴스 정보 종료 */

        resultMap = startupService.update(startupDTO, resMap, loginId);

        //정상 등록되면 리스트 페이지로 이동
        if (resultMap.get("errorCd").equals("00")) {
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return "redirect:" + directory + "/list/" + startupDTO.getMenu_id();
        } else {  //등록 중 오류 발생시 등록 화면 유지
            redirectAttributes.addFlashAttribute("msg", resultMap.get("errorMsg"));
            return directory + "/statrup_update";
        }
    }

    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("ent_cd") String ent_cd) {
        startupService.delete(ent_cd);
        return "success";
    }

    /**
     * KB 스타터스 미리보기
     */
    @PostMapping("/preview")
    public String preview(StartupDTO startupDTO, Model model) {
        //DB에 저장할 최종값을 담을 Map
        HashMap<String, Object> resMap = new HashMap<>();

        /* 사업 서비스 정보 시작 */
        //서비스명 리스트로 담기
        List<String> srvc_nm_List = new ArrayList<>();
        srvc_nm_List = Arrays.asList(startupDTO.getSrvc_nm().split(",", -1));
        resMap.put("srvc_nm_List", srvc_nm_List);

        //서비스 웹 링크 리스트로 담기
        List<String> web_srvc_link_List = new ArrayList<>();
        web_srvc_link_List = Arrays.asList(startupDTO.getWeb_srvc_link().split(",", -1));
        resMap.put("web_srvc_link_List", web_srvc_link_List);

        //구글앱 링크 리스트로 담기
        List<String> google_app_link_List = new ArrayList<>();
        google_app_link_List = Arrays.asList(startupDTO.getGoogle_app_link().split(",", -1));
        resMap.put("google_app_link_List", google_app_link_List);

        //아이폰 앱 링크 리스트로 담기
        List<String> apple_app_link_List = new ArrayList<>();
        apple_app_link_List = Arrays.asList(startupDTO.getApple_app_link().split(",", -1));
        resMap.put("apple_app_link_List", apple_app_link_List);
        /* 사업 서비스 정보 종료 */

        /* 투자정보 시작 */
        //투자일자 리스트로 담기
        List<String> invest_ymd_List = new ArrayList<>();
        System.out.println("startupDTO.getInvest_ymd()============"+startupDTO.getInvest_ymd());
        invest_ymd_List = Arrays.asList(startupDTO.getInvest_ymd().split(",", -1));
        resMap.put("invest_ymd_List", invest_ymd_List);

        //투자단계 리스트로 담기
        List<String> series_nm_List = new ArrayList<>();
        series_nm_List = Arrays.asList(startupDTO.getSeries_nm().split(",", -1));
        resMap.put("series_nm_List", series_nm_List);

        //투자금액 리스트로 담기
        List<String> invest_amt_List = new ArrayList<>();
        invest_amt_List = Arrays.asList(startupDTO.getInvest_amt().split(",", -1));
        resMap.put("invest_amt_List", invest_amt_List);

        //투자자 리스트로 담기
        List<String> investor_List = new ArrayList<>();
        investor_List = Arrays.asList(startupDTO.getInvestor().split(",", -1));
        resMap.put("investor_List", investor_List);
        /* 투자정보 종료 */

        /* 고용정보 시작 */
        //기준년월 리스트로 담기
        List<String> crtr_ym_List = new ArrayList<>();
        crtr_ym_List = Arrays.asList(startupDTO.getCrtr_ym().split(",", -1));
        resMap.put("crtr_ym_List", crtr_ym_List);

        //입사자 리스트에 담기
        List<String> jncmp_nocs_List = new ArrayList<>();
        jncmp_nocs_List = Arrays.asList(startupDTO.getJncmp_nocs().split(",", -1));
        resMap.put("jncmp_nocs_List", jncmp_nocs_List);
        //퇴사자 리스트에 담기
        List<String> rsgntn_nocs_List = new ArrayList<>();
        rsgntn_nocs_List = Arrays.asList(startupDTO.getRsgntn_nocs().split(",", -1));
        resMap.put("rsgntn_nocs_List", rsgntn_nocs_List);
        //근무자 리스트에 담기
        List<String> hdof_nocs_List = new ArrayList<>();
        hdof_nocs_List = Arrays.asList(startupDTO.getHdof_nocs().split(",", -1));
        resMap.put("hdof_nocs_List", hdof_nocs_List);
        /* 고용정보 종료 */

        /* 뉴스 정보 시작 */
        //뉴스제목 리스트로 담기
        List<String> news_ttl_List = new ArrayList<>();
        news_ttl_List = Arrays.asList(startupDTO.getNews_ttl().split(",", -1));
        resMap.put("news_ttl_List", news_ttl_List);
        //제공자 리스트에 담기
        List<String> provider_List = new ArrayList<>();
        provider_List = Arrays.asList(startupDTO.getProvider().split(",", -1));
        resMap.put("provider_List", provider_List);
        //뉴스 링크 리스트에 담기
        List<String> news_link_List = new ArrayList<>();
        news_link_List = Arrays.asList(startupDTO.getNews_link().split(",", -1));
        resMap.put("news_link_List", news_link_List);
        /* 뉴스 정보 종료 */

        startupService.getPreview(startupDTO, resMap, model);

        return directory + "/startup_preview";
    }
}
