/**
 * 파일명     : AdminLoginController.java
 * 화면명     : 로그인
 * 설명       : 관리자 화면 접속시 로그인 및 세션 생성
 *              로그아웃 세션 삭제
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.VO.SendMailInfoVO;
import com.kb.inno.common.CommonUtil;
import com.kb.inno.common.PropertiesValue;
import com.kb.inno.common.Sha256;
import com.kb.inno.admin.DTO.AdminLoginDTO;
import com.kb.inno.admin.Service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

@Controller
public class AdminLoginController {

    @Autowired
    AdminLoginService adminLoginService;

    /**
     * 로그인 페이지 이동
     */
    @RequestMapping("/")
    public String login() {
        return "login/login";
    }

    /**
     * 로그인 처리
     * @param params
     * @param request  세션정보를 생성하기 위한 param
     * @return HashMap result
     * 로직 순서
     * 1. 입력받은 아이디로 관리자 정보가 존재하는지 DB를 조회한다.
     *   1.1. DB에 해당 정보가 없으면 fail 리턴
     *   1.2. DB에 해당 정보가 존재하면 입력받은 아이디와 DB에 아이디를 비교 틀리면 fail 리턴
     * 2. 입력받은 비밀번호를 sha256으로 암호화 한다.
     *   2.1. DB에서 가져온 비밀번호와 암호화한 입력받은 비밀번호를 비교해서 틀리면 fail 리턴
     * 3. 입력받은 비밀번호와 DB의 비밀번호가 같으면 세션 생성
     *   3.1. 세션 생성 후 관리자 정보(일련번호, 아이디, 이름, 이메일)를 세션에 생성
     * 4. 정상 로그인 처리 success 리턴
     */
    @PostMapping(value = "/loginProc")
    @ResponseBody
    public HashMap<String, Object> loginProc(@RequestParam Map<String, Object> params, HttpServletRequest request) {
    	// System.out.println("================여기1");
        HashMap<String, Object> resultMap = new HashMap<>();

        //입력받은 아이디와 패스워드 세팅
        String mngrId   = (String)params.get("mngr_id");
        String mngrPswd = (String)params.get("mngr_pswd");

        // System.out.println("================여기2");

        //아이디로 관리자 정보 조회
        AdminLoginDTO adminLoginDTO = adminLoginService.adminSelectOne(mngrId);

        // System.out.println("================여기3");

        //관리자 정보가 없으면 리턴
        if (adminLoginDTO == null) {
            resultMap.put("success", "fail");
            resultMap.put("message", "관리자로 등록되어 있지 않습니다.");

            return resultMap;
        }
        
        //DB에서 가져온 정보를 세팅
        int dbMngrSn = adminLoginDTO.getMngr_sn();         //DB에서 가져온 일련번호
        String dbMngrId = adminLoginDTO.getMngr_id();      //DB에서 가져온 아이디
        String dbMngrPswd = adminLoginDTO.getMngr_pswd();  //DB에서 가져온 비밀번호
        String dbMngrNm = adminLoginDTO.getMngr_nm();      //DB에서 가져온 이름
        String dbMngrEml = adminLoginDTO.getMngr_eml();    //DB에서 가져온 이메일 주소

        // System.out.println("================여기4");

        //DB에서 가져온 아이디와 입력받은 아이디를 비교
        if (!dbMngrId.equals(mngrId)) {
            resultMap.put("success", "fail");
            resultMap.put("message", "입력하신 아이디로 관리자 정보가 없습니다.");

            return resultMap;
        }

        // System.out.println("================여기5");

        //입력받은 비밀번호를 암호화 해서 DB에서 가져온 비밀번호와 비교
        try {
            String shaPw = Sha256.encrypt(mngrPswd);  //입력받은 비밀번호 암호화

            // System.out.println("================여기6");

            //입력한 비밀번호와 DB의 비밀번호가 틀리면 실패 리턴
            if (!shaPw.equals(dbMngrPswd)) {
                resultMap.put("success", "fail");
                resultMap.put("message", "입력하신 비밀번호가 올바르지 않습니다.");
                return resultMap;
            }
        } catch (Exception e) {
            // System.out.println("================여기7");
            resultMap.put("success", "fail");
            resultMap.put("message", "비밀번호 검증 중 시스템 오류가 발생했습니다.");

            return resultMap;
        }

        // System.out.println("================여기8");

        //세션 생성
        HttpSession session = request.getSession();
        //세션에 관리자 정보 보관
        session.setAttribute("mngrSn", dbMngrSn);    //일련번호
        session.setAttribute("mngrId", dbMngrId);    //아이디
        session.setAttribute("mngrNm", dbMngrNm);    //이름
        session.setAttribute("mngrEml", dbMngrEml);  //이메일

        session.setMaxInactiveInterval(3600);  //세션타임 1시간

        // System.out.println("================여기9");

        //정상적으로 로그인 처리 완료되면 리턴
        resultMap.put("success", "success");
        resultMap.put("message", "환영합니다. "+ dbMngrNm + " 님");

        // System.out.println("================여기10");

        return resultMap;
    }

    @RequestMapping("/findPw")
    @ResponseBody
    public HashMap<String, Object> findPw(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
        //adminLoginDTO.setFind_id((String)params.get("pwdMailId"));
        //adminLoginDTO.setFind_email((String)params.get("pwdMailAddr"));

        //임시비밀번호 생성
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        String randomPw = sb.toString();

        try {
            int chkAdmCnt = adminLoginService.adminInfo((String) params.get("pwdMailId"), (String) params.get("pwdMailAddr"));

            if(chkAdmCnt > 0) {
                String shaPw = Sha256.encrypt(randomPw);  //입력받은 비밀번호 암호화
                int pwSave = adminLoginService.findPw((String)params.get("pwdMailId"), shaPw);
                if (pwSave != 1) {
                    resultMap.put("success", "fail");
                    resultMap.put("message", "임시 비밀번호 발급 중 오류가 발생했습니다.");

                    return resultMap;
                }
            }else{
                resultMap.put("success", "fail");
                resultMap.put("message", "일치하는 정보가 없습니다.");

                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("success", "fail");
            resultMap.put("message", "비밀번호 검증 중 시스템 오류가 발생했습니다.");

            return resultMap;
        }

        String title = "KB Innovation Hub 입니다.";
        String content = "임시 비밀번호 입니다.\n접속 후 반드시 비밀번호를 변경해서 사용해 주세요.\n임시비밀번호는 "+randomPw+" 입니다.";

        // SMTP 정보
        SendMailInfoVO mailInfo = SendMailInfoVO.getInfo();

        String to = (String)params.get("pwdMailAddr");

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailInfo.getHost());
        props.setProperty("mail.smtp.port", mailInfo.getPort());
        props.setProperty("mail.smtp.auth", mailInfo.getSmtpAuth());
        //tls1.2 이슈로 false 처리
        props.setProperty("mail.smtp.starttls.enable", "false");

        Session session; //Session.getDefaultInstance(props);

        if(CommonUtil.isProd(PropertiesValue.profilesActive)) {
            session = Session.getDefaultInstance(props);
        }else{
            final String finalFrom = mailInfo.getFrom();
            final String finalPw = mailInfo.getPw();
            session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(finalFrom, finalPw);
                }
            });
        }

        try {
            // 메일 메시지 생성
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailInfo.getFrom())); // 발신자 이메일 주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 수신자 이메일 주소
            message.setSubject(title); // 이메일 제목
            message.setText(content); // 이메일 내용

            // 메일 보내기
            Transport.send(message);
            resultMap.put("success", "success");
            resultMap.put("message", "입력하신 이메일로 임시 비밀번호를 보냈습니다.");
        } catch (MessagingException e) {
            e.printStackTrace();
            resultMap.put("success", "fail");
            resultMap.put("message", "메일 발송에 실패 하였습니다.");

            return resultMap;
        }

        return resultMap;
    }

    /**
     * 로그아웃
     * @param request
     * @return String 관리자 로그인 페이지
     * 생성된 세션을 해제 시킨다.
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        //현재 세션이 있는지 확인한다.
        HttpSession session = request.getSession(false);
        //세션가져오기
        //session.getAttribute("mngrSn");

        //세션이 존재하면 세션 소멸
        if (session != null) {
            session.invalidate();
        }

        //로그인 페이지로 이동
        return "redirect:/";
        //return "login/login";
    }
}
