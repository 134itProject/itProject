package com.marre.cm.mapper;

import com.marre.cm.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统角色持久层
 *
 * @author :marRE
 * @Date :2024/3/17
 */
@Mapper
public interface SysRoleMapper {

    List<SysRole> findAllRoles();

    SysRole findRoleById(Long id);

    int addRole(SysRole sysRole);

    int update(SysRole sysRole);

    int deleteRole(Long id);

    int addRight(Long roleId, Long resourceId);

    int deleteRight(Long roleId, Long resourceId);

    List<Long> findAllRights(Long roleId);
}
