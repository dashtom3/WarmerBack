package com.warmlight.DAOImpl;

import com.warmlight.DAO.BaseDao;
import com.warmlight.DAO.VoteDao;
import com.warmlight.model.VoteEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
@Repository
public class VoteDaoImpl extends BaseDao<VoteEntity> implements VoteDao {
    @Override
    public boolean saveVote(VoteEntity vote) {
        return save(vote);
    }

    @Override
    public VoteEntity getVoteByNewsAndUser(Long newsId, Long userId) {
        List<VoteEntity> ret;
        Session session = getSession();
        Criteria criteria = session.createCriteria(VoteEntity.class);

        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("newsId", newsId));

        ret = criteria.list();
        if(ret != null && ret.size() > 0) {
            return  ret.get(0);
        }

        return null;
    }

    @Override
    public boolean deleteVoteByNewsAndUser(Long newsId, Long userId) {
        String hql = "delete VoteEntity where newsId = " + newsId + " and userId = " + userId;
        return executeHql(hql);
    }
}
