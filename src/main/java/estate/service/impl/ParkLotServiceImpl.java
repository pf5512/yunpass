package estate.service.impl;

import estate.dao.BaseDao;
import estate.dao.ParkLotDao;
import estate.dao.ParkLotOwnerInfoDao;
import estate.entity.database.ParkingLotEntity;
import estate.entity.database.ParklotOwnerInfoEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.ParkLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-15.
 *
 */
@Service("parkLotService")
public class ParkLotServiceImpl implements ParkLotService
{

    @Autowired
    private ParkLotDao parkLotDao;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private ParkLotOwnerInfoDao parkLotOwnerInfoDao;

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        TableData tableData=parkLotDao.getList(tableFilter);
        tableData.setRecordsTotal(baseDao.count(ParkingLotEntity.class));
        return tableData;
    }

    @Override
    public ArrayList<ParklotOwnerInfoEntity> getByParkLotID(Integer id)
    {
        return parkLotOwnerInfoDao.getByParkLotID(id);
    }

    @Override
    public ArrayList<ParkingLotEntity> getByBrakeID(Integer id)
    {
        return parkLotDao.getByBrakeID(id);
    }

    @Override
    public ArrayList<ParklotOwnerInfoEntity> getByPhone(String phone)
    {
        return parkLotOwnerInfoDao.getByPhone(phone);
    }

    @Override
    public Integer countByType(byte type)
    {
        return parkLotDao.countByType(type);
    }
}
