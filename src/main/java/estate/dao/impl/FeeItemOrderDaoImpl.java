package estate.dao.impl;

import estate.common.util.LogUtil;
import estate.dao.FeeItemOrderDao;
import estate.entity.database.FeeItemOrderEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbiao on 15-9-24.
 *
 */
@Repository("feeItemOrderDao")
public class FeeItemOrderDaoImpl implements FeeItemOrderDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public ArrayList<FeeItemOrderEntity> getFeeItemOrdersByPropertyID(Integer id)
    {
        Session session=getSession();
        String hql="from FeeItemOrderEntity t where t.propertyId=:id";
        List list=session.createQuery(hql).setInteger("id",id).list();
        if (list.size()>0)
            return (ArrayList<FeeItemOrderEntity>) list;
        return null;
    }
}
