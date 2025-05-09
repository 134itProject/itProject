package com.marre.cm.service;

import com.marre.cm.domain.LoginUser;
import com.marre.cm.domain.SysUser;
import com.marre.cm.domain.vo.SysUserVo;

import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/21
 */

public interface SysUserService {

    List<SysUser> findAllUsers(SysUser sysUser);

    SysUser findUserById(Long id);

    SysUser findUserByName(String userName);

    int addUser(SysUser sysUser);

    int updateUser(SysUser sysUser);

    int deleteUser(Long[] ids);

    LoginUser login(SysUserVo sysUserVo);

    LoginUser findLoginUser(SysUserVo sysUserVo);

    boolean isUserNameUnique(String userName, Long userId);
}
