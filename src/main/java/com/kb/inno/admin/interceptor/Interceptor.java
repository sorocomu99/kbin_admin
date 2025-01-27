/**
 * 파일명     : interceptor.java
 * 화면명     : 없음
 * 설명       : 관리자 화면 접속시 URL 체크 로그인 되어 있지 않으면 로그인 페이지로 이동
 * 최초개발일 : 2024.10.23
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.interceptor;

import com.kb.inno.common.CommonUtil;
import com.kb.inno.common.PropertiesValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("===============================================");
        log.info("==================== BEGIN ====================");
        log.info("Request URI ===> " + request.getRequestURI());

        //세션 정보 가져오기
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        //세션 정보가 없으면 로그인 페이지로 이동
        if (session == null) {
            //TODO: author krh 2025-01-27, 월, 15:56 : 테스트&디버깅 완료후 아래 주석 적용
        //if (session == null || session.getAttribute("mngrId") == null) {
            if(CommonUtil.isProd(PropertiesValue.profilesActive)) {
                //TODO: author krh 2025-01-26, 일, 21:10 : 정식 오픈하면 별도 도메인이 있는지 확인 ex) https://kbinnovationhub_adm.com
                // dev 도메인도 같이 확인
                // real path check
                response.sendRedirect("/kbinnovationhub_adm");
            }else{
                response.sendRedirect("/kbinnovationhub_devadm");
            }

            return false;
        }

        return true;
        //return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("==================== END ======================");
        log.info("===============================================");
        //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
