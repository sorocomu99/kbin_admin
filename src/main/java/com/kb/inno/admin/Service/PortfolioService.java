/**
 * 파일명     : PortfolioService.java
 * 화면명     : 포트폴리오 관리
 * 설명       : 포트폴리오 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.12.31
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.PortfolioDAO;
import com.kb.inno.admin.DTO.*;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    // DAO 연결
    private final PortfolioDAO portfolioDAO;
    
    /**
     * 포트폴리오 년도 리스트 조회
     * @return
     */
    //public List<HistoryDTO> selectList(int menuId, int page) {
    public void selectListYear(int menuId, Model model) {
        //List<HistoryDTO> selectList = historyDAO.selectList();
        PortfolioDTO portfolioDTO = new PortfolioDTO();

        List<PortfolioDTO> selectListYear = portfolioDAO.selectListYear();
        model.addAttribute("selectListYear", selectListYear);
        model.addAttribute("menuId", menuId);
        //return selectList;
    }

    /**
     * 포트폴리오 년도 등록
     * @param portfolioDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> yearInsert(PortfolioDTO portfolioDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        portfolioDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        portfolioDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        int yearIns = portfolioDAO.yearInsert(portfolioDTO);

        if (yearIns == 1) {
            resultMap.put("errorCd", "00");
            resultMap.put("errorMsg", "포트폴리오 년도 정보가 정상적으로 등록되었습니다.");
        } else {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "포트폴리오 년도 등록 중 오류가 발생되었습니다.");
        }

        return resultMap;
    }

    public String existYearData(String port_yr) {
        //해당년도의 데이터 존재 여부 확인
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setPort_yr(port_yr);
        PortfolioDTO selectYearDetail = portfolioDAO.selectYearDetail(portfolioDTO);
        return selectYearDetail != null ? "Y" : "N";
    }

    /**
     * 포트폴리오 년도 상세
     * @param port_yr
     * @param model
     */
    public void selectYearDetail(String port_yr, Model model) {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setPort_yr(port_yr);
        PortfolioDTO selectYearDetail = portfolioDAO.selectYearDetail(portfolioDTO);
        model.addAttribute("selectYearDetail", selectYearDetail);
    }

    /**
     * 포트폴리오 년도 수정
     * @param portfolioDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> yearUpdate(PortfolioDTO portfolioDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        portfolioDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        portfolioDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        portfolioDAO.yearUpdate(portfolioDTO);

        resultMap.put("errorCd", "00");
        resultMap.put("errorMsg", "정상적으로 수정 되었습니다.");

        return resultMap;
    }

    /**
     * 포트폴리오 년도 삭제
     * @param portfolioDTO
     * @return
     */
    public int portYrDelete(PortfolioDTO portfolioDTO) {
        portfolioDAO.portYrListDelete(portfolioDTO);
        return portfolioDAO.portYrDelete(portfolioDTO);
    }

    /**
     * 포트폴리오 년도별 리스트
     * @param menuId
     * @param model
     * @param port_yr
     * @param type
     * @param keyword
     * @param page
     */
    //public void selectPortList(int menuId, Model model, PortfolioDTO portfolioDTO) {
    public void selectPortList(int menuId, Model model, String port_yr, String type, String keyword, int page) {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setPort_yr(port_yr);
        portfolioDTO.setType(type);
        portfolioDTO.setKeyword(keyword);

        // 페이지의 전체 글 갯수
        int allCount = portfolioDAO.selectPortListCount(portfolioDTO);

        //한 페이지당 글 갯수
        int pageLetter = 10;

        //전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        //나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            //더하기
            repeat += 1;
        }

        //repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        //만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        //만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        portfolioDTO.setEnd(end);
        int start = end + 1 - pageLetter;
        portfolioDTO.setStart(start);

        List<PortfolioDTO> selectPortList = portfolioDAO.selectPortList(portfolioDTO);
        model.addAttribute("selectPortList", selectPortList);
        model.addAttribute("menuId", menuId);
        model.addAttribute("port_yr", portfolioDTO.getPort_yr());
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("type", portfolioDTO.getType());
        model.addAttribute("keyword", portfolioDTO.getKeyword());
        //return selectList;
    }

    /**
     * 포트폴리오 등록
     * @param portfolioDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> portListInsert(PortfolioDTO portfolioDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        portfolioDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        portfolioDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        int fileYn = portfolioDTO.getFile_yn();

        if (fileYn == 1) {
            // 파일 저장
            MultipartFile file = portfolioDTO.getMain_file();

            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = portfolioDAO.insertFile(fileSave);

            // 게시글 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                portfolioDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }
        int portSn = portfolioDAO.selectMaxSn(portfolioDTO);
        portfolioDTO.setPort_sn(portSn);
        int yearIns = portfolioDAO.portListInsert(portfolioDTO);

        if (yearIns == 1) {
            resultMap.put("errorCd", "00");
            resultMap.put("errorMsg", "정상적으로 등록되었습니다.");
        } else {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "등록 중 오류가 발생되었습니다.");
        }

        return resultMap;
    }

    /**
     * 포트폴리오 삭제
     * @param portfolioDTO
     * @return
     */
    public int portListDel(PortfolioDTO portfolioDTO) {
        return portfolioDAO.portListDel(portfolioDTO);
    }

    /**
     * 포트폴리오 상세
     * @param port_sn
     * @param port_yr
     * @param model
     */
    public void portListDet(int port_sn, String port_yr, Model model) {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setPort_yr(port_yr);
        portfolioDTO.setPort_sn(port_sn);

        PortfolioDTO portListDet = portfolioDAO.portListDet(portfolioDTO);

        model.addAttribute("portListDet", portListDet);
        model.addAttribute("port_yr", port_yr);
        model.addAttribute("port_sn", port_sn);
    }

    /**
     * 포트폴리오 수정
     * @param portfolioDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> portListUpdate(PortfolioDTO portfolioDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        portfolioDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        portfolioDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        int fileYn = portfolioDTO.getFile_yn();

        if (fileYn == 1) {
            // 파일 저장
            MultipartFile file = portfolioDTO.getMain_file();

            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = portfolioDAO.insertFile(fileSave);

            // 게시글 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                portfolioDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        int yearUpd = portfolioDAO.portListUpdate(portfolioDTO);

        if (yearUpd == 1) {
            resultMap.put("errorCd", "00");
            resultMap.put("errorMsg", "정상적으로 수정되었습니다.");
        } else {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "수정 중 오류가 발생되었습니다.");
        }

        return resultMap;
    }

    public Map<String, Object> getPortYearList(Model model) {
        Map<String, Object> result = new HashMap<>();
        List<PortfolioDTO> portYearList = portfolioDAO.selectPortYearList(model);
        result.put("portYearList", portYearList);
        return result;
    }

    public Map<String, Object> selectList(Model model, String keyword) {
        Map<String, Object> result = new HashMap<>();
        SearchDTO search = new SearchDTO();
        search.setKeyword(keyword); //년도 (port_yr)
        List<PortfolioDTO> selectList = portfolioDAO.selectList(search);
        result.put("selectList", selectList);
        return result;
    }

    public void previewImgInsert(PortfolioDTO portfolioDTO, int loginId, Model model) {
        MultipartFile file = portfolioDTO.getMain_file();
        FileUploader fileUploader = new FileUploader();
        FileDTO fileSave = fileUploader.insertFile(file, loginId);

        int result = portfolioDAO.insertFile(fileSave);

        if(fileSave != null && result == 1){
            FileDTO fileDTO = portfolioDAO.selectPreviewFile(fileSave.getFile_sn());
            model.addAttribute("preview_file_path",fileDTO.getFile_path());
            model.addAttribute("preview_file_name",fileDTO.getFile_nm());
        }
    }
}
