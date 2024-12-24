/**
 * 파일명     : StartersDAO.java
 * 화면명     : KB 스타터스 관리
 * 설명       : KB 스타터스 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.StartersDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StartersDAO {
    // KB 스타터스 조회
    StartersDTO select();
    // KB 스타터스 저장
    int insert(StartersDTO startersDTO);
    // KB 스타터스 수정
    int update(StartersDTO startersDTO);
}
