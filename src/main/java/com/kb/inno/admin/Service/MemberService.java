/**
 * 파일명     : MemberService.java
 * 화면명     : 관리자 계정 관리
 * 설명       : 관리자 계정 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.24
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.MemberDAO;
import com.kb.inno.admin.DTO.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    // DAO 연결
    private final MemberDAO memberDAO;
    
    // 관리자 계정 리스트 조회
    public List<MemberDTO> selectList() {
        List<MemberDTO> selectList = memberDAO.selectList();
        return selectList;
    }
    
    // 관리자 계정 상세 조회
    public MemberDTO detail(int memberId) {
        MemberDTO memberDTO = memberDAO.select(memberId);
        if(memberDTO == null) {
            return null;
        }
        return memberDTO;
    }

    // 관리자 계정 유무
    public Boolean idCheck(String memberId) {
        // 해당 아이디 있는지 확인
        int member = memberDAO.selectId(memberId);
        Boolean result = false;
        if(member == 0) {
            result = true;
        }
        return result;
    }

    // 관리자 계정 추가
    public int insert(MemberDTO memberDTO, int loginId) {
        // 결과 초기 값
        int result = 2;

        // 로그인 한 아이디 세팅
        memberDTO.setFrst_rgtr(loginId);
        memberDTO.setLast_mdfr(loginId);
        
        // 최종 로그인 이력 값 없어서 임시 값 대입 : 수정 요망
        memberDTO.setLast_cntn_dt(new Date());
        
        // 해당 아이디가 존재하는 지 유무
        if(idCheck(memberDTO.getMngr_id())) {
            result = memberDAO.insert(memberDTO);
        }
        return result;
    }

    // 관리자 계정 수정
    public int update(MemberDTO memberDTO, int loginId) {
        // login Id 세팅
        memberDTO.setLast_mdfr(loginId);
        memberDTO.setLast_cntn_dt(new Date());
        return memberDAO.update(memberDTO);
    }
    
    // 관리자 계정 삭제
    public int delete(int memberId) {
        return memberDAO.delete(memberId);
    }
}
