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
    
    //추가 문항1 ~10
    private String qstn_type1;
    private String qstn_type2;
    private String qstn_type3;
    private String qstn_type4;
    private String qstn_type5;
    private String qstn_type6;
    private String qstn_type7;
    private String qstn_type8;
    private String qstn_type9;
    private String qstn_type10;
    
    //문항 -> 질문 1~10
    private String srvy_qstn1;
    private String srvy_qstn2;
    private String srvy_qstn3;
    private String srvy_qstn4;
    private String srvy_qstn5;
    private String srvy_qstn6;
    private String srvy_qstn7;
    private String srvy_qstn8;
    private String srvy_qstn9;
    private String srvy_qstn10;
    
    //문항 -> 첫번째 숫자는 타입 1~3 두번째 숫자는 질문 상세 1~10
    private String srvy_dtl_qstn11;
    private String srvy_dtl_qstn21;
    private String srvy_dtl_qstn31;
    private String srvy_dtl_qstn12;
    private String srvy_dtl_qstn22;
    private String srvy_dtl_qstn32;
    private String srvy_dtl_qstn13;
    private String srvy_dtl_qstn23;
    private String srvy_dtl_qstn33;
    private String srvy_dtl_qstn14;
    private String srvy_dtl_qstn24;
    private String srvy_dtl_qstn34;
    private String srvy_dtl_qstn15;
    private String srvy_dtl_qstn25;
    private String srvy_dtl_qstn35;
    private String srvy_dtl_qstn16;
    private String srvy_dtl_qstn26;
    private String srvy_dtl_qstn36;
    private String srvy_dtl_qstn17;
    private String srvy_dtl_qstn27;
    private String srvy_dtl_qstn37;
    private String srvy_dtl_qstn18;
    private String srvy_dtl_qstn28;
    private String srvy_dtl_qstn38;
    private String srvy_dtl_qstn19;
    private String srvy_dtl_qstn29;
    private String srvy_dtl_qstn39;
    private String srvy_dtl_qstn110;
    private String srvy_dtl_qstn210;
    private String srvy_dtl_qstn310;
    
    //보기 1~10에 뮨헝 1~10개 (100개)
    private String ans_cn11;
    private String ans_cn12;
    private String ans_cn13;
    private String ans_cn14;
    private String ans_cn15;
    private String ans_cn16;
    private String ans_cn17;
    private String ans_cn18;
    private String ans_cn19;
    private String ans_cn110;
    private String ans_cn21;
    private String ans_cn22;
    private String ans_cn23;
    private String ans_cn24;
    private String ans_cn25;
    private String ans_cn26;
    private String ans_cn27;
    private String ans_cn28;
    private String ans_cn29;
    private String ans_cn210;
    private String ans_cn31;
    private String ans_cn32;
    private String ans_cn33;
    private String ans_cn34;
    private String ans_cn35;
    private String ans_cn36;
    private String ans_cn37;
    private String ans_cn38;
    private String ans_cn39;
    private String ans_cn310;
	private String ans_cn41;
    private String ans_cn42;
    private String ans_cn43;
    private String ans_cn44;
    private String ans_cn45;
    private String ans_cn46;
    private String ans_cn47;
    private String ans_cn48;
    private String ans_cn49;
    private String ans_cn410;
	private String ans_cn51;
    private String ans_cn52;
    private String ans_cn53;
    private String ans_cn54;
    private String ans_cn55;
    private String ans_cn56;
    private String ans_cn57;
    private String ans_cn58;
    private String ans_cn59;
    private String ans_cn510;
	private String ans_cn61;
    private String ans_cn62;
    private String ans_cn63;
    private String ans_cn64;
    private String ans_cn65;
    private String ans_cn66;
    private String ans_cn67;
    private String ans_cn68;
    private String ans_cn69;
    private String ans_cn610;
	private String ans_cn71;
    private String ans_cn72;
    private String ans_cn73;
    private String ans_cn74;
    private String ans_cn75;
    private String ans_cn76;
    private String ans_cn77;
    private String ans_cn78;
    private String ans_cn79;
    private String ans_cn710;
	private String ans_cn81;
    private String ans_cn82;
    private String ans_cn83;
    private String ans_cn84;
    private String ans_cn85;
    private String ans_cn86;
    private String ans_cn87;
    private String ans_cn88;
    private String ans_cn89;
    private String ans_cn810;
	private String ans_cn91;
    private String ans_cn92;
    private String ans_cn93;
    private String ans_cn94;
    private String ans_cn95;
    private String ans_cn96;
    private String ans_cn97;
    private String ans_cn98;
    private String ans_cn99;
    private String ans_cn910;
	private String ans_cn101;
    private String ans_cn102;
    private String ans_cn103;
    private String ans_cn104;
    private String ans_cn105;
    private String ans_cn106;
    private String ans_cn107;
    private String ans_cn108;
    private String ans_cn109;
    private String ans_cn1010;

    //
    private String required1;
    private String required2;
    private String required3;
    private String required4;
    private String required5;
    private String required6;
    private String required7;
    private String required8;
    private String required9;
    private String required10;
    
    private String aftr_mvmn1;
    private String aftr_mvmn2;
    private String aftr_mvmn3;
    private String aftr_mvmn4;
    private String aftr_mvmn5;
    private String aftr_mvmn6;
    private String aftr_mvmn7;
    private String aftr_mvmn8;
    private String aftr_mvmn9;
    private String aftr_mvmn10;
    
    private String srvy_sn1;

    
}
