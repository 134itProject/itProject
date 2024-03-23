package com.marre.cm.common.utils;

import java.util.Random;

/**
 * @author :marRE
 * @Description:生成盐工具类
 * @Date :2024/3/23
 */

public class SaltUtils {

    public static String getSalt(int n) {
        //返回长度为n的随机盐 加密密码用
        StringBuilder sb = new StringBuilder();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()".toCharArray();
        for (int i = 0; i < n; i++) {
            char c = chars[new Random().nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getSalt(8));
    }
}
