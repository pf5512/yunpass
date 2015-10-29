package estate.service.impl;

import estate.common.config.SecretType;
import estate.dao.BaseDao;
import estate.dao.SsidSecretDao;
import estate.entity.database.SsidSecretEntity;
import estate.entity.json.Select2;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.SsidSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 *
 */
@Service("ssidSecretService")
public class SsidSecretServiceImpl implements SsidSecretService
{
    @Autowired
    private SsidSecretDao ssidSecretDao;
    @Autowired
    private BaseDao baseDao;

    @Override
    public SsidSecretEntity getSelfBySymbol(String symbol)
    {
        return ssidSecretDao.getBySymbol(symbol);
    }

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        TableData tableData=ssidSecretDao.getList(tableFilter);
        tableData.setRecordsTotal(baseDao.count("SsidSecretEntity"));
        return tableData;

    }

    @Override
    public ArrayList<Select2> getSelect2()
    {
        ArrayList<SsidSecretEntity> ssidSecretEntities = ssidSecretDao.getNotUsed();
        if (ssidSecretEntities==null)
            return null;
        ArrayList<Select2> select2s=new ArrayList<>();
        for (SsidSecretEntity ssidSecretEntity:ssidSecretEntities)
        {
            Select2 select2=new Select2();
            select2.setId(String.valueOf(ssidSecretEntity.getId()));
            if (ssidSecretEntity.getType()== SecretType.LANYA)
                select2.setText(ssidSecretEntity.getSymbol()+"&ensp;&ensp;蓝牙");
            else if (ssidSecretEntity.getType()==SecretType.WIFI)
                select2.setText(ssidSecretEntity.getSymbol()+"&ensp;&ensp;WIFI");

            select2s.add(select2);
        }
        return select2s;
    }

}
