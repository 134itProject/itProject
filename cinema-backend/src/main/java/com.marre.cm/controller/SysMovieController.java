package com.marre.cm.controller;

import com.marre.cm.common.constant.MovieRankingList;
import com.marre.cm.common.response.R;
import com.marre.cm.domain.SysMovie;
import com.marre.cm.domain.vo.SysMovieVo;
import com.marre.cm.service.SysMovieService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author :marRE
 * @Description:电影控制层
 * @Date :2024/4/10
 */

@RestController
public class SysMovieController extends BaseController{

    @Resource
    private SysMovieService sysMovieService;

    /**
     * 查询所有电影
     *
     * @param sysMovieVo 查询条件
     * @return 电影
     */
    @GetMapping("/sysMovie/find")
    public R findAllMovies(SysMovieVo sysMovieVo) {
        startPage();
        List<SysMovie> data = sysMovieService.findAllMovies(sysMovieVo);
        return getResult(data);
    }

    /**
     * 通过id查询电影
     *
     * @param id 电影id
     * @return 电影
     */
    @GetMapping("/sysMovie/find/{id}")
    public R findMovieById(@PathVariable Long id) {
        return getResult(sysMovieService.findMovieById(id));
    }

    /**
     * 添加电影
     *
     * @param sysMovie 电影信息
     * @return 结果
     */
    @PostMapping("/sysMovie")
    public R addMovie(@Validated @RequestBody SysMovie sysMovie) {
        return getResult(sysMovieService.addMovie(sysMovie));
    }

    /**
     * 更新电影
     *
     * @param sysMovie 电影信息
     * @return 结果
     */
    @PutMapping("/sysMovie")
    public R updateMovie(@Validated @RequestBody SysMovie sysMovie) {
        return getResult(sysMovieService.updateMovie(sysMovie));
    }

    /**
     * 删除电影
     *
     * @param ids 电影id
     * @return 结果
     */
    @DeleteMapping("/sysMovie/{ids}")
    public R deleteMovie(@PathVariable Long[] ids) {
        return getResult(sysMovieService.deleteMovie(ids));
    }

    /**
     * 查询电影榜单
     *
     * @param listId 榜单id
     * @return 榜单
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @GetMapping("/sysMovie/find/rankingList/{listId}")
    public R findRankingList(@PathVariable Integer listId) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        if (listId <= 0 || listId > 4) {
            //暂时只支持4种榜单
            return R.error("抱歉，暂时只支持3种榜单，id为[1,3]");
        }
        Method getList = sysMovieService.getClass().getMethod(MovieRankingList.listNames[listId - 1]);
        startPage();
        List<SysMovie> data = (List<SysMovie>) getList.invoke(sysMovieService);
        return getResult(data);
    }
}
