package com.marre.cm.mapper;

import com.marre.cm.domain.SysBill;
import com.marre.cm.domain.SysCinema;
import org.apache.ibatis.annotations.Mapper;

/**
 *影院持久层
 *
 * @author marRE
 */
@Mapper
public interface SysCinemaMapper {

    SysCinema findCinemaById(Long id);

    SysCinema findCinema();

    int updateCinema(SysCinema sysCinema);


}
