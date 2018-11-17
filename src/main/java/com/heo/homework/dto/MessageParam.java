package com.heo.homework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class MessageParam {

    @JsonIgnore
    private String id;

    @JsonProperty("touser")
    private String openid;

    @JsonProperty("template_id")
    private String templateId;

    private String page;

    @JsonProperty("form_id")
    private String formId;

    private Map<String,Map> data = new LinkedHashMap<>();

    @JsonIgnore
    private int i = 1;

    public MessageParam(String id,String openid,String templateId,String page){
        this(id,openid,templateId,page,null);
    }

    public MessageParam(String id,String openid,String templateId,String page,String formId){
        this.id = id;
        this.openid = openid;
        this.templateId = templateId;
        this.page = page;
        this.formId = formId;
    }

    public MessageParam addData(String value){
        addData(i++,value);
        return this;
    }

    public MessageParam addData(Integer i,String value){
        Map<String,String> valueMap = new HashMap<>();
        valueMap.put("value",value);
        this.data.put("keyword"+i,valueMap);
        return this;
    }

    public String toJson(){

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MyException(ResultEnum.SYSTEM_EXCEPTION);
        }
    }
}


/**
 {
 "touser": "OPENID",
 "template_id": "TEMPLATE_ID",
 "page": "index",
 "form_id": "FORMID",
 "data": {
 "keyword1": {
 "value": "339208499"
 },
 "keyword2": {
 "value": "2015年01月05日 12:30"
 },
 "keyword3": {
 "value": "粤海喜来登酒店"
 } ,
 "keyword4": {
 "value": "广州市天河区天河路208号"
 }
 },
 "emphasis_keyword": "keyword1.DATA"
 }
 */