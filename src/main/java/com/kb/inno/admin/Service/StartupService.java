package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.StartupDAO;
import com.kb.inno.admin.DTO.StartupDTO;
import com.kb.inno.admin.DTO.starter.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StartupService {

    // 서비스 연결
    private final StartupDAO startupDAO;

    /**
     * 스타트업 리스트 조회
     * @param menuId
     * @param model
     * @param type
     * @param keyword
     * @param page
     */
    public void selectList(int menuId, Model model, String type, String keyword, int page) {
        // Search DTO에 담기
        StartupDTO startupDTO = new StartupDTO();
        startupDTO.setType(type);
        startupDTO.setKeyword(keyword);

        // 페이지의 전체 글 갯수
        int allCount = startupDAO.selectPageCount(startupDTO);

        // 한 페이지당 글 갯수
        int pageLetter = 10;

        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        // 나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            // 더하기
            repeat += 1;
        }

        // repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        // 만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        startupDTO.setEnd(end);
        // 시작 페이지
        int start = end + 1 - pageLetter;

        startupDTO.setStart(start);

        // 리스트 조회
        List<StartupDTO> selectList = startupDAO.selectList(startupDTO);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        model.addAttribute("type", startupDTO.getType());
        model.addAttribute("keyword", startupDTO.getKeyword());
    }

    public HashMap<String, Object> insert(StartupDTO startupDTO, HashMap<String, Object> map, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        startupDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        startupDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        //기업정보 등록 (KB_API_STARTER_INFO)
        startupDAO.insertStarterInfo(startupDTO);
        //사업서비스 정보 등록 (KB_API_BIZ_SRVC_INFO)
        if (map.get("srvc_nm_List") != null && !map.get("srvc_nm_List").equals("")) {
            List<String> srvc_nm_List = new ArrayList<>();
            List<String> web_srvc_link_List = new ArrayList<>();
            List<String> google_app_link_List = new ArrayList<>();
            List<String> apple_app_link_List = new ArrayList<>();
            srvc_nm_List = (List) map.get("srvc_nm_List");
            web_srvc_link_List = (List) map.get("web_srvc_link_List");
            google_app_link_List = (List) map.get("google_app_link_List");
            apple_app_link_List = (List) map.get("apple_app_link_List");
            for (int i = 0; i < srvc_nm_List.size(); i++) {
                startupDTO.setSrvc_nm(srvc_nm_List.get(i));
                startupDTO.setWeb_srvc_link(web_srvc_link_List.get(i));
                startupDTO.setGoogle_app_link(google_app_link_List.get(i));
                startupDTO.setApple_app_link(apple_app_link_List.get(i));
                if(StringUtils.hasText(startupDTO.getSrvc_nm())
                        || StringUtils.hasText(startupDTO.getWeb_srvc_link())
                        || StringUtils.hasText(startupDTO.getGoogle_app_link())
                        || StringUtils.hasText(startupDTO.getApple_app_link())) {
                    startupDAO.insertBizSrvcInfo(startupDTO);
                }
            }
        }
        //투자정보 등록 (KB_API_INVEST_INFO)
        if (map.get("invest_ymd_List") != null && !map.get("invest_ymd_List").equals("")) {
            List<String> invest_ymd_List = new ArrayList<>();
            List<String> series_nm_List = new ArrayList<>();
            List<String> invest_amt_List = new ArrayList<>();
            List<String> investor_List = new ArrayList<>();
            invest_ymd_List = (List) map.get("invest_ymd_List");
            series_nm_List = (List) map.get("series_nm_List");
            invest_amt_List = (List) map.get("invest_amt_List");
            investor_List = (List) map.get("investor_List");
            for (int i = 0; i < invest_ymd_List.size(); i++) {
                startupDTO.setInvest_ymd(invest_ymd_List.get(i));
                startupDTO.setSeries_nm(series_nm_List.get(i));
                startupDTO.setInvest_amt(invest_amt_List.get(i));
                startupDTO.setInvestor(investor_List.get(i));
                if(StringUtils.hasText(startupDTO.getInvest_ymd())
                        || StringUtils.hasText(startupDTO.getSeries_nm())
                        || StringUtils.hasText(startupDTO.getInvest_amt())
                        || StringUtils.hasText(startupDTO.getInvestor())) {
                    startupDAO.insertInvestInfo(startupDTO);
                }
            }
        }
        //고용현황 등록 (KB_API_EMPLO_INFO)
        if (map.get("crtr_ym_List") != null && !map.get("crtr_ym_List").equals("")) {
            List<String> crtr_ym_List = new ArrayList<>();
            List<String> jncmp_nocs_List = new ArrayList<>();
            List<String> rsgntn_nocs_List = new ArrayList<>();
            List<String> hdof_nocs_List = new ArrayList<>();
            crtr_ym_List = (List) map.get("crtr_ym_List");
            jncmp_nocs_List = (List) map.get("jncmp_nocs_List");
            rsgntn_nocs_List = (List) map.get("rsgntn_nocs_List");
            hdof_nocs_List = (List) map.get("hdof_nocs_List");
            for (int i = 0; i < crtr_ym_List.size(); i++) {
                if(StringUtils.hasText(crtr_ym_List.get(i))) {
                    startupDTO.setCrtr_ym(crtr_ym_List.get(i));
                    startupDTO.setJncmp_nocs(jncmp_nocs_List.get(i));
                    startupDTO.setRsgntn_nocs(rsgntn_nocs_List.get(i));
                    startupDTO.setHdof_nocs(hdof_nocs_List.get(i));
                    startupDAO.insertEmploInfo(startupDTO);
                }
            }
        }
        //매출정보(손익계산서) 등록 (KB_API_SLS_INFO)
        startupDTO.setSls_yr(startupDTO.getSls_yr1());
        startupDTO.setSls_amt(startupDTO.getSls_amt1());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt1());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt1());
        startupDTO.setSga_amt(startupDTO.getSga_amt1());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit1());
        startupDTO.setNet_profit(startupDTO.getNet_profit1());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr2());
        startupDTO.setSls_amt(startupDTO.getSls_amt2());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt2());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt2());
        startupDTO.setSga_amt(startupDTO.getSga_amt2());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit2());
        startupDTO.setNet_profit(startupDTO.getNet_profit2());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr3());
        startupDTO.setSls_amt(startupDTO.getSls_amt3());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt3());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt3());
        startupDTO.setSga_amt(startupDTO.getSga_amt3());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit3());
        startupDTO.setNet_profit(startupDTO.getNet_profit3());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr4());
        startupDTO.setSls_amt(startupDTO.getSls_amt4());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt4());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt4());
        startupDTO.setSga_amt(startupDTO.getSga_amt4());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit4());
        startupDTO.setNet_profit(startupDTO.getNet_profit4());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr5());
        startupDTO.setSls_amt(startupDTO.getSls_amt5());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt5());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt5());
        startupDTO.setSga_amt(startupDTO.getSga_amt5());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit5());
        startupDTO.setNet_profit(startupDTO.getNet_profit5());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }
        //자산정보(재무상태표) 등록 (KB_API_AST_INFO)
        startupDTO.setAst_yr(startupDTO.getAst_yr1());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets1());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets1());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt1());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt1());
        startupDTO.setCptl(startupDTO.getCptl1());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt1());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr2());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets2());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets2());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt2());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt2());
        startupDTO.setCptl(startupDTO.getCptl2());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt2());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr3());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets3());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets3());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt3());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt3());
        startupDTO.setCptl(startupDTO.getCptl3());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt3());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr4());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets4());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets4());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt4());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt4());
        startupDTO.setCptl(startupDTO.getCptl4());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt4());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr5());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets5());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets5());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt5());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt5());
        startupDTO.setCptl(startupDTO.getCptl5());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt5());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }
        //뉴스정보 등록 (KB_API_NEWS_INFO)
        if (map.get("news_ttl_List") != null && !map.get("news_ttl_List").equals("")) {
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateTimeStr = formatter.format(now);
            Random random = new Random();

            List<String> news_ttl_List = new ArrayList<>();
            List<String> provider_List = new ArrayList<>();
            List<String> news_link_List = new ArrayList<>();
            news_ttl_List = (List) map.get("news_ttl_List");
            provider_List = (List) map.get("provider_List");
            news_link_List = (List) map.get("news_link_List");

            for (int i = 0; i < news_ttl_List.size(); i++) {
                if(i == 0) {
                    try{
                        startupDTO.setThumb_url(startupDTO.getThumb_url());
                    }catch (Exception ignored) {}
                }else{
                    startupDTO.setThumb_url(null);
                }

                startupDTO.setNews_id(dateTimeStr + 100 + random.nextInt(900));
                startupDTO.setNews_ttl(news_ttl_List.get(i));
                startupDTO.setProvider(provider_List.get(i));
                startupDTO.setNews_link(news_link_List.get(i));

                if(StringUtils.hasText(startupDTO.getNews_ttl())
                        || StringUtils.hasText(startupDTO.getProvider())
                        || StringUtils.hasText(startupDTO.getNews_link())) {
                    startupDAO.insertNewsInfo(startupDTO);
                }
            }
        }
        //키워드정보 등록 (KB_API_KEYWD_INFO)
        startupDTO.setKeywd(startupDTO.getKeywd1());
        startupDTO.setNocs(startupDTO.getNocs1());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd2());
        startupDTO.setNocs(startupDTO.getNocs2());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd3());
        startupDTO.setNocs(startupDTO.getNocs3());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd4());
        startupDTO.setNocs(startupDTO.getNocs4());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd5());
        startupDTO.setNocs(startupDTO.getNocs5());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd6());
        startupDTO.setNocs(startupDTO.getNocs6());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd7());
        startupDTO.setNocs(startupDTO.getNocs7());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd8());
        startupDTO.setNocs(startupDTO.getNocs8());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd9());
        startupDTO.setNocs(startupDTO.getNocs9());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd10());
        startupDTO.setNocs(startupDTO.getNocs10());
        startupDAO.insertKeywdInfo(startupDTO);

        resultMap.put("errorCd", "00");
        resultMap.put("errorMsg", "KB 스타터스가 정상적으로 등록되었습니다.");

        return resultMap;
    }

    public void selectDetail(String ent_cd, Model model, int menuId) {
        StartupDTO startupDTO = new StartupDTO();
        startupDTO.setEnt_cd(ent_cd);
        StartupDTO selectStarterInfo = startupDAO.selectStarterInfo(startupDTO);
        model.addAttribute("selectStarterInfo", selectStarterInfo);

        List<StartupDTO> selectBizSrvcInfo = startupDAO.selectBizSrvcInfo(startupDTO);
        model.addAttribute("selectBizSrvcInfo", selectBizSrvcInfo);

        List<StartupDTO> selectInvestInfo = startupDAO.selectInvestInfo(startupDTO);
        model.addAttribute("selectInvestInfo", selectInvestInfo);

        List<StartupDTO> selectEmploInfo = startupDAO.selectEmploInfo(startupDTO);
        model.addAttribute("selectEmploInfo", selectEmploInfo);

        List<StartupDTO> selectListSlsInfo = startupDAO.selectListSlsInfo(startupDTO);
        model.addAttribute("selectSlsInfoList", selectListSlsInfo);

        List<StartupDTO> selectListAstInfo = startupDAO.selectListAstInfo(startupDTO);
        model.addAttribute("selectListAstInfo", selectListAstInfo);

        List<StartupDTO> selectNewsInfo = startupDAO.selectNewsInfo(startupDTO);
        model.addAttribute("selectNewsInfo", selectNewsInfo);

        List<StartupDTO> selectKeywdInfo = startupDAO.selectKeywdInfo(startupDTO);
        model.addAttribute("selectKeywdInfo", selectKeywdInfo);

        model.addAttribute("menuId", menuId);
        model.addAttribute("ent_cd", ent_cd);
    }

    public HashMap<String, Object> update(StartupDTO startupDTO, HashMap<String, Object> map, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        startupDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        startupDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        //기업정보 수정 (KB_API_STARTER_INFO)
        startupDAO.updateStarterInfo(startupDTO);
        //사업서비스 정보 등록 (KB_API_BIZ_SRVC_INFO)
        if (map.get("srvc_nm_List") != null && !map.get("srvc_nm_List").equals("")) {
            List<String> srvc_nm_List = new ArrayList<>();
            List<String> web_srvc_link_List = new ArrayList<>();
            List<String> google_app_link_List = new ArrayList<>();
            List<String> apple_app_link_List = new ArrayList<>();
            srvc_nm_List = (List) map.get("srvc_nm_List");
            web_srvc_link_List = (List) map.get("web_srvc_link_List");
            google_app_link_List = (List) map.get("google_app_link_List");
            apple_app_link_List = (List) map.get("apple_app_link_List");
            startupDAO.deleteBizSrvcInfo(startupDTO);
            for (int i = 0; i < srvc_nm_List.size(); i++) {
                startupDTO.setSrvc_nm(srvc_nm_List.get(i));
                startupDTO.setWeb_srvc_link(web_srvc_link_List.get(i));
                startupDTO.setGoogle_app_link(google_app_link_List.get(i));
                startupDTO.setApple_app_link(apple_app_link_List.get(i));
                if(StringUtils.hasText(startupDTO.getSrvc_nm())
                        || StringUtils.hasText(startupDTO.getWeb_srvc_link())
                        || StringUtils.hasText(startupDTO.getGoogle_app_link())
                        || StringUtils.hasText(startupDTO.getApple_app_link())) {
                    startupDAO.insertBizSrvcInfo(startupDTO);
                }
            }
        }
        //투자정보 등록 (KB_API_INVEST_INFO)
        if (map.get("invest_ymd_List") != null && !map.get("invest_ymd_List").equals("")) {
            List<String> invest_ymd_List = new ArrayList<>();
            List<String> series_nm_List = new ArrayList<>();
            List<String> invest_amt_List = new ArrayList<>();
            List<String> investor_List = new ArrayList<>();
            invest_ymd_List = (List) map.get("invest_ymd_List");
            series_nm_List = (List) map.get("series_nm_List");
            invest_amt_List = (List) map.get("invest_amt_List");
            investor_List = (List) map.get("investor_List");
            startupDAO.deleteInvestInfo(startupDTO);
            for (int i = 0; i < invest_ymd_List.size(); i++) {
                startupDTO.setInvest_ymd(invest_ymd_List.get(i));
                startupDTO.setSeries_nm(series_nm_List.get(i));
                startupDTO.setInvest_amt(invest_amt_List.get(i));
                startupDTO.setInvestor(investor_List.get(i));
                if(StringUtils.hasText(startupDTO.getInvest_ymd())
                        || StringUtils.hasText(startupDTO.getSeries_nm())
                        || StringUtils.hasText(startupDTO.getInvest_amt())
                        || StringUtils.hasText(startupDTO.getInvestor())) {
                    startupDAO.insertInvestInfo(startupDTO);
                }
            }
        }
        //고용현황 등록 (KB_API_EMPLO_INFO)
        if (map.get("crtr_ym_List") != null && !map.get("crtr_ym_List").equals("")) {
            List<String> crtr_ym_List = new ArrayList<>();
            List<String> jncmp_nocs_List = new ArrayList<>();
            List<String> rsgntn_nocs_List = new ArrayList<>();
            List<String> hdof_nocs_List = new ArrayList<>();
            crtr_ym_List = (List) map.get("crtr_ym_List");
            jncmp_nocs_List = (List) map.get("jncmp_nocs_List");
            rsgntn_nocs_List = (List) map.get("rsgntn_nocs_List");
            hdof_nocs_List = (List) map.get("hdof_nocs_List");
            startupDAO.deleteEmploInfo(startupDTO);
            for (int i = 0; i < crtr_ym_List.size(); i++) {
                if(StringUtils.hasText(crtr_ym_List.get(i))) {
                    startupDTO.setCrtr_ym(crtr_ym_List.get(i));
                    startupDTO.setJncmp_nocs(jncmp_nocs_List.get(i));
                    startupDTO.setRsgntn_nocs(rsgntn_nocs_List.get(i));
                    startupDTO.setHdof_nocs(hdof_nocs_List.get(i));
                    startupDAO.insertEmploInfo(startupDTO);
                }
            }
        }
        //매출정보(손익계산서) 등록 (KB_API_SLS_INFO)
        startupDAO.deleteSlsInfo(startupDTO);
        startupDTO.setSls_yr(startupDTO.getSls_yr1());
        startupDTO.setSls_amt(startupDTO.getSls_amt1());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt1());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt1());
        startupDTO.setSga_amt(startupDTO.getSga_amt1());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit1());
        startupDTO.setNet_profit(startupDTO.getNet_profit1());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr2());
        startupDTO.setSls_amt(startupDTO.getSls_amt2());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt2());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt2());
        startupDTO.setSga_amt(startupDTO.getSga_amt2());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit2());
        startupDTO.setNet_profit(startupDTO.getNet_profit2());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr3());
        startupDTO.setSls_amt(startupDTO.getSls_amt3());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt3());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt3());
        startupDTO.setSga_amt(startupDTO.getSga_amt3());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit3());
        startupDTO.setNet_profit(startupDTO.getNet_profit3());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr4());
        startupDTO.setSls_amt(startupDTO.getSls_amt4());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt4());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt4());
        startupDTO.setSga_amt(startupDTO.getSga_amt4());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit4());
        startupDTO.setNet_profit(startupDTO.getNet_profit4());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr5());
        startupDTO.setSls_amt(startupDTO.getSls_amt5());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt5());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt5());
        startupDTO.setSga_amt(startupDTO.getSga_amt5());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit5());
        startupDTO.setNet_profit(startupDTO.getNet_profit5());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            startupDAO.insertSlsInfo(startupDTO);
        }
        //자산정보(재무상태표) 등록 (KB_API_AST_INFO)
        startupDAO.deleteAstInfo(startupDTO);
        startupDTO.setAst_yr(startupDTO.getAst_yr1());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets1());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets1());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt1());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt1());
        startupDTO.setCptl(startupDTO.getCptl1());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt1());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr2());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets2());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets2());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt2());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt2());
        startupDTO.setCptl(startupDTO.getCptl2());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt2());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr3());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets3());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets3());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt3());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt3());
        startupDTO.setCptl(startupDTO.getCptl3());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt3());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr4());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets4());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets4());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt4());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt4());
        startupDTO.setCptl(startupDTO.getCptl4());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt4());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr5());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets5());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets5());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt5());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt5());
        startupDTO.setCptl(startupDTO.getCptl5());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt5());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            startupDAO.insertAstInfo(startupDTO);
        }
        //뉴스정보 등록 (KB_API_NEWS_INFO)
        if (map.get("news_ttl_List") != null && !map.get("news_ttl_List").equals("")) {
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateTimeStr = formatter.format(now);
            Random random = new Random();

            List<String> news_ttl_List = new ArrayList<>();
            List<String> provider_List = new ArrayList<>();
            List<String> news_link_List = new ArrayList<>();
            news_ttl_List = (List) map.get("news_ttl_List");
            provider_List = (List) map.get("provider_List");
            news_link_List = (List) map.get("news_link_List");
            startupDAO.deleteNewsInfo(startupDTO);

            for (int i = 0; i < news_ttl_List.size(); i++) {
                if(i == 0) {
                    try{
                        startupDTO.setThumb_url(startupDTO.getThumb_url());
                    }catch (Exception ignored) {}
                }else{
                    startupDTO.setThumb_url(null);
                }

                startupDTO.setNews_id(dateTimeStr + 100 + random.nextInt(900));
                startupDTO.setNews_ttl(news_ttl_List.get(i));
                startupDTO.setProvider(provider_List.get(i));
                startupDTO.setNews_link(news_link_List.get(i));

                if(StringUtils.hasText(startupDTO.getNews_ttl())
                        || StringUtils.hasText(startupDTO.getProvider())
                        || StringUtils.hasText(startupDTO.getNews_link())) {
                    startupDAO.insertNewsInfo(startupDTO);
                }
            }
        }
        //키워드정보 등록 (KB_API_KEYWD_INFO)
        startupDAO.deleteKeywdInfo(startupDTO);
        startupDTO.setKeywd(startupDTO.getKeywd1());
        startupDTO.setNocs(startupDTO.getNocs1());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd2());
        startupDTO.setNocs(startupDTO.getNocs2());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd3());
        startupDTO.setNocs(startupDTO.getNocs3());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd4());
        startupDTO.setNocs(startupDTO.getNocs4());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd5());
        startupDTO.setNocs(startupDTO.getNocs5());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd6());
        startupDTO.setNocs(startupDTO.getNocs6());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd7());
        startupDTO.setNocs(startupDTO.getNocs7());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd8());
        startupDTO.setNocs(startupDTO.getNocs8());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd9());
        startupDTO.setNocs(startupDTO.getNocs9());
        startupDAO.insertKeywdInfo(startupDTO);

        startupDTO.setKeywd(startupDTO.getKeywd10());
        startupDTO.setNocs(startupDTO.getNocs10());
        startupDAO.insertKeywdInfo(startupDTO);

        resultMap.put("errorCd", "00");
        resultMap.put("errorMsg", "KB 스타터스가 정상적으로 수정되었습니다.");

        return resultMap;
    }

    public int delete(String ent_cd) {
        StartupDTO startupDTO = new StartupDTO();
        startupDTO.setEnt_cd(ent_cd);
        startupDAO.deleteKeywdInfo(startupDTO);
        startupDAO.deleteNewsInfo(startupDTO);
        startupDAO.deleteAstInfo(startupDTO);
        startupDAO.deleteSlsInfo(startupDTO);
        startupDAO.deleteEmploInfo(startupDTO);
        startupDAO.deleteInvestInfo(startupDTO);
        startupDAO.deleteBizSrvcInfo(startupDTO);
        startupDAO.deleteStarterInfo(startupDTO);
        return 1;
    }

    public void getPreview(StartupDTO startupDTO, HashMap<String, Object> map, Model model) {

        //사업서비스 정보 조회 (리스트)
        List<BizSrvcDTO> selectBizList = new ArrayList<>();
        if (map.get("srvc_nm_List") != null && !map.get("srvc_nm_List").equals("")) {
            List<String> srvc_nm_List = new ArrayList<>();
            List<String> web_srvc_link_List = new ArrayList<>();
            List<String> google_app_link_List = new ArrayList<>();
            List<String> apple_app_link_List = new ArrayList<>();
            srvc_nm_List = (List) map.get("srvc_nm_List");
            web_srvc_link_List = (List) map.get("web_srvc_link_List");
            google_app_link_List = (List) map.get("google_app_link_List");
            apple_app_link_List = (List) map.get("apple_app_link_List");

            for (int i = 0; i < srvc_nm_List.size(); i++) {
                startupDTO.setSrvc_nm(srvc_nm_List.get(i));
                startupDTO.setWeb_srvc_link(web_srvc_link_List.get(i));
                startupDTO.setGoogle_app_link(google_app_link_List.get(i));
                startupDTO.setApple_app_link(apple_app_link_List.get(i));
                if(StringUtils.hasText(startupDTO.getSrvc_nm())
                        || StringUtils.hasText(startupDTO.getWeb_srvc_link())
                        || StringUtils.hasText(startupDTO.getGoogle_app_link())
                        || StringUtils.hasText(startupDTO.getApple_app_link())) {
                    selectBizList.add(BizSrvcDTO.make(startupDTO));
                }
            }

        }

        long maxYear = 0;
        String lastInvestSeriesNm = "";
        Long sumInvestAmt = 0L;
        int investCnt = 0;

        //투자정보 조회 (리스트)
        List<InvestDTO> selectInvestList = new ArrayList<>();
        if (map.get("invest_ymd_List") != null && !map.get("invest_ymd_List").equals("")) {
            List<String> invest_ymd_List = new ArrayList<>();
            List<String> series_nm_List = new ArrayList<>();
            List<String> invest_amt_List = new ArrayList<>();
            List<String> investor_List = new ArrayList<>();
            invest_ymd_List = (List) map.get("invest_ymd_List");
            series_nm_List = (List) map.get("series_nm_List");
            invest_amt_List = (List) map.get("invest_amt_List");
            investor_List = (List) map.get("investor_List");
            for (int i = 0; i < invest_ymd_List.size(); i++) {
                startupDTO.setInvest_ymd(invest_ymd_List.get(i));
                startupDTO.setSeries_nm(series_nm_List.get(i));
                startupDTO.setInvest_amt(invest_amt_List.get(i));
                startupDTO.setInvestor(investor_List.get(i));
                if(StringUtils.hasText(startupDTO.getInvest_ymd())
                        || StringUtils.hasText(startupDTO.getSeries_nm())
                        || StringUtils.hasText(startupDTO.getInvest_amt())
                        || StringUtils.hasText(startupDTO.getInvestor())) {
                    selectInvestList.add(InvestDTO.make(startupDTO));
                    if(maxYear < Long.parseLong(startupDTO.getInvest_ymd().replaceAll("[^0-9]", ""))) {
                        lastInvestSeriesNm = startupDTO.getSeries_nm();
                        maxYear = Long.parseLong(startupDTO.getInvest_ymd().replaceAll("[^0-9]", ""));
                    }
                    investCnt++;
                    sumInvestAmt = sumInvestAmt + Long.parseLong(StringUtils.hasText(startupDTO.getInvest_amt()) ? startupDTO.getInvest_amt() : "0");
                }
            }
        }
        startupDTO.setInvest_amt(String.valueOf(sumInvestAmt));
        startupDTO.setSeries_nm(lastInvestSeriesNm);

        //기본정보 설정하기
        StaterDTO startupInfo = StaterDTO.make(startupDTO, investCnt);

        //고용정보 조회 (리스트)
        List<EmploDTO> selectEmploList = new ArrayList<>();
        if (map.get("crtr_ym_List") != null && !map.get("crtr_ym_List").equals("")) {
            List<String> crtr_ym_List = new ArrayList<>();
            List<String> jncmp_nocs_List = new ArrayList<>();
            List<String> rsgntn_nocs_List = new ArrayList<>();
            List<String> hdof_nocs_List = new ArrayList<>();
            crtr_ym_List = (List) map.get("crtr_ym_List");
            jncmp_nocs_List = (List) map.get("jncmp_nocs_List");
            rsgntn_nocs_List = (List) map.get("rsgntn_nocs_List");
            hdof_nocs_List = (List) map.get("hdof_nocs_List");
            for (int i = 0; i < crtr_ym_List.size(); i++) {
                if(StringUtils.hasText(crtr_ym_List.get(i))) {
                    startupDTO.setCrtr_ym(crtr_ym_List.get(i));
                    startupDTO.setJncmp_nocs(jncmp_nocs_List.get(i));
                    startupDTO.setRsgntn_nocs(rsgntn_nocs_List.get(i));
                    startupDTO.setHdof_nocs(hdof_nocs_List.get(i));
                    selectEmploList.add(EmploDTO.make(startupDTO));
                }
            }
        }

        //매출정보 조회 (리스트)
        List<SlsDTO> selectSlsList = new ArrayList<>();

        startupDTO.setSls_yr(startupDTO.getSls_yr1());
        startupDTO.setSls_amt(startupDTO.getSls_amt1());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt1());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt1());
        startupDTO.setSga_amt(startupDTO.getSga_amt1());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit1());
        startupDTO.setNet_profit(startupDTO.getNet_profit1());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            selectSlsList.add(SlsDTO.make(startupDTO));
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr2());
        startupDTO.setSls_amt(startupDTO.getSls_amt2());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt2());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt2());
        startupDTO.setSga_amt(startupDTO.getSga_amt2());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit2());
        startupDTO.setNet_profit(startupDTO.getNet_profit2());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            selectSlsList.add(SlsDTO.make(startupDTO));
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr3());
        startupDTO.setSls_amt(startupDTO.getSls_amt3());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt3());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt3());
        startupDTO.setSga_amt(startupDTO.getSga_amt3());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit3());
        startupDTO.setNet_profit(startupDTO.getNet_profit3());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            selectSlsList.add(SlsDTO.make(startupDTO));
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr4());
        startupDTO.setSls_amt(startupDTO.getSls_amt4());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt4());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt4());
        startupDTO.setSga_amt(startupDTO.getSga_amt4());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit4());
        startupDTO.setNet_profit(startupDTO.getNet_profit4());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            selectSlsList.add(SlsDTO.make(startupDTO));
        }

        startupDTO.setSls_yr(startupDTO.getSls_yr5());
        startupDTO.setSls_amt(startupDTO.getSls_amt5());
        startupDTO.setSls_cost_amt(startupDTO.getSls_cost_amt5());
        startupDTO.setSls_gramt(startupDTO.getSls_gramt5());
        startupDTO.setSga_amt(startupDTO.getSga_amt5());
        startupDTO.setOperating_profit(startupDTO.getOperating_profit5());
        startupDTO.setNet_profit(startupDTO.getNet_profit5());
        if (StringUtils.hasText(startupDTO.getSls_yr())) {
            selectSlsList.add(SlsDTO.make(startupDTO));
        }

        model.addAttribute("selectSlsList", selectSlsList);
        if(selectSlsList != null) {
            for(int i = 0; i < selectSlsList.size(); i++) {
                model.addAttribute("selectSlsChgList" + (i + 1), selectSlsList.get(i));
            }
        }

        //재무상태 조회 (리스트)
        List<AstDTO> selectAstList = new ArrayList<>();
        startupDTO.setAst_yr(startupDTO.getAst_yr1());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets1());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets1());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt1());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt1());
        startupDTO.setCptl(startupDTO.getCptl1());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt1());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            selectAstList.add(AstDTO.make(startupDTO));
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr2());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets2());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets2());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt2());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt2());
        startupDTO.setCptl(startupDTO.getCptl2());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt2());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            selectAstList.add(AstDTO.make(startupDTO));
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr3());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets3());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets3());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt3());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt3());
        startupDTO.setCptl(startupDTO.getCptl3());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt3());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            selectAstList.add(AstDTO.make(startupDTO));
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr4());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets4());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets4());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt4());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt4());
        startupDTO.setCptl(startupDTO.getCptl4());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt4());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            selectAstList.add(AstDTO.make(startupDTO));
        }

        startupDTO.setAst_yr(startupDTO.getAst_yr5());
        startupDTO.setCurrent_assets(startupDTO.getCurrent_assets5());
        startupDTO.setNon_current_assets(startupDTO.getNon_current_assets5());
        startupDTO.setAst_gramt(startupDTO.getAst_gramt5());
        startupDTO.setDebt_gramt(startupDTO.getDebt_gramt5());
        startupDTO.setCptl(startupDTO.getCptl5());
        startupDTO.setCptl_gramt(startupDTO.getCptl_gramt5());
        if (StringUtils.hasText(startupDTO.getAst_yr())) {
            selectAstList.add(AstDTO.make(startupDTO));
        }

        model.addAttribute("selectAstList", selectAstList);
        if(selectAstList != null) {
            for (int i = 0; i < selectAstList.size(); i++) {
                model.addAttribute("selectAstChgList" + (i + 1), selectAstList.get(i));
            }
        }

        //뉴스 메인 조회 (단건) i == 0 에서 처리
        //뉴스정보 조회 (리스트)
        List<NewsDTO> selectNewsList = new ArrayList<>();
        if (map.get("news_ttl_List") != null && !map.get("news_ttl_List").equals("")) {
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateTimeStr = formatter.format(now);
            Random random = new Random();

            List<String> news_ttl_List = new ArrayList<>();
            List<String> provider_List = new ArrayList<>();
            List<String> news_link_List = new ArrayList<>();
            news_ttl_List = (List) map.get("news_ttl_List");
            provider_List = (List) map.get("provider_List");
            news_link_List = (List) map.get("news_link_List");

            for (int i = 0; i < news_ttl_List.size(); i++) {
                if(i == 0) {
                    try{
                        startupDTO.setThumb_url(startupDTO.getThumb_url());
                    }catch (Exception ignored) {}
                }else{
                    startupDTO.setThumb_url(null);
                }

                startupDTO.setNews_id(dateTimeStr + 100 + random.nextInt(900));
                startupDTO.setNews_ttl(news_ttl_List.get(i));
                startupDTO.setProvider(provider_List.get(i));
                startupDTO.setNews_link(news_link_List.get(i));

                if(StringUtils.hasText(startupDTO.getNews_ttl())
                        || StringUtils.hasText(startupDTO.getProvider())
                        || StringUtils.hasText(startupDTO.getNews_link())) {
                    if(i == 0) {
                        model.addAttribute("selectNewsMain", NewsDTO.make(startupDTO));
                    }else{
                        selectNewsList.add(NewsDTO.make(startupDTO));
                    }
                }
            }
        }

        //키워드정보 조회 (리스트)
        List<KeywdDTO> selectKeywdList = new ArrayList<>();
        startupDTO.setKeywd(startupDTO.getKeywd1());
        startupDTO.setNocs(startupDTO.getNocs1());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd2());
        startupDTO.setNocs(startupDTO.getNocs2());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd3());
        startupDTO.setNocs(startupDTO.getNocs3());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd4());
        startupDTO.setNocs(startupDTO.getNocs4());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd5());
        startupDTO.setNocs(startupDTO.getNocs5());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd6());
        startupDTO.setNocs(startupDTO.getNocs6());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd7());
        startupDTO.setNocs(startupDTO.getNocs7());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd8());
        startupDTO.setNocs(startupDTO.getNocs8());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd9());
        startupDTO.setNocs(startupDTO.getNocs9());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        startupDTO.setKeywd(startupDTO.getKeywd10());
        startupDTO.setNocs(startupDTO.getNocs10());
        selectKeywdList.add(KeywdDTO.make(startupDTO));

        model.addAttribute("startupInfo", startupInfo);

        model.addAttribute("selectBizList", selectBizList);
        model.addAttribute("selectEmploList", selectEmploList);
        model.addAttribute("selectInvestList", selectInvestList);

        model.addAttribute("selectNewsList", selectNewsList);
        model.addAttribute("selectKeywdList", selectKeywdList);
    }
}