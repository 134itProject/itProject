package com.marre.cm.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.marre.cm.domain.SysBill;
import com.marre.cm.domain.SysSession;
import com.marre.cm.service.SysBillService;
import com.marre.cm.service.impl.SysBillServiceImpl;
import com.marre.cm.service.impl.SysSessionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :marRE
 * @Description: 订单取消支付工具类
 * @Date :2024/3/23
 */
@Component
public class CancelTimeoutBillUtil {

    @Resource
    private SysBillServiceImpl sysBillService;

    @Resource
    private SysSessionServiceImpl sysSessionService;

    //取消所有超时订单

    @Bean(name = "cancelTimeoutBill")
    public void cancelTimeoutBill() {

        //查询所有超时未支付订单
        List<SysBill> timeoutBillList = sysBillService.findTimeoutBill();

        //循环遍历，处理超时订单
        timeoutBillList.forEach((timeoutBill) -> {
            // 更新订单取消状态
            timeoutBill.setCancelState(true);
            // 更新订单信息
            sysBillService.updateBill(timeoutBill);

            SysSession curSession = timeoutBill.getSysSession();
            if (curSession == null) {
                throw new DataNotFoundException("场次不存在");
            }
            System.out.println(curSession.getSessionSeats());

            // 获取当前超时订单座位信息
            String[] selectSeats = timeoutBill.getSeats().split(",");
            // 取消的订单座位数
            int cancelSallNums = selectSeats.length;
            curSession.setSoldNums(curSession.getSoldNums() - cancelSallNums);
            System.out.println(selectSeats.length);
            // 超时订单已选座位
            Map<String, Integer> selectedSeatsMap = new LinkedHashMap<>();
            for (int i = 0; i < selectSeats.length; i++) {
                String row = selectSeats[i].substring(selectSeats[i].indexOf("\"") + 1, selectSeats[i].indexOf("排"));
                Integer col = Integer.parseInt(selectSeats[i].substring(selectSeats[i].indexOf("排") + 1, selectSeats[i].indexOf("座")));
                selectedSeatsMap.put(row, col);
            }
            // 显示已选座位坐标
            selectedSeatsMap.forEach((key, value) -> {
                System.out.println("key = " + key + " value=" + value);
            });
            // 取消场次座位占座
            String newSessionSeats = cancelTimeoutBillSessionSeats(curSession.getSessionSeats(), selectedSeatsMap);
            curSession.setSessionSeats(newSessionSeats);
            sysSessionService.updateSession(curSession);
        });
    }

    /**
     * 取消场次座位占座
     *
     * @param curSessionSeats
     * @param selectedSeatsMap
     * @return 结果
     */
    public static String cancelTimeoutBillSessionSeats(String curSessionSeats, Map<String, Integer> selectedSeatsMap) {
        JSONObject curSessionSeatsJSON = JSONObject.parseObject(curSessionSeats);
        Map<String, Object> valueMap = new LinkedHashMap<>();
        valueMap.putAll(curSessionSeatsJSON);
        valueMap.forEach((key, value) -> System.out.println("\"" + key + "\":" + "   " + value));
        // 取消选座
        selectedSeatsMap.forEach((index, value) -> {
            ((JSONArray) valueMap.get(index)).set(value - 1, 0);
        });
        JSONObject newSessionSeatsJSON = new JSONObject(valueMap);
        return JSONObject.toJSONString(newSessionSeatsJSON);
    }
}
