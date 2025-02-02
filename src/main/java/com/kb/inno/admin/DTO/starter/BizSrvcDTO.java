/**
 * 파일명     : BizSrvcDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 사업 서비스 정보 변수
 * 관련 DB    : KB_API_BIZ_SRVC_INFO
 * 최초개발일 : 2024.11.15
 * 최초개발자 : 이훈희
 * ==========================================================
 * 수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO.starter;

import com.kb.inno.admin.DTO.StartupDTO;
import com.kb.inno.common.StringUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BizSrvcDTO implements Serializable {

    //private static final long serialVersionUID = 1L;

    private String ent_cd;            //기업 코드
    private String srvc_nm;           //서비스 명
    private String google_app_link;   //구글 플레이스토어 링크
    private String google_icon_link;  //구글 플레이스토어 아이콘 링크
    private String apple_app_link;    //애플 앱스토어 링크
    private String apple_icon_link;   //애플 앱스토어 아이콘 링크
    private String web_srvc_link;     //웹서비스 링크

    public static BizSrvcDTO make(StartupDTO startupDTO) {
        return BizSrvcDTO.builder()
                .srvc_nm(StringUtil.hasText(startupDTO.getSrvc_nm()) ? startupDTO.getSrvc_nm().replace("@@RP@@", ",") : "")
                .google_app_link(startupDTO.getGoogle_app_link())
                .apple_app_link(startupDTO.getApple_app_link())
                .web_srvc_link(startupDTO.getWeb_srvc_link())
                .build();
    }
}