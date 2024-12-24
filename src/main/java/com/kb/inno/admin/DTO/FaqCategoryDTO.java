package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class FaqCategoryDTO {
    private int ctgry_sn;       // 카테고리 번호
    private String ctgry_nm;    // 카테고리 이름
    private int frst_rgtr;      // 최초 등록자
    private Date frst_reg_dt;   // 최초 등록 일시
    private int last_mdfr;      // 최종 수정자
    private Date last_mdfcn_dt; // 최종 수정 일시
    private int faq_cnt;        // FAQ 게시글 갯수
    private int menu_id;        // 메뉴 ID
    private int category_sn;
}
