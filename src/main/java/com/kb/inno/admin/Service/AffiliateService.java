/**
 * 파일명     : AffiliateService.java
 * 화면명     : 국내 프로그램 - 제휴 사례 관리
 * 설명       : 국내 프로그램 - 제휴 사례 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.31
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.AffiliateDAO;
import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AffiliateService {

    // DAO 연결
    private final AffiliateDAO affiliateDAO;

    /**
     * 국내 프로그램 - 제휴 사례 리스트 조회
     * @return
     */
    public List<AffiliateDTO> selectList() {
        return affiliateDAO.selectList();
    }

    /**
     * 국내 프로그램 - 제휴 사례 등록
     * @param affiliateDTO
     * @param loginId
     * @return
     */
    public int insert(AffiliateDTO affiliateDTO, int loginId) {
        // 로그인 한 사람 대입
        affiliateDTO.setFrst_rgtr(loginId);
        affiliateDTO.setLast_mdfr(loginId);

        // 파일을 등록했는 지 확인
        int fileYn = affiliateDTO.getFile_yn();

        if(fileYn == 1) {
            // 파일 저장
            MultipartFile file = affiliateDTO.getAffiliate_file();

            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = affiliateDAO.insertFile(fileSave);

            // 게시글 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                affiliateDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        return affiliateDAO.insert(affiliateDTO);
    }

    /**
     * 국내 프로그램 - 제휴 사례 상세 조회
     * @param affiliate_sn
     * @return
     */
    public AffiliateDTO select(int affiliate_sn) {
        return affiliateDAO.select(affiliate_sn);
    }

    /**
     * 국내 프로그램 - 제휴 사례 수정
     * @param affiliateDTO
     * @param loginId
     * @return
     */
    public int update(AffiliateDTO affiliateDTO, int loginId) {
        int resultUpd = 1;
        // 파일을 새로 등록했는 지 확인
        int fileYn = affiliateDTO.getFile_yn();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        if(fileYn != 0) {
            // 0. 기존 파일 재조회
            AffiliateDTO basicFile = affiliateDAO.select(affiliateDTO.getAffiliate_sn());

            // 1. 기존 경로에 있는 파일 삭제
            FileUploader fileUploader = new FileUploader();
            boolean removed = fileUploader.deleteFile(basicFile.getAffiliate_path(), basicFile.getAffiliate_file_name());

            // 2. 만약 경로에 파일이 지워졌다면 테이블에 있는 파일 삭제
            if(removed) {
                affiliateDAO.deleteFile(basicFile.getAtch_file_sn());
            }

            // 3. 새로운 파일 경로에 저장
            MultipartFile file = affiliateDTO.getAffiliate_file();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = affiliateDAO.insertFile(fileSave);

            // 4. 새로운 파일 테이블에 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                affiliateDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        // 최종 수정자 대입
        affiliateDTO.setLast_mdfr(loginId);
        resultUpd = affiliateDAO.update(affiliateDTO);
        // 정렬 알고리즘 수정 및 전체 case 일괄 적용 필요
        // affiliateDAO.sortUpdate(affiliateDTO);
        return resultUpd;
    }

    /**
     * 국내 프로그램 - 제휴 사례 삭제
     * @param affiliate_sn
     */
    public void delete(int affiliate_sn) {
        // 0. 국내 프로그램 - 제휴 사례 상세 조회
        AffiliateDTO selectInfo = affiliateDAO.select(affiliate_sn);

        // 1. 조회한 것에서 file_id 꺼내기
        int file_sn = selectInfo.getAtch_file_sn();

        // 2. fileId가 빈 값이 아니면(파일이 있으면)
        if(file_sn != 0) {
            // 경로 내 파일 삭제
            FileUploader fileUploader = new FileUploader();
            boolean removed = fileUploader.deleteFile(selectInfo.getAffiliate_path(), selectInfo.getAffiliate_file_name());

            // 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 파일 삭제
                affiliateDAO.deleteFile(file_sn);
            }
        }

        // 3. 국내 프로그램 - 제휴 사례 삭제
        affiliateDAO.delete(affiliate_sn);
    }

    /**
     * 국내 프로그램 - 제휴 사례 리스트 조회 (미리보기용)
     * @param model
     * @param affiliateDTO
     */
    public void selectListAll(Model model, AffiliateDTO affiliateDTO) {
        int affiliate_sn = affiliateDTO.getAffiliate_sn();
        //List<VisualDTO> selectList = affiliateDAO.selectListAll(affiliate_sn);
        List<VisualDTO> selectList = affiliateDAO.selectListAll(affiliateDTO);
        model.addAttribute("selectList", selectList);

        // 기존 파일 확인
        if(affiliate_sn > 0) {
            AffiliateDTO affiliate = affiliateDAO.select(affiliate_sn);
            affiliateDTO.setAffiliate_path(affiliate.getAffiliate_path());
            affiliateDTO.setAffiliate_file_name(affiliate.getAffiliate_file_name());
        }

        // 파일 업로드
        MultipartFile files = affiliateDTO.getAffiliate_file();

        if(files.getSize() > 0) {
            FileUploader fileUploader = new FileUploader();
            FileDTO file = fileUploader.insertFile(files, affiliate_sn);
            affiliateDTO.setAffiliate_file_name(file.getFile_nm());
            affiliateDTO.setAffiliate_path(file.getFile_path());
        }

        // 파일 화면에 전달
        model.addAttribute("affiliate", affiliateDTO);
    }
}
