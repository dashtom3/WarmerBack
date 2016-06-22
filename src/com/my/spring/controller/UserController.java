package com.my.spring.controller;

import com.my.spring.model.UserEntity;
import com.my.spring.service.UserService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value="addUser", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addUser(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "token",required = false) String token){
        return userService.addUser(user);
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
