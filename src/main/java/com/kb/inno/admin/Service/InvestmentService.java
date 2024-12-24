/**
 * 파일명     : InvestmentService.java
 * 화면명     : 국내 프로그램 - 투자 그래프 관리
 * 설명       : 투자 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.InvestmentDAO;
import com.kb.inno.admin.DTO.InvestmentDTO;
import com.kb.inno.admin.DTO.PromoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    // DAO 연결
    private final InvestmentDAO investmentDAO;

    // 국내 프로그램 - 투자 그래프 조회
    public InvestmentDTO select() {
        return investmentDAO.select();
    }

    // 콤마 제거
    public void formatComma(InvestmentDTO investmentDTO) {
        String graph1_nocs = investmentDTO.getGraph1_nocs().replaceAll(",", "");
        investmentDTO.setGraph1_nocs(graph1_nocs);

        String graph2_nocs = investmentDTO.getGraph2_nocs().replaceAll(",", "");
        investmentDTO.setGraph2_nocs(graph2_nocs);

        String graph3_nocs = investmentDTO.getGraph3_nocs().replaceAll(",", "");
        investmentDTO.setGraph3_nocs(graph3_nocs);

        String graph4_nocs = investmentDTO.getGraph4_nocs().replaceAll(",", "");
        investmentDTO.setGraph4_nocs(graph4_nocs);

        String graph5_nocs = investmentDTO.getGraph5_nocs().replaceAll(",", "");
        investmentDTO.setGraph5_nocs(graph5_nocs);
    }
    
    // 국내 프로그램 - 투자 그래프 추가
    public int insert(InvestmentDTO investmentDTO, int loginId) {
        formatComma(investmentDTO);
        investmentDTO.setFrst_rgtr(loginId);
        investmentDTO.setLast_mdfr(loginId);
        return investmentDAO.insert(investmentDTO);
    }

    // 국내 프로그램 - 투자 그래프 수정
    public int update(InvestmentDTO investmentDTO, int loginId) {
        formatComma(investmentDTO);
        investmentDTO.setLast_mdfr(loginId);
        return investmentDAO.update(investmentDTO);
    }

    // 미리보기 페이지로 이동
    public void preview(InvestmentDTO investmentDTO, Model model) {
        formatComma(investmentDTO);
        model.addAttribute("investment", investmentDTO);
    }
}
