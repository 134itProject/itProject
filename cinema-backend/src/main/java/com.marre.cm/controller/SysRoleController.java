package com.marre.cm.controller;

import com.marre.cm.common.response.R;
import com.marre.cm.domain.SysRole;
import com.marre.cm.service.SysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Description:系统角色控制层
 * @Date :2024/4/10
 */

@RestController
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @GetMapping("/sysRole")
    public R findAllRoles() {
        startPage();
        List<SysRole> data = sysRoleService.findAllRoles();
        return getResult(data);
    }

    /**
     * 通过id查询角色
     *
     * @param id 角色id
     * @return 角色
     */
    @GetMapping("/sysRole/{id}")
    public R findRoleById(@PathVariable Long id) {
        return getResult(sysRoleService.findRoleById(id));
    }

    /**
     * 添加角色
     *
     * @param sysRole 角色信息
     * @return 结果
     */
    @PostMapping("/sysRole")
    public R addRole(@Validated @RequestBody SysRole sysRole) {
        return getResult(sysRoleService.addRole(sysRole));
    }

    /**
     * 更新角色
     *
     * @param sysRole 角色信息
     * @return 结果
     */
    @PutMapping("/sysRole")
    public R updateRole(@Validated @RequestBody SysRole sysRole) {
        return getResult(sysRoleService.updateRole(sysRole));
    }

    /**
     * 删除角色
     *
     * @param ids 角色id
     * @return 结果
     */
    @DeleteMapping("/sysRole/{ids}")
    public R deleteRole(@PathVariable Long[] ids) {
        return getResult(sysRoleService.deleteRole(ids));
    }

    /**
     * 给指定 id 的角色分配权限，包括增加或者删除权限
     *
     * @param roleId 角色id
     * @param keys   权限key
     * @return 结果
     */
    @PostMapping("/sysRole/{roleId}")
    public R allotRight(@PathVariable Long roleId, @RequestBody Long[] keys) {
        return getResult(sysRoleService.allotRight(roleId, keys));
    }
}
