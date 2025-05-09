package com.marre.cm.mapper;

import com.marre.cm.domain.SysBill;
import com.marre.cm.domain.SysMovieCategory;
import com.marre.cm.domain.SysMovieToCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 电影分类持久层
 *
 * @author :marRE
 * @Date :2024/3/17
 */
@Mapper
public interface SysMovieCategoryMapper {

    List<SysMovieCategory> findAllCategorys();

    SysMovieCategory findCategoryById(Long id);

    List<SysMovieCategory> findOneMovieCategorys(Long id);

    int addCategory(SysMovieCategory sysMovieCategory);

    int updateCategory(SysMovieCategory sysMovieCategory);

    int deleteCategory(Long id);

    int addMovieToCategory(SysMovieToCategory sysMovieToCategory);

    int deleteMovieToCategory(SysMovieToCategory sysMovieToCategory);


}
