/**
 * 파일명     : RecruitService.java
 * 화면명     : 국내 프로그램 - 채용 지원 관리
 * 설명       : 국내 프로그램 - 채용 지원 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.RecruitDAO;
import com.kb.inno.admin.DTO.RecruitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class RecruitService {

    // DAO 연결
    private final RecruitDAO recruitDAO;

    // 국내 프로그램 - 채용 지원 조회
    public RecruitDTO select() {
        RecruitDTO recruitDTO = recruitDAO.select();
        BigDecimal amount = BigDecimal.valueOf(recruitDTO.getSprt_amt());
        DecimalFormat df = new DecimalFormat("####.###");
        recruitDTO.setRet_sprt_amt(df.format(amount));
        return recruitDTO;
    }

    // 국내 프로그램 - 채용 지원 등록
    public int insert(RecruitDTO recruitDTO, int loginId) {
        // 로그인 한 아이디 대입
        recruitDTO.setFrst_rgtr(loginId);
        recruitDTO.setLast_mdfr(loginId);

        return recruitDAO.insert(recruitDTO);
    }
    
    // 국내 프로그램 - 채용 지원 수정
    public int update(RecruitDTO recruitDTO, int loginId) {
        // 로그인 한 아이디 대입
        recruitDTO.setLast_mdfr(loginId);

        return recruitDAO.update(recruitDTO);
    }
}
