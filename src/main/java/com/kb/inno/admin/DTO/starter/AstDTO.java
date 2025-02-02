/**
 * 파일명     : AstDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 자산(재무상태) 정보 변수
 * 관련 DB    : KB_API_AST_INFO
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
public class AstDTO implements Serializable {

    //private static final long serialVersionUID = 1L;

    private String ent_cd;           //기업 코드
    private String yr;               //년도
    private Long current_assets;      //유동자산(단위 : 천원)
    private Long non_current_assets;  //비유동자산(단위 : 천원)
    private Long ast_gramt;           //자산총계(단위 : 천원)
    private Long debt_gramt;          //부채총계(단위 : 천원)
    private Long cptl;                //자본금(단위 : 천원)
    private Long cptl_gramt;          //자본총계(단위 : 천원)
    private String current_assets_str;
    private String non_current_assets_str;
    private String ast_gramt_str;
    private String debt_gramt_str;
    private String cptl_str;
    private String cptl_gramt_str;
    private String current_assets_col;
    private String non_current_assets_col;
    private String ast_gramt_col;
    private String debt_gramt_col;
    private String cptl_col;
    private String cptl_gramt_col;

    public static AstDTO make(StartupDTO startupDTO) {
        return AstDTO.builder()
                .yr(startupDTO.getAst_yr())
                .current_assets(calculateInMillions(startupDTO.getCurrent_assets()).longValue())
                .non_current_assets(calculateInMillions(startupDTO.getNon_current_assets()).longValue())
                .ast_gramt(calculateInMillions(startupDTO.getAst_gramt()).longValue())
                .debt_gramt(calculateInMillions(startupDTO.getDebt_gramt()).longValue())
                .cptl(calculateInMillions(startupDTO.getCptl()).longValue())
                .cptl_gramt(calculateInMillions(startupDTO.getCptl_gramt()).longValue())
                .current_assets_str(formatNumber(startupDTO.getCurrent_assets()))
                .non_current_assets_str(formatNumber(startupDTO.getNon_current_assets()))
                .ast_gramt_str(formatNumber(startupDTO.getAst_gramt()))
                .debt_gramt_str(formatNumber(startupDTO.getDebt_gramt()))
                .cptl_str(formatNumber(startupDTO.getCptl()))
                .cptl_gramt_str(formatNumber(startupDTO.getCptl_gramt()))
                .current_assets_col(determineColor(startupDTO.getCurrent_assets()))
                .non_current_assets_col(determineColor(startupDTO.getNon_current_assets()))
                .ast_gramt_col(determineColor(startupDTO.getAst_gramt()))
                .debt_gramt_col(determineColor(startupDTO.getDebt_gramt()))
                .cptl_col(determineColor(startupDTO.getCptl()))
                .cptl_gramt_col(determineColor(startupDTO.getCptl_gramt()))
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
