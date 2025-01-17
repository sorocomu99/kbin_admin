/**
 * 파일명     : SurveyDAO.java
 * 화면명     : 설문 관리
 * 설명       : 설문 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2025.01.06
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.HistoryDTO;
import com.kb.inno.admin.DTO.SurveyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SurveyDAO {
	//항목 일련번호 구해오기
    int selectQstnMaxSn();
    //
    int qstnInsert(SurveyDTO surveyDTO);
    int ansInsert(SurveyDTO surveyDTO);
    
    //설문조사 갯수 조회
    int selectPageCount();
    //설문조사 리스트 조회
    List<SurveyDTO> selectList(SurveyDTO surveyDTO);
    //첨부파일 정보 등록
    int insertFile(FileDTO fileDTO);
    //설문조사 일련번호 구해오기
    int selectMaxSn();
    //설문조사 등록
    int exmnInsert(SurveyDTO surveyDTO);
    //지원안내정보
    int guideInsert(SurveyDTO surveyDTO);
    //지원안내정보
    SurveyDTO selectGuide(SurveyDTO surveyDTO);
    //상세내용 조회
    List<SurveyDTO> selectDetail(SurveyDTO surveyDTO);
    //설문조사 삭제
    int exmnDelete(SurveyDTO surveyDTO);
    

    
}
