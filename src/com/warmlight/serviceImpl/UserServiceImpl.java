package com.warmlight.serviceImpl;

import com.warmlight.DAO.TokenDao;
import com.warmlight.DAO.UserDao;
import com.warmlight.enums.ErrorCodeEnum;
import com.warmlight.model.TokenEntity;
import com.warmlight.model.UserEntity;
import com.warmlight.service.UserService;
import com.warmlight.utils.DataWrapper;
import com.warmlight.utils.FileUtil;
import com.warmlight.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
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
    @Autowired
    TokenDao tokenDao;

    @Override
    public DataWrapper<Void> addUser(UserEntity user,MultipartFile image,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();

        String userName = user.getUserName();
        String passWord = user.getPassword();
        if(userName == null || passWord == null
                || userName.contains(" ") || passWord.contains(" ") || userName.length() < 8 || passWord.length() < 8
                || userName.length() > 16 || passWord.length() > 16 ) {
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
                user.setUserImg("user_photo/" + newFileName);
            } else {
                user.setUserImg(null);
            }
        } else {
            user.setUserImg(null);
        }
        user.setId(null);
        user.setRegisterDate(new java.sql.Date(System.currentTimeMillis()));
        user.setIntro(user.getIntro() != null ? user.getIntro().trim() : null);
        user.setPassword(MD5Util.getMD5String(user.getPassword()));
        if(!userDao.addUser(user)) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<UserEntity> login(String userName, String password) {
        DataWrapper<UserEntity> dataWrapper = new DataWrapper<UserEntity>();
        UserEntity user = userDao.getUserByUserName(userName);
        if(user != null) {
            if (user.getPassword().equals(MD5Util.getMD5String(password))) {
                String token = MD5Util.getMD5String(user.getId() + user.getUserName() + user.getPassword() + new java.util.Date() );
                TokenEntity tokenEntity = tokenDao.getByUserId(user.getId());
                boolean toAdd = false;
                if(tokenEntity == null) {
                    tokenEntity = new TokenEntity();
                    tokenEntity.setId(null);
                    tokenEntity.setUserId(user.getId());
                    toAdd = true;
                }

                tokenEntity.setToken(token);
                tokenEntity.setLoginDate(new Timestamp(System.currentTimeMillis()));

                boolean flag = false;
                if(toAdd) {
                    flag = tokenDao.addToken(tokenEntity);
                } else {
                    flag = tokenDao.updateToken(tokenEntity);
                }
                if(flag) {
                    dataWrapper.setToken(token);
                    dataWrapper.setData(user);
                } else {
                    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                }
            } else {
                dataWrapper.setErrorCode(ErrorCodeEnum.PASSWORD_ERROR);
            }
        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.USER_NOT_EXIST);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteUser(String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if(tokenEntity != null) {
            UserEntity user = userDao.getUserById(tokenEntity.getUserId());
            if(!tokenDao.deleteToken(tokenEntity.getId()) || !userDao.deleteUser(tokenEntity.getUserId())) {
                dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            } else {
                String filePath = request.getSession().getServletContext().getRealPath("/");
                FileUtil.deleteFile(filePath + user.getUserImg());
                FileUtil.deleteDir(new File(filePath + "upload/photos/" + user.getId()));
                FileUtil.deleteDir(new File(filePath + "upload/video/" + user.getId()));
                FileUtil.deleteDir(new File(filePath + "upload/radio/" + user.getId()));
            }
        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<UserEntity> updateUser(UserEntity user,MultipartFile image,String token,HttpServletRequest request) {
        DataWrapper<UserEntity> dataWrapper = new DataWrapper<UserEntity>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if(tokenEntity != null) {
            UserEntity userToUpdate = userDao.getUserById(tokenEntity.getUserId());
            if (image != null) {
                String filePath = request.getSession().getServletContext().getRealPath("/");
                //删除之前的照片
                if (userToUpdate.getUserImg() != null) {
                    FileUtil.deleteFile(filePath + userToUpdate.getUserImg());
                }

                //保存新上传的照片
                String newFileName = MD5Util.getMD5String(image.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
                        + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
                boolean flag = FileUtil.saveFile(filePath + "user_photo",newFileName,image);
                if(flag) {
                    userToUpdate.setUserImg("user_photo/" + newFileName);
                } else {
                    userToUpdate.setUserImg(null);
                }
            }


            if (user.getPassword() != null) {
                if (!user.getPassword().contains(" ") && user.getPassword().length() >= 8 && user.getPassword().length() <= 16) {
                    userToUpdate.setPassword(MD5Util.getMD5String(user.getPassword()));
                } else {
                    dataWrapper.setErrorCode(ErrorCodeEnum.CHARACTER_ERROR);
                    return dataWrapper;
                }
            }

            if (user.getIntro() != null) {
                userToUpdate.setIntro(user.getIntro().trim());
            }
            if (user.getRealName() != null) {
                if (!user.getRealName().contains(" ") && user.getRealName().length() > 0 && user.getRealName().length() <= 20) {
                    userToUpdate.setRealName(user.getRealName());
                } else {
                    dataWrapper.setErrorCode(ErrorCodeEnum.CHARACTER_ERROR);
                    return dataWrapper;
                }
            }

            if (!userDao.updateUser(userToUpdate)) {
                dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            } else {
                dataWrapper.setData(userToUpdate);
            }


        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> logout(String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if(tokenEntity != null) {
            if(!tokenDao.deleteToken(tokenEntity.getId())) {
                dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

}
