package com.marre.cm.mapper;

import com.marre.cm.domain.SysResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统菜单持久层
 *
 * @author :marRE
 * @Date :2024/3/17
 */
@Mapper
public interface SysResourceMapper {

    List<SysResource> findAllResources();

    List<SysResource> findWithChildren();

    List<SysResource> findAllWithAllChildren();

    SysResource findResourceById(Long id);

    int addResource(SysResource sysResource);

    int updateResource(SysResource sysResource);

    int deleteResource(Long id);
}
