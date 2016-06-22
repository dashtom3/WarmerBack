package com.warmlight.controller;

import com.warmlight.service.FileService;
import com.warmlight.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/file")
public class FileController {
    @Autowired
    FileService fileService;
    @RequestMapping(value="uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadFile(
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            @RequestParam(value = "token",required = false) String token){
        return fileService.uploadFile(request,file);
    }
}
