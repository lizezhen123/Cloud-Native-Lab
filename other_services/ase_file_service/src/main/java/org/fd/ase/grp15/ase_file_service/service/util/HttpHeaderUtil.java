package org.fd.ase.grp15.ase_file_service.service.util;

import org.springframework.http.*;

import java.net.URLEncoder;

public class HttpHeaderUtil {
    public static ResponseEntity<?> dispositionHeader(byte[] fileContent, String deposition){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.ok();
        HttpHeaders httpHeaders = new HttpHeaders();
        //inline预览 | attachment下载
        //加文件名的话（虽然不知道为什么没用）用URLEncoder编码一下，否则中文不行
        httpHeaders.setContentDisposition(ContentDisposition.builder(deposition).build());
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        httpHeaders.setCacheControl(CacheControl.noCache());
        return responseEntity.headers(httpHeaders).body(fileContent);
    }
}
