package com.warmlight.DAOImpl;

import com.warmlight.DAO.BaseDao;
import com.warmlight.DAO.CommentDao;
import com.warmlight.model.CommentEntity;
import com.warmlight.utils.DaoUtil;
import com.warmlight.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
@Repository
public class CommentDaoImpl extends BaseDao<CommentEntity> implements CommentDao {
    @Override
    public boolean saveComment(CommentEntity comment) {
        return save(comment);
    }

    @Override
    public boolean deleteComment(Long id) {
        return delete(get(id));
    }

    @Override
    public DataWrapper<List<CommentEntity>> getCommentListByNews(Long newsId, Integer pageSize, Integer pageIndex) {
        DataWrapper<List<CommentEntity>> dataWrapper = new DataWrapper<List<CommentEntity>>();
        List<CommentEntity> ret = new ArrayList<CommentEntity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(CommentEntity.class);

        criteria.add(Restrictions.eq("newsId", newsId));
        criteria.addOrder(Order.desc("publishDate"));


        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);

        // 真正取值
        criteria.setProjection(null);
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            System.out.println(e);
        }
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
    }

    @Override
    public CommentEntity getCommentById(Long id) {
        return get(id);
    }
}
