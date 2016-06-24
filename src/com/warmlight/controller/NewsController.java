package com.warmlight.controller;

import com.warmlight.model.CommentEntity;
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

    //发布新闻
    // type=0 文字，此时 backgroundNo=null      file数组为空
    // type=1 照片，此时 backgroundNo=null      file数组长度为照片数量
    // type=2 音频，此时 backgroundNo=1,2,3,4   file数组长度为1
    // type=3 视频，此时 backgroundNo=null      file数组长度为1
    //finished tested
    @RequestMapping(value="publish", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> publish(
            @ModelAttribute NewsEntity news,
            @RequestParam(value = "type", required = true) Integer type,
            @RequestParam(value = "backgroundNo", required = false) Integer backgroundNo,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request){
        return newsService.publishNews(news,type,backgroundNo,file,token,request);
    }

    //获取新闻列表
    //finished tested
    @RequestMapping(value="getNewsList")
    @ResponseBody
    public  DataWrapper<List<NewsEntity>> getNewsList(
            @RequestParam(value = "pageSize", required = true) Integer pageSize,
            @RequestParam(value = "pageIndex", required = true) Integer pageIndex,
            @RequestParam(value = "token", required = true) String token){
        return newsService.getNewsList(pageSize,pageIndex,token);
    }

    //删除新闻
    //finished tested
    @RequestMapping(value="delete/{newsId}")
    @ResponseBody
    public DataWrapper<Void> deleteNews(
            @PathVariable("newsId") Long newsId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request){
        return newsService.deleteNews(newsId,token,request);
    }

    //点赞
    //finished tested
    @RequestMapping(value="{newsId}/vote/add")
    @ResponseBody
    public DataWrapper<Void> addVotedAmount(
            @PathVariable("newsId") Long newsId,
            @RequestParam(value = "token", required = true) String token){
        return newsService.addVotedAmount(newsId,token);
    }

    //取消点赞
    //finished tested
    @RequestMapping(value="{newsId}/vote/delete")
    @ResponseBody
    public DataWrapper<Void> deleteVotedAmount(
            @PathVariable("newsId") Long newsId,
            @RequestParam(value = "token", required = true) String token){
        return newsService.deleteVotedAmount(newsId, token);
    }

    //评论
    //type=0 文字评论， type=1 语音评论
    //finished tested
    @RequestMapping(value="{newsId}/comment/add",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addComment(
            @PathVariable("newsId") Long newsId,
            @ModelAttribute CommentEntity commentEntity,
            @RequestParam(value = "voice", required = false) MultipartFile voice,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request){
        return newsService.comment(newsId,commentEntity,voice,token,request);
    }

    //删除评论
    //type=0 文字评论， type=1 语音评论
    //finished tested
    @RequestMapping(value="{newsId}/comment/{commentId}/delete")
    @ResponseBody
    public DataWrapper<Void> deleteComment(
            @PathVariable("newsId") Long newsId,
            @PathVariable("commentId") Long commentId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request){
        return newsService.deleteComment(newsId,commentId,token,request);
    }

    //获取评论列表
    //finished tested
    @RequestMapping(value="{newsId}/comment/getCommentList")
    @ResponseBody
    public DataWrapper<List<CommentEntity>> getCommentList(
            @PathVariable("newsId") Long newsId,
            @RequestParam(value = "pageSize", required = true) Integer pageSize,
            @RequestParam(value = "pageIndex", required = true) Integer pageIndex,
            @RequestParam(value = "token", required = true) String token){
        return newsService.getCommentList(newsId,pageSize,pageIndex,token);
    }



}
