package com.marre.cm.domain.vo;


import lombok.Data;

import java.io.Serializable;

/**
 *
 * 封装用户登录的信息
 */
@Data
public class SysUserVo implements Serializable {

    public static final Long serialVersionUID = 1L;

    private String userName;

    private String password;
}
