package com.marre.cm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 影院实体
 *
 * @author  :marRE
 */
@Data
public class SysCinema implements Serializable{

    private final static Long serialVersionUID = 1L;

    private Long cinemaId;

    @NotBlank(message = "影院名称为空")
    private String cinemaName;

    //影厅类别
    private String hallCategoryList;

    private String cinemaPicture;

    private String cinemaAddress;

    private String cinemaPhone;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "HH:mm")
    private String workStartTime;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "HH:mm")
    private String workEndTime;

    private List<SysMovie> sysMovieList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysCinema sysCinema = (SysCinema) o;
        return Objects.equals(cinemaId, sysCinema.cinemaId) && Objects.equals(cinemaName, sysCinema.cinemaName) && Objects.equals(hallCategoryList, sysCinema.hallCategoryList) && Objects.equals(cinemaPicture, sysCinema.cinemaPicture) && Objects.equals(cinemaAddress, sysCinema.cinemaAddress) && Objects.equals(cinemaPhone, sysCinema.cinemaPhone) && Objects.equals(workStartTime, sysCinema.workStartTime) && Objects.equals(workEndTime, sysCinema.workEndTime) && Objects.equals(sysMovieList, sysCinema.sysMovieList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cinemaId, cinemaName, hallCategoryList, cinemaPicture, cinemaAddress, cinemaPhone, workStartTime, workEndTime, sysMovieList);
    }

}
