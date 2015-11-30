package estate.service.impl;

import estate.common.FeeRuleUnit;
import estate.common.config.BillPayStatus;
import estate.common.config.ParkLotOwnerRole;
import estate.common.util.Convert;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.dao.*;
import estate.entity.database.*;
import estate.entity.json.ParkLotExtra;
import estate.exception.PropertyNotBindFeeItemException;
import estate.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-6.
 *
 */
@Service("billService")
public class BillServiceImpl implements BillService
{
    @Autowired
    private FeeItemOrderDao feeItemOrderDao;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private ParkLotOwnerInfoDao parkLotOwnerInfoDao;
    @Autowired
    private PropertyOwnerInfoDao propertyOwnerInfoDao;
    @Autowired
    private BillDao billDao;
    @Autowired
    private FeeItemDao feeItemDao;

    @Override
    public UserBillEntity getBillByPhone(String phone, Byte status, Long startTime, Long endTime)
    {
        UserBillEntity userBillEntity=new UserBillEntity();
        StringBuilder serviceBill=new StringBuilder();
        StringBuilder propertyBill=new StringBuilder();
        StringBuilder parkLotBill=new StringBuilder();

        //获取物业账单
        ArrayList<PropertyOwnerInfoEntity> propertyOwnerInfoEntities= propertyOwnerInfoDao.getByPhone(phone);
        if (propertyOwnerInfoEntities!=null)
        {
            int temp=0;
            for (PropertyOwnerInfoEntity propertyOwnerInfoEntity : propertyOwnerInfoEntities)
            {
                Integer propertyId = propertyOwnerInfoEntity.getPropertyEntity().getId();
                ArrayList<PropertyBillEntity> propertyBillEntities = billDao
                        .getPropertyBillByPropertyID(propertyId, BillPayStatus.UNPAY, null, null);
                if (propertyBillEntities!=null)
                {
                    int temp2 = 0;
                    for (PropertyBillEntity propertyBillEntity : propertyBillEntities)
                    {
                        if (temp!=0&&temp2==0)
                        {
                            propertyBill.append(";");
                        }
                        if (temp2==0)
                        {
                            propertyBill.append(propertyBillEntity.getFeeItemFee());
                        }
                        else
                        {
                            propertyBill.append(";").append(propertyBillEntity.getFeeItemFee());
                        }
                        temp2++;
                    }
                    temp++;
                }
            }
            userBillEntity.setPropertyBill(propertyBill.toString());
        }

        //获取车位账单
        ArrayList<ParklotOwnerInfoEntity> parklotOwnerInfoEntities=parkLotOwnerInfoDao.getByPhone(phone);
        if (parklotOwnerInfoEntities!=null)
        {
            int temp=0;
            for (ParklotOwnerInfoEntity parklotOwnerInfoEntity : parklotOwnerInfoEntities)
            {
                FeeItemEntity feeItemEntity = (FeeItemEntity) feeItemDao.getParkLotByVillageIdType
                        (parklotOwnerInfoEntity.getParkingLotEntity().getBrakeEntity().getVillageId(),
                                String.valueOf(parklotOwnerInfoEntity.getParkingLotEntity().getType()));
                if (feeItemEntity != null)
                {
                    ParkLotExtra parkLotExtra = (GsonUtil.getGson().fromJson(feeItemEntity.getExtendInfo(), ParkLotExtra.class));
                    switch (parklotOwnerInfoEntity.getOwnerType())
                    {
                        case ParkLotOwnerRole.OWNER:
                            if (temp == 0)
                            {

                                parkLotBill
                                        .append("车位管理费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getManagePrice());
                            } else
                            {
                                parkLotBill
                                        .append(";")
                                        .append("车位管理费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getManagePrice());
                            }
                            break;
                        case ParkLotOwnerRole.TEMP:
                            parkLotExtra.getPerTimePrice();
                            if (temp == 0)
                            {
                                parkLotBill
                                        .append("车位费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getPerTimePrice());
                            } else
                            {
                                parkLotBill
                                        .append(";")
                                        .append("车位费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getPerTimePrice());
                            }
                            break;
                        case ParkLotOwnerRole.TENANT:
                            parkLotExtra.getMonthPrice();
                            if (temp == 0)
                            {
                                parkLotBill
                                        .append("车位月租费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getMonthPrice());
                            } else
                            {
                                parkLotBill
                                        .append(";")
                                        .append("车位月租费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getMonthPrice());
                            }
                            break;
                        case ParkLotOwnerRole.USER:
                            if (temp == 0)
                            {
                                parkLotBill
                                        .append("车位管理费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getManagePrice());
                            } else
                            {
                                parkLotBill
                                        .append(";")
                                        .append("车位管理费(")
                                        .append(parklotOwnerInfoEntity.getParkingLotEntity().getCode())
                                        .append("):")
                                        .append(parkLotExtra.getManagePrice());
                            }
                            break;
                        default:
                            break;
                    }
                    temp++;
                }
            }
            userBillEntity.setParkLotBill(parkLotBill.toString());
        }

        return userBillEntity;
    }

    @Override
    public void generateBillByPropertyID(Integer id)
    {
        ArrayList<FeeItemOrderEntity> feeItemOrderEntities =feeItemOrderDao.getFeeItemOrdersByPropertyID(id);
        PropertyEntity propertyEntity= (PropertyEntity) baseDao.get(id,PropertyEntity.class);

        if (feeItemOrderEntities==null)
            return;

        StringBuilder billInfo=new StringBuilder("");
        PropertyBillEntity propertyBillEntity = new PropertyBillEntity();

        Long thisMonth = Convert.time2num(Convert.num2time(System.currentTimeMillis(), "yyyy-MM"), "yyyy-MM");

        ArrayList<PropertyBillEntity> propertyBillEntities=billDao
                .getPropertyBillByPropertyID(id, BillPayStatus.UNPAY, thisMonth, null);
        if (propertyBillEntities!=null)
        {
            for (PropertyBillEntity propertyBillEntityTemp:propertyBillEntities)
            {
                propertyBillEntity=propertyBillEntityTemp;
            }
        }

        int forCount=0;
        for (FeeItemOrderEntity feeItemOrderEntity:feeItemOrderEntities)
        {
            String kv;
            if (feeItemOrderEntity.getFeeItemEntity().getUnit().equals(FeeRuleUnit.SQURE))
            {
                double unitPrice=feeItemOrderEntity.getFeeItemEntity().getUnitPrice().doubleValue();
                double price=unitPrice*propertyEntity.getPropertySquare().doubleValue();
                BigDecimal sum=new BigDecimal(price);
                sum=sum.setScale(2,BigDecimal.ROUND_HALF_UP);
                kv=feeItemOrderEntity.getFeeItemEntity().getName()+":"+sum;
            }
            else if (feeItemOrderEntity.getFeeItemEntity().getUnit().equals(FeeRuleUnit.PERPROPERTY))
            {
                kv=feeItemOrderEntity.getFeeItemEntity().getName()+":"+feeItemOrderEntity
                        .getFeeItemEntity().getUnitPrice();
            }
            else
                kv="";
            if (forCount==0)
            {
                billInfo.append(kv);
            }
            else
            {
                billInfo.append(";").append(kv);
            }
            forCount++;
        }

        propertyBillEntity.setBillGenerationTime(System.currentTimeMillis());
        propertyBillEntity.setFeeItemFee(billInfo.toString());
        propertyBillEntity.setPayStatus(BillPayStatus.UNPAY);
        propertyBillEntity.setPropertyId(id);
        baseDao.save(propertyBillEntity);
    }

}
