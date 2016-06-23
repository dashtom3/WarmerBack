package com.warmlight.controller;

import com.warmlight.model.NewsEntity;
import com.warmlight.model.UserEntity;
import com.warmlight.service.NewsService;
import com.warmlight.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
@Controller
@RequestMapping(value="api/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    // type=0 文字，此时 backgroundNo=null      file数组为空
    // type=1 照片，此时 backgroundNo=null      file数组长度为照片数量
    // type=2 音频，此时 backgroundNo=1,2,3,4   file数组长度为1
    // type=3 视频，此时 backgroundNo=null      file数组长度为1
    @RequestMapping(value="publish", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> publish(
            @ModelAttribute NewsEntity news,
            @RequestParam(value = "type", required = true) Integer type,
            @RequestParam(value = "backgroundNo", required = false) Integer backgroundNo,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam(value = "token", required = false) String token,
            HttpServletRequest request){
        return newsService.publishNews(news,type,backgroundNo,file,token,request);
    }

    @RequestMapping(value="getNewsList")
    @ResponseBody
    public DataWrapper<List<NewsEntity>> getNewsList(
            @RequestParam(value = "pageSize", required = true) Integer pageSize,
            @RequestParam(value = "pageIndex", required = true) Integer pageIndex,
            @RequestParam(value = "token", required = false) String token){
        return newsService.getNewsList(pageSize,pageIndex,token);
    }

}
