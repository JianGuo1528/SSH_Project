package com.itheima.action.sysadmin;

import com.itheima.action.BaseAction;
import com.itheima.domain.Dept;
import com.itheima.service.DeptService;
import com.itheima.utils.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

import java.util.List;

public class DeptAction extends BaseAction implements ModelDriven<Dept> {
    private Dept model = new Dept();

    public Dept getModel() {
        return model;
    }

    private Page<Dept> page = new Page<Dept>();

    public Page<Dept> getPage() {
        return page;
    }

    //注入deptService
    private DeptService deptService;

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 分页查询
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        deptService.findPage("from Dept", page, Dept.class, null);
        //设置分页url
        page.setUrl("deptAction_list");
        super.push(page);
        return "list";
    }

    /**
     * 查看部门
     *
     * @return
     * @throws Exception
     */
    public String toview() throws Exception {
        System.out.println(model.getId());
        Dept dept = deptService.get(Dept.class, model.getId());
        super.push(dept);
        return "toview";
    }

    /**
     * 新增部门
     *
     * @return
     * @throws Exception
     */
    public String tocreate() throws Exception {
        List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
        super.put("deptList", deptList);
        return "tocreate";
    }

    /**
     * 保存新增部门
     *
     * @return
     * @throws Exception
     */
    public String insert() throws Exception {
        deptService.saveOrUpdate(model);
        return "alist";
    }

    /**
     * 更新部门信息
     *
     * @return
     * @throws Exception
     */
    public String toupdate() throws Exception {
        Dept dept = deptService.get(Dept.class, model.getId());
        super.push(dept);

        List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
        //自己不能成为自己的父部门
        deptList.remove(dept);
        super.put("deptList", deptList);
        return "toupdate";
    }

    /**
     * 保存更新后部门信息
     *
     * @return
     * @throws Exception
     */
    public String update() throws Exception {
        Dept dept = deptService.get(Dept.class, model.getId());
        dept.setParent(model.getParent());
        dept.setDeptName(model.getDeptName());

        deptService.saveOrUpdate(dept);
        return "saveUpdate";
    }

    public String delete() throws Exception {
        String[] ids = model.getId().split(", ");
        deptService.delete(Dept.class, ids);
        return "alist";
    }
}
