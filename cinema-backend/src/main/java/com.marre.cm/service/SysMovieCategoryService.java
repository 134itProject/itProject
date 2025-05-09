package com.marre.cm.service;

import com.marre.cm.domain.SysMovieCategory;
import com.marre.cm.domain.SysMovieToCategory;

import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/21
 */

public interface SysMovieCategoryService {
    List<SysMovieCategory> findAllCategorys();

    SysMovieCategory findCategoryById(Long id);

    int addCategory(SysMovieCategory sysMovieCategory);

    int updateCategory(SysMovieCategory sysMovieCategory);

    int deleteCategory(Long[] ids);

    int addMovieToCategory(SysMovieToCategory sysMovieToCategory);

    int deleteMovieToCategory(SysMovieToCategory sysMovieToCategory);

}
