package com.warmlight.DAOImpl;

import com.warmlight.DAO.BaseDao;
import com.warmlight.DAO.NewsDao;
import com.warmlight.model.NewsEntity;
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
 * Created by Administrator on 2016/6/23.
 */
@Repository
public class NewsDaoImpl extends BaseDao<NewsEntity> implements NewsDao {
    @Override
    public boolean saveNews(NewsEntity news) {
        return save(news);
    }

    @Override
    public boolean deleteNews(Long id) {
        return delete(get(id));
    }

    @Override
    public NewsEntity getNewsById(Long id) {
        return get(id);
    }

    @Override
    public DataWrapper<List<NewsEntity>> getNewsList(Long userId,Integer pageSize, Integer pageIndex) {
        DataWrapper<List<NewsEntity>> dataWrapper = new DataWrapper<List<NewsEntity>>();
        List<NewsEntity> ret = new ArrayList<NewsEntity>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(NewsEntity.class);

        if (userId != null) {
            criteria.add(Restrictions.eq("userId",userId));
        }

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
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(pageIndex);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(pageSize);

        return dataWrapper;
    }
}
