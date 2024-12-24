/**
 * 파일명     : ResultService.java
 * 화면명     : 육성 현황 관리
 * 설명       : 육성 현황 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.29
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.ResultDAO;
import com.kb.inno.admin.DTO.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class ResultService {
    // DAO 연결
    private final ResultDAO resultDAO;
    
    // 육성 현황 조회
    public ResultDTO select() {
        return resultDAO.select();
    }

    // 콤마 제거
    public void formatComma(ResultDTO resultDTO) {
        String ent_nocs = resultDTO.getEnt_nocs().replaceAll(",", "");
        resultDTO.setEnt_nocs(ent_nocs);

        String invest_nocs = resultDTO.getInvest_nocs().replaceAll(",", "");
        resultDTO.setInvest_nocs(invest_nocs);

        String affiliate_nocs = resultDTO.getAffiliate_nocs().replaceAll(",", "");
        resultDTO.setAffiliate_nocs(affiliate_nocs);
    }
    
    // 육성 현황 등록
    public int insert(ResultDTO resultDTO, int loginId) {
        formatComma(resultDTO);
        // 로그인한 사람 최초등록자, 수정자 대입
        resultDTO.setFrst_rgtr(loginId);
        resultDTO.setLast_mdfr(loginId);
        return resultDAO.insert(resultDTO);
    }
    
    // 육성 현황 수정
    public int update(ResultDTO resultDTO, int loginId) {
        formatComma(resultDTO);
        // 로그인한 사람 수정자 대입
        resultDTO.setLast_mdfr(loginId);
        return resultDAO.update(resultDTO);
    }

    // 미리보기 화면으로 이동
    public void preview(ResultDTO resultDTO, Model model) {
        formatComma(resultDTO);
        resultDTO.setRslt_sn(1);
        model.addAttribute("result", resultDTO);
    }
}
