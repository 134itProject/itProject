package com.marre.cm.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 电影实体对象
 *
 * @author: marRE
 *
 */
@Data
public class SysMovie implements Serializable{

    private static  final long serialVersionUID = 1L;

    private Long movieId;

    @NotBlank(message = "电影名不能为空")
    private String movieName;

    //时长
    private Integer movieLength;

    //海报图片
    private String moviePoster;

    //地区
    private String movieArea;

    //电影票房
    private Double movieBoxOffice;

    private String movieIntroduction;

    private String moviePictures;

    //上映日期
    private Date releaseDate;

    //电影类别
    private List<SysMovieCategory> movieCategoryList;

    //强对比
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysMovie sysMovie = (SysMovie) o;
        return Objects.equals(movieId, sysMovie.movieId) && Objects.equals(movieName, sysMovie.movieName) && Objects.equals(movieLength, sysMovie.movieLength) && Objects.equals(moviePoster, sysMovie.moviePoster) && Objects.equals(movieArea, sysMovie.movieArea) && Objects.equals(releaseDate, sysMovie.releaseDate) && Objects.equals(movieBoxOffice, sysMovie.movieBoxOffice) && Objects.equals(movieIntroduction, sysMovie.movieIntroduction) && Objects.equals(moviePictures, sysMovie.moviePictures) && Objects.equals(movieCategoryList, sysMovie.movieCategoryList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieName, movieLength, moviePoster, movieArea, releaseDate, movieBoxOffice, movieIntroduction, moviePictures, movieCategoryList);
    }
}
