package com.marre.cm.mapper;

import com.marre.cm.domain.SysMovie;
import com.marre.cm.domain.vo.SysMovieVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 电影持久层
 *
 * @author :marRE
 * @Date :2024/3/17
 */
@Mapper
public interface SysMovieMapper {

    List<SysMovie> findAllMovies(SysMovieVo sysMovieVo);

    SysMovie findMovieById(Long id);

    SysMovie findOneMovie(Long id);

    int addMovie(SysMovie sysMovie);

    int updateMovie(SysMovie sysMovie);

    int deleteMovie(Long id);

    List<SysMovie> findMovieByCinemaId(Long id);

    //总票房
    List<SysMovie> totalBoxOfficeList();
    //国内票房
    List<SysMovie> domesticBoxOfficeList();
    //国外票房
    List<SysMovie> foreignBoxOfficeList();


}
