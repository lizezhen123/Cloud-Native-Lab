package org.fd.ase.grp15.ase_file_service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.fd.ase.grp15.ase_file_service.service.IFileService;
import org.fd.ase.grp15.ase_file_service.service.impl.FileServiceImpl;
import org.fd.ase.grp15.ase_file_service.service.impl.MockMultipartFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@EnableDubbo
public class AseFileServiceApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AseFileServiceApplication.class, args);
//        File file = new File("D:/1yanjiusheng/baseline/材料/复审无个人信息版.pdf");
//        MultipartFile mockMultipartFile = new MockMultipartFile("随便吧.pdf", new FileInputStream(file));
//        IFileService service = new FileServiceImpl();
//        String essayId = service.fileUpload(mockMultipartFile);

    }
}