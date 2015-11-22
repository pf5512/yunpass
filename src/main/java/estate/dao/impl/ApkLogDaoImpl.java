package estate.dao.impl;

import estate.dao.ApkLogDao;
import estate.entity.database.ApkLogEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
@Repository("apkLogDao")
public class ApkLogDaoImpl implements ApkLogDao
{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public ApkLogEntity getByCode(String code)
    {
        Session session=getSession();

        String hql="from ApkLogEntity t where t.versionCode=:code";
        List list=session.createQuery(hql).setString("code",code).list();
        if (list.size()>0)
            return (ApkLogEntity) list.get(0);
        return null;
    }

    @Override
    public ApkLogEntity getNewestApk()
    {
        Session session=getSession();
        String hql="from ApkLogEntity t order by t.versionCode desc";
        List list=session.createQuery(hql).list();
        if (list.size()>0)
            return (ApkLogEntity) list.get(0);
        return null;
    }
}
