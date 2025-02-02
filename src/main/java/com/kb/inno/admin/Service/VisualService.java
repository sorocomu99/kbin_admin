/**
 * 파일명     : VisualService.java
 * 화면명     : 메인 비주얼 관리
 * 설명       : 메인 비주얼 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.VisualDAO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.common.FileUploader;
import com.kb.inno.common.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisualService {

    // DAO 연결
    private final VisualDAO visualDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 메인 비주얼 리스트 조회
    public List<VisualDTO> selectList() {
        return visualDAO.selectList();
    }
    
    // 메인 비주얼 등록
    public int insert(VisualDTO visualDTO, int loginId) {
        // 로그인 한 아이디 세팅
        visualDTO.setFrst_rgtr(loginId);
        visualDTO.setLast_mdfr(loginId);

        // 파일을 등록했는 지 확인
        int fileYn = visualDTO.getFile_yn();

        if (fileYn == 1) {
            // 파일 저장
            MultipartFile file = visualDTO.getMain_file();

            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = visualDAO.insertFile(fileSave);

            // 게시글 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                visualDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        return visualDAO.insert(visualDTO);
    }

    // 메인 비주얼 상세 조회
    public VisualDTO select(int main_sn) {
        return visualDAO.select(main_sn);
    }

    // 메인 비주얼 수정
    public int update(VisualDTO visualDTO, int loginId) {
        // 파일을 새로 등록했는 지 확인
        int fileYn = visualDTO.getFile_yn();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        if(fileYn != 0) {

            // 0. 기존 파일 재조회
            VisualDTO basicFile = visualDAO.select(visualDTO.getMain_sn());

            // 1. 기존 경로에 있는 파일 삭제
            FileUploader fileUploader = new FileUploader();
            boolean removed = fileUploader.deleteFile(basicFile.getMain_path(), basicFile.getMain_file_name());

            // 2. 만약 경로에 파일이 지워졌다면 테이블에 있는 파일 삭제
            if(removed) {
                visualDAO.deleteFile(basicFile.getAtch_file_sn());
            }
            
            // 3. 새로운 파일 저장
            MultipartFile file = visualDTO.getMain_file();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = visualDAO.insertFile(fileSave);

            // 4. 새로운 파일 테이블에 저장
            if(fileSave != null && result == 1) {
                visualDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        // 최종 수정자 대입
        visualDTO.setLast_mdfr(loginId);

        return visualDAO.update(visualDTO);
    }

    // 메인 비주얼 삭제
    public void delete(int main_sn) {
        // 0. 메인 비주얼 상세 조회
        VisualDTO selectInfo = visualDAO.select(main_sn);

        // 1. 조회한 것에서 file_id 꺼내기
        int file_sn = selectInfo.getAtch_file_sn();

        // 2. fileId가 빈 값이 아니면(파일이 있으면)
        if(file_sn != 0) {
            // 경로 내 파일 삭제
            FileUploader fileUploader = new FileUploader();
            boolean removed = fileUploader.deleteFile(selectInfo.getMain_path(), selectInfo.getMain_file_name());

            // 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 파일 삭제
                visualDAO.deleteFile(file_sn);
            }
        }

        // 3. 메인 비주얼 삭제
        visualDAO.delete(main_sn);
    }

    // 메인 비주얼 리스트 조회 (미리보기용)
    public void selectListAll(Model model, VisualDTO visualDTO) {
    	int main_sn = visualDTO.getMain_sn();
        //List<VisualDTO> selectList = visualDAO.selectListAll(main_sn);
        List<VisualDTO> selectList = visualDAO.selectListAll(visualDTO);

        // 기존 파일 확인
        if(main_sn > 0) {
            VisualDTO main = visualDAO.select(main_sn);
            visualDTO.setMain_path(main.getMain_path());
            visualDTO.setMain_file_name(main.getMain_file_name());
        }

        // 파일 업로드
        MultipartFile files = visualDTO.getMain_file();

        if(files.getSize() > 0) {
            FileUploader fileUploader = new FileUploader();
            FileDTO file = fileUploader.insertFile(files, main_sn);
            visualDTO.setMain_file_name(file.getFile_nm());
            visualDTO.setMain_path(file.getFile_path());
        }

        // 파일 화면에 전달
        //model.addAttribute("visual", visualDTO);
        // 기존 데이터와 현재 처리중인 데이터를 병합하고 sort_no 기준 재정렬
        // 노출중인 경우 표출
        if(StringUtil.hasText(visualDTO.getExpsr_yn())
                && visualDTO.getExpsr_yn().equals("Y")) {
            selectList.add(visualDTO);

            Collections.sort(selectList, new Comparator<VisualDTO>() {
                @Override
                public int compare(VisualDTO v1, VisualDTO v2) {
                    return Integer.compare(v1.getSort_no(), v2.getSort_no());
                }
            });
        }

        model.addAttribute("selectList", selectList);
    }
}
