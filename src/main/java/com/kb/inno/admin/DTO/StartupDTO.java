package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class StartupDTO {
    //기본정보
    private String ent_cd;             //기업코드
    private String ent_nm;             //기업명
    private String main_biz;           //주요사업
    private String tpbiz;              //업종
    private int now_wrkr_nocs;         //현재근무자수
    private String rprsv_nm;           //대표자명
    private String fndn_ymd;           //설립일자
    private String brno;               //사업자번호
    private String corp_no;            //법인번호
    private long cptl_amt;             //자본금
    private String ent_shape;          //기업 형태
    private String external_audit_yn;  //외감기업 여부
    private String venture_cert_yn;    //벤처인증 여부
    private String addr;               //주소
    private String ent_scale;          //기업 규모
    private String prdct;              //제품
    private String hmpg;               //홈페이지 주소
    private int mm12_jncmp_nocs;       //12개월 입사자
    private int mm12_rsgntn_nocs;      //12개월 퇴사자
    private long mm12_rsgntn_rt;       //12개월 퇴사율
    //사업 서비스 정보( , -> | 으로 변경 DB 등록시 , 로 다시 변경)
    private String srvc_nm;            //서비스 명
    private String web_srvc_nm;        //웹 서비스 링크
    private String google_app_link;    //구글 링크
    private String apple_app_link;     //애플 링크
    //투자정보( , -> | 으로 변경 DB 등록시 , 로 다시 변경)
    private String invest_ymd;         //투자일자
    private String series_nm;          //투자단계
    private long invest_amt;           //투자금액
    private String investor;           //투자자
    //고용정보
    private String crtr_ym;            //기준 년월
    private int jncmp_nocs;            //입사자
    private int rsgntn_nocs;           //퇴사자
    private int hdof_nocs;             //근무자
    //매출정보
    private String sls_yr1;            //년도1
    private String sls_yr2;            //년도2
    private String sls_yr3;            //년도3
    private String sls_yr4;            //년도4
    private String sls_yr5;            //년도5
    private long sls_amt1;             //매출액1
    private long sls_amt2;             //매출액2
    private long sls_amt3;             //매출액3
    private long sls_amt4;             //매출액4
    private long sls_amt5;             //매출액5
    private long sls_cost_amt1;        //매출원가1
    private long sls_cost_amt2;        //매출원가2
    private long sls_cost_amt3;        //매출원가3
    private long sls_cost_amt4;        //매출원가4
    private long sls_cost_amt5;        //매출원가5
    private long sls_gramt1;           //매출총이익1
    private long sls_gramt2;           //매출총이익2
    private long sls_gramt3;           //매출총이익3
    private long sls_gramt4;           //매출총이익4
    private long sls_gramt5;           //매출총이익5
    private long sga_amt1;             //판관비1
    private long sga_amt2;             //판관비2
    private long sga_amt3;             //판관비3
    private long sga_amt4;             //판관비4
    private long sga_amt5;             //판관비5
    private long operating_profit1;    //영업이익1
    private long operating_profit2;    //영업이익2
    private long operating_profit3;    //영업이익3
    private long operating_profit4;    //영업이익4
    private long operating_profit5;    //영업이익5
    private long net_profit1;          //순이익1
    private long net_profit2;          //순이익2
    private long net_profit3;          //순이익3
    private long net_profit4;          //순이익4
    private long net_profit5;          //순이익5
    //재무상태표
    private String ast_yr1;            //년도1
    private String ast_yr2;            //년도2
    private String ast_yr3;            //년도3
    private String ast_yr4;            //년도4
    private String ast_yr5;            //년도5
    private long current_assets1;      //유동자산1
    private long current_assets2;      //유동자산2
    private long current_assets3;      //유동자산3
    private long current_assets4;      //유동자산4
    private long current_assets5;      //유동자산5
    private long non_current_assets1;  //비유동자산1
    private long non_current_assets2;  //비유동자산2
    private long non_current_assets3;  //비유동자산3
    private long non_current_assets4;  //비유동자산4
    private long non_current_assets5;  //비유동자산5
    private long ast_gramt1;           //자산총계1
    private long ast_gramt2;           //자산총계2
    private long ast_gramt3;           //자산총계3
    private long ast_gramt4;           //자산총계4
    private long ast_gramt5;           //자산총계5
    private long debt_gramt1;          //부채총계1
    private long debt_gramt2;          //부채총계2
    private long debt_gramt3;          //부채총계3
    private long debt_gramt4;          //부채총계4
    private long debt_gramt5;          //부채총계5
    private long cptl1;                //자본금1
    private long cptl2;                //자본금2
    private long cptl3;                //자본금3
    private long cptl4;                //자본금4
    private long cptl5;                //자본금5
    private long cptl_gramt1;          //자본총계1
    private long cptl_gramt2;          //자본총계2
    private long cptl_gramt3;          //자본총계3
    private long cptl_gramt4;          //자본총계4
    private long cptl_gramt5;          //자본총계5
    //뉴스정보( , -> | 으로 변경 DB 등록시 , 로 다시 변경)
    private String thumb_url;          //썸네일 이미지
    private String news_ttl;           //기사 제목
    private String provider;           //제공자
    private String news_link;          //뉴스링크
    //키워드
    private String keywd1;             //키워드1
    private int nocs1;                 //카운트1
    private String keywd2;             //키워드2
    private int nocs2;                 //카운트2
    private String keywd3;             //키워드3
    private int nocs3;                 //카운트3
    private String keywd4;             //키워드4
    private int nocs4;                 //카운트4
    private String keywd5;             //키워드5
    private int nocs5;                 //카운트5
    private String keywd6;             //키워드6
    private int nocs6;                 //카운트6
    private String keywd7;             //키워드7
    private int nocs7;                 //카운트7
    private String keywd8;             //키워드8
    private int nocs8;                 //카운트8
    private String keywd9;             //키워드9
    private int nocs9;                 //카운트9
    private String keywd10;            //키워드10
    private int nocs10;                //카운트10
    //공통
    private int frst_rgtr;       // 최초 등록자
    private Date frst_reg_dt;    // 최초 등록 일시
    private int last_mdfr;       // 최종 수정자
    private Date last_mdfcn_dt;  // 최종 수정 일시
    private int menu_id;         // 메뉴 ID
    private int rownumber;       // 순번
    private int rnum;
    //검색
    private String type;
    private String keyword;
    private int start;
    private int end;
}
