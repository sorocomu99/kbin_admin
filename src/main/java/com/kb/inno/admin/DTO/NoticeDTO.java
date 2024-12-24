package com.kb.inno.admin.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class NoticeDTO {
    private int ntc_sn;              // 공지사항 일련번호
    private String ntc_ttl;          // 공지사항 제목
    private String ntc_cn;           // 공지사항 내용
    private int atch_file_sn;        // 첨부파일 일련번호
    private String expsr_yn;         // 노출 여부
    private int sort_no;             // 정렬 번호
    private int frst_rgtr;           // 최초 등록자
    private Date frst_reg_dt;        // 최초 등록 일시
    private int last_mdfr;           // 최종 수정자
    private Date last_mdfcn_dt;      // 최종 수정 일시
    private String ntc_yn;           // 공지사항 등록 여부
    private MultipartFile ntc_file;  // 공지사항 파일
    private String ntc_file_name;    // 공지사항 파일명
    private String origin_file_name; // 공지사항 오리지널 파일명
    private String ntc_path;         // 공지사항 파일 경로
    private int file_yn;             // 파일이 담겨있는 지 아닌지 확인
    private String del_yn;           // 파일 삭제 여부
    private int menu_id;             // 메뉴 ID
    private int rownumber;          // 순번
}
