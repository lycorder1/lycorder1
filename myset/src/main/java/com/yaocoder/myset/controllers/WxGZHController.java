package com.yaocoder.myset.controllers;

import com.alibaba.fastjson.JSONObject;
import com.yaocoder.myset.MainServices.IFileTransferService;
import com.yaocoder.myset.common.*;
import com.yaocoder.myset.commonDto.WxGzhDto;
import com.yaocoder.myset.commonDto.WxGzhResponse;
import com.yaocoder.myset.entitiesMysql.User;
import com.yaocoder.myset.primaryReposition.FilesRepository;
import com.yaocoder.myset.redis.AuthUser;
import com.yaocoder.myset.secondReposition.BaseAppDAOimpl;
import com.yaocoder.myset.secondReposition.UserRepository;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RestController
public class WxGZHController {

    @Autowired
    private FilesRepository repository;

    @Autowired
    private RestMock restMock;

    private static final Logger logger = LoggerFactory.getLogger(WxGZHController.class);

    @Value("${wxGzh.appId}")//获取配置文件中的配置参数
    private String appId;

    @Value("${wxGzh.secret}")//获取配置文件中的配置参数
    private String secret;

    @Value("${wxGzh.wxGzhUrl}")//获取配置文件中的配置参数
    private String wxGzhUrl;

    @Value("${wxGzh.FUdomain}")//获取配置文件中的配置参数
    private String FUdomain;

    @Value("${wxGzh.chatrobotApi}")//获取配置文件中的配置参数
    private String chatrobotApi;

    @Value("${wxGzh.menuCreateUrl}")//获取配置文件中的配置参数
    private String menuCreateUrl;

    @Autowired
    private IFileTransferService fileTransferService;

    public Map<String,String> wxGzhUrlList = new HashMap<String,String>();

    {
        wxGzhUrlList.put("token",wxGzhUrl);
        wxGzhUrlList.put("createMenu",menuCreateUrl);
    }

    @RequestMapping("/WxGZH/GetInterfaceGzh")
    public Object  GetInterfaceGzh(@RequestBody WxGzhDto wxGzhDto, HttpServletRequest request) throws IOException {
        logger.info("API:"+"/WxGZH/GetInterfaceGzh");
        if(StringUtils.hasText(wxGzhDto.MsgId)){
            //文本消息判断下载码
            if(wxGzhDto.getMsgType().equals("text")&&StringUtils.hasText(wxGzhDto.getContent())&&wxGzhDto.getContent().length()==6){
                var fileRe =repository.findByDownCodeEquals(wxGzhDto.getContent().toUpperCase(Locale.ROOT));

                if(fileRe!=null){
                    var resp = new WxGzhResponse(wxGzhDto.FromUserName,wxGzhDto.ToUserName,
                            wxGzhDto.getCreateTime(),"news",1,"文件下载",fileRe.getFileName(),FUdomain+"/Rest/profile/userFile/uploadFile/2ca07534-c7ab-4945-8fea-f981f7092a01.jpg?x-oss-process=style/blog.list.item.pc",
                            FUdomain+"REST"+fileRe.getVirtualAddress()).parseXml();
                    return resp;
                }else{}
            }

            if(wxGzhDto.getMsgType().equals("text")){
                var respStr = HttpRestUtils.get(chatrobotApi+wxGzhDto.getContent(),null);
                if(StringUtils.hasText(respStr)){
                    var reObj = JSONObject.parseObject(respStr);
                    if(reObj.getString("result").equals("0")){
                        return new WxGzhResponse(wxGzhDto.FromUserName,wxGzhDto.ToUserName,
                                wxGzhDto.getCreateTime(),"text",reObj.getString("content")).parseXml();
                    }
                }
            }

            return null;
        }

        //用户进入的情况
        if(StringUtils.hasText(wxGzhDto.signature)){
            var accessToken = getAccessToken();
            //自定义菜单
            var cMenuUrl = wxGzhUrlList.get("createMenu");
            var repStr = HttpRestUtils.post(cMenuUrl+accessToken,null);
        }

        //URl验证
        return wxGzhDto.echostr;
    }


    @RequestMapping("/WxGZH/createMenu")
    public String createMenu(){
        return null;
    }

    //TODO 获取access_Token
    private String getAccessToken(){
        var wxUrl = wxGzhUrlList.get("token").replace("APPID",appId).replace("APPSECRET",secret);
        try {
            var respStr = HttpRestUtils.get(wxUrl,null);
            var reObj = JSONObject.parseObject(respStr);
            return reObj.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
