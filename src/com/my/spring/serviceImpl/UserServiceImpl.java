package com.my.spring.serviceImpl;

import com.my.spring.DAO.UserDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.UserEntity;
import com.my.spring.service.UserService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addUser(UserEntity user) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!userDao.addUser(user)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteUser(Long id) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!userDao.deleteUser(id)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> updateUser(UserEntity user) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        if(!userDao.updateUser(user)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<UserEntity>> getUserList() {
        return userDao.getUserList();
    }
}
