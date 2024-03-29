package org.fd.ase.grp15.ase_file_service.service.impl;

import org.fd.ase.grp15.ase_file_service.exception.NotPDFException;
import org.fd.ase.grp15.ase_file_service.service.IFileService;
import org.fd.ase.grp15.ase_file_service.service.util.HttpHeaderUtil;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.dubbo.config.annotation.DubboService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;


@DubboService
@Service
public class FileServiceImpl implements IFileService {
    {
//        //Spring对物理资源的访问方式抽象成Resource，我们可以通过Spring提供的接口来访问磁盘文件等数据。
//        ResourceLoader resourceLoader = new DefaultResourceLoader();
//        //字节输入流，用来将文件中的数据读取到java程序中
//        try {
//            rootPath = resourceLoader.getResource("").getURL().getPath() + "/upload/";
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        rootPath = System.getProperty("user.home") + File.separator + "upload" + File.separator;

    }
    final private String pdfSuffix = ".pdf";

    private String rootPath;

    @Override
    public String fileUpload(MultipartFile file) throws IOException {
        //判断是否为pdf文件
        String originName = file.getOriginalFilename();
        //System.out.println(originName);
        String suffix = originName.substring(originName.lastIndexOf("."));
        if(!suffix.equals(pdfSuffix)){
            throw new NotPDFException();
        }

        //查找文件存储目录，不存在就新建
        String filePath = rootPath;
        File fileDir = new File(filePath);
        if(!fileDir.exists()){
            boolean res = fileDir.mkdir();
            System.out.println(fileDir.getAbsoluteFile());
        }

        //构造新文件名
        String uuid = UUID.nameUUIDFromBytes(originName.getBytes()).toString();
        String currName = uuid + pdfSuffix;
        file.transferTo(new File(fileDir, currName));

        return uuid;
    }

    @Override
    public ResponseEntity<?> preview(String id) throws IOException {
        byte[] buffer = getFileContent(id);
        if(buffer == null)return ResponseEntity.ok("文件不存在");
        return HttpHeaderUtil.dispositionHeader(buffer, "inline");
    }

    public ResponseEntity<?> download(String id) throws IOException {
        byte[] buffer = getFileContent(id);
        if(buffer == null)return ResponseEntity.ok("文件不存在");

        return HttpHeaderUtil.dispositionHeader(buffer, "attachment");
    }

    public byte[] getFileContent(String id) throws IOException {
        String fileName = id + pdfSuffix;
        File file = new File(rootPath + fileName);
        if(!file.exists())return null;

        FileInputStream stream = new FileInputStream(file);
        byte[] buffer = new byte[stream.available()];
        stream.read(buffer);

        return buffer;
    }

}
