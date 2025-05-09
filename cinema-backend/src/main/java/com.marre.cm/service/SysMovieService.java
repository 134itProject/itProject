package com.marre.cm.service;

import com.marre.cm.domain.SysMovie;
import com.marre.cm.domain.vo.SysMovieVo;

import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/21
 */

public interface SysMovieService {
    List<SysMovie> findAllMovies(SysMovieVo sysMovieVo);

    SysMovie findMovieById(Long id);

    SysMovie findOneMovie(Long id);

    int addMovie(SysMovie sysMovie);

    int updateMovie(SysMovie sysMovie);

    int deleteMovie(Long[] ids);

    List<SysMovie> totalBoxOfficeList();

    List<SysMovie> domesticBoxOfficeList();

    List<SysMovie> foreignBoxOfficeList();
}
