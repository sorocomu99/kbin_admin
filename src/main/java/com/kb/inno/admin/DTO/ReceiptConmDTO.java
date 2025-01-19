package com.kb.inno.admin.DTO;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReceiptConmDTO implements Serializable {
    //설문 조사 정보
    private int srvy_sn;                 //설문 일련번호
    private String srvy_ttl;             //설문 제목
    private String qstn_type; //항목 타입
    private String srvy_qstn; //질문 제목
    private String srvy_qstn_sn; //질문 시퀀스(낮은 순자가 1번)
    private String rspns_cn; //답변
    private String conm; //회사명
    private String prgrs_stts; //진행 상태(0 : 접수, 1 : 1차 심사, 2 : 2차 심사, 3 : 3차 심사, 4 : 4차 심사, 5 : 5차 심사, 6 : 합격, 9 : 불합격)
    private int rownumber;               //순번
}
