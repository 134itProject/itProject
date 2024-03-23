package com.marre.cm.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Date;

/**
 * @author :marRE
 * @Description:签名校验token工具类
 * @Date :2024/3/23
 */

public class JwtUtil {

    static Logger log = LoggerFactory.getLogger(JwtUtil.class);

    //Jwt验证过期时间 EXPIRE_TIME 分钟 设置一个小时
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    /**
     *
     * @Description:校验TOKEN
     *
     * @param token 密钥
     * @param secret 密码
     * @param username 账户名
     * @return 正确或否
     */

    public static boolean verify(String token, String username, String secret){
     try {
         //根据密码生成Jwt校验器
         Algorithm algorithm = Algorithm.HMAC256(secret);
         JWTVerifier verifier = JWT.require(algorithm)
                 .withClaim("username", username)
                 .build();
         //校验用户的token
         DecodedJWT jwt = verifier.verify(token);
         log.info("登录token检验成功");
         return true;
     }catch (Exception exception){
         log.error("token校验失败");
         return false;
     }
    }

    /**
     * @Decription:获得token信息后无需secret
     *
     * @return token中的用户名
     */

    public static String getUsername(String token){
        try{
            DecodedJWT jwt = JWT.decode(token);
            return  jwt.getClaim("username").asString();
        }catch (JWTDecodeException e ){
            return null;
        }
    }

    /**
     * @Decription: 生成token签名 EXPIRE_TIME 分钟后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);

    }

    public static void main(String[] args) {
        /**
         * 测试生成一个token
         * 结果：eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDc0ODI5OTYsInVzZXJuYW1lIjoi5ZGo5YWrIn0.UP6kFC0BofuX7FLoPDMWCQno-NhVuYA0NlQG8xgt2Rc
         */
        String sign = sign("周八", "ssjsaas");
        log.warn("测试生成一个token\n" + sign);
    }
}
