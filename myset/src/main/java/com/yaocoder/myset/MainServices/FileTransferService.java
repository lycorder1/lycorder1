package com.yaocoder.myset.MainServices;


import com.yaocoder.myset.common.Common;
import com.yaocoder.myset.common.Result;
import com.yaocoder.myset.common.SessionCommon;
import com.yaocoder.myset.controllers.FileTransferController;
import com.yaocoder.myset.entities.FileRecord;
import com.yaocoder.myset.primaryReposition.FilesRepository;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileTransferService implements IFileTransferService {

    @Autowired
    SessionCommon sessionCommon;

    @Autowired
    private FilesRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(FileTransferService.class);

    @Value("${file.uploadPath}")//获取配置文件中的配置参数
    private String filePath;

    @Value("${file.uploadDicPath}")//获取配置文件中的配置参数
    private String dicPath;

    @Value("${file.uploadNDicPath}")//获取配置文件中的配置参数
    private String dicNPath;

    @Value("${net.virtualAddress}")//获取配置文件中的配置参数
    private String virtualAddress;

    @Override
    public FileRecord downFile(String code, HttpServletResponse response) {
        FileRecord _fi = repository.findByDownCodeEquals(code.toUpperCase(Locale.ROOT));

        if(_fi==null){
            try{
                response.getWriter().write("code is null");
            }catch (Exception e){};
            return null;
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
                return _fi;
            }catch (Exception e){}
        }

        _fi.setDownCount((short)(downCount-s_1));
        repository.save(_fi);
        return _fi;
    }

    @Override
    public Result uploadFile(MultipartFile file, HttpServletRequest request) {
        //判断用户是否的登录 登录的情况获取登录信息
        var user = sessionCommon.getUserInfoBySession(request);
        String remark = "文件上传";//备注信息
        String filename = file.getOriginalFilename();//获取文件名称
        String suffixname = filename.substring(filename.lastIndexOf("."));//后缀
        filename = UUID.randomUUID() + suffixname;//文件上传后重命名数据库存储
        File dest = new File(dicPath+filePath,filename);
        Map<String, String> data = new HashMap<String, String>();
        data.put("filename", filename);
        data.put("备注 ", remark);
        try {
            //MultipartFile对象的transferTo方法用于文件的保存（效率和操作比原来用的FileOutputStream方便和高效）
            file.transferTo(dest);//拷贝文件到指定路径储存
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(-1, data, "上传失败");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 2);

        String  dcode = Common.getRandomUid(6);
        FileRecord _file = new FileRecord();
        _file.setDes(remark);
        _file.setPath(dicPath+filePath+filename);
        _file.setUid(getIpAddr(request));
        _file.setDownCode(dcode);
        _file.setDownCount((short)2);
        _file.setCreateDate(new Date());
        _file.setEffectiveDate(cal.getTime());
        _file.setFileName(file.getOriginalFilename());
        _file.setVirtualAddress(virtualAddress+filePath+filename);
        if(user!=null){
            _file.setUserId(user.getId());
        }

        repository.save(_file);
        // 返回数据处理
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        data.put("code", dcode);
        data.put("Effective Date", sdf.format(cal.getTime()));
        return new Result(0, data, "上传成功");
    }

    @Override
    public Object getDownList(HttpServletRequest request) {
        var user = sessionCommon.getUserInfoBySession(request);
        List<FileRecord> files = null;

        logger.info("getDownList(checkLogin):"+ sessionCommon.checkLoginBycookie(request));
        if(sessionCommon.checkLoginBycookie(request)){
            files = repository.getFilesByUid(user.getId());
        }

        logger.info("getDownList(count):"+ (files==null?0:files.size()));
        if(files!=null&&!files.isEmpty()){
            return new Result(0, files, "success");
        }

        return new Result(-1, null, "fail");
    }


    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");//先从nginx自定义配置获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
