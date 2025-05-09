package com.marre.cm.controller;

import com.marre.cm.common.exception.DataNotFoundException;
import com.marre.cm.common.response.R;
import com.marre.cm.common.utils.ApplicationContextUtils;
import com.marre.cm.domain.SysBill;
import com.marre.cm.domain.SysSession;
import com.marre.cm.domain.SysMovie;
import com.marre.cm.domain.vo.SysBillVo;
import com.marre.cm.service.SysBillService;
import com.marre.cm.service.SysMovieService;
import com.marre.cm.service.SysSessionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Description:
 * @Date :2024/4/1
 */

@RestController
public class SysBillController extends BaseController{

    @Resource
    private SysBillService sysBillService;

    @Resource
    private SysSessionService sysSessionService;

    @Resource
    private SysMovieService sysMovieService;

    /**
     * 查询所有订单
     *
     * @param sysBill 订单信息
     * @return 订单列表
     */
    @GetMapping("/sysBill")
    public R findAllBills(SysBill sysBill) {
        startPage();
        // 取消所有超时订单并释放占座资源
        ApplicationContextUtils.getBean("cancelTimeoutBill");
        List<SysBill> data = sysBillService.findAllBills(sysBill);
        return getResult(data);
    }

    /**
     * 查询订单
     *
     * @param id 订单id
     * @return 订单信息
     */
    @GetMapping("/sysBill/{id}")
    public R findBillById(@PathVariable Long id) {
        return getResult(sysBillService.findBillById(id));
    }

    /**
     * 添加订单场次
     *
     * @param sysBillVo 订单信息
     * @return 结果
     */
    @PostMapping("/sysBill")
    public R addBill(@Validated @RequestBody SysBillVo sysBillVo) {
        // 获取当前场次信息
        SysSession curSession = sysSessionService.findOneSession(sysBillVo.getSysBill().getSessionId());
        if (curSession == null) {
            throw new DataNotFoundException("添加订单的场次没找到");
        }
        System.out.println(curSession.getSessionSeats());
        curSession.setSessionSeats(sysBillVo.getSessionSeats());

        int addSallNums = sysBillVo.getSysBill().getSeats().split(",").length;
        curSession.setSoldNums(curSession.getSoldNums() + addSallNums);
        // 更新场次座位信息
        sysSessionService.updateSession(curSession);

        Object obj = sysBillService.addBill(sysBillVo.getSysBill());
        if (obj instanceof Integer) {
            return getResult(obj);
        }
        return getResult(obj);
    }

    /**
     * 支付订单
     *
     * @param sysBill 订单
     * @return 结果
     */
    @PutMapping("/sysBill")
    public R pay(@RequestBody SysBill sysBill) {
        int rows = sysBillService.updateBill(sysBill);
        if (rows > 0 && sysBill.getPayState()) {
            //更新场次的座位状态
            SysSession curSession = sysSessionService.findOneSession(sysBill.getSessionId());
            if (curSession == null) {
                throw new DataNotFoundException("支付订单的场次没找到");
            }
            //更新电影票房
            SysMovie curMovie = sysMovieService.findOneMovie(curSession.getMovieId());
            if (curMovie == null) {
                throw new DataNotFoundException("支付订单的电影没找到");
            }
            //订单的座位数
            int seatNum = sysBill.getSeats().split(",").length;
            double price = curSession.getSessionPrice();
            curMovie.setMovieBoxOffice(curMovie.getMovieBoxOffice() + seatNum * price);
            sysMovieService.updateMovie(curMovie);
        }
        return getResult(rows);
    }

    /**
     * 取消订单
     *
     * @param sysBillVo 订单信息
     * @return 结果
     */
    @PutMapping("/sysBill/cancel")
    public R cancel(@RequestBody SysBillVo sysBillVo) {
        // 订单取消，更新订单状态
        int rows = sysBillService.updateBill(sysBillVo.getSysBill());
        if (rows > 0 && sysBillVo.getSysBill().getCancelState()) {
            // 订单取消座位不再占用，更新场次的座位状态
            SysSession curSession = sysSessionService.findOneSession(sysBillVo.getSysBill().getSessionId());
            // 取消的订单座位数
            int cancelSallNums = sysBillVo.getSysBill().getSeats().split(",").length;
            curSession.setSoldNums(curSession.getSoldNums() - cancelSallNums);
            if (curSession == null) {
                throw new DataNotFoundException("添加订单的场次没找到");
            }
            curSession.setSessionSeats(sysBillVo.getSessionSeats());
            sysSessionService.updateSession(curSession);
        }
        return getResult(rows);
    }

    /**
     * 删除订单
     *
     * @param ids 订单id
     * @return 结构
     */
    @DeleteMapping("/sysBill/{ids}")
    public R deleteBill(@PathVariable Long[] ids) {
        return getResult(sysBillService.deleteBill(ids));
    }

}
