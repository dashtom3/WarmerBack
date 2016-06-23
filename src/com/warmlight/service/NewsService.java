package com.warmlight.service;

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
}
