package com.marre.cm.service;

import com.marre.cm.domain.SysBill;
import com.marre.cm.domain.SysCinema;

/**
 * @author :marRE
 * @Date :2024/3/21
 */

public interface SysCinemaService {

    SysCinema findCinema();

    int updateCinema(SysCinema sysCinema);

    SysCinema findCinemaById(Long id);
}
