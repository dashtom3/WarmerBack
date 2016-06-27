package com.warmlight.DAO;

import com.warmlight.model.NewsEntity;
import com.warmlight.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public interface NewsDao {
    boolean saveNews(NewsEntity news);
    boolean deleteNews(Long id);
    NewsEntity getNewsById(Long id);
    DataWrapper<List<NewsEntity>> getNewsList(Long userId,Integer pageSize,Integer pageIndex);
}
