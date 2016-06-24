package com.warmlight.DAOImpl;

import com.warmlight.DAO.BaseDao;
import com.warmlight.DAO.TokenDao;
import com.warmlight.DAO.UserDao;
import com.warmlight.model.TokenEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
@Repository
public class TokenDaoImpl extends BaseDao<TokenEntity> implements TokenDao {
    @Override
    public boolean addToken(TokenEntity token) {
        return save(token);
    }

    @Override
    public boolean deleteToken(Long id) {
        return delete(get(id));
    }


    @Override
    public TokenEntity getByUserId(Long userId) {
        List<TokenEntity> ret;
        Session session = getSession();
        Criteria criteria = session.createCriteria(TokenEntity.class);
        criteria.add(Restrictions.eq("userId", userId));

        ret = criteria.list();
        if(ret != null && ret.size() > 0) {
            return  ret.get(0);
        }

        return null;
    }

    @Override
    public TokenEntity getByTokenString(String tokenString) {
        List<TokenEntity> ret;
        Session session = getSession();
        Criteria criteria = session.createCriteria(TokenEntity.class);
        criteria.add(Restrictions.eq("token", tokenString));

        ret = criteria.list();
        if(ret != null && ret.size() > 0) {
            return  ret.get(0);
        }

        return null;
    }

    @Override
    public boolean updateToken(TokenEntity token) {
        return update(token);
    }
}
