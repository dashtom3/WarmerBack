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
    DataWrapper<UserEntity> login(String userName,String password);
    DataWrapper<Void> deleteUser(String token,HttpServletRequest request);
    DataWrapper<UserEntity> updateUser(UserEntity user,MultipartFile image,MultipartFile background,String token,HttpServletRequest request);
    DataWrapper<Void> logout(String token);

    DataWrapper<UserEntity> getUserDetails(Long userId,String token);
}
