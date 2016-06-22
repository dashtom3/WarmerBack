package com.warmlight.serviceImpl;

import com.warmlight.DAO.UserDao;
import com.warmlight.enums.ErrorCodeEnum;
import com.warmlight.model.UserEntity;
import com.warmlight.service.UserService;
import com.warmlight.utils.DataWrapper;
import com.warmlight.utils.FileUtil;
import com.warmlight.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public DataWrapper<Void> addUser(UserEntity user,MultipartFile image,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();

        String userName = user.getUserName();
        String passWord = user.getPassword();
        String realName = user.getRealName();
        if(userName.contains(" ") || passWord.contains(" ") || realName.contains(" ") || userName.length() < 8 || passWord.length() < 8 || realName.length() < 0
                || userName.length() > 16 || passWord.length() > 16 || realName.length() > 20) {
            dataWrapper.setErrorCode(ErrorCodeEnum.CHARACTER_ERROR);
            return dataWrapper;
        }

        if(userDao.getUserByUserName(user.getUserName()) != null) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error.USER_EXISTED);
            return dataWrapper;
        }

        if(image != null) {
            String filePath = request.getSession().getServletContext().getRealPath("/") + "user_photo";
            String newFileName = MD5Util.getMD5String(image.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
                    + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
            boolean flag = FileUtil.saveFile(filePath,newFileName,image);
            if(flag) {
                user.setUserImg("user_photo\\" + newFileName);
            } else {
                user.setUserImg(null);
            }
        } else {
            user.setUserImg(null);
        }
        user.setId(null);
        user.setRegisterDate(new java.sql.Date(System.currentTimeMillis()));
        user.setIntro(user.getIntro().trim());
        user.setPassword(MD5Util.getMD5String(user.getPassword()));
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
