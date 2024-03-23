package com.marre.cm.service.impl;


import com.marre.cm.common.utils.JwtUtil;
import com.marre.cm.common.utils.SaltUtils;
import com.marre.cm.domain.LoginUser;
import com.marre.cm.domain.SysUser;
import com.marre.cm.domain.vo.SysUserVo;
import com.marre.cm.mapper.SysUserMapper;
import com.marre.cm.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :marRE
 * @Date :2024/3/22
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> findAllUsers(SysUser sysUser) {
        return sysUserMapper.findAllUsers(sysUser);
    }

    @Override
    public SysUser findUserById(Long id) {
        return sysUserMapper.findUserById(id);
    }

    @Override
    public SysUser findUserByName(String userName) {
        return sysUserMapper.findUserByName(userName);
    }

    /**
     * 处理注册逻辑
     *
     * @param sysUser
     * @return
     */
    @Override
    public int addUser(SysUser sysUser) {
        if (!isUserNameUnique(sysUser.getUserName(), -1L)) {
            throw new AuthenticationException("用户名重复");
        }
        //处理密码：md5 + salt + hash散列
        String salt = SaltUtils.getSalt(8);
        Md5Hash md5Hash = new Md5Hash(sysUser.getPassword(), salt, 1024);

        sysUser.setPassword(md5Hash.toHex());
        sysUser.setSalt(salt);
        return sysUserMapper.addUser(sysUser);
    }

    @Override
    public int updateUser(SysUser sysUser) {
        if (!isUserNameUnique(sysUser.getUserName(), sysUser.getUserId())) {
            throw new AuthenticationException("用户名重复");
        }
        SysUser originUser = sysUserMapper.findUserById(sysUser.getUserId());
        if (originUser == null) {
            throw new AuthenticationException("用户不存在");
        }

        if (!originUser.getPassword().equals(sysUser.getPassword())) {
            //修改了密码
            //重新处理密码存储
            String salt = SaltUtils.getSalt(8);
            Md5Hash md5Hash = new Md5Hash(sysUser.getPassword(), salt, 1024);

            sysUser.setPassword(md5Hash.toHex());
            sysUser.setSalt(salt);
        }
        return sysUserMapper.updateUser(sysUser);
    }

    @Override
    public int deleteUser(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += sysUserMapper.deleteUser(id);
        }
        return rows;
    }

    @Override
    public LoginUser login(SysUserVo sysUserVo) {
        //登录，先查询用户信息
        SysUser user = sysUserMapper.findUserByName(sysUserVo.getUserName());
        if (user == null) {
            throw new AuthenticationException("用户名不存在");
        }

        //验证密码
        Md5Hash md5Hash = new Md5Hash(sysUserVo.getPassword(), user.getSalt(), 1024);
        if (!user.getPassword().equals(md5Hash.toHex())) {
            throw new AuthenticationException("用户名或密码错误");
        }

        //设置登录用户对象
        LoginUser loginUser = findLoginUser(sysUserVo);

        //颁发token
        String token = JwtUtil.sign(user.getUserName(), user.getPassword());
        loginUser.setToken(token);
        return loginUser;
    }


    @Override
    public LoginUser findLoginUser(SysUserVo sysUserVo) {
        return sysUserMapper.findLoginUser(sysUserVo);
    }

    @Override
    public boolean isUserNameUnique(String userName, Long userId) {
        List<Long> userIds = sysUserMapper.checkUserNameUnique(userName);
        for (Long id : userIds) {
            if (id.equals(userId)) {
                return true;
            }
        }
        return userIds.isEmpty();
    }
}
