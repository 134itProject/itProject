package com.marre.cm.common.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import sun.text.normalizer.ICUBinary;

/**
 * @author :marRE
 * @Description: 实现shiro的AuthenticationToken接口的类JwtToken
 * @Date :2024/3/30
 */

public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
