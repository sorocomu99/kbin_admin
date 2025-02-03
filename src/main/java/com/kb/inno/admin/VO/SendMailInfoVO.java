package com.kb.inno.admin.VO;

import com.kb.inno.common.CommonUtil;
import com.kb.inno.common.PropertiesValue;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMailInfoVO {
    String host;
    String port;
    String from;
    String pw;
    String smtpAuth;
    String smtpEnable;

    public static SendMailInfoVO getInfo() {
        if(CommonUtil.isProd(PropertiesValue.profilesActive)
                || CommonUtil.isDev(PropertiesValue.profilesActive)) {
            return SendMailInfoVO.builder()
                    .host("10.200.85.103")
                    .port("25")
                    .from("hwan@kbfg.com")
                    .pw("")
                    .smtpAuth("false")
                    .smtpEnable("false")
                    .build();
        }else{
            return SendMailInfoVO.builder()
                    .host("smtp.fmcity.com")
                    .port("587")
                    .from("hunhee@soroweb.co.kr")
                    .pw("1q2w3e4r!@")
                    .smtpAuth("true")
                    .smtpEnable("true")
                    .build();
        }
    }
}