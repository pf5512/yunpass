package estate.service;

import estate.entity.database.PropertyEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-16.
 *
 */
public interface PropertyService
{

    TableData getList(TableFilter tableFilter);

    /**
     * 获取所有的园区信息,以select2的数组返回
     * @return
     */
    Object getAllVillage ();

    /**
     * 通过园区的id获取该园区下所有的楼栋信息,以select2的数组返回
     * @param id
     * @return
     */
    Object getBuildingsByValliageId(Integer id);

    /**
     * 通过楼栋id获取该楼栋下面的所有物业,以select2的数组返回
     * @param buildingID
     * @return
     */
    Object getPropertyByBuildingId(Integer buildingID);


    /**
     * 通过楼栋id返回该楼栋下的所有物业
     * @param id
     * @return
     */
    ArrayList<PropertyEntity> getByBuildingID(Integer id);


    /**
     * 通过物业编号获取物业信息
     * @param code
     * @return
     */
    PropertyEntity getByCode(String code);

    /**
     * 通过用户电话和类型获取用户绑定的所有的物业
     * @param phone
     * @return
     */
    ArrayList<PropertyEntity> getPropertyByPhoneRole(String phone, Byte userRole);


    /**
     * 根据物业类型获取数量
     * @param type
     * @return
     */
    Integer getNumByType(byte type);

    /**
     * 获取所有的物业
     * @return
     */
    ArrayList<PropertyEntity> getAllProperty();


}
