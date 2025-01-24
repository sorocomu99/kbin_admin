/**
 * 파일명     : FileUploader.java
 * 화면명     : 파일 업로드 (공통)
 * 설명       : 첨부파일 업로드
 * 최초개발일  : 2024.10.24
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 * 2024.12.25         이훈희           첨부파일 업로드 경로 오류 해결
 */
package com.kb.inno.common;

import com.kb.inno.admin.DTO.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class FileUploader {
    // summernote 이미지 업로드
    public Map<String, Object> summernoteInsertImage(MultipartFile file) {
        // 빈 객체 생성
        Map<String, Object> resultMap = new HashMap<>();

        // 경로 설정
        Path path = Paths.get("D:\\fsfile").toAbsolutePath().normalize();
        String savePath = path + "\\dev_kbinnovation\\";
//        Path path = Paths.get("/fsfile").toAbsolutePath().normalize();
//        String savePath = path + "/dev_kbinnovation/";

        // String savePath = "/Users/johuiyang/Documents/web/uploads/kbinno/";
        //String savePath = "/kbinnovationhub_devadm/";

        File directory = new File(savePath);

        // 디렉터리가 존재하지 않으면 생성
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 꺼내기
        String originalFileName = file.getOriginalFilename();	// 원래 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	// 파일 확장자
        String savedFileName = UUID.randomUUID() + extension;	// 저장될 파일명

        // 옮겨지는 곳
        File targetFile = new File(savePath + savedFileName);

        // 보여지는 input, output
        try (InputStream fileStream = file.getInputStream()) {

            // 파일 복사
            Files.copy(fileStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/kbinnovationhub_devadm/summernoteimages/" + savedFileName;
            resultMap.put("url", imageUrl);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultMap;
    }

    public List<FileDTO> insertFiles(List<MultipartFile> files, int loginId) {

        // 경로 설정
    	Path path = Paths.get("D:\\fsfile").toAbsolutePath().normalize();
        String savePath = path + "\\dev_kbinnovation\\";

//        Path path = Paths.get("/fsfile").toAbsolutePath().normalize();
//        String savePath = path + "/dev_kbinnovation/";

        //String savePath = "/Users/johuiyang/Documents/web/uploads/kbinno/";

        // 디렉토리 없으면 생성
        File directory = new File(savePath);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        List<FileDTO> fileList = new ArrayList<>();

        // 파일 Array 길이만큼 반복
        for (int i = 0; i < files.size(); i++) {
            // 파일이 null 이면 패스, 아니면 파일 저장 실행
            if (files.get(i).getSize() == 0) {
                continue;
            } else {
                // 오리지널 파일 이름
                String originalFilename = files.get(i).getOriginalFilename();
                // 확장자 꺼내기
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                // 파일 이름 설정
                String fileName = UUID.randomUUID().toString() + fileExtension;
                //String fileName = UUID.randomUUID() + fileExtension;

                File targetFile = new File(savePath + fileName);

                // 파일 저장
                try (InputStream fileStream = files.get(i).getInputStream()) {
                    // 파일 복사
                    Files.copy(fileStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                /*
                try {
                    files.get(i).transferTo(new File(savePath, fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                */
                // 파일 사이즈 구하기
                int bytes = (int) files.get(i).getSize();

                // 빈 DTO 생성
                FileDTO fileDTO = new FileDTO();

                // DTO에 객체 담기
                fileDTO.setFile_nm(fileName);
                fileDTO.setOrgnl_file_nm(originalFilename);
                fileDTO.setFile_extn(fileExtension);
                //fileDTO.setFile_path("\\upload\\");
                fileDTO.setFile_path("\\upload\\");
                fileDTO.setFile_sz(bytes);

                // 로그인 한 사람 대입
                fileDTO.setFrst_rgtr(loginId);
                fileDTO.setLast_mdfr(loginId);

                fileList.add(fileDTO);
            }
        }

        return fileList;
    }

    // 파일 저장
    public FileDTO insertFile(MultipartFile file, int loginId) {

        // 오리지널 파일 이름
        String originalFileName = file.getOriginalFilename();

        // 파일 확장자 설정
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 파일 이름 설정
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // 경로 설정 (아래는 개발 테스트 경로. 운영 리눅스서버 경로로 추후 변경해줘야합니다.)
        Path path = Paths.get("D:\\fsfile").toAbsolutePath().normalize();
        String savePath = path + "\\kbinnovationhub_devadm\\";
//        String savePath = "/kbinnovationhub_devadm/";

        File directory = new File(savePath);

        System.out.println("savePath====================="+savePath);

        // 디렉토리 없으면 생성
        //File directory = new File(savePath + fileName);
        //File directory = new File(savePath);
        //File directory = new File(savePath + fileName);

        System.out.println("directory====================="+directory);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        File targetFile = new File(savePath + fileName);

        // 파일 저장
        /*
        try {
            file.transferTo(new File(savePath, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
        try (InputStream fileStream = file.getInputStream()) {
            // 파일 복사
            Files.copy(fileStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 파일 사이즈 구하기
        int bytes = (int) file.getSize();

        // 빈 DTO 생성
        FileDTO fileDTO = new FileDTO();

        // 파일 DTO에 값 저장
        fileDTO.setFile_nm(fileName);
        fileDTO.setOrgnl_file_nm(originalFileName);
        fileDTO.setFile_sz(bytes);
        fileDTO.setFile_extn(fileExtension);
//        fileDTO.setFile_path("\\upload\\");
        fileDTO.setFile_path("/upload/");

        // 파일 DTO에 로그인 한 사람 추가 (임시로 나중에 수정 요망!)
        fileDTO.setFrst_rgtr(loginId);
        fileDTO.setLast_mdfr(loginId);

        return fileDTO;
    }

    // 파일 삭제
    public boolean deleteFile(String realPath, String fileName) {
//    	Path path = Paths.get("D:\\fsfile\\dev_kbinnovation\\").toAbsolutePath().normalize();
//        Path path = Paths.get("/fsfile/dev_kbinnovation/").toAbsolutePath().normalize();
//        File deleteFile = new File(path + realPath + fileName);
//        String savePath = "/Users/johuiyang/Documents/web/uploads/kbinno/";
        String savePath = "D:\\fsfile\\dev_kbinnovation\\";
        //String savePath = "/kbinnovationhub_devadm/";

        File deleteFile = new File(savePath + fileName);
        return deleteFile.delete();
    }
}
