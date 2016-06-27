package com.warmlight.controller;

import com.warmlight.model.UserEntity;
import com.warmlight.service.UserService;
import com.warmlight.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/user")
public class UserController {
    @Autowired
    UserService userService;
    //用户注册
    //finished tested
    @RequestMapping(value="register", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> register(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpServletRequest request){
        return userService.addUser(user,image,request);
    }

    //用户登录
    //finished tested
    @RequestMapping(value="login")
    @ResponseBody
    public DataWrapper<UserEntity> login(
            @RequestParam(value = "userName",required = true) String userName,
            @RequestParam(value = "password",required = true) String password){
        return userService.login(userName,password);
    }

    //用户登出
    //finished tested
    @RequestMapping(value="logout")
    @ResponseBody
    public DataWrapper<Void> logout(
            @RequestParam(value = "token",required = true) String token){
        return userService.logout(token);
    }

    //用户注销
    //finished tested
    @RequestMapping(value="deleteUser")
    @ResponseBody
    public DataWrapper<Void> deleteUser(
            @RequestParam(value = "token",required = false) String token,
            HttpServletRequest request){
        return userService.deleteUser(token,request);
    }

    //更新用户信息
    //finished tested
    @RequestMapping(value="updateUser",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<UserEntity> updateUser(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "background", required = false) MultipartFile background,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request){
        return userService.updateUser(user,image,background,token,request);
    }

    //获取用户详细信息
    @RequestMapping(value="getUserDetails")
    @ResponseBody
    public DataWrapper<UserEntity> getUserDetails(
            @RequestParam(value = "userId", required = true) Long userId,
            @RequestParam(value = "token",required = true) String token
            ){
        return userService.getUserDetails(userId, token);
    }

}
