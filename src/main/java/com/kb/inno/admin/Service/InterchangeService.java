/**
 * 파일명     : InterchangeService.java
 * 화면명     : 글로벌 – 현지교류 관리
 * 설명       : 글로벌 – 현지교류 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.11
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.InterchangeDAO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.InterchangeDTO;
import com.kb.inno.admin.DTO.PlaceDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterchangeService {
    
    // DAO 연결
    private final InterchangeDAO interchangeDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 글로벌 – 현지교류 리스트 조회
    public List<InterchangeDTO> selectList() {
        return interchangeDAO.selectList();
    }

    // 글로벌 – 현지교류 등록
    public int insert(InterchangeDTO interchangeDTO, int loginId) {

        // 0. 로그인 한 아이디 세팅
        interchangeDTO.setFrst_rgtr(loginId);
        interchangeDTO.setLast_mdfr(loginId);

        // 1. 파일을 등록했는 지 확인
        int fileYn = interchangeDTO.getFile_yn1();

        // 2. 현지 교류 저장
        if(fileYn == 1) {
            // 파일 저장
            MultipartFile file = interchangeDTO.getInter_file1();

            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = interchangeDAO.insertFile(fileSave);

            // 게시글 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                interchangeDTO.setAtch_file_sn1(fileSave.getFile_sn());
            }
        }

        return interchangeDAO.insert(interchangeDTO);
    }

    // 글로벌 – 현지교류 상세 조회
    public InterchangeDTO select(int exch_sn) {
        return interchangeDAO.select(exch_sn);
    }

    // 글로벌 – 현지교류 수정
    public int update(InterchangeDTO interchangeDTO, int loginId) {
        int resultUpd = 1;
        // 파일을 새로 등록했는 지 확인
        int fileYn = interchangeDTO.getFile_yn1();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        if(fileYn != 0) {
            // 0. 기존 파일 재조회
            InterchangeDTO basicFile = interchangeDAO.select(interchangeDTO.getExch_sn());

            // 1. 기존 경로에 있는 파일 삭제
            FileUploader fileUploader = new FileUploader();
            boolean removed = fileUploader.deleteFile(basicFile.getInter_path1(), basicFile.getInter_file_name1());

            // 2. 만약 경로에 파일이 지워졌다면 테이블에 있는 파일 삭제
            if(removed) {
                interchangeDAO.deleteFile(basicFile.getAtch_file_sn1());
            }

            // 3. 새로운 파일 저장
            MultipartFile file = interchangeDTO.getInter_file1();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = interchangeDAO.insertFile(fileSave);

            // 4. 새로운 파일 테이블에 저장
            if(fileSave != null && result == 1) {
                interchangeDTO.setAtch_file_sn1(fileSave.getFile_sn());
            }
        }

        // 최종 수정자 대입
        interchangeDTO.setLast_mdfr(loginId);
        resultUpd = interchangeDAO.update(interchangeDTO);
        interchangeDAO.sortUpdate(interchangeDTO);
        //return interchangeDAO.update(interchangeDTO);
        return resultUpd;
    }

    // 글로벌 – 현지교류 삭제
    public void delete(int exch_sn) {
        // 0. 현지 교류 상세 조회
        InterchangeDTO selectInfo = interchangeDAO.select(exch_sn);

        int file_sn = selectInfo.getAtch_file_sn1();

        if(file_sn != 0) {
            // 경로 내 파일 삭제
            FileUploader fileUploader = new FileUploader();
            boolean removed = fileUploader.deleteFile(selectInfo.getInter_path1(), selectInfo.getInter_file_name1());

            if(removed) {
                interchangeDAO.deleteFile(file_sn);
            }
        }
        
        // 4. 현지 교류 삭제
        interchangeDAO.delete(exch_sn);
    }

    // 글로벌 – 현지교류 리스트 조회 (미리보기용)
    public void selectListAll(Model model, InterchangeDTO interchangeDTO) {
        int exch_sn = interchangeDTO.getExch_sn();
        //List<VisualDTO> selectList = interchangeDAO.selectListAll(exch_sn);
        List<VisualDTO> selectList = interchangeDAO.selectListAll(interchangeDTO);
        model.addAttribute("selectList", selectList);

        // 기존 파일 확인
        if(exch_sn > 0) {
            InterchangeDTO main = interchangeDAO.select(exch_sn);
            interchangeDTO.setInter_path1(main.getInter_path1());
            interchangeDTO.setInter_file_name1(main.getInter_file_name1());
        }

        // 파일 업로드
        MultipartFile files = interchangeDTO.getInter_file1();

        if(files.getSize() > 0) {
            FileUploader fileUploader = new FileUploader();
            FileDTO file = fileUploader.insertFile(files, exch_sn);
            interchangeDTO.setInter_file_name1(file.getFile_nm());
            interchangeDTO.setInter_path1(file.getFile_path());
        }

        // 파일 화면에 전달
        model.addAttribute("interchange", interchangeDTO);
    }
}
