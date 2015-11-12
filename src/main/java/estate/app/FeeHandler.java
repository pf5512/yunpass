package estate.app;

import estate.common.config.BillPayStatus;
import estate.common.util.Convert;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.entity.app.Bill;
import estate.entity.database.UserBillEntity;
import estate.entity.json.BasicJson;
import estate.entity.json.Select2;
import estate.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        String phone= (String) request.getSession().getAttribute("phone");
        try
        {
            UserBillEntity userBillEntity=billService.getBillByPhone(phone, BillPayStatus.UNPAY,null,null);
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
}
