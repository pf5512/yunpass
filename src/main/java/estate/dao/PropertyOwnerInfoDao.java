package estate.dao;

import estate.entity.database.PropertyOwnerInfoEntity;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-26.
 *
 */
public interface PropertyOwnerInfoDao
{


    /**
     * 通过电话,物业id和绑定关系删除绑定记录
     * @param phone
     * @param id
     * @param role
     */
    void deleteByPhonePropertyID(String phone,Integer id,Byte role);

    /**
     * 通过电话返回物业和用户的绑定关系
     * @param phone
     * @return
     */
    ArrayList<PropertyOwnerInfoEntity> getByPhone(String phone);

    /**
     * 通过电话和审核状态获取用户和物业的绑定关系
     * @param phone
     * @param status
     * @return
     */
    ArrayList<PropertyOwnerInfoEntity> getByPhoneStatus(String phone,byte status);

    /**
     * 通过物业id和状态返回
     * @param propertyID
     * @param status
     * @return
     */
    ArrayList<PropertyOwnerInfoEntity> getBindBypropertyIDStatus(Integer propertyID,Byte status);

    /**
     * 通过电话和物业id获取绑定关系
     * @param phone
     * @param propertyID
     * @return
     */
    ArrayList<PropertyOwnerInfoEntity> getByPhonePropertyID(String phone , Integer propertyID);

    /**
     * 根据物业id和用户角色返回对应的绑定关系
     * @param propertyID
     * @param role 为null则返回所有角色的绑定关系
     * @return
     */
    ArrayList<PropertyOwnerInfoEntity> getByPropertyIdRole(Integer propertyID,Byte role);


}
