package com.warmlight.serviceImpl;

import com.warmlight.DAO.NewsDao;
import com.warmlight.DAO.TokenDao;
import com.warmlight.enums.ErrorCodeEnum;
import com.warmlight.model.NewsEntity;
import com.warmlight.model.TokenEntity;
import com.warmlight.service.NewsService;
import com.warmlight.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {
    private final static Integer contentLength = 200;
    @Autowired
    TokenDao tokenDao;
    @Autowired
    NewsDao newsDao;

    @Override
    public DataWrapper<Void> publishNews(NewsEntity news, Integer type, Integer backgroundNo, MultipartFile[] file, String token, HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if (tokenEntity != null) {
            switch (type) {
                case 0 :
                    news.setId(null);
                    news.setPublishDate(new Date(System.currentTimeMillis()));
                    news.setUserId(tokenEntity.getUserId());
                    String content = news.getContent().trim();
                    if (content == null || content.length() == 0 || content.length() > contentLength) {
                        dataWrapper.setErrorCode(ErrorCodeEnum.CHARACTER_ERROR);
                        break;
                    }
                    news.setContent(content);
                    if (!newsDao.saveNews(news)) {
                        dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                    break;
            }

        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<NewsEntity>> getNewsList(Integer pageSize,Integer pageIndex,String token) {
        DataWrapper<List<NewsEntity>> dataWrapper = new DataWrapper<List<NewsEntity>>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if (tokenEntity != null) {
            dataWrapper = newsDao.getNewsList(pageSize,pageIndex);
        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }
}
