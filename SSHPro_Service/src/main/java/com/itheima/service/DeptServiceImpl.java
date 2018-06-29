package com.itheima.service;

import com.itheima.dao.BaseDao;
import com.itheima.domain.Dept;
import com.itheima.utils.Page;
import com.itheima.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class DeptServiceImpl implements DeptService {
    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    public Dept get(Class<Dept> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    public Page<Dept> findPage(String hql, Page<Dept> page, Class<Dept> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    public void saveOrUpdate(Dept entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            entity.setState(1);
        }
        baseDao.saveOrUpdate(entity);
    }

    public void saveOrUpdateAll(Collection<Dept> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    public void deleteById(Class<Dept> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id);
    }

    public void delete(Class<Dept> entityClass, Serializable[] ids) {
        baseDao.delete(entityClass, ids);
    }
}
