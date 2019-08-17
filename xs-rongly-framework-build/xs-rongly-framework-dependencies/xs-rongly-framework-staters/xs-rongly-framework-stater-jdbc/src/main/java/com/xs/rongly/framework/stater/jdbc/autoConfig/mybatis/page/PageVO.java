package com.xs.rongly.framework.stater.jdbc.autoConfig.mybatis.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;


/**
 * @Author: lvrongzhuan
 * @Description: 分页对象实体
 * @Date: 2019/3/14 14:43
 * @Version: 1.0
 * modified by:
 */
@Data
public class PageVO<T> {

    public static final Integer DEFAULT_PAGESIZE = 10;
    /**
     * 页码
     */
    private Integer pageNumber;
    /**
     * 每页记录数
     */
    private Integer pageSize = DEFAULT_PAGESIZE;
    /**
     *  当前返回的数据
     */
    private List<T> rows;
    /**
     * 总记录数
     */
    private Integer rowTotal;
    /**
     * 总页数
     */
    private Integer pageTotal;


    public PageVO() {
        this(1, DEFAULT_PAGESIZE);
    }

    public PageVO(int pageNumber, int pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }


}
