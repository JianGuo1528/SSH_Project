package com.itheima.shiro;

import com.itheima.domain.Module;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        System.out.println("授权...");
        User user = (User) pc.fromRealm(this.getName()).iterator().next();//根据realm的名字去找对应的realm

        Set<Role> roles = user.getRoles();//对象导航
        List<String> permissions = new ArrayList<String>();
        for (Role role : roles) {
            //遍历每个角色
            Set<Module> modules = role.getModules();//得到每个角色下的模块列表
            for (Module m : modules) {
                permissions.add(m.getName());
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//添加用户的模块（权限）
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证...");
        //1.向下转型
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //2.从数据库中获取User对象
        List<User> users = userService.find("from User where userName = ?0 ", User.class, new String[]{upToken.getUsername()});
        if (users != null && users.size() > 0) {
            User user = users.get(0);
            AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return info;//跳到CustomCredentialsMatcher中的doCredentialsMatch方法
        }
        //3.发生异常 返回null
        return null;
    }
}
