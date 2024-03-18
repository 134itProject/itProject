package com.marre.cm.domain;

import com.mysql.cj.log.Log;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * 当前登录用户
 * @author  :marRE
 */

@Data
public class LoginUser implements Serializable{

    //登录信息
    private SysUser sysUser;
    //影院ID
    private Long cinemaId;
    //影院名
    private String cinemaName;
    //Token
    private String token;

    public LoginUser(){

    }

    public LoginUser(SysUser sysUser, Long cinemaId, String cinemaName, String token){

        this.sysUser=sysUser;
        this.cinemaId=cinemaId;
        this.cinemaName=cinemaName;
        this.token=token;
    }

    /**
     *
     * LoginUser{
     *     sysUser=admin, // User对象的详细信息
     *     cinemaId=123, // 影院ID的值
     *     cinemaName='Cinema XYZ', // 影院名称的值
     *     token='abcdef123456' // 登录令牌的值
     * }
     *
     * @return
     */

    @Override
    public String toString() {
        return "LoginUser{" +
                "sysUser=" + sysUser + ", cinemaId=" + cinemaId + ", cinemaName='" + cinemaName + '\'' + ", token='" + token + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) { //强对比
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginUser loginUser = (LoginUser) o;
        return Objects.equals(sysUser, loginUser.sysUser) && Objects.equals(cinemaId, loginUser.cinemaId) && Objects.equals(cinemaName, loginUser.cinemaName) && Objects.equals(token, loginUser.token);
    }

    @Override
    public int hashCode() { //hash
        return Objects.hash(sysUser, cinemaId, cinemaName, token);
    }

    /**
     *
     *
     * CRUD
     *
     */

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
