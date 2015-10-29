package estate.service.impl;

import estate.common.config.SsidControlType;
import estate.dao.PropertyOwnerInfoDao;
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
                     ids.add(propertyOwnerInfoEntity.getPropertyEntity().getBuildingId());
                }
            }
            else
            {
                for (PropertyOwnerInfoEntity propertyOwnerInfoEntity : propertyOwnerInfoEntities)
                {
                    ids.add(propertyOwnerInfoEntity.getPropertyEntity().getBuildingEntity().getVillageId());
                }
            }
            return ids;
        }
        else if (type==SsidControlType.BRAKE)
        {

            return null;
        }
        else
            return null;
    }
}
