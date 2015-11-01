package estate.service;

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


    ArrayList<Object> getBillByPhone(String phone,Byte status,Long startTime,Long endTime);

    /**
     * 根据物业id生成这个物业的账单
     * @param id
     */
    void generateBillByPropertyID(Integer id) throws PropertyNotBindFeeItemException;

    /**
     * 通过用户的电话获取这个用户的车位费账单
     * @param phone
     * @return
     */
//    Object getParkLotBillByPhone(String phone);
}
