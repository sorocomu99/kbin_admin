/**
 * 파일명     : HistoryDAO.java
 * 화면명     : 연혁 관리
 * 설명       : 연혁 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.12.31
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.PortfolioDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
@Repository
public interface PortfolioDAO {
    //포트폴리오 년도별 갯수 리스트
    List<PortfolioDTO> selectListYear();
    //포트폴리오 년도 등록
    int yearInsert(PortfolioDTO portfolioDTO);
    PortfolioDTO selectYearDetail(PortfolioDTO portfolioDTO);
    int yearUpdate(PortfolioDTO portfolioDTO);
    int selectPortListCount(PortfolioDTO portfolioDTO);
    List<PortfolioDTO> selectPortList(PortfolioDTO portfolioDTO);
    int portListInsert(PortfolioDTO portfolioDTO);
    int insertFile(FileDTO fileDTO);
    int selectMaxSn(PortfolioDTO portfolioDTO);
    int portListDel(PortfolioDTO portfolioDTO);
    int portYrDelete(PortfolioDTO portfolioDTO);
    int portYrListDelete(PortfolioDTO portfolioDTO);
    PortfolioDTO portListDet(PortfolioDTO portfolioDTO);
    int portListUpdate(PortfolioDTO portfolioDTO);
    List<PortfolioDTO> selectPortYearList(Model model);
    List<PortfolioDTO> selectList(SearchDTO search);
    int insert(PortfolioDTO portfolioDTO);
    FileDTO selectPreviewFile(int file_sn);
}
