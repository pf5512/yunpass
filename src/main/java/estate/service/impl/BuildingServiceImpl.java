package estate.service.impl;

import estate.dao.BaseDao;
import estate.dao.BuildingDao;
import estate.entity.database.BuildingEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-22.
 *
 */
@Service("buildingService")
public class BuildingServiceImpl implements BuildingService
{
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private BuildingDao buildingDao;

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        TableData tableData=buildingDao.getList(tableFilter);
//        ArrayList<BuildingEntity> buildingEntities= (ArrayList<BuildingEntity>) tableData.getJsonString();
//        for (BuildingEntity buildingEntity:buildingEntities)
//        {
//            SsidSecretEntity ssidSecretEntity=ssidSecretDao.
//                    getByControTypeControId(buildingEntity.getId(), SsidControlType.BUILDING);
//            if (ssidSecretEntity==null)
//                buildingEntity.setSymbol(null);
//            else buildingEntity.setSymbol(ssidSecretEntity.getSymbol());
//        }
        tableData.setRecordsTotal(baseDao.count(BuildingEntity.class));
        return tableData;
    }

    @Override
    public Integer getIDByCode(String code)
    {
        BuildingEntity buildingEntity=buildingDao.getByCode(code);
        if (buildingEntity!=null)
        {
            return buildingEntity.getId();
        }
        else
        {
            return null;
        }
    }

    @Override
    public ArrayList<BuildingEntity> getByVillageID(Integer id)
    {
        return buildingDao.getAllBuildingsByVillageId(id);
    }
}
