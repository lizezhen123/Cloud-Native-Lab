package org.fd.ase.grp15.ase_file_service.controller;

import jakarta.annotation.Resource;
import org.fd.ase.grp15.ase_file_service.response.ResponseWithData;
import org.fd.ase.grp15.ase_file_service.service.IFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {
    @Resource
    private IFileService service;

    @PostMapping("/file/upload")
    public ResponseWithData upload(@RequestBody() MultipartFile file) throws IOException {
        return new ResponseWithData(service.fileUpload(file), "");
    }

    @GetMapping("/file/preview/{essayId}")
    public ResponseEntity<?> preview(@PathVariable("essayId")String id) throws IOException {
        return service.preview(id);
    }

    @GetMapping("/file/download/{essayId}")
    public ResponseEntity<?> download(@PathVariable("essayId")String id) throws IOException {
        return service.download(id);
    }

}
