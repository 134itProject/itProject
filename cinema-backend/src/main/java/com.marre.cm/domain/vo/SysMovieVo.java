package com.marre.cm.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 封装电影查询的条件
 *
 */

@Data
public class SysMovieVo implements Serializable {

    public static final Long serialVersionUID = 1L;

    private String movieName;

    private String movieArea;

    //电影类型
    private Long movieCategoryId;

    private Date startDate;

    private Date endDate;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass())return false;
        SysMovieVo that = (SysMovieVo) o;
        return Objects.equals(movieName, that.movieName) && Objects.equals(movieArea, that.movieArea) && Objects.equals(movieCategoryId, that.movieCategoryId);

    }

    @Override
    public int hashCode(){
        return Objects.hash(movieName, movieArea, movieCategoryId);
    }
}
