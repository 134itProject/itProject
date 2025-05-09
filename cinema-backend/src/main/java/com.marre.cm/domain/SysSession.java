package com.marre.cm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 场次实体类
 *
 * @author: marRE
 *
 */
@Data
public class SysSession implements Serializable{

    private static final Long serialVersionUID = 1L;

    //场次编号
    private Long sessionId;

    //影厅号
    @NotNull(message = "场次所在影厅为空")
    private Long hallId;

    //语言 版本
    @NotBlank(message = "电影语言版本为空")
    private String languageVersion;

    //电影编号
    @NotBlank(message = "电影编号为空")
    private Long movieId;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "HH:mm")
    private String playTime;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "HH:mm")
    private String endTime;

    //deadline之前不可以删改电影和影厅信息
    private String deadline;

    //场次与日期
    @NotNull(message = "场次日期为空")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "HH:mm")
    private LocalDate sessionDate;

    //场次票价
    @NotNull(message = "场次票价为空")
    @Size(min = 0, message = "票价不能是负数，兄弟")
    private Double sessionPrice;

    private String sessionTips;

    @NotBlank(message = "座位信息为空")
    private String sessionSeats;

    private Integer seatNums;

    //已售座位
    private Integer soldNums;

    private SysHall sysHall;

    //多表连接
    private SysMovie sysMovie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysSession that = (SysSession) o;
        return Objects.equals(sessionId, that.sessionId) && Objects.equals(hallId, that.hallId) && Objects.equals(languageVersion, that.languageVersion) && Objects.equals(movieId, that.movieId) && Objects.equals(playTime, that.playTime) && Objects.equals(endTime, that.endTime) && Objects.equals(deadline, that.deadline) && Objects.equals(sessionDate, that.sessionDate) && Objects.equals(sessionPrice, that.sessionPrice) && Objects.equals(sessionTips, that.sessionTips) && Objects.equals(sessionSeats, that.sessionSeats) && Objects.equals(seatNums, that.seatNums) && Objects.equals(soldNums, that.soldNums) && Objects.equals(sysHall, that.sysHall) && Objects.equals(sysMovie, that.sysMovie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, hallId, languageVersion, movieId, playTime, endTime, deadline, sessionDate, sessionPrice, sessionTips, sessionSeats, seatNums, soldNums, sysHall, sysMovie);
    }

}
