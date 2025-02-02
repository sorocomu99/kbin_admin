/**
 * 파일명     : InvestDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 투자 정보 변수
 * 관련 DB    : KB_API_INVEST_INFO
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
public class InvestDTO implements Serializable {

    //private static final long serialVersionUID = 1L;

    private String ent_cd;      //기업 코드
    private String investor;    //투자자
    private String series_nm;   //투자 시리즈명
    private String invest_amt;     //투자 금액(단위 : 억)
    private String invest_ymd;  //투자 일자
    private String news_link;   //기사 링크
    private String series_color;
    private String invest_amt_str;

    public static InvestDTO make(StartupDTO startupDTO) {
        return InvestDTO.builder()
                .investor(StringUtil.hasText(startupDTO.getInvestor()) ? startupDTO.getInvestor().replace("@@RP@@", ",") : "")
                .series_nm(formatSeriesNm(startupDTO.getSeries_nm()))
                .invest_amt(startupDTO.getInvest_amt())
                .invest_ymd(StringUtil.hasText(startupDTO.getInvest_ymd()) ? startupDTO.getInvest_ymd().replace("@@RP@@", ",") : "")
                .news_link(startupDTO.getNews_link())
                .invest_amt_str(formatInvestAmtStr(startupDTO.getInvest_amt()))
                .build();
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