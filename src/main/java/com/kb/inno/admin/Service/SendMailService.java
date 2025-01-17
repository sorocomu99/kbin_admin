/**
 * 파일명     : SurveyService.java
 * 화면명     : 설문 관리
 * 설명       : 설문 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2025.01.06
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.SendMailDAO;
import com.kb.inno.admin.DTO.SendMailDTO;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SendMailService {
    // DAO 연결
    private final SendMailDAO sendMailDAO;

    /**
     * 보낸 메일함 조회
     * @param menuId
     * @param model
     * @param type
     * @param keyword
     * @param page
     */
    public void selectListMail(int menuId, Model model, String type, String keyword, int page) {
        SendMailDTO sendMailDTO = new SendMailDTO();

        sendMailDTO.setType(type);
        sendMailDTO.setKeyword(keyword);

        int allCount = sendMailDAO.selectPageCount();

        //한 페이지당 글 갯수
        int pageLetter = 10;

        //전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        //나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            //더하기
            repeat += 1;
        }

        //repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        //만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        //만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        sendMailDTO.setEnd(end);
        int start = end + 1 - pageLetter;
        sendMailDTO.setStart(start);

        List<SendMailDTO> selectListMail = sendMailDAO.selectListMail(sendMailDTO);
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectListMail", selectListMail);
        model.addAttribute("menuId", menuId);
        model.addAttribute("type", sendMailDTO.getType());
        model.addAttribute("keyword", sendMailDTO.getKeyword());
    }

    /**
     * 보낸 메일함 상세 조회
     * @param send_ymd
     * @param send_mail_sn
     * @param model
     */
    public void selectMailDetail(String send_ymd, int send_mail_sn, Model model) {
        SendMailDTO sendMailDTO = new SendMailDTO();
        sendMailDTO.setSend_ymd(send_ymd);
        sendMailDTO.setSend_mail_sn(send_mail_sn);

        SendMailDTO selectDetailMail = sendMailDAO.selectDetailMail(sendMailDTO);
        SendMailDTO selectMailSendName = sendMailDAO.selectMailSendName(sendMailDTO);

        model.addAttribute("send_ymd", send_ymd);
        model.addAttribute("send_mail_sn", send_mail_sn);
        model.addAttribute("selectDetailMail", selectDetailMail);
        model.addAttribute("selectMailSendName", selectMailSendName);
    }

    /**
     * 보낸 메일함 삭제(단건)
     * @param sendMailDTO
     * @return
     */
    public int sendMailDel(SendMailDTO sendMailDTO) {
        //이력 삭제
        sendMailDAO.deleteDetail(sendMailDTO);
        //마스터 삭제
        int result = sendMailDAO.deleteOne(sendMailDTO);
        return result;
    }

    public int chkMailDel(String chkDel) {
        int result = 0;
        List<String> checkDelList = new ArrayList<>();
        SendMailDTO sendMailDTO = new SendMailDTO();

        if (chkDel == null || chkDel.isEmpty()) {
        } else {
            checkDelList = Arrays.asList(chkDel.split(","));
        }

        String sendYmd = "";
        int sendMailSn = 0;
        int length = 0;
        for (int i = 0; i < checkDelList.size(); i++) {
            length = checkDelList.get(i).length();
            sendYmd = checkDelList.get(i).substring(0, 8);
            sendMailSn = Integer.parseInt(checkDelList.get(i).substring(9, length));
            sendMailDTO.setSend_ymd(sendYmd);
            sendMailDTO.setSend_mail_sn(sendMailSn);

            sendMailDAO.deleteDetail(sendMailDTO);
            //마스터 삭제
            result = sendMailDAO.deleteOne(sendMailDTO);
        }

        return result;
    }
}
