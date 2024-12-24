/**
 * 파일명     : FaqDAO.java
 * 화면명     : FAQ 관리
 * 설명       : FAQ 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.12
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface FaqDAO {
    // FAQ 리스트 갯수 조회
    int selectPageCount(SearchDTO search);
    // FAQ 리스트 조회
    List<FaqDTO> selectList(SearchDTO search);
    // FAQ 등록
    int insert(FaqDTO faqDTO);
    // FAQ 상세 조회
    FaqDTO select(int faq_sn);
    // FAQ 수정
    int update(FaqDTO faqDTO);
    // FAQ 삭제
    void delete(int faq_sn);
    // FAQ 카테고리 조회
    Map<String, Object> selectCategory(int ctgry);
    
}
