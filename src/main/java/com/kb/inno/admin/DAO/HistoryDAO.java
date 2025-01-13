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

import com.kb.inno.admin.DTO.HistoryDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HistoryDAO {
    //HUB 센터 소식 갯수 조회
    int selectPageCount();
    //연혁 리스트 조회
    List<HistoryDTO> selectList();
    //단일건 조회(해당년도 등록 여부 조회)
    List<HistoryDTO> selectListOne(HistoryDTO historyDTO);
    //상세내용 조회
    List<HistoryDTO> selectDetail(HistoryDTO historyDTO);
    //해당년도 등록 여부
    int selectCount(HistoryDTO historyDTO);
    //연혁 등록
    int insert(HistoryDTO historyDTO);
    //삭제 처리
    int delete(HistoryDTO historyDTO);
    int selectMaxLcl(HistoryDTO historyDTO);
}
