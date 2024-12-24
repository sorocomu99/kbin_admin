/**
 * 파일명     : FileDTO.java
 * 화면명     : 파일 관리
 * 설명       : 파일 관련 DTO
 * 최초개발일 : 2024.10.29
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class FileDTO {
    private int file_sn;            // 파일 일련번호
    private String file_nm;         // 파일명
    private String orgnl_file_nm;   // 원본 파일명
    private int file_sz;            // 파일 사이즈
    private String file_extn;       // 파일 확장자
    private String file_path;       // 파일 경로
    private int frst_rgtr;          // 최초 등록자
    private Date frst_reg_dt;       // 최초 등록 일시
    private int last_mdfr;          // 최종 수정자
    private Date last_mdfcn_dt;     // 최종 수정 일시
}
