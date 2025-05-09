package com.marre.cm.service;

import com.marre.cm.domain.SysRole;

import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/21
 */

public interface SysRoleService {

    List<SysRole> findAllRoles();

    SysRole findRoleById(Long id);

    int addRole(SysRole sysRole);

    int updateRole(SysRole sysRole);

    int deleteRole(Long[] ids);

    int allotRight(Long roleId, Long[] resourceIds);
}
