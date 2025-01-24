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
    private Integer now_wrkr_nocs;         //현재근무자수
    private String rprsv_nm;           //대표자명
    private String fndn_ymd;           //설립일자
    private String brno;               //사업자번호
    private String corp_no;            //법인번호
    private Long cptl_amt;             //자본금
    private String ent_shape;          //기업 형태
    private String external_audit_yn;  //외감기업 여부
    private String venture_cert_yn;    //벤처인증 여부
    private String addr;               //주소
    private String ent_scale;          //기업 규모
    private String prdct;              //제품
    private String hmpg;               //홈페이지 주소
    private Integer mm12_jncmp_nocs;       //12개월 입사자
    private Integer mm12_rsgntn_nocs;      //12개월 퇴사자
    private String mm12_rsgntn_rt;       //12개월 퇴사율
    //사업 서비스 정보( , -> | 으로 변경 DB 등록시 , 로 다시 변경)
    private String srvc_nm;            //서비스 명
    private String web_srvc_link;        //웹 서비스 링크
    private String google_app_link;    //구글 링크
    private String apple_app_link;     //애플 링크
    //투자정보( , -> | 으로 변경 DB 등록시 , 로 다시 변경)
    private String invest_ymd;         //투자일자
    private String series_nm;          //투자단계
    private String invest_amt;         //투자금액
    private String investor;           //투자자
    //고용정보
    private String crtr_ym;            //기준 년월
    private String jncmp_nocs;         //입사자
    private String rsgntn_nocs;        //퇴사자
    private String hdof_nocs;          //근무자
    //매출정보
    private String sls_yr;
    private String sls_yr1;            //년도1
    private String sls_yr2;            //년도2
    private String sls_yr3;            //년도3
    private String sls_yr4;            //년도4
    private String sls_yr5;            //년도5
    private Long sls_amt;
    private Long sls_amt1 = 0L;             //매출액1
    private Long sls_amt2 = 0L;             //매출액2
    private Long sls_amt3 = 0L;             //매출액3
    private Long sls_amt4 = 0L;             //매출액4
    private Long sls_amt5 = 0L;             //매출액5
    private Long sls_cost_amt;
    private Long sls_cost_amt1 = 0L;        //매출원가1
    private Long sls_cost_amt2 = 0L;        //매출원가2
    private Long sls_cost_amt3 = 0L;        //매출원가3
    private Long sls_cost_amt4 = 0L;        //매출원가4
    private Long sls_cost_amt5 = 0L;        //매출원가5
    private Long sls_gramt;
    private Long sls_gramt1 = 0L;           //매출총이익1
    private Long sls_gramt2 = 0L;           //매출총이익2
    private Long sls_gramt3 = 0L;           //매출총이익3
    private Long sls_gramt4 = 0L;           //매출총이익4
    private Long sls_gramt5 = 0L;           //매출총이익5
    private Long sga_amt;
    private Long sga_amt1 = 0L;             //판관비1
    private Long sga_amt2 = 0L;             //판관비2
    private Long sga_amt3 = 0L;             //판관비3
    private Long sga_amt4 = 0L;             //판관비4
    private Long sga_amt5 = 0L;             //판관비5
    private Long operating_profit;
    private Long operating_profit1 = 0L;    //영업이익1
    private Long operating_profit2 = 0L;    //영업이익2
    private Long operating_profit3 = 0L;    //영업이익3
    private Long operating_profit4 = 0L;    //영업이익4
    private Long operating_profit5 = 0L;    //영업이익5
    private Long net_profit;
    private Long net_profit1 = 0L;          //순이익1
    private Long net_profit2 = 0L;          //순이익2
    private Long net_profit3 = 0L;          //순이익3
    private Long net_profit4 = 0L;          //순이익4
    private Long net_profit5 = 0L;          //순이익5
    //재무상태표
    private String ast_yr;
    private String ast_yr1;            //년도1
    private String ast_yr2;            //년도2
    private String ast_yr3;            //년도3
    private String ast_yr4;            //년도4
    private String ast_yr5;            //년도5
    private Long current_assets;
    private Long current_assets1 = 0L;      //유동자산1
    private Long current_assets2 = 0L;      //유동자산2
    private Long current_assets3 = 0L;      //유동자산3
    private Long current_assets4 = 0L;      //유동자산4
    private Long current_assets5 = 0L;      //유동자산5
    private Long non_current_assets;
    private Long non_current_assets1 = 0L;  //비유동자산1
    private Long non_current_assets2 = 0L;  //비유동자산2
    private Long non_current_assets3 = 0L;  //비유동자산3
    private Long non_current_assets4 = 0L;  //비유동자산4
    private Long non_current_assets5 = 0L;  //비유동자산5
    private Long ast_gramt;
    private Long ast_gramt1 = 0L;           //자산총계1
    private Long ast_gramt2 = 0L;           //자산총계2
    private Long ast_gramt3 = 0L;           //자산총계3
    private Long ast_gramt4 = 0L;           //자산총계4
    private Long ast_gramt5 = 0L;           //자산총계5
    private Long debt_gramt;
    private Long debt_gramt1 = 0L;          //부채총계1
    private Long debt_gramt2 = 0L;          //부채총계2
    private Long debt_gramt3 = 0L;          //부채총계3
    private Long debt_gramt4 = 0L;          //부채총계4
    private Long debt_gramt5 = 0L;          //부채총계5
    private Long cptl;
    private Long cptl1 = 0L;                //자본금1
    private Long cptl2 = 0L;                //자본금2
    private Long cptl3 = 0L;                //자본금3
    private Long cptl4 = 0L;                //자본금4
    private Long cptl5 = 0L;                //자본금5
    private Long cptl_gramt;
    private Long cptl_gramt1 = 0L;          //자본총계1
    private Long cptl_gramt2 = 0L;          //자본총계2
    private Long cptl_gramt3 = 0L;          //자본총계3
    private Long cptl_gramt4 = 0L;          //자본총계4
    private Long cptl_gramt5 = 0L;          //자본총계5
    //뉴스정보( , -> | 으로 변경 DB 등록시 , 로 다시 변경)
    private String thumb_url;          //썸네일 이미지
    private String news_ttl;           //기사 제목
    private String provider;           //제공자
    private String news_link;          //뉴스링크
    private String news_id;
    //키워드
    private String keywd;
    private Integer nocs;
    private String keywd1;             //키워드1
    private Integer nocs1 = 0;                 //카운트1
    private String keywd2;             //키워드2
    private Integer nocs2 = 0;                 //카운트2
    private String keywd3;             //키워드3
    private Integer nocs3 = 0;                 //카운트3
    private String keywd4;             //키워드4
    private Integer nocs4 = 0;                 //카운트4
    private String keywd5;             //키워드5
    private Integer nocs5 = 0;                 //카운트5
    private String keywd6;             //키워드6
    private Integer nocs6 = 0;                 //카운트6
    private String keywd7;             //키워드7
    private Integer nocs7 = 0;                 //카운트7
    private String keywd8;             //키워드8
    private Integer nocs8 = 0;                 //카운트8
    private String keywd9;             //키워드9
    private Integer nocs9 = 0;                 //카운트9
    private String keywd10;            //키워드10
    private Integer nocs10 = 0;                //카운트10
    //공통
    private Integer frst_rgtr;       // 최초 등록자
    private Date frst_reg_dt;    // 최초 등록 일시
    private Integer last_mdfr;       // 최종 수정자
    private Date last_mdfcn_dt;  // 최종 수정 일시
    private Integer menu_id;         // 메뉴 ID
    private Integer rownumber;       // 순번
    private Integer rnum;
    //검색
    private String type;
    private String keyword;
    private Integer start;
    private Integer end;
}
