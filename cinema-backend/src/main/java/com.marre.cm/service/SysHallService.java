package com.marre.cm.service;

import com.marre.cm.domain.SysHall;

import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/21
 */

public interface SysHallService {

    List<SysHall> findAllHalls(SysHall sysHall);

    SysHall findHallById(SysHall sysHall);

    int addHall(SysHall sysHall);
    int updateHall(SysHall sysHall);
    int deleteHall(SysHall sysHall);
}
