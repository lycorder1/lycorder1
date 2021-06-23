package com.yaocoder.myset.common;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class Common {

    /**
     * 获取客户端ip地址
     * @param request
     * @return
     */
    public static String getCliectIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }


    public static String getRandomUid(int num){
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        str+= str.toLowerCase();
        str +="0123456789";
        char ch = str.charAt(str.length()-1);
        System.out.println(ch);
        StringBuffer sb = new StringBuffer(num);
        for (int i = 0; i <num ; i++) {
            char ch2 = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch2);
        }
       return sb.toString();
    }
}
