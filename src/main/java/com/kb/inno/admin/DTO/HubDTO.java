/**
 * 파일명     : HubDTO.java
 * 화면명     : HUB 센터 소식 관리
 * 설명       : HUB 센터 소식 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.14
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class HubDTO {
    private int hub_sn;             // 허브 일련번호
    private String hub_ttl;         // 허브 제목
    private String hub_cn;          // 허브 내용
    private String expsr_yn;        // 노출 여부
    private int sort_no;            // 정렬 번호
    private String ctgry;           // 카테고리
    private int atch_file_sn1;      // 첨부파일 일련번호(썸네일)
    private int atch_file_sn2;      // 첨부파일 일련번호(동영상)
    private int atch_file_sn3;      // 첨부파일 일련번호
    private int frst_rgtr;          // 최초 등록자
    private Date frst_reg_dt;       // 최초 등록일시
    private int last_mdfr;          // 최종 수정자
    private Date last_mdfcn_dt;     // 최종 수정 일시
    private String del_yn;          // 파일 삭제 여부

    // 썸네일
    private MultipartFile hub_img;  // 공지사항 파일
    private String hub_img_name;    // 공지사항 파일명
    private String origin_img_name; // 공지사항 오리지널 파일명
    private String hub_path_img;    // 공지사항 파일 경로
    private int file_yn1;           // 파일이 담겨있는 지 아닌지 확인

    // 동영상
    private MultipartFile hub_mov;  // 공지사항 파일
    private String hub_mov_name;    // 공지사항 파일명
    private String origin_mov_name; // 공지사항 오리지널 파일명
    private String hub_path_mov;    // 공지사항 파일 경로
    private int file_yn2;           // 파일이 담겨있는 지 아닌지 확인

    // 파일
    private MultipartFile hub_file;  // 공지사항 파일
    private String hub_file_name;    // 공지사항 파일명
    private String origin_file_name; // 공지사항 오리지널 파일명
    private String hub_path;         // 공지사항 파일 경로
    private int file_yn3;            // 파일이 담겨있는 지 아닌지 확인

    private int menu_id;             // 메뉴 ID
}
