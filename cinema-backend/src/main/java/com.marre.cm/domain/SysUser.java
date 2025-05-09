package com.marre.cm.domain;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 * 用户实体类
 *
 * @author marRE
 *
 */

@Data
public class SysUser {
    private final static long serialVersionUID = 1L; //这个字段是用来确保在序列化和反序列化过程中，类和对象的版本匹配。

    private Long userId;

    @NotBlank(message = "用户名不可为空")
    private String userName;

    @NotBlank(message = "密码不可为空")
    private String password;

    private String salt; //“加盐”

    @Email(message = "邮箱格式有误")
    private String email;

    @Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$", message = "电话号码格式有错")
    //字符串必须以数字1开头。
    //第二位数字必须是3、4、5、7或8中的一个。
    //后面跟着9个数字（\d{9}）。
    //字符串必须以这9个数字结束（$表示字符串的结束）。

    private String phoneNumber;

    private Boolean sex;

    private String userPicture;

    //角色对应的用户ID 管理员/用户
    private Long roleId;

    private String birthday;

    private String autograph;

    private SysRole sysRole;

    @Override
    public boolean equals(Object o) { //每一个项目都比较
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUser sysUser = (SysUser) o;
        return Objects.equals(userId, sysUser.userId) && Objects.equals(userName, sysUser.userName) && Objects.equals(password, sysUser.password) && Objects.equals(salt, sysUser.salt) && Objects.equals(email, sysUser.email) && Objects.equals(phoneNumber, sysUser.phoneNumber) && Objects.equals(sex, sysUser.sex) && Objects.equals(userPicture, sysUser.userPicture) && Objects.equals(roleId, sysUser.roleId) && Objects.equals(birthday, sysUser.birthday) && Objects.equals(autograph, sysUser.autograph) && Objects.equals(sysRole, sysUser.sysRole);
    }

    @Override
    public int hashCode() { //Hash
        return Objects.hash(userId, userName, password, salt, email, phoneNumber, sex, userPicture, roleId, birthday, autograph, sysRole);
    }
}
