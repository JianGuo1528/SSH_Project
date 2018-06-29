package com.itheima.service;

import com.itheima.dao.BaseDao;
import com.itheima.domain.Module;
import com.itheima.utils.Page;
import com.itheima.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class ModuleServiceImpl implements ModuleService {
    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    public Module get(Class<Module> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    public Page<Module> findPage(String hql, Page<Module> page, Class<Module> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    public void saveOrUpdate(Module entity) {
        if (UtilFuns.isEmpty(entity.getId())) {

        }
        baseDao.saveOrUpdate(entity);
    }

    public void saveOrUpdateAll(Collection<Module> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    public void deleteById(Class<Module> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id);
    }

    public void delete(Class<Module> entityClass, Serializable[] ids) {
        baseDao.delete(entityClass, ids);
    }
}
