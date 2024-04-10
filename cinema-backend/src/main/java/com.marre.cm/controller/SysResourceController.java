package com.marre.cm.controller;

import com.marre.cm.common.response.R;
import com.marre.cm.domain.SysResource;
import com.marre.cm.service.SysResourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Description:系统资源控制层 （菜单等）
 * @Date :2024/4/10
 */

@RestController
public class SysResourceController extends BaseController {

    @Resource
    private SysResourceService sysResourceService;

    /**
     * 查询所有菜单列表
     *
     * @return 菜单列表
     */
    @GetMapping("/sysResource")
    public R findAllResources() {
        startPage();
        List<SysResource> data = sysResourceService.findAllResources();
        return getResult(data);
    }

    /**
     * 查询菜单列表 和子菜单
     *
     * @return 菜单列表
     */
    @GetMapping("/sysResource/children")
    public R findWithChildren() {
        return getResult((Object) sysResourceService.findWithChildren());
    }

    /**
     * 通过id查询菜单
     *
     * @param id 菜单id
     * @return 结果
     */
    @GetMapping("/sysResource/{id}")
    public R findResourceById(@PathVariable Long id) {
        return getResult(sysResourceService.findResourceById(id));
    }

    /**
     * 查询菜单树
     *
     * @return 菜单树
     */
    @GetMapping("/sysResource/tree")
    public R findAllWithAllChildren() {
        return getResult(sysResourceService.findAllWithAllChildren());
    }

    /**
     * 添加菜单
     *
     * @param sysResource 菜单信息
     * @return 结果
     */
    @PostMapping("/sysResource")
    public R addResource(@Validated @RequestBody SysResource sysResource) {
        return getResult(sysResourceService.addResource(sysResource));
    }

    /**
     * 更新菜单
     *
     * @param sysResource 菜单信息
     * @return 结果
     */
    @PutMapping("/sysResource")
    public R updateResource(@Validated @RequestBody SysResource sysResource) {
        return getResult(sysResourceService.updateResource(sysResource));
    }

    /**
     * 删除菜单
     *
     * @param ids 菜单id
     * @return 结果
     */
    @DeleteMapping("/sysResource/{ids}")
    public R deleteResource(@PathVariable Long[] ids) {
        return getResult(sysResourceService.deleteResource(ids));
    }

}
