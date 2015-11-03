package estate.dao.impl;

import estate.dao.RepairDao;
import estate.entity.database.RepairEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by kangbiao on 15-9-15.
 */
@Repository("repairDao")
public class RepairDaoImpl implements RepairDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    public TableData getList(TableFilter tableFilter)
    {
        Session session=getSession();
        TableData tableData=new TableData();
        ArrayList<RepairEntity> entities;
        Query query;

        StringBuilder hql=new StringBuilder("from RepairEntity t where 1=1 ");
        if (tableFilter.getSearchValue()!=null)
            hql.append(" and t.phone like('%").append(tableFilter.getSearchValue()).append("%')");

        hql.append(" order by t.status asc,t.submitTime desc");

        query=session.createQuery(hql.toString());
        Integer count=query.list().size();
        entities=(ArrayList<RepairEntity>)query.setFirstResult(tableFilter.getStart()).setMaxResults(tableFilter
                .getLength()).list();

        tableData.setRecordsTotal(this.count());
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    public Integer count()
    {
        Session session=getSession();
        String hql="select count(*) from RepairEntity";
        return ((Long)session.createQuery(hql).uniqueResult()).intValue();
    }

    public void setRepairMan(RepairEntity repairEntity)
    {

    }

    @Override
    public ArrayList<RepairEntity> getByPhone(String phone, Byte status)
    {
        Session session=getSession();
        String hql;
        List list;
        if (status==null)
        {
            hql = "from RepairEntity t where t.phone=:phone";
            list = session.createQuery(hql).setString("phone", phone).list();
        }
        else
        {
            hql = "from RepairEntity t where t.phone=:phone and t.status=:status";
            list= session.createQuery(hql).setString("phone", phone).setByte("status",status).list();
        }
        if (list.size()>0)
            return (ArrayList<RepairEntity>) list;
        return null;
    }
}
