package estate.controller;

import estate.common.config.SecretType;
import estate.common.config.SsidControlType;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.entity.database.SsidSecretEntity;
import estate.entity.json.BasicJson;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.exception.TypeErrorException;
import estate.service.BaseService;
import estate.service.BuildingService;
import estate.service.SsidSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kangbiao on 15-9-22.
 *
 */
@RestController
@RequestMapping("/web/secret")
public class SecretController
{
    @Autowired
    BuildingService buildingService;
    @Autowired
    private SsidSecretService ssidSecretService;
    @Autowired
    private BaseService baseService;

    /**
     * 添加密钥
     * @param ssidSecretEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public BasicJson add(SsidSecretEntity ssidSecretEntity,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        if(ssidSecretEntity.getSecret()==null||ssidSecretEntity.getSecret().equals(""))
        {
            basicJson.getErrorMsg().setDescription("密钥不能为空");
            return basicJson;
        }
        try
        {
            LogUtil.E(GsonUtil.getGson().toJson(ssidSecretEntity));
            SecretType.checkType(ssidSecretEntity.getType());
        }
        catch (TypeErrorException e)
        {
            basicJson.getErrorMsg().setDescription(e.getMessage());
            return basicJson;
        }

        if (ssidSecretEntity.getType()== SecretType.WIFI)
        {
            if (ssidSecretEntity.getPassword()==null||ssidSecretEntity.getPassword().equals(""))
            {
                basicJson.getErrorMsg().setDescription("wifi密码不不能为空");
                return basicJson;
            }
        }

        try
        {
            if (ssidSecretService.getSelfBySymbol(ssidSecretEntity.getSymbol())!=null)
            {
                basicJson.getErrorMsg().setDescription("该密钥已存在");
                return basicJson;
            }
            baseService.save(ssidSecretEntity);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("添加失败,请重试");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    @RequestMapping(value = "/list")
    public TableData getList(TableFilter tableFilter,HttpServletRequest request)
    {
        TableData tableData=new TableData();
        tableFilter.setSearchValue(request.getParameter("search[value]"));

        try
        {
            tableData=ssidSecretService.getList(tableFilter);
        }
        catch (Exception e)
        {
            tableData.getErrorMsg().setCode(e.getMessage());
            tableData.getErrorMsg().setDescription("获取失败");
        }
        return tableData;
    }

    /**
     * 获取未使用的密钥
     * @param request
     * @return
     */
    @RequestMapping(value = "/select")
    public BasicJson getSelect2(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            basicJson.setJsonString(ssidSecretService.getSelect2());
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setDescription("获取数据失败");
            basicJson.getErrorMsg().setCode(e.getMessage()+"数据库异常");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 设置密钥的作用对象
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "/setSecret/{type}")
    public BasicJson setSecret(@PathVariable String type,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        Integer id;
        Integer controlId;
        try
        {
            id= Integer.valueOf(request.getParameter("id"));
            controlId= Integer.valueOf(request.getParameter("controlId"));
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setDescription("参数错误");
            return basicJson;
        }
        byte controlType;
        switch (type)
        {
            case "village":
                controlType=SsidControlType.VILLAGE;
                break;
            case "brake":
                controlType=SsidControlType.BRAKE;
                break;
            case "building":
                controlType=SsidControlType.BUILDING;
                break;
            default:
                basicJson.getErrorMsg().setDescription("请求路径错误!");
                return basicJson;
        }
        try
        {
            SsidSecretEntity ssidSecretEntity= (SsidSecretEntity) baseService.get(id,SsidSecretEntity.class);
            ssidSecretEntity.setControlId(controlId);
            ssidSecretEntity.setControlType(controlType);
            baseService.save(ssidSecretEntity);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setDescription("设置失败,请刷新重试");
            basicJson.getErrorMsg().setCode(e.getMessage());
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }
}
