package com.marre.cm.mapper;

import com.marre.cm.domain.SysBill;
import com.marre.cm.domain.SysHall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *排座持久层
 *
 * @author marRE
 */
@Mapper
public interface SysHallMapper {

    List<SysHall> findAllHalls(SysHall sysHall);

    SysHall findHallById(SysHall sysHall);

    SysHall findHall();

    int addHall(SysHall sysHall);

    int updateHall(SysHall sysHall);

    int deleteHall(SysHall sysHall);
}
