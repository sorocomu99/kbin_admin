/**
 * 파일명     : PopupDTO.java
 * 화면명     : 팝업 관리
 * 설명       : 팝업 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class PopupDTO {
    private int popup_sn;           // 팝업 일련번호
    private String mngr_ttl;        // 팝업 제목
    private String bgng_ymd;        // 시작일자
    private String bgng_hr;         // 시작 시간
    private String end_ymd;         // 종료일자
    private String end_hr;          // 종료시간
    private int popup_left_pstn;    // 팝업 좌측 위치
    private int popup_top_pstn;     // 팝업 상단 위치
    private String popup_cn;        // 팝업 내용
    private int frst_rgtr;          // 최초 등록자
    private Date frst_reg_dt;       // 최초 등록 일시
    private int last_mdfr;          // 최종 수정자
    private Date last_mdfcn_dt;     // 최종 수정 일시
    private int menu_id;            // 메뉴 아이디
    private int rownumber;          // 순번
}
