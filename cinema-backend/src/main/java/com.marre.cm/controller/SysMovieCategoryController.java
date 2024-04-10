package com.marre.cm.controller;

import com.marre.cm.common.response.R;
import com.marre.cm.domain.SysMovieCategory;
import com.marre.cm.domain.SysMovieToCategory;
import com.marre.cm.service.SysMovieCategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Description:电影分类控制层
 * @Date :2024/4/10
 */
@RestController
public class SysMovieCategoryController extends BaseController{

    @Resource
    private SysMovieCategoryService sysMovieCategoryService;

    /**
     * 查询所有分类
     *
     * @return 电影分类
     */
    @GetMapping("/sysMovieCategory/find")
    public R findAllCategorys() {
        startPage();
        List<SysMovieCategory> data = sysMovieCategoryService.findAllCategorys();
        return getResult(data);
    }

    /**
     * 通过id查询分类
     *
     * @param id 分类id
     * @return 分类
     */
    @GetMapping("/sysMovieCategory/{id}")
    public R findCategoryById(@PathVariable Long id) {
        return getResult(sysMovieCategoryService.findCategoryById(id));
    }

    /**
     * 添加电影分类
     *
     * @param sysMovieCategory 电影分类信息
     * @return 结果
     */
    @PostMapping("/sysMovieCategory")
    public R addCategory(@Validated @RequestBody SysMovieCategory sysMovieCategory) {
        return getResult(sysMovieCategoryService.addCategory(sysMovieCategory));
    }

    /**
     * 更新电影分类
     *
     * @param sysMovieCategory 电影分类信息
     * @return 结果
     */
    @PutMapping("/sysMovieCategory")
    public R updateCategory(@Validated @RequestBody SysMovieCategory sysMovieCategory) {
        return getResult(sysMovieCategoryService.updateCategory(sysMovieCategory));
    }

    /**
     * 删除电影分类
     *
     * @param ids 电影分类id
     * @return 结果
     */
    @DeleteMapping("/sysMovieCategory/{ids}")
    public R deleteCategory(@PathVariable Long[] ids) {
        return getResult(sysMovieCategoryService.deleteCategory(ids));
    }

    /**
     * 添加电影到指定分类
     *
     * @param movieId         电影id
     * @param movieCategoryId 分类id
     * @return 结果
     */
    @PostMapping("/sysMovieToCategory/{movieId}/{movieCategoryId}")
    public R addMovieToCategory(@PathVariable Long movieId, @PathVariable Long movieCategoryId) {
        return getResult(sysMovieCategoryService.addMovieToCategory(new SysMovieToCategory(movieId, movieCategoryId)));
    }

    /**
     * 删除电影从指定分类
     *
     * @param movieId         电影id
     * @param movieCategoryId 分类id
     * @return 结果
     */
    @DeleteMapping("/sysMovieToCategory/{movieId}/{movieCategoryId}")
    public R deleteMovieToCategory(@PathVariable Long movieId, @PathVariable Long movieCategoryId) {
        return getResult(sysMovieCategoryService.deleteMovieToCategory(new SysMovieToCategory(movieId,
                movieCategoryId)));
    }

}
