package com.warmlight.DAO;

import com.warmlight.model.UserEntity;
import com.warmlight.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface UserDao {
    boolean addUser(UserEntity user);
    boolean deleteUser(Long id);
    boolean updateUser(UserEntity user);
    UserEntity getUserByUserName(String userName);
    DataWrapper<List<UserEntity>> getUserList();
}
