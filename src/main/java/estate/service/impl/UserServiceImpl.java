package estate.service.impl;

import estate.common.config.UserType;
import estate.common.util.Convert;
import estate.dao.*;
import estate.entity.database.AppUserEntity;
import estate.entity.database.OwnerEntity;
import estate.entity.display.Owner;
import estate.entity.display.Tenant;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.exception.AppUserNotExitException;
import estate.exception.UserTypeErrorException;
import estate.service.FamilyService;
import estate.service.PropertyService;
import estate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-16.
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private PropertyOwnerInfoDao propertyOwnerInfoDao;


    @Override
    public void register(AppUserEntity appUserEntity, Integer propertyID)
    {
        baseDao.save(appUserEntity);
//        switch (appUserEntity.getUserRole())
//        {
//            case UserType.TENANT:
//                TenantEntity tenantEntity=new TenantEntity();
//                tenantEntity.setPhone(appUserEntity.getPhone());
//                tenantEntity.setAuthStatus(AppUserStatus.FORCHECK);
//                tenantEntity.setName(appUserEntity.getUserName());
//                tenantEntity.setPropertyId(propertyID);
//                baseDao.save(tenantEntity);
//                break;
//            case UserType.FAMILY:
//                FamilyEntity familyEntity=new FamilyEntity();
//                familyEntity.setName(appUserEntity.getUserName());
//                familyEntity.setPhone(appUserEntity.getPhone());
//                familyEntity.setAuthStatus(AppUserStatus.FORCHECK);
//                familyEntity.setPropertyId(propertyID);
//                baseDao.save(familyEntity);
//                break;
//            default:
//                break;
//        }
    }

    public TableData getOwnerList(TableFilter tableFilter)
    {
        TableData tableData=userDao.getOwnerList(tableFilter);
        ArrayList<OwnerEntity> entities= (ArrayList<OwnerEntity>) tableData.getJsonString();
        ArrayList<Owner> owners= new ArrayList<>();
        for (OwnerEntity ownerEntity:entities)
        {
            Owner owner=new Owner();

            owner.setName(ownerEntity.getName());
            owner.setPhone(ownerEntity.getPhone());
            owner.setIdentityCode(ownerEntity.getIdentityCode());
            owner.setPropertyIdList(ownerEntity.getPropertyIdList());
            owner.setVehicleIdIst(ownerEntity.getVehicleIdIst());
            owner.setUrgentName(ownerEntity.getUrgentName());
            owner.setUrgentPhone(ownerEntity.getUrgentPhone());

            owner.setSex(Convert.num2sex(ownerEntity.getSex()));
            owner.setIdentityType(Convert.num2idtype(ownerEntity.getIdentityType()));
            owner.setAuthenticationTime(Convert.num2time(ownerEntity.getAuthenticationTime()));
            owner.setBirthday(Convert.num2time(ownerEntity.getBirthday()));

//            owner.setFamilies(familyService.getFamiliesByOwnerID(ownerEntity.getId()));
//            owner.setPropertyEntities(propertyService.getPropertiesByString(ownerEntity.getPropertyIdList()));

            owners.add(owner);

        }
        tableData.setJsonString(owners);
        return tableData;
    }


    public TableData getAuthenticatedUserList(TableFilter tableFilter)
    {
        return null;
    }

    @Override
    public TableData getAppUserList(TableFilter tableFilter)
    {
        return userDao.getAppUserList(tableFilter);
    }

    @Override
    public void changeAppUserStatus(AppUserEntity appUserEntity)
    {
        AppUserEntity appUserEntity1;
        appUserEntity1=(AppUserEntity)baseDao.get(appUserEntity.getPhone(), appUserEntity);
//        appUserEntity1.setStatus(appUserEntity.getStatus());
        baseDao.save(appUserEntity1);
    }

    @Override
    public Object getUserInfoByPhoneRole(String phone, int type)
    {
        return userDao.getUserInfoByPhoneRole(phone, type);
    }

    @Override
    public Object getUserDetailByPhone(String phone) throws AppUserNotExitException
    {
//        AppUserEntity appUserEntity= (AppUserEntity) baseDao.get(phone,AppUserEntity.class);
//        if (appUserEntity==null)
//            throw new AppUserNotExitException("该用户不存在");
//        return userDao.getUserInfoByPhoneRole(phone,appUserEntity.getUserRole());
        return null;
    }


    @Override
    public void deleteOwner(String phone)
    {
        userDao.deleteUserByPhone(phone, UserType.OWNER);
    }


    @Override
    public ArrayList<Object> getUserInfoByProperityID(Integer id,int userType) throws UserTypeErrorException
    {
//        switch (userType)
//        {
//            case OWNER:
//                return userDao.getOwnersByPropertyID(id);
//            case UserType.FAMILY:
//                return userDao.getAppUserByPropertyID(id);
//            case UserType.TENANT:
//                break;
//            default:
//                throw new UserTypeErrorException("用户类型错误");
//        }
        return null;
    }


    @Override
    public ArrayList<AppUserEntity> getAllAppUser()
    {
        return userDao.getAllAppUser();
    }

}
