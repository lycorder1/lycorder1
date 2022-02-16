package com.yaocoder.myset.MainServices;

import com.yaocoder.myset.common.Result;
import com.yaocoder.myset.entities.FileRecord;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IFileTransferService {

    FileRecord downFile(String code, HttpServletResponse response);

    Result uploadFile(MultipartFile file, HttpServletRequest request);

    Object getDownList(HttpServletRequest request);
}
