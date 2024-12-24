/**
 * 파일명     : InvestmentDAO.java
 * 화면명     : 국내 프로그램 - 투자 그래프 관리
 * 설명       : 투자 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.InvestmentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InvestmentDAO {
    // 국내 프로그램 - 투자 그래프 조회
    InvestmentDTO select();
    // 국내 프로그램 - 투자 그래프 등록
    int insert(InvestmentDTO investmentDTO);
    // 국내 프로그램 - 투자 그래프 수정
    int update(InvestmentDTO investmentDTO);
}
