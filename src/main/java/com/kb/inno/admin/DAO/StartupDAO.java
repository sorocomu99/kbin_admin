package com.kb.inno.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kb.inno.admin.DTO.StartupDTO;

@Mapper
@Repository
public interface StartupDAO {
    //KB 스타터스 리스트 갯수 조회
    int selectPageCount(StartupDTO startupDTO);
    //KB 스타터스 리스트 조회
    List<StartupDTO> selectList(StartupDTO startupDTO);
    //KB 스타터스 기본정보 등록(KB_API_STARTER_INFO)
    int insertStarterInfo(StartupDTO startupDTO);
    //KB 스타터스 사업 서비스 정보 등록(KB_API_BIZ_SRVC_INFO)
    int insertBizSrvcInfo(StartupDTO startupDTO);
    //KB 스타터스 투자정보 등록(KB_API_INVEST_INFO)
    int insertInvestInfo(StartupDTO startupDTO);
    //KB 스타터스 고용정보 등록(KB_API_EMPLO_INFO)
    int insertEmploInfo(StartupDTO startupDTO);
    //KB 스타터스 매출정보 등록(KB_API_SLS_INFO)
    int insertSlsInfo(StartupDTO startupDTO);
    //KB 스타터스 자산 정보 등록(KB_API_AST_INFO)
    int insertAstInfo(StartupDTO startupDTO);
    //KB 스타터스 뉴스 정보 등록(KB_API_NEWS_INFO)
    int insertNewsInfo(StartupDTO startupDTO);
    //KB 스타터스 키워드 정보 등록(KB_API_KEYWD_INFO)
    int insertKeywdInfo(StartupDTO startupDTO);
    StartupDTO selectStarterInfo(StartupDTO startupDTO);
    List<StartupDTO> selectBizSrvcInfo(StartupDTO startupDTO);
    List<StartupDTO> selectInvestInfo(StartupDTO startupDTO);
    List<StartupDTO> selectEmploInfo(StartupDTO startupDTO);
    StartupDTO selectSlsInfo(StartupDTO startupDTO);
    StartupDTO selectAstInfo(StartupDTO startupDTO);
    List<StartupDTO> selectNewsInfo(StartupDTO startupDTO);
    List<StartupDTO> selectKeywdInfo(StartupDTO startupDTO);
    //KB 스타터스 삭제
    int deleteStarterInfo(StartupDTO startupDTO);
    int deleteBizSrvcInfo(StartupDTO startupDTO);
    int deleteInvestInfo(StartupDTO startupDTO);
    int deleteEmploInfo(StartupDTO startupDTO);
    int deleteSlsInfo(StartupDTO startupDTO);
    int deleteAstInfo(StartupDTO startupDTO);
    int deleteNewsInfo(StartupDTO startupDTO);
    int deleteKeywdInfo(StartupDTO startupDTO);
}