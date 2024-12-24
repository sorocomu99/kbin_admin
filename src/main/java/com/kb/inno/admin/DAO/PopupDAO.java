/**
 * 파일명     : PopupDAO.java
 * 화면명     : 팝업 관리
 * 설명       : 팝업 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface PopupDAO {
    // 팝업 리스트 갯수 조회
    int selectPageCount();
    // 팝업 리스트 조회
    //List<PopupDTO> selectList(int start, int end);
    List<PopupDTO> selectList(HashMap map);
    // 팝업 등록
    int insert(PopupDTO popupDTO);
    // 팝업 상세 조회
    PopupDTO select(int popupId);
    // 팝업 수정
    int update(PopupDTO popupDTO);
    // 팝업 삭제
    void delete(int popupId);
    // 팝업 전체 조회
    //List<PopupDTO> selectListAll(int popup_sn);
    List<PopupDTO> selectListAll(PopupDTO popupDTO);
}
