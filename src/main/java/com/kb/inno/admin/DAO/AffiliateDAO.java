/**
 * 파일명     : AffiliateDAO.java
 * 화면명     : 제휴 사례 관리
 * 설명       : 제휴 사례 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.31
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AffiliateDAO {
    // 국내 프로그램 - 제휴 사례 리스트 조회
    List<AffiliateDTO> selectList();
    // 국내 프로그램 - 제휴 사례 파일 저장
    int insertFile(FileDTO fileDTO);
    // 국내 프로그램 - 제휴 사례 등록
    int insert(AffiliateDTO affiliateDTO);
    // 국내 프로그램 - 제휴 사례 상세 조회
    AffiliateDTO select(int affiliate_sn);
    // 국내 프로그램 - 제휴 사례 파일 삭제
    void deleteFile(int file_sn);
    // 국내 프로그램 - 제휴 사례 수정
    int update(AffiliateDTO affiliateDTO);
    int sortUpdate(AffiliateDTO affiliateDTO);
    // 국내 프로그램 - 제휴 사례 삭제
    void delete(int affiliate_sn);
    // 국내 프로그램 - 제휴 사례 리스트 조회 (미리보기용)
    //List<VisualDTO> selectListAll(int affiliate_sn);
    List<VisualDTO> selectListAll(AffiliateDTO affiliateDTO);
}
