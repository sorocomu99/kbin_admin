/**
 * 파일명     : HubDAO.java
 * 화면명     : HUB 센터 소식 관리
 * 설명       : HUB 센터 소식 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.14
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.HubDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HubDAO {
    // HUB 센터 소식 갯수 조회
    int selectPageCount(SearchDTO search);
    // HUB 센터 소식 리스트 조회
    List<HubDTO> selectList(SearchDTO search);
    // HUB 센터 소식 파일 저장
    int insertFile(FileDTO fileDTO);
    // HUB 센터 소식 등록
    int insert(HubDTO hubDTO);
    // HUB 센터 소식 상세
    HubDTO select(int hubSn);
    // HUB 센터 소식 파일 삭제
    void deleteFile(int fileSn);
    // HUB 센터 소식 수정
    int update(HubDTO hubDTO);
    // HUB 센터 소식 삭제
    void delete(int hub_sn);
}
