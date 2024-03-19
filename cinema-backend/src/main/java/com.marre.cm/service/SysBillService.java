package com.marre.cm.service;

import com.marre.cm.domain.SysBill;
import com.marre.cm.domain.SysUser;

import java.util.List;

/**
 * 订单服务层  接口
 *
 * @author :marRE
 * @Date :2024/3/19
 */

public interface SysBillService {

    List<SysBill> findAllBills(SysBill sysBill);

    SysBill findBillById(Long id);

    Object addBill(SysBill sysBill);

    int updateBill(SysBill sysBill);

    int deleteBill(Long[] ids);

    List<SysBill> findTimeoutBill();

}
