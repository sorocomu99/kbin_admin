/**
 * 파일명     : SendMailDTO.java
 * 화면명     : 보낸 메일함 관리
 * 설명       : 보낸 메일 조회 및 삭제 처리
 * 최초개발일  : 2025.01.14
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
public class SendMailDTO implements Serializable {
    //보낸 메일 관리
    private String send_ymd;             //보낸 일자
    private int send_mail_sn;            //보낸 메일 일련번호
    private String mail_rcvr;            //메일 받는 사람 주소
    private String mail_ttl;             //메일 제목
    private String mail_cn;              //메일 내용
    private String reg_date;             //보낸 일시
    //이력정보
    private int send_mail_hist_sn;       //보낸메일 이력 일련번호
    //공통
    private int frst_rgtr;               //최초등록자
    private Date frst_reg_dt;            //최초 등록 일시
    private int last_mdfr;               //최종 수정자
    private Date last_mdfcn_dt;          //최종 수정 일시
    private int menu_id;                 //메뉴 ID
    private int rownumber;               //순번
    //검색 조건
    private String type;
    private String keyword;
    //페이징 변수
    private int start;
    private int end;
}
