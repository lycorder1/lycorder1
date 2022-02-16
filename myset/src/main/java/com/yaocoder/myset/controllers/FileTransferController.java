package com.yaocoder.myset.controllers;

import com.yaocoder.myset.MainServices.IFileTransferService;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * */
@RestController
@RequestMapping(value = "/FileTransfer")
public class FileTransferController {

    private static final Logger logger = LoggerFactory.getLogger(FileTransferController.class);

    @Autowired
    private IFileTransferService fileTransferService;


    @PostMapping("/file/upload")
    @ResponseBody
    public Object  upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        return fileTransferService.uploadFile(file,request);
    }

    @RequestMapping("/file/down")
    public HttpServletResponse download(@RequestParam(value = "code") String code, HttpServletResponse response) {
        var fileRecord = fileTransferService.downFile(code,response);
        return response;
    }

    /*
    * desc:获取上传历史列表
    * */
    @RequestMapping("/getDownList")
    public Object getDownList(HttpServletRequest request) {
        return fileTransferService.getDownList(request);
    }


}
