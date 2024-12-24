/**
 * 파일명     : MenuDTO.java
 * 화면명     : 메뉴 관리
 * 설명       : 메뉴 조회 및 등록, 수정, 삭제 처리
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
public class MenuDTO {
    private int menu_sn;        // 메뉴 일련번호
    private String menu_nm;     // 메뉴명
    private int menu_depth;     // 메뉴뎁스
    private int menu_up_sn;     // 메뉴 상위 일련번호
    private int menu_sort;      // 메뉴 정렬
    private String use_yn;      // 사용 여부
    private String menu_link;   // 메뉴 링크
    private int frst_rgtr;      // 최초 등록자
    private Date frst_reg_dt;   // 최초 등록 일시
    private int last_mdfr;      // 최종 수정자
    private Date last_mdfcn_dt; // 최종 수정 일시
    private int menu_id;        // 메뉴 ID
}
