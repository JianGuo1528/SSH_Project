package com.itheima.shiro;

import com.itheima.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.util.Arrays;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //1.向下转型
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //2.获得加密后密码
        Object encryptedPwd = Encrypt.md5(new String(upToken.getPassword()), upToken.getUsername());
        //3.将加密后密码与数据库中密码进行比较
        return this.equals(encryptedPwd, info.getCredentials());
    }
}
