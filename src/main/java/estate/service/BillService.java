package estate.service;

import estate.entity.database.UserBillEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.exception.PropertyNotBindFeeItemException;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-6.
 *
 */
public interface BillService
{

    ArrayList<UserBillEntity> getUserBill(String phone,Byte status,Long startTime,Long endTime);

    String getPropertyBillString(String phone,Byte status,Long startTime,Long endTime);

    UserBillEntity getBillByPhone(String phone,Byte status,Long startTime,Long endTime);

    /**
     * 根据物业id生成这个物业的账单
     * @param id
     */
    void generateBillByPropertyID(Integer id);

    /**
     * 通过用户的电话获取这个用户的车位费账单
     * @param phone
     * @return
     */
//    Object getParkLotBillByPhone(String phone);

    /**
     * 生成所有用户的账单
     */
    void generateUserBill(String phone);

    /**
     * 获取app用户的账单列表
     * @param tableFilter
     * @return
     */
    TableData getBill(TableFilter tableFilter);
}
