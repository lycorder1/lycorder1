package com.yaocoder.myset.controllers;

import com.yaocoder.myset.common.Common;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
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

    @PostMapping("/file/upload")
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

//        String  dcode = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 2);

        String  dcode = Common.getRandomUid(6);
        Files _file = new Files();
        _file.setDes(remark);
        _file.setPath(dicPath+filePath+filename);
        _file.setUid(request.getRemoteAddr());
        _file.setDownCode(dcode);
        _file.setDownCount((short)2);
        _file.setCreateDate(new Date());
        _file.setEffectiveDate(cal.getTime());
        _file.setFileName(file.getOriginalFilename());
        _file.setVirtualAddress(virtualAddress+filePath+filename);
        repository.save(_file);

        //TODO 返回数据处理
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        data.put("code", dcode);
        data.put("Effective Date", sdf.format(cal.getTime()));
        return new Result(0, data, "上传成功");
    }

    @RequestMapping("/file/down")
    public HttpServletResponse download(@RequestParam(value = "code") String code, HttpServletResponse response) {
        //根据code 到数据库获取数据
        Files _fi = repository.findByDownCodeEquals(code.toUpperCase(Locale.ROOT));

        if(_fi==null){
            try{
                response.getWriter().write("code is null");
            }catch (Exception e){};
            return response;
        }
        //TODO 验证下载信息
        String path = _fi.getPath();
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = _fi.getFileName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-fileName", "" + filename);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //TODO
        short s_1 = 1;
        short downCount = _fi.getDownCount();
        if(downCount<2){
            try{
                //做删除处理
                File file = new File(path);
                file.delete();
                repository.delete(_fi);
                return response;
            }catch (Exception e){}
        }

        _fi.setDownCount((short)(downCount-s_1));
        repository.save(_fi);
        return response;
    }
}
