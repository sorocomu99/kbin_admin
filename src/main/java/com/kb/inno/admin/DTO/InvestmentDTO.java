/**
 * 파일명     : InvestmentDTO.java
 * 화면명     : 국내 프로그램 - 투자 그래프 관리
 * 설명       : 투자 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class InvestmentDTO {
    private int graph_sn;       // 그래프 일련번호
    private String crtr_ym;     // 기준년월
    private String graph1_yr;   // 그래프 1 연도
    private String graph1_nocs; // 그래프 1 기업 수
    private String graph2_yr;   // 그래프 2 연도
    private String graph2_nocs; // 그래프 2 기업 수
    private String graph3_yr;   // 그래프 3 연도
    private String graph3_nocs; // 그래프 3 기업 수
    private String graph4_yr;   // 그래프 4 연도
    private String graph4_nocs; // 그래프 4 기업 수
    private String graph5_yr;   // 그래프 5 연도
    private String graph5_nocs; // 그래프 5 기업 수
    private int frst_rgtr;      // 최초 등록자
    private Date frst_reg_dt;   // 최초 등록 일시
    private int last_mdfr;      // 최종 수정자
    private Date last_mdfcn_dt; // 최종 수정 일시
    private int menu_id;        // 메뉴 ID
}
