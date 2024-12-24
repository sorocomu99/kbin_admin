/**
 * 파일명     : MemberDTO.java
 * 화면명     : 관리자 계정 관리
 * 설명       : 관리자 계정 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.24
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class MemberDTO {
    private int mngr_sn;        // 관리자 일련번호
    private String mngr_id;     // 관리자 아이디
    private String mngr_pswd;   // 관리자 비밀번호
    private String mngr_nm;     // 관리자 명
    private String mngr_eml;    // 관리자 이메일
    private Date last_cntn_dt;  // 최종 접속 일시
    private int frst_rgtr;      // 최초 등록자
    private Date frst_reg_dt;   // 최초 등록 일시
    private int last_mdfr;      // 최종 수정자
    private Date last_mdfcn_dt; // 최종 수정 일시
    private int menu_id;        // 메뉴 ID
}
