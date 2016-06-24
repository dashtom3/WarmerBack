package com.warmlight.DAO;

import com.warmlight.model.CommentEntity;
import com.warmlight.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public interface CommentDao {
    boolean saveComment(CommentEntity comment);
    boolean deleteComment(Long id);
    DataWrapper<List<CommentEntity>> getCommentListByNews(Long newsId,Integer pageSize,Integer pageIndex);
    CommentEntity getCommentById(Long id);

}
