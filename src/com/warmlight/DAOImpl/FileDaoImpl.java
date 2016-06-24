package com.warmlight.DAOImpl;

import com.warmlight.DAO.BaseDao;
import com.warmlight.DAO.FileDao;
import com.warmlight.model.FileEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class FileDaoImpl extends BaseDao<FileEntity> implements FileDao {
    @Override
    public boolean saveFile(FileEntity file) {
        return save(file);
    }

    @Override
    public List<FileEntity> getFileListByNews(Long newsId) {
        List<FileEntity> ret;
        Session session = getSession();
        Criteria criteria = session.createCriteria(FileEntity.class);

        criteria.add(Restrictions.eq("newsId", newsId));

        ret = criteria.list();

        return ret;
    }
}
