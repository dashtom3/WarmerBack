package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.UserDao;
import com.my.spring.model.UserEntity;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
