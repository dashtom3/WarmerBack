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
    //finished
    @RequestMapping(value="register", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> register(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpServletRequest request){
        return userService.addUser(user,image,request);
    }

    @RequestMapping(value="deleteUser")
    @ResponseBody
    public DataWrapper<Void> deleteUser(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return userService.deleteUser(id);
    }

    @RequestMapping(value="updateUser",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateUser(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(user);
        return userService.updateUser(user);
    }


    @RequestMapping(value="getUserList")
    @ResponseBody
    public DataWrapper<List<UserEntity>> getUserList(
            @RequestParam(value = "token",required = false) String token){
        return userService.getUserList();
    }
}
