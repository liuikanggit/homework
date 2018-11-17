package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestionVO {

    @JsonProperty("name")
    private String questionDesc;

    @JsonProperty("imageUrl")
    private List<String> questionImageUrl;

    /** 批改结果  0错误 1正确  */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String comment;

}
