/**
 * 파일명     : VisualDTO.java
 * 화면명     : 메인 비주얼 관리
 * 설명       : 메인 비주얼 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

@Data
public class VisualDTO implements Serializable {
    private int main_sn;             // 메인 일련번호
    private String main_cn;          // 메인 내용
    private String expsr_yn;         // 노출 여부
    private int sort_no;             // 정렬 번호
    private int atch_file_sn;        // 첨부파일 일련번호
    private int frst_rgtr;           // 최초 등록자
    private Date frst_reg_dt;        // 최초 등록 일시
    private int last_mdfr;           // 최종 수정자
    private Date last_mdfcn_dt;      // 최종 수정 일시
    private MultipartFile main_file; // 메인 비주얼 파일
    private String main_file_name;   // 메인 비주얼 파일명
    private String origin_file_name; // 메인 비주얼 오리지널 파일명
    private String main_path;        // 메인 비주얼 파일 경로
    private int file_yn;             // 파일이 담겨있는 지 아닌지 확인
    private int menu_id;             // 메뉴 ID
    private int rownumber;           // 메뉴 순번
}
