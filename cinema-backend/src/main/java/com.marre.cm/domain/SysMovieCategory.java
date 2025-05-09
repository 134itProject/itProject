package com.marre.cm.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * 电影分类实体类
 *
 * @author marRE
 */

@Data
public class SysMovieCategory implements Serializable{
    private static final long serivalVersionUID = 1L;

    //电影分类Id
    private Long movieCategoryId;

    //分类名称
    @NotBlank(message = "电影分类名不能为空")
    private String movieCategoryName;

    public SysMovieCategory(){

    }

    public SysMovieCategory(Long movieCategoryId, String movieCategoryName){
        this.movieCategoryId = movieCategoryId;
        this.movieCategoryName = movieCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysMovieCategory that = (SysMovieCategory) o;
        return Objects.equals(movieCategoryId, that.movieCategoryId) && Objects.equals(movieCategoryName, that.movieCategoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieCategoryId, movieCategoryName);
    }
}
