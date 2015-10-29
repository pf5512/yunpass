package estate.service.impl;

import estate.common.config.SsidControlType;
import estate.dao.BaseDao;
import estate.dao.SsidSecretDao;
import estate.dao.VillageDao;
import estate.entity.database.SsidSecretEntity;
import estate.entity.database.VillageEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.VillageService;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-15.
 *
 */
@Service("villageService")
public class VillageServiceImpl implements VillageService
{

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private VillageDao villageDao;
    @Autowired
    private SsidSecretDao ssidSecretDao;

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        TableData tableData=villageDao.getList(tableFilter);
        ArrayList<VillageEntity> villageEntities= (ArrayList<VillageEntity>) tableData.getJsonString();
        for (VillageEntity villageEntity:villageEntities)
        {
            SsidSecretEntity ssidSecretEntity=ssidSecretDao.
                    getByControTypeControId(villageEntity.getId(), SsidControlType.VILLAGE);
            if (ssidSecretEntity==null)
                villageEntity.setSymbol(null);
            else villageEntity.setSymbol(ssidSecretEntity.getSymbol());
        }
        tableData.setRecordsTotal(baseDao.count("VillageEntity"));
        return tableData;
    }
}
