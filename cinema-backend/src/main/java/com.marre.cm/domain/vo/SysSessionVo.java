package com.marre.cm.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


/**
 *
 * 封装电影场次的条件
 */
@Data
public class SysSessionVo implements Serializable {

    public static final Long serialVersionUID = 1L;

    private Long hallId;

    private Long movieId;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate sessionDate;

    public SysSessionVo(){}

    public SysSessionVo(Long hallId, Long movieId, LocalDate sessionDate){
        this.hallId = hallId;
        this.movieId = movieId;
        this.sessionDate = sessionDate;
    }

}
