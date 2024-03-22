package com.marre.cm.service.impl;


import com.marre.cm.domain.SysResource;
import com.marre.cm.mapper.SysResourceMapper;
import com.marre.cm.service.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/22
 */
@Slf4j
@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Resource
    private SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResource> findAllResources() {
        return sysResourceMapper.findAllResources();
    }

    @Override
    public List<SysResource> findWithChildren() {
        return sysResourceMapper.findWithChildren();
    }

    @Override
    public List<SysResource> findAllWithAllChildren() {
        return sysResourceMapper.findAllWithAllChildren();
    }

    @Override
    public SysResource findResourceById(Long id) {
        return sysResourceMapper.findResourceById(id);
    }

    @Override
    public int addResource(SysResource sysResource) {
        if (sysResource.getParentId() == 0) {
            sysResource.setLevel(1);
        } else {
            SysResource parent = this.findResourceById(sysResource.getParentId());
            if (parent != null) {
                sysResource.setLevel(parent.getLevel() + 1);
            }
        }
        return sysResourceMapper.addResource(sysResource);
    }

    @Override
    public int updateResource(SysResource sysResource) {
        if (sysResource.getParentId() == 0) {
            sysResource.setLevel(1);
        } else {
            SysResource parent = this.findResourceById(sysResource.getParentId());
            if (parent != null) {
                sysResource.setLevel(parent.getLevel() + 1);
            }
        }
        log.debug(sysResource.toString());
        return sysResourceMapper.updateResource(sysResource);
    }

    @Override
    public int deleteResource(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += sysResourceMapper.deleteResource(id);
        }
        return rows;
    }
}
