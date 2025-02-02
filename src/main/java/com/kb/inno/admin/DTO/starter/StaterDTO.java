/**
 * 파일명     : StaterDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 스타터스 기본 정보 변수
 * 관련 DB    : KB_API_STARTER_INFO
 * 최초개발일 : 2024.11.15
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.DTO.starter;

import com.kb.inno.admin.DTO.StartupDTO;
import com.kb.inno.common.DateUtil;
import com.kb.inno.common.StringUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
public class StaterDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
    
	private String ent_cd;                //기업 코드(UNIQUE)
    private String brno;                  //사업자번호
    private String corp_no;               //법인 번호
    private String rprsv_nm;              //대표자 명(CEO)
    private String ent_nm;                //기업 이름
    private String tpbiz;                 //업종
    private String ent_shape;             //기업 형태
    private String ent_scale;             //기업 규모
    private String addr;                  //주소
    private String fndn_ymd;              //설립일자
    private String ksic_cd;               //표준산업분류코드
    private String telno;                 //전화번호
    private String fxno;                  //팩스번호
    private String zip;                   //우편번호
    private String public_ent_yn;         //공기업 여부
    private String indiv_bzmn_yn;         //개인사업자 여부
    private String hdofc_yn;              //본사 여부
    private String venture_cert_yn;       //벤처인증 여부
    private String ipo_yn;                //IPO 여부
    private String foreign_invest_yn;     //외국인투자법인 여부
    private String fnst_yn;               //금융회사 여부
    private String listing_ymd;           //상장 일자
    private String listing_co;            //상장주관사
    private String major_ent_yn;          //대기업 여부
    private String medium_ent_yn;         //중견기업 여부
    private String kospi_yn;              //코스피상장 여부
    private String kosdaq_yn;             //코스닥상장 여부
    private String konex_yn;              //코넥스상장 여부
    private String kotc_yn;               //KOTC 여부
    private String external_audit_yn;     //외감기업 여부
    private String general_ent_yn;        //일반기업 여부
    private String non_profit_yn;         //비영리단체 여부
    private String absorption_merged_yn;  //피흡수합병 여부
    private String clsbiz_yn;             //폐업 여부
    private String tcbiz_yn;              //휴업 여부
    private String bankruptcy_yn;         //파산 여부
    private String main_biz;              //주요 사업
    private String prdct;                 //제품
    private String hmpg;                  //홈페이지
    private String crtr_ym;               //기준년월
    private Integer mm12_jncmp_nocs;         //최근 12개월간 신규 입사자 수
    private Integer mm12_rsgntn_nocs;        //최근 12개월간 신규 퇴사자 수
    private String mm12_rsgntn_rt;        //최근 12개월간 퇴사율
    private Integer now_wrkr_nocs;            //현재 근무자 수
    private Long cptl_amt;                 //자본잉여금(단위 : 원)
    private Long profit_amt;               //이익잉여금(단위 : 원)
    private String invest_amt;               //누적투자금액(단위 : 억)
    private String series_nm;              //최종 투자단계
    private Integer invest_cnt;                //투자유치횟수
    private String now_date;               //오늘 일자
    private String invest_amt_str;
    private String series_color;
    private Integer investCnt;

    public static StaterDTO make(StartupDTO startupDTO, int investCnt){
        return StaterDTO.builder()
                .brno(formatBRNO(startupDTO.getBrno()))
                .corp_no(formatCORPNO(startupDTO.getCorp_no()))
                .rprsv_nm(startupDTO.getRprsv_nm())
                .ent_nm(startupDTO.getEnt_nm())
                .tpbiz(startupDTO.getTpbiz())
                .ent_shape(startupDTO.getEnt_shape())
                .ent_scale(startupDTO.getEnt_scale())
                .addr(startupDTO.getAddr())
                .fndn_ymd(formatFNDNYMD(startupDTO.getFndn_ymd()))
                .venture_cert_yn(startupDTO.getVenture_cert_yn())
                .external_audit_yn(startupDTO.getExternal_audit_yn())
                .main_biz(startupDTO.getMain_biz())
                .prdct(startupDTO.getPrdct())
                .hmpg(formatHMPG(startupDTO.getHmpg()))
                .crtr_ym(startupDTO.getCrtr_ym())
                .mm12_jncmp_nocs(startupDTO.getMm12_jncmp_nocs() == null ? 0 : startupDTO.getMm12_jncmp_nocs())
                .mm12_rsgntn_nocs(startupDTO.getMm12_rsgntn_nocs() == null ? 0 : startupDTO.getMm12_rsgntn_nocs())
                .mm12_rsgntn_rt(startupDTO.getMm12_rsgntn_rt())
                .now_wrkr_nocs(startupDTO.getNow_wrkr_nocs() == null ? 0 : startupDTO.getNow_wrkr_nocs())
                .cptl_amt(startupDTO.getCptl_amt() == null ? 0L : startupDTO.getCptl_amt())
                .invest_amt(startupDTO.getInvest_amt())
                .series_nm(formatSeriesNm(startupDTO.getSeries_nm()))
                .invest_cnt(investCnt)
                .now_date(DateUtil.getToDay("yyyy.MM.dd"))
                .invest_amt_str(formatInvestAmtStr(startupDTO.getInvest_amt()))
                .build();
    }

    private static String formatBRNO(String brno) {
        if (!StringUtil.hasText(brno)) {
            return brno;
        }
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{2})(\\d{0,5})");
        Matcher matcher = pattern.matcher(brno);
        if (matcher.matches()) {
            return matcher.group(1) + "-" + matcher.group(2) +
                    (matcher.group(3).isEmpty() ? "" : "-" + matcher.group(3));
        }
        return brno;
    }

    private static String formatCORPNO(String corpNo) {
        if (!StringUtil.hasText(corpNo)) {
            return corpNo;
        }
        Pattern pattern = Pattern.compile("(\\d{6})(\\d*)");
        Matcher matcher = pattern.matcher(corpNo);
        if (matcher.matches()) {
            return matcher.group(1) + (matcher.group(2).isEmpty() ? "" : "-" + matcher.group(2));
        }
        return corpNo;
    }

    private static String formatFNDNYMD(String fndnYmd) {
        if (!StringUtil.hasText(fndnYmd)) {
            return fndnYmd;
        }
        Pattern pattern = Pattern.compile("(\\d{4})(\\d{0,2})(\\d{0,2})");
        Matcher matcher = pattern.matcher(fndnYmd);
        if (matcher.matches()) {
            return matcher.group(1) +
                    (matcher.group(2).isEmpty() ? "" : "-" + matcher.group(2)) +
                    (matcher.group(3).isEmpty() ? "" : "-" + matcher.group(3));
        }
        return fndnYmd;
    }

    private static String formatHMPG(String hmpg) {
        if (!StringUtil.hasText(hmpg)) {
            return null;
        }

        String upperHmpg = hmpg.toUpperCase();
        if (upperHmpg.startsWith("HTTP")) {
            return hmpg;
        } else {
            return "https://" + hmpg;
        }
    }

    private static String formatInvestAmtStr(String investAmt) {
        if (!StringUtil.hasText(investAmt) || investAmt.equals("0")) {
            return "비공개";
        }

        try {
            long amount = Long.parseLong(investAmt);
            if (amount == 0) {
                return "비공개";
            }

            double amountInBillion = amount / 100000000.0;
            String formattedAmount = String.format("%.2f", amountInBillion);

            formattedAmount = formattedAmount.replaceAll("0*$", "").replaceAll("\\.$", "");

            String[] parts = formattedAmount.split("\\.");
            parts[0] = parts[0].replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");

            return (parts.length > 1) ? parts[0] + "." + parts[1] + " 억원" : parts[0] + " 억원";
        } catch (NumberFormatException e) {
            return "비공개";
        }
    }

    private static String formatSeriesNm(String seriesNm) {
        if (!StringUtil.hasText(seriesNm)) {
            return "알수없음";
        }

        if (seriesNm.length() == 1) {
            return "Series " + seriesNm;
        }

        if ("SEED".equals(seriesNm) || "TIPS".equals(seriesNm) || "IPO".equals(seriesNm)
                || seriesNm.startsWith("M")) {
            return seriesNm;
        }

        if (seriesNm.startsWith("PRE")) {
            return "Pre-" + seriesNm.substring(3);
        }

        return "";
    }
}
