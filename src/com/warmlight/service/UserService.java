package com.warmlight.service;

import com.warmlight.model.UserEntity;
import com.warmlight.utils.DataWrapper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface UserService {
    DataWrapper<Void> addUser(UserEntity user,MultipartFile image,HttpServletRequest request);
    DataWrapper<Void> deleteUser(Long id);
    DataWrapper<Void> updateUser(UserEntity user);
    DataWrapper<List<UserEntity>> getUserList();
}
