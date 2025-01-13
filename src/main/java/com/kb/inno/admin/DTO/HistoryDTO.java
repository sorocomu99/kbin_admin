/**
 * 파일명     : HistoryDTO.java
 * 화면명     : 연혁 관리
 * 설명       : 연혁 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.12.31
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class HistoryDTO implements Serializable {
    private String hstry_yr;             //연혁 년도
    private int hstry_lclsf_sn;          //연혁 대분류 일련번호
    private String hstry_lclsf_ttl;      //연혁 대분류 제목
    private int hstry_lclsf_sort_no;     //연혁 대분류 정렬 번호
    private int hstry_sclsf_sn;          //연혁 소분류 일련번호
    private String hstry_sclsf_ttl;      //연혁 소분류 제목
    private int hstry_sclsf_sort_no;     //연혁 소분류 정렬 번호
    private int hstry_sclsf_up_lclsf_sn; //연혁 소분류 상위 대분류 일련번호
    private int frst_rgtr;               //최초등록자
    private Date frst_reg_dt;            //최초 등록 일시
    private int last_mdfr;               //최종 수정자
    private Date last_mdfcn_dt;          //최종 수정 일시
    private int menu_id;                 //메뉴 ID
    private int rownumber;               //순번
    private String main_id_name;
    private String sub_id;
    private String sub_name;
    private int display_yn;
    private String h_title;
    private String main_sn;
    private String sub_sn;
    private int cnt_over;
    private String main_input1;
    private String sub_input1;
    private String main_input2;
    private String sub_input2;
    private String main_input3;
    private String sub_input3;
    private String main_input4;
    private String sub_input4;
    private String main_input5;
    private String sub_input5;
    private String main_input6;
    private String sub_input6;
    private String main_input7;
    private String sub_input7;
    private String main_input8;
    private String sub_input8;
    private String main_input9;
    private String sub_input9;
    private String main_input10;
    private String sub_input10;
    private int start;
    private int end;
}
