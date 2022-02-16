package com.yaocoder.myset.chat;

public class ChatResponseDto {

    public ChatResponseDto(int code,Object data,String messsage){
        this.code = code;
        this.data = data;
        this.messsage = messsage;
    }

    public int code;

    public int uId;

    public String messsage;

    public Object data;

    public String handleName;

    public Object res;

}
