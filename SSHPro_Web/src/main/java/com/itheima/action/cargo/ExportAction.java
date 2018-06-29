package com.itheima.action.cargo;

import com.itheima.action.BaseAction;
import com.itheima.domain.Contract;
import com.itheima.domain.Export;
import com.itheima.domain.User;
import com.itheima.service.ContractService;
import com.itheima.service.ExportService;
import com.itheima.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Administrator
 */
public class ExportAction extends BaseAction implements ModelDriven<Export> {
    private Export model = new Export();

    public Export getModel() {
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


    //注入ContractService
    private ExportService exportService;

    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }

    private ContractService contractService;

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * 分页查询
     */
    public String list() throws Exception {
        String hql = "from Export where 1=1 ";
		/*
		//如何确定出用户的等级
		User user =  super.getCurUser();
		int degree = user.getUserinfo().getDegree();
		if(degree==4){
			//说明是员工
			hql+=" and createBy='"+user.getId()+"'";
		}else if(degree==3){
			//说明是部门经理，管理本部门
			hql+=" and createDept='"+user.getDept().getId()+"'";
		}else if(degree==2){
			//说明是管理本部门及下属部门？？？？？
			
			
		}else if(degree==1){
			//说明是副总？？？？？
			
			
		}else if(degree==0){
			//说明是总经理
			
		}
		*/
        exportService.findPage(hql, page, Export.class, null);

        //设置分页的url地址
        page.setUrl("exportAction_list");

        //将page对象压入栈顶
        super.push(page);


        return "list";
    }


    /**
     * 查询状态为1的购销合同
     */
    public String contractList() throws Exception {
        //查询状态为1的购销合同
        String hql = "from Contract where state = 1";
        //分页查询
        contractService.findPage(hql, page, Contract.class, null);

        page.setUrl("exportAction_contractList");

        //放入值栈
        super.push(page);

        return "contractList";
    }

    /**
     * 查看
     * id=rterytrytrytr
     * model对象
     * id属性：rterytrytrytr
     */
    public String toview() throws Exception {
        //1.调用业务方法，根据id,得到对象
        Export dept = exportService.get(Export.class, model.getId());

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
     * <input type="hidden" name="contractIds" value="4028817a33812ffd0133813f25940001, 4028817a33812ffd013382048ff80024, 4028817a33812ffd0133821a8eb5002b" />
     * model对象    ：Export类型
     * id:
     * contractIds:4028817a33812ffd0133813f25940001, 4028817a33812ffd013382048ff80024, 4028817a33812ffd0133821a8eb5002b
     */
    public String insert() throws Exception {
        //1.加入细粒度权限控制的数据
        User user = super.getCurUser();
        model.setCreateBy(user.getId());//设置创建者的id
        model.setCreateDept(user.getDept().getId());//设置创建者所在部门的id

        //1.调用业务方法，实现保存
        exportService.saveOrUpdate(model);
        //跳页面
        return contractList();
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        //1.根据id,得到一个对象
        Export obj = exportService.get(Export.class, model.getId());

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
        Export obj = exportService.get(Export.class, model.getId());//根据id,得到一个数据库中保存的对象

        //2.设置修改的属性

        obj.setRemark(model.getRemark());

        exportService.saveOrUpdate(obj);
        return "alist";
    }

    /**
     * 删除
     */
    public String delete() throws Exception {
        String ids[] = model.getId().split(", ");

        //调用业务方法，实现批量删除
        exportService.delete(Export.class, ids);


        return "alist";
    }

    /**
     * 提交
     */
    public String submit() throws Exception {
        String ids[] = model.getId().split(", ");

        //2.遍历ids,并加载出每个购销合同对象，再修改购销合同的状态
        exportService.changeState(ids, 1);
        return "alist";
    }

    /**
     * 取消
     */
    public String cancel() throws Exception {
        String ids[] = model.getId().split(", ");

        //2.遍历ids,并加载出每个购销合同对象，再修改购销合同的状态
        exportService.changeState(ids, 0);
        return "alist";
    }

}
