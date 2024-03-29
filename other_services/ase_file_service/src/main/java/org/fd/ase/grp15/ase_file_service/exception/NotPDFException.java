package org.fd.ase.grp15.ase_file_service.exception;

import lombok.NonNull;

public class NotPDFException extends RuntimeException{
    public NotPDFException() {
        super("请检查上传文件是否为PDF格式");
    }
}
