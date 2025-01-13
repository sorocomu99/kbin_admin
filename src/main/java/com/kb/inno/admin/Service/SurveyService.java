/**
 * 파일명     : SurveyService.java
 * 화면명     : 설문 관리
 * 설명       : 설문 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2025.01.06
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.SurveyDAO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.SurveyDTO;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    // DAO 연결
    private final SurveyDAO surveyDAO;

    /**
     * 설문 조회
     * @param menuId
     * @param page
     * @param model
     */
    public void selectList(int menuId, int page, Model model) {
        SurveyDTO surveyDTO = new SurveyDTO();

        int allCount = surveyDAO.selectPageCount();

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
        surveyDTO.setEnd(end);
        int start = end + 1 - pageLetter;
        surveyDTO.setStart(start);

        List<SurveyDTO> selectList = surveyDAO.selectList(surveyDTO);
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
    }

    /**
     * 설문정보 등록
     * @param surveyDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> exmnInsert(SurveyDTO surveyDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        surveyDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        surveyDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        //파일을 첨부했는지 확인
        int file_yn = surveyDTO.getFile_yn();
        if (file_yn == 1) {
            //파일 저장
            MultipartFile file = surveyDTO.getSurvey_file();

            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            int fileIns = surveyDAO.insertFile(fileSave);
            if(fileSave != null && fileIns == 1) {
                surveyDTO.setFile_sn(fileSave.getFile_sn());
                surveyDTO.setBane_file_sn(fileSave.getFile_sn());
            }
        }

        int srvySn = surveyDAO.selectMaxSn();
        surveyDTO.setSrvy_sn(srvySn);
        int exminIns = surveyDAO.exmnInsert(surveyDTO);

        if (exminIns == 1) {
            resultMap.put("errorCd", "00");
            resultMap.put("errorMsg", "설문정보가 정상적으로 등록되었습니다.");
        } else {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "설문정보가 등록 중 오류가 발생되었습니다.");
        }

        return resultMap;
    }

    public void selectGuide(SurveyDTO surveyDTO, Model model) {
        surveyDTO.setSprt_expln_sn(surveyDTO.getSrvy_sn());
        SurveyDTO selectGuide = surveyDAO.selectGuide(surveyDTO);
        model.addAttribute("selectGuide", selectGuide);
        model.addAttribute("menuId", surveyDTO.getMenu_id());
        model.addAttribute("srvy_sn", surveyDTO.getSrvy_sn());
    }

    /**
     * 지원안내 정보 등록 및 수정
     * @param surveyDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> guideInsert(SurveyDTO surveyDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        surveyDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        surveyDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        int guideIns = surveyDAO.guideInsert(surveyDTO);

        if (guideIns == 1) {
            resultMap.put("errorCd", "00");
            resultMap.put("errorMsg", "지원안내정보가 정상적으로 등록되었습니다.");
        } else {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "지원안내정보 등록 중 오류가 발생되었습니다.");
        }

        return resultMap;
    }

    /**
     * 설문정보 삭제 (하위 모든 정보 삭제)
     * @param surveyDTO
     */
    public int exmnDelete(SurveyDTO surveyDTO) {
        //KB_SRVY_EXMN_INFO(설문조사정보), KB_SRBVY_QSTN_INFO(설문질문정보), KB_SRVY_ANS_INFO(설문답변정보), KB_SRVY_RSPNS_INFO(설문응답정보) 모두 삭제
        return surveyDAO.exmnDelete(surveyDTO);
    }
}
