package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class FaqDTO {
    private int faq_sn;          // FAQ 일련 번호
    private String faq_ctgry;    // FAQ 카테고리
    private String faq_ctgry_sn; // FAQ 카테고리 순번
    private String faq_ctgry_nm; // FAQ 카테고리명
    private String faq_qstn;     // FAQ 질문
    private String faq_ans;      // FAQ 답변
    private String expsr_yn;     // 노출 여부
    private int sort_no;         // 정렬 번호
    private int frst_rgtr;       // 최초 등록자
    private Date frst_reg_dt;    // 최초 등록 일시
    private int last_mdfr;       // 최종 수정자
    private Date last_mdfcn_dt;  // 최종 수정 일시
    private int menu_id;         // 메뉴 ID
    private int rownumber;          // 순번


    private String faq_id;
    private String faq_sec;
    private String ctgry_nm; // FAQ 카테고리명
}
