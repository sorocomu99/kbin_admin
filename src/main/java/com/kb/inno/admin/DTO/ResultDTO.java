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
package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ResultDTO {
    private int rslt_sn;                // 성과 일련 번호
    private String ent_nocs;            // 기업 건수(육성기업)
    private String ent_crtr_ymd;        // 기준년월일(육성기업)
    private String invest_nocs;         // 투자 건수
    private String invest_crtr_ymd;     // 투자 기준년월일
    private String affiliate_nocs;      // 제휴 건수
    private String affiliate_crtr_ymd;  // 제휴 기준년월일
    private int frst_rgtr;              // 최초 등록자
    private Date frst_reg_dt;           // 최초 등록 일시
    private int last_mdfr;              // 최종 수정자
    private Date last_mdfcn_dt;         // 최종 수정 일시
    private int menu_id;                // 메뉴 ID
}
