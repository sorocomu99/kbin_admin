package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class StartupDTO {
    private int star_sn;         // 스타터스 일련번호
    private String slctn_prcs;   // 선발 프로세스
    private String starter_stts; // 스타터스 현황
    private String sprt;         // 지원하기
    private int frst_rgtr;       // 최초 등록자
    private Date frst_reg_dt;    // 최초 등록 일시
    private int last_mdfr;       // 최종 수정자
    private Date last_mdfcn_dt;  // 최종 수정 일시
    private int menu_id;         // 메뉴 ID
    private int rownumber;       // 순번
}
