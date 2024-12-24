/**
 * 파일명     : PlaceService.java
 * 화면명     : 육성공간 관리
 * 설명       : 육성공간 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.06
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.PlaceDAO;
import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.DTO.FileDTO;
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
public class PlaceService {

    // DAO 연결
    private final PlaceDAO placeDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 육성공간 리스트 조회
    public List<PlaceDTO> selectList() {
        return placeDAO.selectList();
    }

    // 육성공간 등록
    public int insert(PlaceDTO placeDTO, int loginId) {
        // 파일을 등록했는 지 확인
        int fileYn1 = placeDTO.getFile_yn1();
        int fileYn2 = placeDTO.getFile_yn2();
        int fileYn3 = placeDTO.getFile_yn3();

        // 파일 꺼내기
        List<MultipartFile> files = new ArrayList<>();

        if(fileYn1 == 1) {
            files.add(placeDTO.getPlc_file1());
        }

        if(fileYn2 == 1) {
            files.add(placeDTO.getPlc_file2());
        }

        if(fileYn3 == 1) {
            files.add(placeDTO.getPlc_file3());
        }

        // 1. 파일 디렉토리에 저장
        FileUploader fileUploader = new FileUploader();
        List<FileDTO> fileSave = fileUploader.insertFiles(files, loginId);

        if(fileSave.isEmpty() || fileSave != null) {
            for(int i = 0; i < fileSave.size(); i++) {
                // 파일이 null 이면 패스, 아니면 파일 저장 실행
                if (i == 0 && fileSave.get(i) != null) {
                    // 2. 파일 테이블에 저장
                    placeDAO.insertFile(fileSave.get(i));
                    placeDTO.setAtch_file_sn1(fileSave.get(i).getFile_sn());
                }
                if (i == 1 && fileSave.get(i) != null) {
                    placeDAO.insertFile(fileSave.get(i));
                    placeDTO.setAtch_file_sn2(fileSave.get(i).getFile_sn());
                }
                if (i == 2 && fileSave.get(i) != null) {
                    placeDAO.insertFile(fileSave.get(i));
                    placeDTO.setAtch_file_sn3(fileSave.get(i).getFile_sn());
                }
            }
        }

        return placeDAO.insert(placeDTO);
    }

    // 육성공간 상세 조회
    public PlaceDTO select(int plc_sn) {
        return placeDAO.select(plc_sn);
    }

    // 육성공간 수정
    public int update(PlaceDTO placeDTO, int loginId) {
        // 파일을 새로 등록했는 지 확인
        List<Integer> fileYnList = new ArrayList<>();
        
        // 파일 등록 여부를 리스트에 담기
        fileYnList.add(placeDTO.getFile_yn1());
        fileYnList.add(placeDTO.getFile_yn2());
        fileYnList.add(placeDTO.getFile_yn3());

        // 파일 여부
        List<Integer> fileSnList = new ArrayList<>();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        for(int i = 0; i < fileYnList.size(); i++) {
            // 새로 등록한 파일이 아니면
            if(fileYnList.get(i) == 0) {
                if(i == 0) {
                    fileSnList.add(placeDTO.getAtch_file_sn1());
                }

                if(i == 1) {
                    fileSnList.add(placeDTO.getAtch_file_sn2());
                }

                if(i == 2) {
                    fileSnList.add(placeDTO.getAtch_file_sn3());
                }
            } else {
                // 0. 기존 파일 재조회
                PlaceDTO basicFile = placeDAO.select(placeDTO.getPlc_sn());

                // 1. 기존 경로에 있는 파일 삭제
                // 경로 설정
                Path path = Paths.get("D:\\").toAbsolutePath().normalize();

                int file_sn = 0;
                File deleteFile = null;
                
                // 만약 첫 번째라면
                if(i == 0) {
                    deleteFile = new File(path + basicFile.getPlc_path1() + basicFile.getPlc_file_name1());
                    file_sn = basicFile.getAtch_file_sn1();
                }

                // 만약 두 번째라면
                if(i == 1) {
                    deleteFile = new File(path + basicFile.getPlc_path2() + basicFile.getPlc_file_name2());
                    file_sn = basicFile.getAtch_file_sn2();
                }

                // 만약 세 번째라면
                if(i == 2) {
                    deleteFile = new File(path + basicFile.getPlc_path3() + basicFile.getPlc_file_name3());
                    file_sn = basicFile.getAtch_file_sn3();
                }
                
                // 파일 삭제
                boolean removed = deleteFile != null && deleteFile.delete();

                // 2. 만약 경로에 파일이 지워졌다면
                if(removed) {
                    // 테이블에 있는 파일 삭제
                    placeDAO.deleteFile(file_sn);
                }

                // 3. 파일 꺼내기
                List<MultipartFile> files = new ArrayList<>();

                MultipartFile file1 = placeDTO.getPlc_file1();
                MultipartFile file2 = placeDTO.getPlc_file2();
                MultipartFile file3 = placeDTO.getPlc_file3();

                if(file1.getSize() > 0) {
                    files.add(file1);
                }

                if(file2.getSize() > 0) {
                    files.add(file2);
                }

                if(file3.getSize() > 0) {
                    files.add(file3);
                }

                // 4. 파일 디렉토리에 저장
                FileUploader fileUploader = new FileUploader();
                List<FileDTO> fileSave = fileUploader.insertFiles(files, loginId);
                
                // 5. 저장된 파일의 PK 값을 SN LIST에 추가
                if(!fileSave.isEmpty() || fileSave != null) {
                    // 파일 일련 번호 대입
                    for (int j = 0; j < fileSave.size(); j++) {
                        if (j == 0 && fileSave.get(j) != null) {
                            // 2. 파일 테이블에 저장
                            placeDAO.insertFile(fileSave.get(j));
                            fileSnList.add(fileSave.get(j).getFile_sn());
                        }
                        if (j == 1 && fileSave.get(j) != null) {
                            placeDAO.insertFile(fileSave.get(j));
                            fileSnList.add(fileSave.get(j).getFile_sn());
                        }
                        if (j == 2 && fileSave.get(j) != null) {
                            placeDAO.insertFile(fileSave.get(j));
                            fileSnList.add(fileSave.get(j).getFile_sn());
                        }
                    }
                }
            }
        }

        // 파일 순번 리스트에 담기
        for(int k = 0; k < fileSnList.size(); k++) {
            if(k == 0) {
                placeDTO.setAtch_file_sn1(fileSnList.get(k));
            }

            if(k == 1) {
                placeDTO.setAtch_file_sn2(fileSnList.get(k));
            }

            if(k == 2) {
                placeDTO.setAtch_file_sn3(fileSnList.get(k));
            }
        }

        // 최종 수정자 대입
        placeDTO.setLast_mdfr(loginId);

        return placeDAO.update(placeDTO);
    }

    // 육성공간 삭제
    public void delete(int plc_sn) {
        // 0. 육성공간 상세 조회
        PlaceDTO basicFile = placeDAO.select(plc_sn);

        // 1. 조회한 것에서 file_id 꺼내기
        List<Integer> list = new ArrayList<>();

        int file_sn1 = basicFile.getAtch_file_sn1();
        int file_sn2 = basicFile.getAtch_file_sn2();
        int file_sn3 = basicFile.getAtch_file_sn3();

        list.add(file_sn1);
        list.add(file_sn2);
        list.add(file_sn3);

        // 2. 경로 설정
        Path path = Paths.get("D:\\").toAbsolutePath().normalize();

        // 3. File_sn 만큼 반복
        for(int i = 0; i < list.size(); i++) {

            // 변수 생성
            int file_sn = 0;
            File deleteFile = null;

            if(i == 0) {
                deleteFile = new File(path + basicFile.getPlc_path1() + basicFile.getPlc_file_name1());
                file_sn = basicFile.getAtch_file_sn1();
            }
            
            if(i == 1) {
                deleteFile = new File(path + basicFile.getPlc_path2() + basicFile.getPlc_file_name2());
                file_sn = basicFile.getAtch_file_sn2();
            }
            
            if(i == 2) {
                deleteFile = new File(path + basicFile.getPlc_path3() + basicFile.getPlc_file_name3());
                file_sn = basicFile.getAtch_file_sn3();
            }
            
            // 파일 삭제
            boolean removed = deleteFile != null && deleteFile.delete();

            // 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 파일 삭제
                placeDAO.deleteFile(file_sn);
            }
        }
        // 4. 육성공간 삭제
        placeDAO.delete(plc_sn);
    }

    // 육성공간 리스트 조회 (미리보기용)
    public void selectListAll(Model model, PlaceDTO placeDTO) {
        int main_sn = placeDTO.getPlc_sn();
        //List<VisualDTO> selectList = placeDAO.selectListAll(main_sn);
        List<VisualDTO> selectList = placeDAO.selectListAll(placeDTO);
        model.addAttribute("selectList", selectList);

        // 기존 파일 확인
        if(main_sn > 0) {
            PlaceDTO main = placeDAO.select(main_sn);
            placeDTO.setPlc_path1(main.getPlc_path1());
            placeDTO.setPlc_file_name1(main.getPlc_file_name1());
            placeDTO.setPlc_path2(main.getPlc_path2());
            placeDTO.setPlc_file_name2(main.getPlc_file_name2());
            placeDTO.setPlc_path3(main.getPlc_path3());
            placeDTO.setPlc_file_name3(main.getPlc_file_name3());
        }

        // 파일 업로드
        MultipartFile file1 = placeDTO.getPlc_file1();
        MultipartFile file2 = placeDTO.getPlc_file2();
        MultipartFile file3 = placeDTO.getPlc_file3();

        if(file1.getSize() > 0) {
            FileUploader fileUploader = new FileUploader();
            FileDTO file = fileUploader.insertFile(file1, main_sn);
            placeDTO.setPlc_file_name1(file.getFile_nm());
            placeDTO.setPlc_path1(file.getFile_path());
        }

        if(file2.getSize() > 0) {
            FileUploader fileUploader = new FileUploader();
            FileDTO file = fileUploader.insertFile(file2, main_sn);
            placeDTO.setPlc_file_name2(file.getFile_nm());
            placeDTO.setPlc_path2(file.getFile_path());
        }

        if(file3.getSize() > 0) {
            FileUploader fileUploader = new FileUploader();
            FileDTO file = fileUploader.insertFile(file3, main_sn);
            placeDTO.setPlc_file_name3(file.getFile_nm());
            placeDTO.setPlc_path3(file.getFile_path());
        }

        // 파일 화면에 전달
        model.addAttribute("place", placeDTO);
    }
}
