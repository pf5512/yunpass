package estate.dao.impl;

import estate.dao.BillDao;
import estate.entity.database.PropertyBillEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kangbiao on 15-10-6.
 *
 */
@Repository("billDao")
public class BillDaoImpl implements BillDao
{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public TableData getList(TableFilter tableFilter)
    {
        return null;
    }

    @Override
    public ArrayList<PropertyBillEntity> getPropertyBillByPropertyID(Integer propertyID, Byte status, Long startTime, Long endTime)
    {
        Session session=getSession();
        StringBuilder hql=new StringBuilder("from PropertyBillEntity t where t.propertyId=").append(propertyID);
        if (status!=null)
            hql.append(" and t.payStatus=").append(status);
        if (startTime!=null&&endTime==null)
            hql.append(" and t.billGenerationTime>=").append(startTime);
        if (startTime==null&&endTime!=null)
            hql.append(" and t.billGenerationTime<=").append(endTime);
        if (startTime!=null&&endTime!=null)
            hql.append(" and t.billGenerationTime>=").append(startTime).append(" and t.billGenerationTime<=").append(endTime);
        List list=session.createQuery(hql.toString()).list();
        if (list.size()>0)
            return (ArrayList<PropertyBillEntity>) list;
        return null;
    }
}
