package com.marre.cm.service;

import com.marre.cm.domain.SysResource;

import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/21
 */

public interface SysResourceService {

    List<SysResource> findAllResources();

    List<SysResource> findWithChildren();

    List<SysResource> findAllWithAllChildren();

    SysResource findResourceById(Long id);

    int addResource(SysResource sysResource);

    int updateResource(SysResource sysResource);

    int deleteResource(Long[] ids);
}
