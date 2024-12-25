package com.kb.inno.admin.Controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.core.io.FileSystemResource;

@RestController
public class ImageController {
    @RequestMapping("/upload/{imageName:.+}")
	@ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
		//System.out.println("imageName==================="+imageName);
        //Path path = Paths.get("D:/fsfile/dev_kbinnovation/").resolve(imageName).normalize();
        String imagePath = "D:/fsfile/dev_kbinnovation/" + imageName;
        //System.out.println("imagePath==================="+imagePath);
        //Resource resource = new FileSystemResource(path.toString());
        Resource resource = new FileSystemResource(imagePath);

        if (resource.exists()) {
        	//System.out.println("파일 존재: " + resource.getFilename());
            return ResponseEntity.ok().body(resource);
        } else {
        	//System.out.println("파일이 존재하지 않습니다.");
            return ResponseEntity.notFound().build();
        }
    }
    
    @RequestMapping("/summernoteimages/{imageName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getNoteImage(@PathVariable String imageName) {
    	String imagePath = "D:/fsfile/dev_kbinnovation/" + imageName;
    	Resource resource = new FileSystemResource(imagePath);

        if (resource.exists()) {
        	//System.out.println("파일 존재: " + resource.getFilename());
            return ResponseEntity.ok().body(resource);
        } else {
        	//System.out.println("파일이 존재하지 않습니다.");
            return ResponseEntity.notFound().build();
        }
    }
}
