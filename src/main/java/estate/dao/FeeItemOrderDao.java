package estate.dao;

import estate.entity.database.FeeItemOrderEntity;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-24.
 *
 */
public interface FeeItemOrderDao
{
    /**
     * 根据物业id返回该物业绑定的所有的费用信息实体,每一个实体包含一个费用详细信息实体
     * @param id
     * @return
     */
    ArrayList<FeeItemOrderEntity> getFeeItemOrdersByPropertyID(Integer id);


    /**
     * 通过费用id删除该费用的所有绑定关系
     * @param id
     */
    void deleteAllByFeeItemID(Integer id);

    /**
     * 通过物业id和费用id获取绑定关系
     * @param propertyID
     * @param feeItemID
     * @return
     */
    FeeItemOrderEntity getByPropertyIdFeeItemId(Integer propertyID,Integer feeItemID);
}
