package estate.controller;

import estate.common.config.AppUserStatus;
import estate.common.config.UserType;
import estate.common.util.Convert;
import estate.common.util.LogUtil;
import estate.entity.database.*;
import estate.entity.json.BasicJson;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.BaseService;
import estate.service.PropertyOwnerService;
import estate.service.PropertyService;
import estate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-3.
 *
 */
@RestController
@RequestMapping("/web/user")
public class UserController
{
    @Autowired
    private UserService userService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private PropertyOwnerService propertyOwnerService;


    /**
     * 获取业主列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/ownerList")
    public TableData getOwnerList(TableFilter tableFilter,HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        if(request.getParameter("search[value]")!=null)
            tableFilter.setSearchValue(request.getParameter("search[value]"));
        else
            tableFilter.setSearchValue("");

        try
        {
            return userService.getOwnerList(tableFilter);
        }
        catch (Exception e)
        {
            TableData tableData=new TableData(false);
            tableData.getErrorMsg().setDescription(e.getMessage());
            return tableData;
        }

    }


    /**
     * 增加业主信息
     * @return
     */
    @RequestMapping(value = "/addOwner",method = RequestMethod.POST)
    public BasicJson addOwner(OwnerEntity ownerEntity,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();
        Integer propertyID;
        try
        {
            propertyID=Integer.valueOf(request.getParameter("propertyID"));
            if (propertyID==null)
            {
                basicJson.getErrorMsg().setDescription("未获取到正确的参数");
                return basicJson;
            }
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setDescription("未获取到正确的参数"+e.getMessage());
            return basicJson;
        }
        ownerEntity.setAuthenticationTime(Convert.time2num(request.getParameter("authTime")));
        try
        {
            String msg=propertyOwnerService.addOwnerToProperty(ownerEntity, propertyID);
            if (!msg.equals("succ"))
            {
                basicJson.getErrorMsg().setDescription(msg);
                return basicJson;
            }
        }
        catch (Exception e)
        {
            LogUtil.E(e.getMessage());
            basicJson.getErrorMsg().setDescription("绑定出错,请重试");
            return basicJson;
        }

        basicJson.setStatus(true);
        basicJson.setJsonString(ownerEntity);
        return basicJson;
    }


    /**
     * 通过用户的电话和类型返回该用户关联的所有物业信息
     * @param phone
     * @return
     */
    @RequestMapping(value = "/{userRole}/getPropertiesByPhone/{phone}",method = RequestMethod.GET)
    public BasicJson getPropertiesByOwnerId(@PathVariable String userRole,@PathVariable String phone)
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            switch (userRole)
            {
                case "owner":
                    basicJson.setJsonString(propertyService.getPropertyByPhoneRole(phone, UserType.OWNER));
                    break;
                case "appuser":
                    basicJson.setJsonString(propertyOwnerService.getByPhone(phone));
                    break;
                default:
                    basicJson.getErrorMsg().setDescription("用户类型参数错误");
                    return basicJson;
            }
        }
        catch (Exception e)
        {
            LogUtil.E(e.getMessage());
            basicJson.getErrorMsg().setCode("21233210");
            basicJson.getErrorMsg().setDescription("获取物业信息失败\n错误详情:"+e.getMessage());
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 通过用户类型和电话删除用户
     * 本来应该通过id删除的,但是物业业主信息表是通过业主的电话关联
     * @param userType
     * @param phone
     * @return
     */
    @RequestMapping(value = "/{userType}/delete/{phone}")
    public BasicJson deleteOwner(@PathVariable String userType,@PathVariable String phone)
    {
        BasicJson basicJson=new BasicJson();
        byte type;
        switch (userType)
        {
            case "owner":
                type=UserType.OWNER;
                break;
            case "appuser":
                type=UserType.APPUSER;
                break;
            default:
                basicJson.getErrorMsg().setDescription("请求参数错误!");
                return basicJson;
        }
        try
        {
            //如果用户类型是业主的话,需要判断该业主是否绑定物业,若绑定了则不允许删除
            if (type==UserType.OWNER)
            {
                ArrayList<PropertyOwnerInfoEntity> propertyOwnerInfoEntities= propertyOwnerService.getByPhone(phone);
                if (propertyOwnerInfoEntities!=null)
                {
                    for (PropertyOwnerInfoEntity propertyOwnerInfoEntity : propertyOwnerInfoEntities)
                    {
                        if (propertyOwnerInfoEntity.getUserRole() == UserType.OWNER)
                        {
                            basicJson.getErrorMsg().setDescription("该业主已绑定物业,不能删除");
                            return basicJson;
                        }
                    }
                }
            }
            userService.deleteOwner(phone,type);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("查询失败\n错误详情:"+e.getMessage());
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }


    /**
     * 获取APP用户列表
     * @param tableFilter
     * @param request
     * @return
     */
    @RequestMapping(value = "/appUserList")
    public TableData getAppUserList(TableFilter tableFilter,HttpServletRequest request)
    {
        tableFilter.setSearchValue(request.getParameter("search[value]"));
        try
        {
            return userService.getAppUserList(tableFilter);
        }
        catch (Exception e)
        {
            LogUtil.E(e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/getUserInfoByPhone/{phone}")
    public BasicJson getUserDetailByPhone(@PathVariable String phone,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();

        try
        {
            UserInfoEntity userInfoEntity=userService.getUserDetailByPhone(phone);
            if (userInfoEntity==null)
            {
                userInfoEntity=new UserInfoEntity();
                userInfoEntity.setPhone(phone);
                baseService.save(userInfoEntity);
            }
            basicJson.setJsonString(userInfoEntity);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取详细信息出错");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 改变appuser的状态,是否禁用该用户.
     * @param request
     * @return
     */
    @RequestMapping(value = "/appUserStatus" ,method = RequestMethod.POST)
    public BasicJson disableAppUser(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        String phone=request.getParameter("phone");
        String statusStr=request.getParameter("status");
        Byte status;
        switch (statusStr)
        {
            case "enable":
                status = AppUserStatus.ENABLE;
                break;
            case "disable":
                status = AppUserStatus.DISABLE;
                break;
            default:
                basicJson.getErrorMsg().setDescription("参数错误!");
                return basicJson;
        }
        if (phone==null)
        {
            basicJson.getErrorMsg().setCode("0");
            basicJson.getErrorMsg().setDescription("参数错误!");
            return basicJson;
        }
        else
        {
            try
            {
                AppUserEntity appUserEntity= (AppUserEntity) baseService.get(phone, AppUserEntity.class);
                appUserEntity.setStatus(status);
                baseService.save(appUserEntity);
            }
            catch (Exception e)
            {
                basicJson.getErrorMsg().setCode(e.getMessage());
                basicJson.getErrorMsg().setDescription("操作失败");
                return basicJson;
            }
        }
        basicJson.setStatus(true);
        return basicJson;
    }

}
