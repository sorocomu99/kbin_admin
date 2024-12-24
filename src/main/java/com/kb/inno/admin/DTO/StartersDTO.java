/**
 * 파일명     : StartersDTO.java
 * 화면명     : KB 스타터스 관리
 * 설명       : KB 스타터스 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 양윤지
 * ==========================================================
 * 수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class StartersDTO {
    private int star_sn;         // 스타터스 일련번호
    private String slctn_prcs;   // 선발 프로세스
    private String starter_stts; // 스타터스 현황
    private String sprt;         // 지원하기
    private int frst_rgtr;       // 최초 등록자
    private Date frst_reg_dt;    // 최초 등록 일시
    private int last_mdfr;       // 최종 수정자
    private Date last_mdfcn_dt;  // 최종 수정 일시
}
