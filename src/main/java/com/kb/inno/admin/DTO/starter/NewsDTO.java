/**
 * 파일명     : NewsDTO.java
 * 화면명     : 스케줄 서비스 (화면 없음)
 * 설명       : API 뉴스 정보 변수
 * 관련 DB    : KB_API_NEWS_INFO
 * 최초개발일 : 2024.11.15
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.DTO.starter;

import com.kb.inno.admin.DTO.StartupDTO;
import com.kb.inno.common.StringUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class NewsDTO implements Serializable {
	
	//private static final long serialVersionUID = 1L;
    
	private String ent_cd;     //기업 코드
    private String news_id;    //뉴스 아이디
    private String provider;   //제공자
    private String news_ttl;   //기사 제목
    private String news_link;  //기사 링크
    private String thumb_url;  //썸네일 이미지 URL

    public static NewsDTO make(StartupDTO startupDTO) {
        return NewsDTO.builder()
                .news_id(startupDTO.getNews_id())
                .provider(StringUtil.hasText(startupDTO.getProvider()) ? startupDTO.getProvider().replace("@@RP@@", ",") : "")
                .news_ttl(StringUtil.hasText(startupDTO.getNews_ttl()) ? startupDTO.getNews_ttl().replace("@@RP@@", ",") : "")
                .news_link(formatUrl(startupDTO.getNews_link()))
                .thumb_url(formatUrl(startupDTO.getThumb_url()))
                .build();
    }

    private static String formatUrl(String url) {
        if (!StringUtil.hasText(url)) {
            return null;
        }

        String upperHmpg = url.toUpperCase();
        if (upperHmpg.startsWith("HTTP")) {
            return url;
        } else {
            return "https://" + url;
        }
    }
}
