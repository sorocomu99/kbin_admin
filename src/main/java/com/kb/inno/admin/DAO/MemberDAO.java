/**
 * 파일명     : MemberDAO.java
 * 화면명     : 관리자 계정 관리
 * 설명       : 관리자 계정 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.24
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberDAO {
    // 관리자 계정 리스트 조회
    List<MemberDTO> selectList();
    // 관리자 계정 상세 조회
    MemberDTO select(int memberId);
    // 관리자 계정 추가
    int insert(MemberDTO memberDTO);
    // 관리자 계정 수정
    int update(MemberDTO memberDTO);
    // 관리자 계정 아이디 유무 확인(아이디 중복 체크)
    int selectId(String memberId);
    // 관리자 계정 삭제
    int delete(int memberId);
    int updateMemberDeleteStatus(MemberDTO memberDTO);
}
