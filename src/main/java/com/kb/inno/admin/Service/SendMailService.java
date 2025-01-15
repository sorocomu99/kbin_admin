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

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SendMailService {
    // DAO 연결
    private final SendMailDAO sendMailDAO;

    /**
     * 설문 조회
     * @param menuId
     * @param page
     * @param model
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

        List<SendMailDTO> selectListMail = sendMailDAO.selectList(sendMailDTO);
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectListMail", selectListMail);
        model.addAttribute("menuId", menuId);
        model.addAttribute("type", sendMailDTO.getType());
        model.addAttribute("keyword", sendMailDTO.getKeyword());
    }

    public void selectMailDetail(String send_ymd, int send_mail_sn, Model model) {
        SendMailDTO sendMailDTO = new SendMailDTO();
        sendMailDTO.setSend_ymd(send_ymd);
        sendMailDTO.setSend_mail_sn(send_mail_sn);

        //SendMailDTO selectDetailMail = sendMailDAO.selectDetailMail(sendMailDTO);

        model.addAttribute("send_ymd", send_ymd);
        model.addAttribute("send_mail_sn", send_mail_sn);
        //model.addAttribute("selectDetailMail", selectDetailMail);
    }
}
