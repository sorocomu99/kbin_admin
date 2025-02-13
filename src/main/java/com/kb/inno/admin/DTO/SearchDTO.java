package com.kb.inno.admin.DTO;

import lombok.Data;

@Data
public class SearchDTO {
    private int start;      // 시작 페이지
    private int end;        // 끝 페이지
    private String type;    // 검색 타입
    private String keyword; // 검색 조건
    private int ctgry;      // FAQ 검색을 위한 카테고리 번호
    private int faq_sn;     // FAQ 검색을 위한 시리얼 번호

    private String delete_yn; // 삭제 여부

    private String srhSelect;
}
