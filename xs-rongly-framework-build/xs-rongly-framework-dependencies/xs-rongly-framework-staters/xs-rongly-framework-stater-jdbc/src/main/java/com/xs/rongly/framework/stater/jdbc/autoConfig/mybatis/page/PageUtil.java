package com.xs.rongly.framework.stater.jdbc.autoConfig.mybatis.page;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author: lvrongzhuan
 * @Description: 分页基工具类型
 * @Date: 2019/3/14 14:43
 * @Version: 1.0
 * modified by:
 */
public class PageUtil {

    private PageUtil() {
    }

    public static <T> PageVO<T> pageVO(List<T> ts) {
        PageVO<T> pageVO = new PageVO<>();
        setPageVo(ts, pageVO);
        pageVO.setRows(ts);
        return pageVO;
    }

    public static <T> PageVO<T> pageVO(List<?> ts,List<T> vOs) {
        PageVO<T> pageVO = new PageVO<>();
        setPageVo(ts, pageVO);
        pageVO.setRows(vOs);
        return pageVO;
    }

    private static <T> void setPageVo(List<?> ts, PageVO<T> pageVO) {
        if (ts instanceof Page) {
            Page page = (Page) ts;
            pageVO.setPageTotal(page.getPages());
            pageVO.setRowTotal((int)page.getTotal());
        }
    }


    /**
     * 获取总页数
     * @param total
     * @param pageSize
     * @return
     */
    public static int getTotalPages(long total, int pageSize) {
        int pages;
        if (total == -1) {
            pages = 1;
            return pages;
        }
        if (pageSize > 0) {
            pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
        } else {
            pages = 0;
        }
        return pages;
    }
}
