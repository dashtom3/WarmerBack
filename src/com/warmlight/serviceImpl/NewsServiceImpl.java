package com.warmlight.serviceImpl;

import com.warmlight.DAO.*;
import com.warmlight.enums.ErrorCodeEnum;
import com.warmlight.model.*;
import com.warmlight.service.NewsService;
import com.warmlight.utils.DataWrapper;
import com.warmlight.utils.FileUtil;
import com.warmlight.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    FileDao fileDao;
    @Autowired
    VoteDao voteDao;
    @Autowired
    CommentDao commentDao;

    @Override
    public DataWrapper<Void> publishNews(NewsEntity news, Integer type, Integer backgroundNo, MultipartFile[] file, String token, HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if (tokenEntity != null) {

            String content = news.getContent();
            if (content == null || content.trim().length() == 0 || content.trim().length() > contentLength) {
                dataWrapper.setErrorCode(ErrorCodeEnum.CHARACTER_ERROR);
                return dataWrapper;
            }

            news.setId(null);
            news.setPublishDate(new Date(System.currentTimeMillis()));
            news.setUserId(tokenEntity.getUserId());
            news.setContent(content.trim());
            news.setVotedAmount(new Long(0));
            if (type == 0 || (type > 0 && type < 4 && file != null && file.length > 0)) {
                if (!newsDao.saveNews(news)) {
                    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                    return dataWrapper;
                }
            } else {
                dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                return dataWrapper;
            }

            if (file != null && file.length > 0) {
                String filePath = request.getSession().getServletContext().getRealPath("/");
                switch (type) {
                    case 0:
                        break;
                    case 1:
                        String relatedSrc1 = "upload/photos/" + tokenEntity.getUserId() + "/";
                        filePath += relatedSrc1;
                        for (MultipartFile item : file) {
                            String newFileName = MD5Util.getMD5String(item.getOriginalFilename() + new java.util.Date() + UUID.randomUUID().toString()).replace(".","")
                                    + item.getOriginalFilename().substring(item.getOriginalFilename().lastIndexOf("."));
                            boolean flag = FileUtil.saveFile(filePath, newFileName, item);
                            FileEntity fileEntity = new FileEntity();
                            fileEntity.setId(null);
                            fileEntity.setBackgroundNo(null);
                            fileEntity.setNewsId(news.getId());
                            fileEntity.setSrc(relatedSrc1 + newFileName);
                            fileEntity.setType(type);
                            fileDao.saveFile(fileEntity);
                        }
                        break;
                    case 2: case 3:
                        MultipartFile item = file[0];
                        String relatedSrc2;
                        if (type == 2) {
                            relatedSrc2 = "upload/radio/" + tokenEntity.getUserId() + "/";
                        } else {
                            relatedSrc2 = "upload/video/" + tokenEntity.getUserId() + "/";
                        }
                        filePath += relatedSrc2;
                        String newFileName = MD5Util.getMD5String(item.getOriginalFilename() + new java.util.Date() + UUID.randomUUID().toString()).replace(".","")
                                + item.getOriginalFilename().substring(item.getOriginalFilename().lastIndexOf("."));
                        boolean flag = FileUtil.saveFile(filePath, newFileName, item);
                        FileEntity fileEntity = new FileEntity();
                        fileEntity.setId(null);
                        fileEntity.setBackgroundNo(type == 2 ? backgroundNo : null);
                        fileEntity.setNewsId(news.getId());
                        fileEntity.setSrc(relatedSrc2 + newFileName);
                        fileEntity.setType(type);
                        if (!flag || !fileDao.saveFile(fileEntity)) {
                            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                        }
                        break;
                    default:
                        dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                        break;
                }
            } else {
                if (type != 0) {
                    newsDao.deleteNews(news.getId());
                    dataWrapper.setErrorCode(ErrorCodeEnum.FILE_EMPTY);
                }
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

    @Override
    public DataWrapper<Void> deleteNews(Long newsId, String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        NewsEntity newsEntity = newsDao.getNewsById(newsId);
        if (tokenEntity != null && newsEntity != null) {
            String filePath = request.getSession().getServletContext().getRealPath("/");
            if (tokenEntity.getUserId() == newsEntity.getUserId()) {
                List<FileEntity> fileList = fileDao.getFileListByNews(newsId);
                if (fileList != null) {

                    for (FileEntity file : fileList) {
                        if (file.getSrc() != null) {
                            FileUtil.deleteFile(filePath + file.getSrc());
                        }
                    }
                }
                String comment = filePath + "comments/voice/" + newsId;

                FileUtil.deleteDir(new File(comment));

                if (!newsDao.deleteNews(newsId)) {
                    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                }

            } else {
                dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_ERROR);
            }

        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> addVotedAmount(Long newsId, String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if (tokenEntity != null) {

            VoteEntity voteEntity = voteDao.getVoteByNewsAndUser(newsId,tokenEntity.getUserId());
            if (voteEntity == null) {
                voteEntity = new VoteEntity();
                voteEntity.setId(null);
                voteEntity.setNewsId(newsId);
                voteEntity.setUserId(tokenEntity.getUserId());
                voteEntity.setVotedDate(new Date(System.currentTimeMillis()));
                if (!voteDao.saveVote(voteEntity)) {
                    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                }
            } else {
                dataWrapper.setErrorCode(ErrorCodeEnum.VOTE_ERROR);
            }


        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteVotedAmount(Long newsId, String token) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if (tokenEntity != null) {
            if (!voteDao.deleteVoteByNewsAndUser(newsId,tokenEntity.getUserId())) {
                dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> comment(Long newsId,CommentEntity commentEntity,MultipartFile voice,String token,HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        NewsEntity newsEntity = newsDao.getNewsById(newsId);
        if (tokenEntity != null && newsEntity != null && commentEntity != null && commentEntity.getType() != null) {
            commentEntity.setId(null);
            commentEntity.setNewsId(newsId);
            commentEntity.setUserId(tokenEntity.getUserId());
            commentEntity.setPublishDate(new Date(System.currentTimeMillis()));
            switch (commentEntity.getType()) {
                case 0:
                    String comment = commentEntity.getComment();
                    if (comment != null && comment.trim().length() > 0 && comment.trim().length() < 100) {
                        commentEntity.setComment(comment.trim());
                        commentEntity.setVoiceSrc(null);
                    } else {
                        dataWrapper.setErrorCode(ErrorCodeEnum.CHARACTER_ERROR);
                        return dataWrapper;
                    }
                    break;
                case 1:
                    if (voice != null) {
                        String filePath = request.getSession().getServletContext().getRealPath("/");
                        String relatedSrc = "comments/voice/" + newsId + "/";
                        filePath += relatedSrc;
                        String newFileName = MD5Util.getMD5String(voice.getOriginalFilename() + new java.util.Date() + UUID.randomUUID().toString()).replace(".","")
                                + voice.getOriginalFilename().substring(voice.getOriginalFilename().lastIndexOf("."));
                        boolean flag = FileUtil.saveFile(filePath, newFileName, voice);
                        if (flag) {
                            commentEntity.setComment(null);
                            commentEntity.setVoiceSrc(relatedSrc + newFileName);
                        } else {
                            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                            return dataWrapper;
                        }
                    } else {
                        dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                        return dataWrapper;
                    }
                    break;
                default:
                    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                    return dataWrapper;
            }

            if (!commentDao.saveComment(commentEntity)) {
                dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }


        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<Void> deleteComment(Long newsId, Long commentId, String token, HttpServletRequest request) {
        DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if (tokenEntity != null) {
            CommentEntity commentEntity = commentDao.getCommentById(commentId);
            if(commentEntity != null && commentEntity.getNewsId() == newsId && commentEntity.getUserId() == tokenEntity.getUserId()) {
                if (commentEntity.getVoiceSrc() != null) {
                    String filePath = request.getSession().getServletContext().getRealPath("/");
                    FileUtil.deleteFile(filePath + commentEntity.getVoiceSrc());
                }
                if (!commentDao.deleteComment(commentEntity.getId())) {
                    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                }
            } else  {
                dataWrapper.setErrorCode(ErrorCodeEnum.AUTH_ERROR);
            }
        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @Override
    public DataWrapper<List<CommentEntity>> getCommentList(Long newsId,Integer pageSize,Integer pageIndex,String token) {
        DataWrapper<List<CommentEntity>> dataWrapper = new DataWrapper<List<CommentEntity>>();
        TokenEntity tokenEntity = tokenDao.getByTokenString(token);
        if (tokenEntity != null) {
            dataWrapper = commentDao.getCommentListByNews(newsId,pageSize,pageIndex);
        } else {
            dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }
}
