package com.marre.cm.service.impl;


import com.marre.cm.domain.SysHall;
import com.marre.cm.mapper.SysHallMapper;
import com.marre.cm.service.SysHallService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/22
 */
@Service
public class SysHallServiceImpl implements SysHallService {

    @Resource
    private SysHallMapper sysHallMapper;

    @Override
    public List<SysHall> findAllHalls(SysHall sysHall) {
        return sysHallMapper.findAllHalls(sysHall);
    }

    @Override
    public SysHall findHallById(SysHall sysHall) {
        return sysHallMapper.findHallById(sysHall);
    }

    @Override
    public int addHall(SysHall sysHall) {
        return sysHallMapper.addHall(sysHall);
    }

    @Override
    public int updateHall(SysHall sysHall) {
        return sysHallMapper.updateHall(sysHall);
    }

    @Override
    public int deleteHall(SysHall[] sysHalls) {
        int rows = 0;
        for (SysHall sysHall : sysHalls) {
            rows += sysHallMapper.deleteHall(sysHall);
        }
        return rows;
    }
}
