package com.itheima.service;

import com.itheima.constants.SysConstant;
import com.itheima.dao.BaseDao;
import com.itheima.domain.User;
import com.itheima.utils.Encrypt;
import com.itheima.utils.MailUtil;
import com.itheima.utils.Page;
import com.itheima.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Transactional
public class UserServiceImpl implements UserService {
    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    public List<User> find(String hql, Class<User> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    public User get(Class<User> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    public void saveOrUpdate(User entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            String id = UUID.randomUUID().toString();
            entity.setId(id);
            entity.getUserinfo().setId(id);
            //设置默认密码 修复shiro bug
            entity.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, entity.getUserName()));
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        MailUtil.sendMail("562427079@qq.com", "新员工入职邮件", entity.getUserName() + ", 欢迎你成为本公司员工!");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        baseDao.saveOrUpdate(entity);
    }

    public void saveOrUpdateAll(Collection<User> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    public void deleteById(Class<User> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id);
    }

    public void delete(Class<User> entityClass, Serializable[] ids) {
        baseDao.delete(entityClass, ids);
    }
}
