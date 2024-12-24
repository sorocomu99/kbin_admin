package com.kb.inno.admin.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class CooperationDTO {
    private int coope_sn;                 // 협력 일련번호
    private String bzenty_nm;             // 업체명
    private String expsr_yn;              // 노출 여부
    private int sort_no;                  // 정렬 번호
    private int atch_file_id;             // 첨부파일 아이디
    private int frst_rgtr;                // 최초 등록자
    private Date frst_reg_dt;             // 최초 등록 일시
    private int last_mdfr;                // 최종 수정자
    private Date last_mdfcn_dt;           // 최종 수정 일시
    private MultipartFile coope_file;     // 협력 기관 파일
    private String coope_file_name;       // 협력 기관 파일명
    private String origin_file_name;      // 협력 기관 오리지널 파일명
    private String coope_path;            // 협력 기관 파일 경로
    private int file_yn;                  // 파일 첨부 유무
    private int menu_id;                  // 메뉴 ID
    private int rownumber;                // 순번
}
