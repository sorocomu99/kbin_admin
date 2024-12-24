/**
 * 파일명     : PromoteDAO.java
 * 화면명     : 국내 프로그램 - 육성 그래프 관리
 * 설명       : 국내 프로그램 - 육성 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.PromoteDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PromoteDAO {
    // 국내 프로그램 - 육성 그래프 조회
    PromoteDTO select();
    // 국내 프로그램 - 육성 그래프 등록
    int insert(PromoteDTO promoteDTO);
    // 국내 프로그램 - 육성 그래프 수정
    int update(PromoteDTO promoteDTO);
}
