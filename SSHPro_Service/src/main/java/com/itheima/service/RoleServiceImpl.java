package com.itheima.service;

import com.itheima.dao.BaseDao;
import com.itheima.domain.Role;
import com.itheima.utils.Page;
import com.itheima.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Transactional
public class RoleServiceImpl implements RoleService {
    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    public Role get(Class<Role> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    public Page<Role> findPage(String hql, Page<Role> page, Class<Role> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    public void saveOrUpdate(Role entity) {
        if (UtilFuns.isEmpty(entity.getId())) {

        }
        baseDao.saveOrUpdate(entity);
    }

    public void saveOrUpdateAll(Collection<Role> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    public void deleteById(Class<Role> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id);
    }

    public void delete(Class<Role> entityClass, Serializable[] ids) {
        baseDao.delete(entityClass, ids);
    }
}
