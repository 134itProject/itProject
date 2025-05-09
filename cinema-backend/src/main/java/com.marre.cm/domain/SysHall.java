package com.marre.cm.domain;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * 座位实体类
 *
 * @author marRE
 *
 */
@Data
public class SysHall implements Serializable {
    private final static Long serialVersionUID = 1L;

    private Long cinemaId;

    private Long hallId;

    @NotBlank(message = "影厅名字空")
    private String hallName;

    //影厅类型
    private String hallCategory;

    //座位开始数字
    private String rowStart;

    //影厅排
    @Min(value = 3, message = "<3")
    @Min(value = 50, message = ">50")
    private Integer rowNums;

    //每排座位
    @Min(value = 3, message = "<3")
    @Min(value = 50, message = ">50")
    private Integer seatNumsRow;

    //可用座位
    @Min(value = 9, message = "<9")
    @Min(value = 2500, message = ">2500")
    private Integer seatNums;

    //座位状态
    private String seatState;

    //逻辑删除
    private Boolean delState;

    //多表链接
    private SysCinema sysCinema;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysHall sysHall = (SysHall) o;
        return Objects.equals(cinemaId, sysHall.cinemaId) && Objects.equals(hallId, sysHall.hallId) && Objects.equals(hallName, sysHall.hallName) && Objects.equals(hallCategory, sysHall.hallCategory) && Objects.equals(rowStart, sysHall.rowStart) && Objects.equals(rowNums, sysHall.rowNums) && Objects.equals(seatNumsRow, sysHall.seatNumsRow) && Objects.equals(seatNums, sysHall.seatNums) && Objects.equals(seatState, sysHall.seatState) && Objects.equals(delState, sysHall.delState) && Objects.equals(sysCinema, sysHall.sysCinema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cinemaId, hallId, hallName, hallCategory, rowStart, rowNums, seatNumsRow, seatNums,
                seatState, delState, sysCinema);
    }
}
