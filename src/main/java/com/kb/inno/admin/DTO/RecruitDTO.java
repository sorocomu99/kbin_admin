/**
 * 파일명     : RecruitDTO.java
 * 화면명     : 국내 프로그램 - 채용 지원 관리
 * 설명       : 국내 프로그램 - 채용 지원 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class RecruitDTO {
    private int employ_sn;       // 채용 일련번호
    private String employ_yr;    // 연도
    private int employ_emp_nocs; // 채용 직원 수
    private double sprt_amt;        // 지원 금액
    private int employ_ent_nocs; // 채용 기업 수
    private int frst_rgtr;       // 최초 등록자
    private Date frst_reg_dt;    // 최초 등록 일시
    private int last_mdfr;       // 최종 수정자
    private Date last_mdfcn_dt;  // 최종 수정 일시
    private int menu_id;         // 메뉴 ID
}
