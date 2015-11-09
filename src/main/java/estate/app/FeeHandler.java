package estate.app;

import estate.common.config.BillPayStatus;
import estate.common.util.Convert;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.entity.app.Bill;
import estate.entity.database.UserBillEntity;
import estate.entity.display.AppBill;
import estate.entity.json.BasicJson;
import estate.entity.json.Select2;
import estate.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kangbiao on 15-10-4.
 * 账单控制器,获取账单
 */
@RestController
@RequestMapping(value = "/api/fee")
public class FeeHandler
{

    @Autowired
    private BillService billService;

    /**
     * 获取账单
     * @param request
     * @return 以数组形式返回用户的账单
     */
    @RequestMapping(value = "/getBill")
    public BasicJson getBill(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);

        try
        {
            UserBillEntity userBillEntity=billService.getBillByPhone("18144240528", BillPayStatus.UNPAY,null,null);
            LogUtil.E(GsonUtil.getGson().toJson(userBillEntity));
            if (userBillEntity!=null)
            {
                ArrayList<Select2> select2s=new ArrayList<>();
                String propertyBill=userBillEntity.getPropertyBill();
                String parkLotBill=userBillEntity.getParkLotBill();
                double total=0.00;
                if (propertyBill!=null)
                {

                    for (String kv: Arrays.asList(propertyBill.split(";")))
                    {
                        try
                        {
                            Select2 select2 = new Select2();
                            select2.setId(kv.split(":")[0]);
                            select2.setText(kv.split(":")[1]);
                            total+=Double.valueOf(kv.split(":")[1]);
                            select2s.add(select2);
                        }
                        catch (Exception e){}
                    }
                }
                if (parkLotBill!=null)
                {

                    for (String kv: Arrays.asList(parkLotBill.split(";")))
                    {
                        try
                        {
                            Select2 select2 = new Select2();
                            select2.setId(kv.split(":")[0]);
                            select2.setText(kv.split(":")[1]);
                            total+=Double.valueOf(kv.split(":")[1]);
                            select2s.add(select2);
                        }
                        catch (Exception e){}
                    }
                }
                ArrayList<Bill> bills=new ArrayList<>();
                Bill bill=new Bill();
                bill.setItems(select2s);
                bill.setId(19);
                bill.setTotal(String.valueOf(total));
                bill.setBillTime(Convert.num2time(System.currentTimeMillis()));
                bills.add(bill);
                basicJson.setJsonString(bills);
            }
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取账单失败");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 获取用户绑定的所有物业的张账单
     * @return
//     */
//    @RequestMapping(value = "/getBill")
//    public BasicJson getBill(HttpServletRequest request)
//    {
//        BasicJson basicJson=new BasicJson();
//        Byte billStatus=null;
//        try
//        {
//            String status=request.getParameter("status");
//            if (!(status.equals("0")||status.equals("1")))
//            {
//                basicJson.getErrorMsg().setDescription("账单状态参数错误");
//                return basicJson;
//            }
//            billStatus = Byte.valueOf(status);
//        }
//        catch (Exception ignore){}
//
//        HttpSession session=request.getSession();
//        String phone= (String) session.getAttribute("phone");
//        try
//        {
//            ArrayList<BillEntity> entities=billService.getBillByAppUserPhone(phone,billStatus);
//            if (entities==null||entities.size()<1)
//            {
//                basicJson.setStatus(true);
//                basicJson.getErrorMsg().setDescription("暂无账单");
//                return basicJson;
//            }
//            ArrayList<AppBill> bills=new ArrayList<>();
//            for (BillEntity billEntity:entities)
//            {
//                ArrayList<Select2> select2s=new ArrayList<>();
//                float total=0;
//                String feeInfo=billEntity.getFeeItemFee();
//                String[] feeCostStrings=feeInfo.split(";");
//                for (String feeCostString:feeCostStrings)
//                {
//                    String[] feeCostArray=feeCostString.split(":");
//                    Select2 select2=new Select2();
//                    select2.setId(feeCostArray[0]);
//                    select2.setText(feeCostArray[1]);
//                    select2s.add(select2);
//                    total+=new Float(feeCostArray[1]);
//                }
//
//                AppBill appBill=new AppBill();
//                appBill.setStatus(billEntity.getPayStatus());
//                appBill.setItems(select2s);
//                appBill.setId(billEntity.getId());
//                appBill.setTotal(String.valueOf(total));
//                appBill.setBillTime(Convert.num2time(billEntity.getBillGenerationTime(),"yyyy-MM"));
//                bills.add(appBill);
//            }
//            LogUtil.E(GsonUtil.getGson().toJson(bills));
//            ArrayList<Select2> parkLotBills= (ArrayList<Select2>) billService.getParkLotBillByPhone(phone);
//            if (parkLotBills!=null)
//            {
//                float total=0;
//                for (Select2 select2:parkLotBills)
//                {
//                    total+=new Float(select2.getText());
//                }
//                AppBill appBill = new AppBill();
//                appBill.setItems(parkLotBills);
//                appBill.setId(99);
//                appBill.setTotal(String.valueOf(total));
//                appBill.setStatus((byte) 0);
//                appBill.setBillTime(Convert.num2time(System.currentTimeMillis()));
//                bills.add(appBill);
//            }
//            basicJson.setJsonString(bills);
//        }
//        catch (Exception e)
//        {
//            basicJson.getErrorMsg().setDescription("获取账单失败!");
//            LogUtil.E("dsdsdd"+e.getMessage());
//            return basicJson;
//        }
//        basicJson.setStatus(true);
//        return basicJson;
//    }
}
