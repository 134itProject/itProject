package com.marre.cm.controller;

import com.marre.cm.common.response.R;
import com.marre.cm.common.utils.ApplicationContextUtils;
import com.marre.cm.domain.SysSession;
import com.marre.cm.domain.vo.SysSessionVo;
import com.marre.cm.service.SysSessionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Description:电影场次控制层
 * @Date :2024/4/10
 */

@RestController
public class SysSessionController extends BaseController {

    @Resource
    private SysSessionService sysSessionService;

    /**
     * 查询所有场次 带查询条件
     *
     * @param sysSessionVo 场次信息
     * @return 结果
     */
    @GetMapping("/sysSession")
    public R findByQuery(SysSessionVo sysSessionVo) {
        startPage();
        List<SysSession> list = sysSessionService.findByVo(sysSessionVo);
        return getResult(list);
    }

    /**
     * 通过id查询场次
     *
     * @param id 场次id
     * @return 结果
     */
    @GetMapping("/sysSession/find/{id}")
    public R findSessionById(@PathVariable Long id) {
        // 取消所有超时订单并释放占座资源
        ApplicationContextUtils.getBean("cancelTimeoutBill");
        return getResult(sysSessionService.findSessionById(id));
    }

    /**
     * 通过电影id,排座id查询场次
     *
     * @param sysSession 场次信息
     * @return 结果
     */
    @GetMapping("/sysSession/isAbleEdit")
    public R findSessionByMovieIdOrHallId(SysSession sysSession) {
        return getResult(sysSessionService.findSessionByMovieIdOrHallId(sysSession));
    }

    /**
     * 添加场次
     *
     * @param sysSession 场次信息
     * @return 结果
     */
    @PostMapping("/sysSession")
    public R addSession(@RequestBody SysSession sysSession) {
        return getResult(sysSessionService.addSession(sysSession));
    }

    /**
     * 更新场次
     *
     * @param sysSession 场次信息
     * @return 结果
     */
    @PutMapping("/sysSession")
    public R updateSession(@RequestBody SysSession sysSession) {
        return getResult(sysSessionService.updateSession(sysSession));
    }

    /**
     * 删除场次
     *
     * @param ids 场次id
     * @return 结果
     */
    @DeleteMapping("/sysSession/{ids}")
    public R deleteSession(@PathVariable Long[] ids) {
        return getResult(sysSessionService.deleteSession(ids));
    }

}
