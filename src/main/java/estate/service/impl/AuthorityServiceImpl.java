package estate.service.impl;

import estate.common.config.SsidControlType;
import estate.common.util.LogUtil;
import estate.dao.BrakeDao;
import estate.dao.ParkLotOwnerInfoDao;
import estate.dao.PropertyOwnerInfoDao;
import estate.entity.database.ParklotOwnerInfoEntity;
import estate.entity.database.PropertyOwnerInfoEntity;
import estate.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 *
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService
{
    @Autowired
    private PropertyOwnerInfoDao propertyOwnerInfoDao;
    @Autowired
    private ParkLotOwnerInfoDao parkLotOwnerInfoDao;

    @Override
    public ArrayList<Integer> getAuthorityIDsByPhoneType(String phone, Byte type)
    {
        ArrayList<Integer> ids=new ArrayList<>();
        if (type== SsidControlType.VILLAGE||type==SsidControlType.BUILDING)
        {
            ArrayList<PropertyOwnerInfoEntity> propertyOwnerInfoEntities = propertyOwnerInfoDao.getByPhone(phone);
            if (propertyOwnerInfoEntities==null)
                return null;
            if (type==SsidControlType.BUILDING)
            {
                for (PropertyOwnerInfoEntity propertyOwnerInfoEntity : propertyOwnerInfoEntities)
                {
                    if (!ids.contains(propertyOwnerInfoEntity.getPropertyEntity().getBuildingId()))
                        ids.add(propertyOwnerInfoEntity.getPropertyEntity().getBuildingId());
                }
            }
            else
            {
                for (PropertyOwnerInfoEntity propertyOwnerInfoEntity : propertyOwnerInfoEntities)
                {
                    if (!ids.contains(propertyOwnerInfoEntity.getPropertyEntity().getBuildingEntity().getVillageId()))
                    ids.add(propertyOwnerInfoEntity.getPropertyEntity().getBuildingEntity().getVillageId());
                }
            }
        }
        else if (type==SsidControlType.BRAKE)
        {
            ArrayList<ParklotOwnerInfoEntity> parklotOwnerInfoEntities=parkLotOwnerInfoDao.getByPhone(phone);
            if (parklotOwnerInfoEntities==null)
                return null;
            for (ParklotOwnerInfoEntity parklotOwnerInfoEntity:parklotOwnerInfoEntities)
            {
                if (!ids.contains(parklotOwnerInfoEntity.getParkingLotEntity().getBrakeId()))
                    ids.add(parklotOwnerInfoEntity.getParkingLotEntity().getBrakeId());
            }
        }
        else
            return null;
        return ids;
    }
}
