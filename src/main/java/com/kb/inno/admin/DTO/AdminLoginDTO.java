/**
 * 파일명     : AdminLoginDTO.java
 * 화면명     : 로그인
 * 설명       : 로그인 및 로그아웃시 사용될 변수
 * 최초개발일 : 2024.10.23
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.DTO;

import lombok.Data;

@Data
public class AdminLoginDTO {
    private int mngr_sn;
    private String mngr_id;
    private String mngr_pswd;
    private String mngr_nm;
    private String mngr_eml;

    //비밀번호 찾기 변수
    private String find_id;
    private String find_email;
}
