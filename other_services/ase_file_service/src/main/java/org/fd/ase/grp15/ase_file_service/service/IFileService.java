package org.fd.ase.grp15.ase_file_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    /*
    文件上传
    @return 文件名
     */
    public String fileUpload(MultipartFile file) throws IOException;

    public ResponseEntity<?> preview(String id) throws IOException;

    public ResponseEntity<?> download(String id) throws IOException;
}
