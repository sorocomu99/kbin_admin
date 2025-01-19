package com.kb.inno.admin.DTO;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReceiptListDTO implements Serializable {
	private int srvy_sn;                 //설문 일련번호
    private String conm;//회사명
    private String prgrs_stts; //진행 상태(0 : 접수, 1 : 1차 심사, 2 : 2차 심사, 3 : 3차 심사, 4 : 4차 심사, 5 : 5차 심사, 6 : 합격, 9 : 불합격)
    private String rspns_cn1;//질문1
    private String rspns_cn2;//질문2
    private String rspns_cn3;//질문3
    private String rspns_cn4;//질문4
    private String rspns_cn5;//지문5
    private String rspns_cn6;//질문6
    private String rspns_cn7;//질문7
    private String rspns_cn8;//질문8
    private String rspns_cn9;//질문9
    private String rspns_cn10;//질문10
    
    private int addRow;//리스트 갯수
    private String type;
    
}
