/**
 * 파일명     : EmploDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 고용 정보 변수
 * 관련 DB    : KB_API_EMPLO_INFO
 * 최초개발일 : 2024.11.15
 * 최초개발자 : 이훈희
 * ==========================================================
 * 수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO.starter;

import com.kb.inno.admin.DTO.StartupDTO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EmploDTO implements Serializable {

    //private static final long serialVersionUID = 1L;

    private String ent_cd;    //기업 코드
    private String crtr_ym;   //기준 년월
    private String jncmp_nocs;   //입사자 수
    private String rsgntn_nocs;  //퇴사자 수
    private String hdof_nocs;    //재직자 수
    private String jncmp_nocs_str;
    private String rsgntn_nocs_str;
    private String hdof_nocs_str;

    public static EmploDTO make(StartupDTO startupDTO) {
        return EmploDTO.builder()
                .crtr_ym(startupDTO.getCrtr_ym())
                .jncmp_nocs(startupDTO.getJncmp_nocs())
                .rsgntn_nocs(startupDTO.getRsgntn_nocs())
                .hdof_nocs(startupDTO.getHdof_nocs())
                .build();
    }
}
