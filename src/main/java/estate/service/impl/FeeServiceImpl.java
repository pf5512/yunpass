package estate.service.impl;

import estate.common.Config;
import estate.common.config.FeeType;
import estate.common.config.ParkLot;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.dao.*;
import estate.entity.database.FeeItemEntity;
import estate.entity.database.PropertyEntity;
import estate.entity.display.ParkLotFeeInfo;
import estate.entity.json.ParkLotExtra;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbiao on 15-9-15.
 *
 */
@Service("feeService")
public class FeeServiceImpl implements FeeService
{
    @Autowired
    private RuleDao ruleDao;
    @Autowired
    private BaseDao baseDao;

    @Autowired
    private FeeItemDao feeItemDao;
    @Autowired
    private PropertyDao propertyDao;
    @Autowired
    private FeeItemOrderDao feeItemOrderDao;


    public TableData feeList(TableFilter tableFilter,byte feeType)
    {
        if (feeType== FeeType.PARKING_LOT)
        {
            TableData tableData=feeItemDao.getList(tableFilter,feeType);
            ArrayList<ParkLotFeeInfo> parkLotFeeInfos=new ArrayList<>();
            List list= (List) tableData.getJsonString();
            if (list.size()>0)
            {
                ArrayList<FeeItemEntity> feeItemEntities = (ArrayList<FeeItemEntity>) tableData.getJsonString();
                for (FeeItemEntity feeItemEntity : feeItemEntities)
                {
                    ParkLotFeeInfo parkLotFeeInfo = new ParkLotFeeInfo();

                    parkLotFeeInfo.setParkLotExtra(GsonUtil.getGson()
                            .fromJson(feeItemEntity.getExtendInfo(), ParkLotExtra.class));
                    parkLotFeeInfo.setName(feeItemEntity.getName());
                    parkLotFeeInfo.setDecription(feeItemEntity.getDecription());
                    parkLotFeeInfo.setUnit(feeItemEntity.getUnit());
                    parkLotFeeInfo.setUnitPrice(feeItemEntity.getUnitPrice());
                    parkLotFeeInfo.setOverdueUnit(feeItemEntity.getOverdueUnit());
                    parkLotFeeInfo.setOverdueUnitPrice(feeItemEntity.getOverdueUnitPrice());
                    parkLotFeeInfo.setPayStartTime(feeItemEntity.getPayStartTime());
                    parkLotFeeInfo.setPayEndTime(feeItemEntity.getPayEndTime());
                    parkLotFeeInfo.setEffectiveStartTime(feeItemEntity.getEffectiveStartTime());
                    parkLotFeeInfo.setEffectiveEndTime(feeItemEntity.getEffectiveEndTime());
                    parkLotFeeInfo.setVillageId(feeItemEntity.getVillageId());
                    parkLotFeeInfo.setAddTime(feeItemEntity.getAddTime());
                    parkLotFeeInfo.setIsPeriodic(feeItemEntity.getIsPeriodic());
                    parkLotFeeInfo.setVillageEntity(feeItemEntity.getVillageEntity());
                    parkLotFeeInfos.add(parkLotFeeInfo);
                }
                tableData.setJsonString(parkLotFeeInfos);
            }
            return tableData;
        }
        else
            return feeItemDao.getList(tableFilter,feeType);
    }

    @Override
    public Object getParkLotFeeByVillageIdType(Integer villageID, String type)
    {
        return feeItemDao.getParkLotByVillageIdType(villageID,type);
    }

}
