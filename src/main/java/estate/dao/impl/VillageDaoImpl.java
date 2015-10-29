package estate.dao.impl;

import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.dao.VillageDao;
import estate.entity.database.VillageEntity;
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
 * Created by kangbiao on 15-9-26.
 *
 */
@Repository("villageDao")
public class VillageDaoImpl implements VillageDao
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
        Session session=getSession();
        TableData tableData=new TableData();
        ArrayList<VillageEntity> entities;
        Query query;

        if (tableFilter.getSearchValue()!=null)
        {
            String hql="from VillageEntity t where t.code like (?)";
            query=session.createQuery(hql).setString(0,"%"+tableFilter.getSearchValue()+"%");
        }
        else
        {
            String hql = "from VillageEntity t";
            query = session.createQuery(hql);
        }
        Integer count=query.list().size();
        entities=(ArrayList<VillageEntity>)query
                .setFirstResult(tableFilter.getStart())
                .setMaxResults(tableFilter.getLength())
                .list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    @Override
    public ArrayList<VillageEntity> getAllVillage()
    {
        Session session=getSession();
        String hql="from VillageEntity t ";
        return (ArrayList<VillageEntity>) session.createQuery(hql).list();
    }
}
