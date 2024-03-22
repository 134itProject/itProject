package com.marre.cm.mapper;

import com.marre.cm.domain.LoginUser;
import com.marre.cm.domain.SysUser;
import com.marre.cm.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * 用户持久层
 *
 * @author :marRE
 * @Date :2024/3/17
 */
@Mapper
public interface SysUserMapper {

    List<SysUser> findAllUsers(SysUser sysUser);

    SysUser findUserById(Long id);

    SysUser findUserByName(String userName);

    int addUser(SysUser sysUser);

    int updateUser(SysUser sysUser);

    int deleteUser(Long id);

    LoginUser findLoginUser(SysUserVo sysUserVo);

    List<Long> checkUserNameUnique(String userName);
}
