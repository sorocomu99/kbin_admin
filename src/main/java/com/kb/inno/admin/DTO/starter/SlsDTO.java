/**
 * 파일명     : SlsDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 매출 정보(손익계산서) 변수
 * 관련 DB    : KB_API_SLS_INFO
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Data
@Builder
public class SlsDTO implements Serializable {

    //private static final long serialVersionUID = 1L;

    private String ent_cd;             //기업 코드
    private String yr;                 //년도
    private Long sls_amt;            //매출액
    private Double operating_revenue;  //영업수익(단위 : 천원)
    private Long sls_cost_amt;       //매출원가(단위 : 천원)
    private Long sls_gramt;          //매출총이익(단위 : 천원)
    private Long sga_amt;            //판관비(단위 : 천원)
    private Long operating_profit;   //영업이익(단위 : 천원)
    private Long net_profit;         //당기순이익(단위 : 천원)
    private String sls_amt_str;
    private String operating_revenue_str;
    private String sls_cost_amt_str;
    private String sls_gramt_str;
    private String sga_amt_str;
    private String operating_profit_str;
    private String net_profit_str;
    private String sls_amt_col;
    private String operating_revenue_col;
    private String sls_cost_amt_col;
    private String sls_gramt_col;
    private String sga_amt_col;
    private String operating_profit_col;
    private String net_profit_col;

    public static SlsDTO make(StartupDTO startupDTO) {
        return SlsDTO.builder()
                .yr(startupDTO.getSls_yr())
                .sls_amt(calculateInMillions(startupDTO.getSls_amt()).longValue())
                .sls_cost_amt(calculateInMillions(startupDTO.getSls_cost_amt()).longValue())
                .sls_gramt(calculateInMillions(startupDTO.getSls_gramt()).longValue())
                .sga_amt(calculateInMillions(startupDTO.getSga_amt()).longValue())
                .operating_profit(calculateInMillions(startupDTO.getOperating_profit()).longValue())
                .net_profit(calculateInMillions(startupDTO.getNet_profit()).longValue())
                .sls_amt_str(formatNumber(startupDTO.getSls_amt()))
                .sls_cost_amt_str(formatNumber(startupDTO.getSls_cost_amt()))
                .sls_gramt_str(formatNumber(startupDTO.getSls_gramt()))
                .sga_amt_str(formatNumber(startupDTO.getSga_amt()))
                .operating_profit_str(formatNumber(startupDTO.getOperating_profit()))
                .net_profit_str(formatNumber(startupDTO.getNet_profit()))
                .sls_amt_col(determineColor(startupDTO.getSls_amt()))
                .sls_cost_amt_col(determineColor(startupDTO.getSls_cost_amt()))
                .sls_gramt_col(determineColor(startupDTO.getSls_gramt()))
                .sga_amt_col(determineColor(startupDTO.getSga_amt()))
                .operating_profit_col(determineColor(startupDTO.getOperating_profit()))
                .net_profit_col(determineColor(startupDTO.getNet_profit()))
                .build();
    }

    private static BigDecimal calculateInMillions(Long amount) {
        if (amount == null) {
            return new BigDecimal(0);
        }

        try {
            BigDecimal value = new BigDecimal(amount);
            BigDecimal thousand = new BigDecimal("1000");
            BigDecimal million = new BigDecimal("1000000");

            return value.multiply(thousand).divide(million, 2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            return new BigDecimal(0);
        }
    }

    private static String determineColor(Long amount) {
        if (amount == null) {
            return "BLACK";
        }
        return calculateInMillions(amount).longValue() >= 0 ? "BLACK" : "RED";
    }


    private static String formatNumber(Long amount) {
        if (amount == null) {
            return "0";
        }

        try {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String formatted = df.format(calculateInMillions(amount));

            if (formatted.endsWith(".00")) {
                return formatted.substring(0, formatted.length() - 3);
            }

            if (formatted.matches(".*\\.[0-9]0$")) {
                return formatted.substring(0, formatted.length() - 1);
            }
            return formatted;
        } catch (NumberFormatException e) {
            return "0";
        }
    }
}
