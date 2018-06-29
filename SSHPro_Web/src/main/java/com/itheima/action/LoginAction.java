package com.itheima.action;

import com.itheima.constants.SysConstant;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.UtilFuns;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * @Description: 登录和退出类
 * @Author: 传智播客 java学院	传智.宋江
 * @Company: http://java.itcast.cn
 * @CreateDate: 2014年10月31日
 */
public class LoginAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //SSH传统登录方式
    public String login() throws Exception {

//		if(true){
//			String msg = "登录错误，请重新填写用户名密码!";
//			this.addActionError(msg);
//			throw new Exception(msg);
//		}
//		User user = new User(username, password);
//		User login = userService.login(user);
//		if (login != null) {
//			ActionContext.getContext().getValueStack().push(user);
//			session.put(SysConstant.CURRENT_USER_INFO, login);	//记录session
//			return SUCCESS;
//		}
//		return "login";

        if (UtilFuns.isEmpty(username)) {
            return "login";
        } else {
            try {
                Subject subject = SecurityUtils.getSubject();
                //1.获得用户名 密码 token
                AuthenticationToken token = new UsernamePasswordToken(username, password);
                //2.调用登录方法
                subject.login(token);
                //3.登录成功后 从shiro获取登录用户信息
                User user = (User) subject.getPrincipal();

                session.put(SysConstant.CURRENT_USER_INFO, user);
            } catch (AuthenticationException e) {
                e.printStackTrace();
                request.put("errorInfo", "对不起, 用户名或者密码错误!");
                return "login";
            }
        }

        return SUCCESS;
		/*if (UtilFuns.isEmpty(username)) {
			return "login";
		}else {
			List<User> users = userService.find("from User where userName = ?", User.class, new String[]{username});
			super.push(users.get(0));
		}
		return "test";*/
    }


    //退出
    public String logout() {
        session.remove(SysConstant.CURRENT_USER_INFO);        //删除session

        return "logout";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

