/**
 * 파일명     : ResultDAO.java
 * 화면명     : 주요 성과보고 관리
 * 설명       : 주요 성과보고 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.29
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.ResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ResultDAO {
    // 육성 현황 조회
    ResultDTO select();
    // 육성 현황 추가
    int insert(ResultDTO result);
    // 육성 현황 수정
    int update(ResultDTO result);
}
