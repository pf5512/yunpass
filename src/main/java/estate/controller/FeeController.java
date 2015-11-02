package estate.controller;

import estate.common.Config;
import estate.common.config.FeeType;
import estate.common.config.UserType;
import estate.common.util.Convert;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.entity.database.FeeItemEntity;
import estate.entity.database.OwnerEntity;
import estate.entity.database.PropertyEntity;
import estate.entity.json.BasicJson;
import estate.entity.json.ParkLotExtra;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.exception.PropertyNotBindFeeItemException;
import estate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-14.
 * 提供缴费管理的所有费用类型的增删改查
 */

@RestController
@RequestMapping("/web/fee")
public class FeeController
{
    @Autowired
    private FeeService feeService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private UserService userService;

    /**
     * 增加三种费用
     * @param feeTypeString
     * @param feeItemEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/add/{feeTypeString}")
    public BasicJson addFeeItem(@PathVariable String feeTypeString,FeeItemEntity feeItemEntity,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            feeItemEntity.setPayStartTime(Convert.time2num(request.getParameter("payStartTimeRaw")));
            feeItemEntity.setPayEndTime(Convert.time2num(request.getParameter("payEndTimeRaw")));
            feeItemEntity.setEffectiveStartTime(Convert.time2num(request.getParameter("effectiveStartTimeRaw")));
            feeItemEntity.setEffectiveEndTime(Convert.time2num(request.getParameter("effectiveEndTimeRaw")));
            feeItemEntity.setIsEffective(Config.TRUE);
            feeItemEntity.setAddTime(System.currentTimeMillis());
            switch (feeTypeString)
            {
                case "estate":
                    feeItemEntity.setFeeType(FeeType.ESTATE);
                    feeItemEntity.setIsPeriodic(Config.TRUE);
                    break;
                case "service":
                    feeItemEntity.setFeeType(FeeType.SERVICE);
                    feeItemEntity.setIsPeriodic(Config.FALSE);
                    break;
                case "parkLot":
                    if (feeService.getParkLotFeeByVillageIdType(feeItemEntity.getVillageId(),feeItemEntity.getName())!=null)
                    {
                        basicJson.getErrorMsg().setDescription("该园区已经配置该类别车位的费用信息");
                        return basicJson;
                    }
                    feeItemEntity.setFeeType(FeeType.PARKING_LOT);
                    feeItemEntity.setIsPeriodic(Config.TRUE);

                    ParkLotExtra parkLotExtra=new ParkLotExtra();
                    parkLotExtra.setMonthPrice(request.getParameter("monthPrice"));
                    parkLotExtra.setPerTimePrice(request.getParameter("perTimePrice"));
                    parkLotExtra.setManagePrice(request.getParameter("managePrice"));
                    feeItemEntity.setExtendInfo(GsonUtil.getGson().toJson(parkLotExtra));
                    break;
                default:
                    basicJson.getErrorMsg().setDescription("请求路径错误");
                    return basicJson;
            }
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("参数有误!");
            return basicJson;
        }

        try
        {
            baseService.save(feeItemEntity);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("费用信息增加失败,请重试");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 获取三种费用数据
     * @param feeType
     * @param tableFilter
     * @param request
     * @return
     */
    @RequestMapping(value = "/list/{feeType}")
    public TableData feeList(@PathVariable String feeType, TableFilter tableFilter,HttpServletRequest request)
    {
        tableFilter.setSearchValue(request.getParameter("search[value]"));

        TableData tableData=new TableData(false);
        try
        {
            switch (feeType)
            {
                case "estate":
                    return feeService.feeList(tableFilter,FeeType.ESTATE);
                case "service":
                    return feeService.feeList(tableFilter,FeeType.SERVICE);
                case "parkLot":
                    return feeService.feeList(tableFilter, FeeType.PARKING_LOT);
                default:
                    tableData.getErrorMsg().setCode("1000525");
                    tableData.getErrorMsg().setDescription("请求路径错误");
                    return tableData;
            }
        }
        catch (Exception e)
        {
            tableData.getErrorMsg().setCode(e.getMessage());
            tableData.getErrorMsg().setDescription("获取费用列表失败,请重试");
            return tableData;
        }
    }

    /**
     * 删除费用信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete/{feeTypeString}/{feeItemId}")
    public BasicJson feeDelete(@PathVariable(value = "feeTypeString") String feeTypeString,
                               @PathVariable(value = "feeItemId") Integer feeItemId,
                               HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            switch (feeTypeString)
            {
                case "estate":
                    feeService.deleteFee(FeeType.ESTATE, feeItemId);
                    break;
                case "service":
                    feeService.deleteFee(FeeType.SERVICE,feeItemId);
                    break;
                case "parkLot":
                    feeService.deleteFee(FeeType.PARKING_LOT,feeItemId);
                    break;
                default:
                    basicJson.getErrorMsg().setDescription("请求路径错误");
                    return basicJson;
            }
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("该费用已绑定,不能删除!");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }



//    /**
//     * 将物业和费用项目绑定
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/relateBuilding")
//    public BasicJson relateBuilding(HttpServletRequest request)
//    {
//        BasicJson basicJson=new BasicJson();
//        Integer feeItemID;
//        ArrayList<Integer> buildingIDs;
//        try
//        {
//            feeItemID=Integer.valueOf(request.getParameter("feeItemID"));
//            buildingIDs=Convert.string2ints(request.getParameter("buildingIDs"),",");
//        }
//        catch (Exception e)
//        {
//            basicJson.getErrorMsg().setDescription("参数错误\n"+e.getMessage());
//            return basicJson;
//        }
//        try
//        {
//            feeService.relateBuilding(buildingIDs,feeItemID);
//        }
//        catch (Exception e)
//        {
//            LogUtil.E(e.getMessage());
//        }
//        LogUtil.E(buildingIDs);
//        LogUtil.E(feeItemID);
//        basicJson.setStatus(true);
//        return basicJson;
//    }

    @RequestMapping(value = "/getBillList")
    public TableData getBillList(TableFilter tableFilter,HttpServletRequest request)
    {
        TableData tableData=new TableData();
        ArrayList<FeeItemEntity> feeItemEntities=new ArrayList<>();
        tableData.setJsonString(feeItemEntities);
        tableFilter.setSearchValue(request.getParameter("search[value]"));
        tableFilter.setStatus(Byte.valueOf(request.getParameter("billStatus")));
        tableFilter.setStartTime(Convert.time2num(request.getParameter("billStartTime")));
        tableFilter.setEndTime(Convert.time2num(request.getParameter("billEndTime")));
        if (tableFilter.getStatus()==-1)
            tableFilter.setStatus(null);
        if (tableFilter.getSearchValue().equals(""))
            tableFilter.setSearchValue(null);
        try
        {
            return tableData;
        }
        catch (Exception e)
        {
            return null;
        }
    }

}
