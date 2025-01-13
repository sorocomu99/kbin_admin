/**
 * 파일명     : SurveyDTO.java
 * 화면명     : 설문 관리
 * 설명       : 설문 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2025.01.06
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
public class SurveyDTO implements Serializable {
    //설문 조사 정보
    private int srvy_sn;                 //설문 일련번호
    private String srvy_ttl;             //설문 제목
    private int bane_file_sn;            //배너 파일 일련번호
    private String tmpr_del_yn;          //임시 삭제 여부
    //설문 질문 정보
    //설문 일련번호는 설문 조사 정보와 같이 사용
    private int srvy_qstn_sn;            //설문 질문 일련번호
    private String qstn_type;            //설문 유형
    private String srvy_qstn;            //설문 질문
    private String srvy_dtl_qstn;        //설문 질문 상세 내용
    private String esntl_vl_yn;          //필수 값 여부
    //설문 답변 정보
    //설문 질문 일련번호는 설문 질문 정보와 같이 사용
    private int srvy_ans_sn;             //설문 답변 일련번호
    private String ans_cn;               //답변 내용
    private String aftr_mvmn;            //이후 이동(다음 단계)
    //공통
    private int frst_rgtr;               //최초등록자
    private Date frst_reg_dt;            //최초 등록 일시
    private int last_mdfr;               //최종 수정자
    private Date last_mdfcn_dt;          //최종 수정 일시
    private int menu_id;                 //메뉴 ID
    private int rownumber;               //순번
    //지원 안내 정보
    private int sprt_expln_sn;           //지원 안내 일련번호 -> 설문 일련번호와 동일하게 설정
    private String recru_se;             //모집 구분
    private String recru_bgng_ymd;       //모집 시작 일자
    private String recru_end_ymd;        //모집 종료 일자
    private String recru_end_hr;         //모집 종료 시간
    private String recru_trgt_m;         //모집 대상 메인
    private String recru_trgt_s;         //모집 대상 서브
    private String recru_fld_m;          //모집 분야 메인
    private String recru_fld_s;          //모집 분야 서브
    private String slctn_benef_cn1;      //선정 혜택 내용1
    private String slctn_benef_cn2;      //선정 혜택 내용2
    private String slctn_benef_cn3;      //선정 혜택 내용3
    private String slctn_benef_cn4;      //선정 혜택 내용4
    private String recru_schdl;          //모집일정(기간)
    private String slctn_prcs;           //선별절차

    //첨부파일 정보
    private int file_sn;
    private String file_nm;
    private String file_path;
    private int file_yn;
    private MultipartFile survey_file;
    //검색 조건
    private String type;
    private String keyword;
    //등록 및 수정에 필요한 변수
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
    //페이징 변수
    private int start;
    private int end;
}
