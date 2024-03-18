package com.marre.cm.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 系统菜单实体类
 *
 * @author marRE
 *
 */

@Data
public class SysResource {
    private static final Long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "菜单名称不能为空")
    private String name;

    private String path;

    //菜单权限等级
    private Integer level;

    //父菜单id
    private Long parentId;

    //父菜单
    private SysResource parent;

    //子菜单
    private List<SysResource> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysResource that = (SysResource) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(path, that.path) && Objects.equals(level, that.level) && Objects.equals(parentId, that.parentId) && Objects.equals(parent, that.parent) && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path, level, parentId, parent, children);
    }
}
