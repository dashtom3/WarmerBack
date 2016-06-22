package com.warmlight.DAOImpl;

import com.warmlight.DAO.BaseDao;
import com.warmlight.DAO.UserDao;
import com.warmlight.model.UserEntity;
import com.warmlight.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class UserDaoImpl extends BaseDao<UserEntity> implements UserDao {

    @Override
    public boolean addUser(UserEntity user) {
        return save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateUser(UserEntity user) {
        return update(user);
    }

    @Override
    public UserEntity getUserByUserName(String userName) {
        List<UserEntity> ret;
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("userName",userName));

        ret = criteria.list();
        if(ret != null && ret.size() > 0) {
            return  ret.get(0);
        }

        return null;
    }

    @Override
    public DataWrapper<List<UserEntity>> getUserList() {
        DataWrapper<List<UserEntity>> retDataWrapper = new DataWrapper<List<UserEntity>>();
        List<UserEntity> ret = new ArrayList<UserEntity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserEntity.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }
}
