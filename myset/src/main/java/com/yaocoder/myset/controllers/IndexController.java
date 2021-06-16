package com.yaocoder.myset.controllers;

import com.yaocoder.myset.common.Result;
import com.yaocoder.myset.entities.Files;
import com.yaocoder.myset.primaryReposition.FilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * */
@RestController
//@RequestMapping(value = "/rest")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private FilesRepository repository;

    @Value("${file.uploadPath}")//获取配置文件中的配置参数
    private String filePath;

    @Value("${file.uploadDicPath}")//获取配置文件中的配置参数
    private String dicPath;

    @Value("${file.uploadNDicPath}")//获取配置文件中的配置参数
    private String dicNPath;

    @Value("${net.virtualAddress}")//获取配置文件中的配置参数
    private String virtualAddress;

    @GetMapping("/")
    public String index(){
        return "hello world! common on13";
    }

    @GetMapping("/index")
    public String index1(){
        return "this is index";
    }

    @PostMapping("/upload")
    @ResponseBody
    public Object  test(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        String remark = "上传用户信息";//备注信息
        String filename = file.getOriginalFilename();//获取文件名称
        String suffixname = filename.substring(filename.lastIndexOf("."));//后缀
        filename = UUID.randomUUID() + suffixname;//文件上传后重命名数据库存储
        File dest = new File(dicPath+filePath,filename);
//        File dest = new File(filePath,filename);
        Map<String, String> data = new HashMap<String, String>();
        data.put("filename", filename);
        data.put("备注 ", remark);
        try {
            //MultipartFile对象的transferTo方法用于文件的保存（效率和操作比原来用的FileOutputStream方便和高效）
            file.transferTo(dest);//拷贝文件到指定路径储存
//            return new Result(0, data, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(-1, data, "上传失败");
        }

        String  dcode = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        Files _file = new Files();
        _file.setDes(remark);
        _file.setPath(dicPath+filePath+filename);
        _file.setUid(request.getRemoteAddr());
        _file.setDownCode(dcode);
        _file.setDownCount((short)2);
        _file.setCreateDate(new Date());
        _file.setVirtualAddress(virtualAddress+filePath+filename);
        repository.save(_file);
        //TODO 返回数据处理
        return new Result(0, data, "上传成功");
    }
}
