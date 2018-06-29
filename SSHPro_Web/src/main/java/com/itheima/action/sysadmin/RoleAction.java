package com.itheima.action.sysadmin;

import com.itheima.action.BaseAction;
import com.itheima.domain.Module;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import com.itheima.service.ModuleService;
import com.itheima.service.RoleService;
import com.itheima.service.UserService;
import com.itheima.utils.Page;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleAction extends BaseAction implements ModelDriven<Role> {
    private Role model = new Role();

    public Role getModel() {
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


    //注入RoleService
    private RoleService roleService;

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //注入UserService
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private ModuleService moduleService;

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    private String moduleIds;

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }

    /**
     * 分页查询
     */
    public String list() throws Exception {
        roleService.findPage("from Role", page, Role.class, null);

        //设置分页的url地址
        page.setUrl("roleAction_list");

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
        Role dept = roleService.get(Role.class, model.getId());

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
        roleService.saveOrUpdate(model);
        //跳页面
        return "alist";
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        //1.根据id,得到一个对象
        Role obj = roleService.get(Role.class, model.getId());

        //2.将对象放入值栈中
        super.push(obj);


        //5.跳页面
        return "toupdate";
    }

    /**
     * 修改
     */
    public String update() throws Exception {
        //调用业务
        Role obj = roleService.get(Role.class, model.getId());//根据id,得到一个数据库中保存的对象

        //2.设置修改的属性
        obj.setName(model.getName());
        obj.setRemark(model.getRemark());


        roleService.saveOrUpdate(obj);
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
        roleService.delete(Role.class, ids);


        return "alist";
    }

    public String tomodule() throws Exception {
        Role role = roleService.get(Role.class, model.getId());
        super.push(role);
        return "tomodule";
    }

    public String roleModuleJsonStr() throws Exception {
        Role role = roleService.get(Role.class, model.getId());
        Set<Module> moduleSet = role.getModules();

        List<Module> moduleList = moduleService.find("from Module", Module.class, null);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Module module : moduleList) {
            sb.append("{\"id\":\"").append(module.getId());
            sb.append("\", \"pId\":\"").append(module.getParentId());
            sb.append("\", \"name\":\"").append(module.getName());
            sb.append("\", \"checked\":\"");
            if (moduleSet.contains(module)) {
                sb.append("true");
            } else {
                sb.append("false");
            }
            sb.append("\"}, ");
        }
        String substring = sb.substring(0, sb.length() - 2);
        substring += "]";

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(substring);

        return NONE;
    }

    public String module() throws Exception {
        Role role = roleService.get(Role.class, model.getId());

        String[] ids = moduleIds.split(",");
        Set<Module> moduleSet = new HashSet<>();

        if (ids.length > 0) {
            for (String id : ids) {
                moduleSet.add(moduleService.get(Module.class, id));
            }
        }

        role.setModules(moduleSet);
        roleService.saveOrUpdate(role);
        return "alist";
    }
}
