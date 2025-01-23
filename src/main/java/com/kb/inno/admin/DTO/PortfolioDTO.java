/**
 * 파일명     : PortfolioDTO.java
 * 화면명     : 포트폴리오 관리
 * 설명       : 포트폴리오 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.12.31
 * 최초개발자 : 이훈희
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
public class PortfolioDTO implements Serializable {
    private String port_yr;      //포트폴리오 년도
    private int port_sn;         //포트폴리오 일련번호
    private String bzenty_nm;    //업체명
    private String intro_cn;     //소개내용
    private String hmpg_link;    //홈페이지 링크
    private int atch_file_sn;    //첨부파일 일련번호
    private int cnt;             //년도별 등록 건수
    private String beforeYr;     //년도 업데이트를 위한 변수
    private int sort_no;
    //첨부파일 변수
    private String file_nm;
    private String file_path;
    private int file_yn;             // 파일이 담겨있는 지 아닌지 확인
    private MultipartFile main_file; // 메인 비주얼 파일
    //테이블 공통
    private int frst_rgtr;       //최초등록자
    private Date frst_reg_dt;    //최초 등록 일시
    private int last_mdfr;       //최종 수정자
    private Date last_mdfcn_dt;  //최종 수정 일시
    private int menu_id;         //메뉴 ID
    private int rnum;            //순번
    private String type;
    private String keyword;
    private int start;
    private int end;

    private String ori_file_name;           //오리지널 파일명
    private String file_name;               //파일명
}
