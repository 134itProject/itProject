package com.marre.cm.service.impl;


import com.marre.cm.domain.SysMovie;
import com.marre.cm.domain.vo.SysMovieVo;
import com.marre.cm.mapper.SysMovieMapper;
import com.marre.cm.service.SysMovieService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/22
 */
@Service
public class SysMovieServiceImpl implements SysMovieService {

    @Resource
    private SysMovieMapper sysMovieMapper;

    @Override
    public List<SysMovie> findAllMovies(SysMovieVo sysMovieVo) {
        return sysMovieMapper.findAllMovies(sysMovieVo);
    }

    @Override
    public SysMovie findMovieById(Long id) {
        return sysMovieMapper.findMovieById(id);
    }

    @Override
    public SysMovie findOneMovie(Long id) {
        return sysMovieMapper.findOneMovie(id);
    }

    @Override
    public int addMovie(SysMovie sysMovie) {
        return sysMovieMapper.addMovie(sysMovie);
    }

    @Override
    public int updateMovie(SysMovie sysMovie) {
        return sysMovieMapper.updateMovie(sysMovie);
    }

    @Override
    public int deleteMovie(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += sysMovieMapper.deleteMovie(id);
        }
        return rows;
    }

    /**
     * 总票房榜
     *
     * @return
     */
    @Override
    public List<SysMovie> totalBoxOfficeList() {
        return sysMovieMapper.totalBoxOfficeList();
    }

    /**
     * 国内票房榜 已上映的国内电影里，按票房取前10 国内电影 movieArea in (港台+大陆)
     *
     * @return
     */
    @Override
    public List<SysMovie> domesticBoxOfficeList() {
        return sysMovieMapper.domesticBoxOfficeList();
    }

    /**
     * 国外票房榜 已上映的国外电影里，按票房取前10 国外电影 movieArea not in (港台+大陆)
     *
     * @return
     */
    @Override
    public List<SysMovie> foreignBoxOfficeList() {
        return sysMovieMapper.foreignBoxOfficeList();
    }

}
