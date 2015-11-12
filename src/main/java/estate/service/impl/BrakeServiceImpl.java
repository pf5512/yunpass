package estate.service.impl;

import estate.dao.BaseDao;
import estate.dao.BrakeDao;
import estate.entity.database.BrakeEntity;
import estate.entity.json.Select2;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.BrakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-15.
 *
 */
@Service("brakeService")
public class BrakeServiceImpl implements BrakeService
{
    @Autowired
    private BrakeDao brakeDao;
    @Autowired
    private BaseDao baseDao;

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        TableData tableData=brakeDao.getList(tableFilter);
//        ArrayList<BrakeEntity> brakeEntities= (ArrayList<BrakeEntity>) tableData.getJsonString();
//        for (BrakeEntity brakeEntity:brakeEntities)
//        {
//            SsidSecretEntity ssidSecretEntity=ssidSecretDao.
//                    getByControTypeControId(brakeEntity.getId(), SsidControlType.BRAKE);
//            if (ssidSecretEntity==null)
//                brakeEntity.setSymbol(null);
//            else brakeEntity.setSymbol(ssidSecretEntity.getSymbol());
//        }
        tableData.setRecordsTotal(baseDao.count(BrakeEntity.class));
        return tableData;
    }

    @Override
    public ArrayList<Select2> getSelectListByVillageID(Integer id)
    {
        ArrayList<BrakeEntity> brakeEntities=brakeDao.getByVillageID(id);
        if (brakeEntities==null)
            return null;
        ArrayList<Select2> select2s=new ArrayList<>();
        for (BrakeEntity brakeEntity:brakeEntities)
        {
            Select2 select2=new Select2();
            select2.setId(String.valueOf(brakeEntity.getId()));
            select2.setText(brakeEntity.getName());
            select2s.add(select2);
        }
        return select2s;
    }
}
