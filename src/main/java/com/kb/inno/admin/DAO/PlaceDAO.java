/**
 * 파일명     : PlaceDAO.java
 * 화면명     : 육성공간 관리
 * 설명       : 육성공간 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.06
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.PlaceDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlaceDAO {
    // 육성공간 리스트 조회
    List<PlaceDTO> selectList();
    // 육성공간 파일 추가
    int insertFile(FileDTO fileDTO);
    // 육성공간 등록
    int insert(PlaceDTO placeDTO);
    // 육성공간 상세 조회
    PlaceDTO select(int plc_sn);
    // 육성공간 수정
    int update(PlaceDTO placeDTO);
    // 육성공간 파일 삭제
    void deleteFile(int file_sn);
    // 육성공간 삭제
    void delete(Integer integer);
    // 육성공간 리스트 조회 (미리보기용)
    //List<VisualDTO> selectListAll(int mainSn);
    List<VisualDTO> selectListAll(PlaceDTO placeDTO);
}
