package com.marre.cm.common.config;

import com.marre.cm.common.utils.ApplicationContextUtils;
import com.marre.cm.common.utils.PathUtils;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

/**
 * @author :marRE
 * @Description: Quartz 定时任务配置
 * @Date :2024/3/30
 */
@Configuration
public class QuartzConfig{

    /**
     * 超时订单释放任务
     *
     * @return JobDetail
     */
    @Bean
    public JobDetail jobDetail() {
        QuartzJobBean quartzJob = new QuartzJobBean() {
            @Override
            protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("执行取消超时订单处理" + sdf.format(new Date()));
                // 取消所有超时订单并释放占座资源
                ApplicationContextUtils.getBean("cancelTimeoutBill");
            }
        };
        return JobBuilder.newJob(quartzJob.getClass()).storeDurably().build();
    }

    @Bean
    public SimpleTrigger trigger() {
        //每5分钟执行一次，一直重复执行
        SimpleScheduleBuilder scheduleBuilder =
                SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5 * 60)
                        .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withSchedule(scheduleBuilder).build();
    }

}
