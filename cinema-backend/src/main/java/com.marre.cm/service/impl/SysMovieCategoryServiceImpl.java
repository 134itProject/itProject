package com.marre.cm.service.impl;


import com.marre.cm.domain.SysMovieCategory;
import com.marre.cm.domain.SysMovieToCategory;
import com.marre.cm.mapper.SysMovieCategoryMapper;
import com.marre.cm.service.SysMovieCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/22
 */
@Service
public class SysMovieCategoryServiceImpl implements SysMovieCategoryService {

    @Resource
    private SysMovieCategoryMapper sysMovieCategoryMapper;

    @Override
    public List<SysMovieCategory> findAllCategorys() {
        return sysMovieCategoryMapper.findAllCategorys();
    }

    @Override
    public SysMovieCategory findCategoryById(Long id) {
        return sysMovieCategoryMapper.findCategoryById(id);
    }

    @Override
    public int addCategory(SysMovieCategory sysMovieCategory) {
        return sysMovieCategoryMapper.addCategory(sysMovieCategory);
    }

    @Override
    public int updateCategory(SysMovieCategory sysMovieCategory) {
        return sysMovieCategoryMapper.updateCategory(sysMovieCategory);
    }

    @Override
    public int deleteCategory(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += sysMovieCategoryMapper.deleteCategory(id);
        }
        return rows;
    }


    @Override
    public int addMovieToCategory(SysMovieToCategory sysMovieToCategory) {
        return sysMovieCategoryMapper.addMovieToCategory(sysMovieToCategory);
    }

    @Override
    public int deleteMovieToCategory(SysMovieToCategory sysMovieToCategory) {
        return sysMovieCategoryMapper.deleteMovieToCategory(sysMovieToCategory);
    }

}
