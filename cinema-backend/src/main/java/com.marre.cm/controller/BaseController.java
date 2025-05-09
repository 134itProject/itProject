package com.marre.cm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.marre.cm.common.page.Page;
import com.marre.cm.common.page.PageBuilder;
import com.marre.cm.common.response.R;

import java.util.List;

/**
 * @author :marRE
 * @Description:抽取重复功能为基类
 * @Date :2024/4/1
 */

public class BaseController {

    /**
     * 开启分页
     */
    public void startPage() {
        Page page = PageBuilder.buildPage();
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize, page.getOrderByColumn());
        }
    }

    /**
     * 根据修改行数返回响应消息
     *
     * @param rows
     * @return
     */
    public R getResult(int rows) {
        return rows == 0 ? R.error() : R.success();
    }

    /**
     * 分页响应消息
     *
     * @param data
     * @return
     */
    public R getResult(List<?> data) {
        PageInfo pageInfo = new PageInfo(data);
        R r = R.success(data);
        r.put(PageBuilder.PAGE_NUM, pageInfo.getPageNum());
        r.put(PageBuilder.PAGE_SIZE, pageInfo.getPageSize());
        r.put(PageBuilder.TOTAL, pageInfo.getTotal());
        return r;
    }

    /**
     * 对象类型响应消息
     *
     * @param data
     * @return
     */
    public R getResult(Object data) {
        return R.success(data);
    }

}
