package com.marre.cm.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 订单实体类
 *
 * @author: marRE
 *
 */

@Data
public class SysBill implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long billId;

    //0：未支付 1：支付
    private Boolean payState;

    @NotNull(message = "订单用户为空")
    private Long userId;

    @NotNull(message = "场次为空")
    private Long sessionId;

    @NotBlank(message = "座位为空")
    private String seats;

    //取消状态 0/1
    private Boolean cancelState;

    private Boolean cancelRole;

    private Date createTime;

    private Date deadline;

    private Date cancelTime;

    //模糊查询量
    private String queryByUserName;

    // 删除状态，1：删除，0：未删除
    private Boolean delState;

    private SysSession sysSession;

    private SysUser sysUser;

    // 这个字段特别用于记录管理员对订单的操作，操作员会输入备注。对于购票系统会自动删除
    private String remark;

    @Override
    public boolean equals(Object o) { //强对比
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysBill sysBill = (SysBill) o;
        return Objects.equals(billId, sysBill.billId) && Objects.equals(payState, sysBill.payState) && Objects.equals(userId, sysBill.userId) && Objects.equals(sessionId, sysBill.sessionId) && Objects.equals(seats, sysBill.seats) && Objects.equals(cancelState, sysBill.cancelState) && Objects.equals(cancelRole, sysBill.cancelRole) && Objects.equals(createTime, sysBill.createTime) && Objects.equals(deadline, sysBill.deadline) && Objects.equals(cancelTime, sysBill.cancelTime) && Objects.equals(queryByUserName, sysBill.queryByUserName) && Objects.equals(remark, sysBill.remark) && Objects.equals(delState, sysBill.delState) && Objects.equals(sysSession, sysBill.sysSession) && Objects.equals(sysUser, sysBill.sysUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billId, payState, userId, sessionId, seats, cancelState, cancelRole, createTime, deadline, cancelTime, queryByUserName, remark, delState, sysSession, sysUser);
    }
}
