package com.warmlight.service;

import com.warmlight.model.CommentEntity;
import com.warmlight.model.NewsEntity;
import com.warmlight.utils.DataWrapper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public interface NewsService {
    DataWrapper<Void> publishNews(NewsEntity news,Integer type,Integer backgroundNo,MultipartFile[] file,String token,HttpServletRequest request);
    DataWrapper<List<NewsEntity>> getNewsList(Integer pageSize,Integer pageIndex,String token);
    DataWrapper<Void> deleteNews(Long newsId,String token,HttpServletRequest request);
    DataWrapper<Void> addVotedAmount(Long newsId,String token);
    DataWrapper<Void> deleteVotedAmount(Long newsId,String token);

    DataWrapper<Void> comment(Long newsId,CommentEntity commentEntity,MultipartFile voice,String token,HttpServletRequest request);
    DataWrapper<Void> deleteComment(Long newsId,Long commentId,String token,HttpServletRequest request);
    DataWrapper<List<CommentEntity>> getCommentList(Long newsId,Integer pageSize,Integer pageIndex,String token);

}
