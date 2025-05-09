package com.marre.cm.domain.vo;


import com.marre.cm.domain.SysBill;
import lombok.Data;

import java.io.Serializable;


/**
 *
 * Vo 即 valueObject 对数据进行封装用
 *
 * 接收前端增加订单的数据
 *
 */
@Data
public class SysBillVo implements Serializable {
    public static final Long serialVersionUID = 1L;

    //订单信息
    private SysBill sysBill;

    //成功更新后场次的座位信息
    private String sessionSeats;
}
