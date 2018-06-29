package com.itheima.action.sysadmin;

import com.itheima.action.BaseAction;
import com.itheima.domain.Dept;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import com.itheima.service.DeptService;
import com.itheima.service.RoleService;
import com.itheima.service.UserService;
import com.itheima.utils.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import java.util.*;

public class UserAction extends BaseAction implements ModelDriven<User> {
    private User model = new User();

    public User getModel() {
        return model;
    }

    //分页查询
    private Page page = new Page();

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


    //注入UserService
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private DeptService deptService;

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    private RoleService roleService;

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    private String[] roleIds;

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * 分页查询
     */
    public String list() throws Exception {
        userService.findPage("from User", page, User.class, null);

        //设置分页的url地址
        page.setUrl("userAction_list");

        //将page对象压入栈顶
        super.push(page);


        return "list";
    }

    /**
     * 查看
     * id=rterytrytrytr
     * model对象
     * id属性：rterytrytrytr
     */
    public String toview() throws Exception {
        //1.调用业务方法，根据id,得到对象
        User dept = userService.get(User.class, model.getId());

        //放入栈顶
        super.push(dept);

        //3.跳页面
        return "toview";
    }

    /**
     * 进入新增页面
     */
    public String tocreate() throws Exception {
        //调用业务方法
        List<Dept> deptList = deptService.find("from Dept where state=1", Dept.class, null);
        super.put("deptList", deptList);

        List<User> userList = userService.find("from User where state=1", User.class, null);
        super.put("userList", userList);

        //跳页面
        return "tocreate";
    }

    /**
     * 保存
     * <s:select name="parent.id"
     * <input type="text" name="deptName" value=""/>
     * model对象能接收
     * parent
     * id
     * deptName
     */
    public String insert() throws Exception {
        //1.调用业务方法，实现保存
        userService.saveOrUpdate(model);
        //跳页面
        return "alist";
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        //1.根据id,得到一个对象
        User obj = userService.get(User.class, model.getId());

        //2.将对象放入值栈中
        super.push(obj);

        //3.查询父部门
        List<Dept> deptList = deptService.find("from Dept where state=1", Dept.class, null);

        //4.将查询的结果放入值栈中 ,它放在context区域中
        super.put("deptList", deptList);

        //5.跳页面
        return "toupdate";
    }

    /**
     * 修改
     */
    public String update() throws Exception {
        //调用业务
        User obj = userService.get(User.class, model.getId());//根据id,得到一个数据库中保存的对象

        //2.设置修改的属性
        obj.setDept(model.getDept());
        obj.setUserName(model.getUserName());
        obj.setState(model.getState());


        userService.saveOrUpdate(obj);
        return "alist";
    }

    /**
     * 删除
     * <input type="checkbox" name="id" value="100"/>
     * <input type="checkbox" name="id" value="3d00290a-1af0-4c28-853e-29fbf96a2722"/>
     * .....
     * model
     * id:String类型
     * 具有同名框的一组值如何封装数据？
     * 如何服务端是String类型：
     * 100, 3d00290a-1af0-4c28-853e-29fbf96a2722, 3d00290a-1af0-4c28-853e-29fbf96a2722
     * <p>
     * id:Integer,Float,Double.Date类型                  id=100               id=200        id=300
     * id=300
     * Integer []id;  {100,200,300}
     */
    public String delete() throws Exception {
        String ids[] = model.getId().split(", ");

        //调用业务方法，实现批量删除
        userService.delete(User.class, ids);
        return "alist";
    }

    public String torole() throws Exception {
        //压入当前用户
        User user = userService.get(User.class, model.getId());
        super.push(user);

        //压入所有角色名
        List<Role> roleList = roleService.find("from Role", Role.class, null);
//        super.put("roleList", roleList);
        ActionContext.getContext().getValueStack().set("roleList", roleList);

        //压入当前用户角色名
        Set<Role> roleSet = user.getRoles();
        StringBuilder sb = new StringBuilder();
        for (Role role : roleSet) {
            sb.append(role.getName()).append(",");
        }
//        super.put("roleStr", sb.toString()); //压入值栈种的context部分
        ActionContext.getContext().getValueStack().set("roleStr", sb.toString()); //压入值栈中的value stack部分

        return "torole";
    }

    public String role() throws Exception {
        //1.根据用户id 获取用户对象
        User user = userService.get(User.class, model.getId());

        Set<Role> set = new HashSet<>();
        //2.遍历roleIds
        for (String roleId : roleIds) {
            set.add(roleService.get(Role.class, roleId));
        }
        user.setRoles(set);

        //3.更新保存user
        userService.saveOrUpdate(user);
        System.out.println("数据是: " + testData);
        return "alist";
    }

    private String testData;

    public void setTestData(String testData) {
        this.testData = testData;
    }
}
