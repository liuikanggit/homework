package com.heo.homework.vo;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageVo<T> {

    /** 一共的页数 */
    private int totalPages;

    /** 总数据 */
    private long totalData;

    private T data;

    public PageVo(){}

    public PageVo(int totalPages,long totalData,T data){
        this.totalData = totalData;
        this.totalPages = totalPages;
        this.data = data;
    }
    public PageVo(Page page){
        this.totalData = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.data = (T) page.getContent();
    }
}
