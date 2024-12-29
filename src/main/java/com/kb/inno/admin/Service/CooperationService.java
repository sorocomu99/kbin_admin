/**
 * 파일명     : CooperationService.java
 * 화면명     : 협력 기관 관리
 * 설명       : 협력 기관 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.CooperationDAO;
import com.kb.inno.admin.DTO.CooperationDTO;
import com.kb.inno.admin.DTO.FileDTO;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CooperationService {

    // DAO 연결
    private final CooperationDAO cooperationDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    /**
     * @return
     * 협력 기관 리스트 조회
     */
    public List<CooperationDTO> selectList() {
         return cooperationDAO.selectList();
    }

    // 파일 저장
    public FileDTO insertFile(CooperationDTO cooperationDTO, int loginId) {
        // 파일 꺼내기
        MultipartFile file = cooperationDTO.getCoope_file();

        // 오리지널 파일 이름
        String originalFilename = file.getOriginalFilename();

        // 확장자 꺼내기
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 파일 이름 설정
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // 파일 경로 설정
        Path path = Paths.get(System.getProperty("user.dir"), staticPath);
        String savePath = path + "\\upload\\";

        // 파일 디렉토리 생성(없으면)
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            file.transferTo(new File(savePath, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 파일 사이즈 구하기
        int bytes = (int) file.getSize();

        // 빈 DTO 생성
        FileDTO fileDTO = new FileDTO();

        // DTO에 객체 담기
        fileDTO.setFile_nm(fileName);
        fileDTO.setOrgnl_file_nm(originalFilename);
        fileDTO.setFile_extn(fileExtension);
        fileDTO.setFile_path("\\upload\\");
        fileDTO.setFile_sz(bytes);

        // 파일 DTO에 로그인 한 사람 추가
        fileDTO.setFrst_rgtr(loginId);
        fileDTO.setLast_mdfr(loginId);

        // 파일 테이블 저장
        int result = cooperationDAO.insertFile(fileDTO);

        // 결과가 있으면 fileDTO return
        if(result == 1) {
            return fileDTO;
        }

        return null;
    }

    // 협력 기관 등록
    public int insert(CooperationDTO cooperationDTO, int loginId) {
        // 로그인 한 사람 대입
        cooperationDTO.setFrst_rgtr(loginId);
        cooperationDTO.setLast_mdfr(loginId);

        // 파일을 등록했는 지 확인
        int fileYn = cooperationDTO.getFile_yn();

        if (fileYn == 1) {
            // 파일 저장
             MultipartFile file = cooperationDTO.getCoope_file();

            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            int result = cooperationDAO.insertFile(fileSave);

            // 게시글 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                cooperationDTO.setAtch_file_id(fileSave.getFile_sn());
            }
        }

        return cooperationDAO.insert(cooperationDTO);
    }

    // 협력 기관 상세 조회
    public CooperationDTO select(int coope_sn) {
        return cooperationDAO.select(coope_sn);
    }

    // 협력 기관 수정
    public int update(CooperationDTO cooperationDTO, int loginId) {
        int resultUpd = 1;
        // 파일을 새로 등록했는 지 확인
        int fileYn = cooperationDTO.getFile_yn();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
         if(fileYn == 1) {
            // 0. 기존 파일 재조회
            CooperationDTO basicFile = cooperationDAO.select(cooperationDTO.getCoope_sn());

            // 1. 기존 경로에 있는 파일 삭제
            FileUploader fileUploader = new FileUploader();
            boolean removed = fileUploader.deleteFile(basicFile.getCoope_path(), basicFile.getCoope_file_name());

            // 2. 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 테이블에 있는 파일 삭제
                cooperationDAO.deleteFile(basicFile.getAtch_file_id());
            }

            // 3. 새로운 파일 경로에 저장
            MultipartFile file = cooperationDTO.getCoope_file();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            int result = cooperationDAO.insertFile(fileSave);

            // 4. 새로운 파일 테이블에 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                cooperationDTO.setAtch_file_id(fileSave.getFile_sn());
            }
        }

        // 최종 수정자 대입
        cooperationDTO.setLast_mdfr(loginId);
        resultUpd = cooperationDAO.update(cooperationDTO);
        cooperationDAO.sortUpdate(cooperationDTO);
        //return cooperationDAO.update(cooperationDTO);
        return resultUpd;
    }

    // 협력 기관 삭제
    public void delete(int coope_sn) {
        // 0. 협력 기관 상세 조회
        CooperationDTO selectInfo = cooperationDAO.select(coope_sn);

        // 1. 조회한 것에서 file_id 꺼내기
        int fileId = selectInfo.getAtch_file_id();

        // 2. fileId가 빈 값이 아니면(파일이 있으면)
        if(fileId != 0) {
            // 경로 내 파일 삭제
            FileUploader fileUploader = new FileUploader();
            boolean removed = fileUploader.deleteFile(selectInfo.getCoope_path(), selectInfo.getCoope_file_name());

            // 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 파일 삭제
                cooperationDAO.deleteFile(fileId);
            }
        }

        // 협력 기관 삭제
        cooperationDAO.delete(coope_sn);
    }

    // 협력 기관 리스트 조회 (미리보기용)
    public void selectListAll(Model model, CooperationDTO cooperationDTO) {
        int coope_sn = cooperationDTO.getCoope_sn();
        //List<VisualDTO> selectList = cooperationDAO.selectListAll(coope_sn);
        List<VisualDTO> selectList = cooperationDAO.selectListAll(cooperationDTO);
        model.addAttribute("selectList", selectList);

        // 기존 파일 확인
        if(coope_sn > 0) {
            CooperationDTO main = cooperationDAO.select(coope_sn);
            cooperationDTO.setCoope_path(main.getCoope_path());
            cooperationDTO.setCoope_file_name(main.getCoope_file_name());
        }

        // 파일 업로드
        MultipartFile files = cooperationDTO.getCoope_file();

        if(files.getSize() > 0) {
            FileUploader fileUploader = new FileUploader();
            FileDTO file = fileUploader.insertFile(files, coope_sn);
            cooperationDTO.setCoope_file_name(file.getFile_nm());
            cooperationDTO.setCoope_path(file.getFile_path());
        }

        // 파일 화면에 전달
        model.addAttribute("cooperation", cooperationDTO);
    }
}
