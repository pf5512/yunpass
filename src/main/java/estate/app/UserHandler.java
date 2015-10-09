package estate.app;

import estate.common.CardType;
import estate.common.SexType;
import estate.common.UserType;
import estate.common.util.Convert;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.common.util.VerifyCodeGenerate;
import estate.entity.database.AppUserEntity;
import estate.entity.database.FamilyEntity;
import estate.entity.database.OwnerEntity;
import estate.entity.database.TenantEntity;
import estate.entity.json.BasicJson;
import estate.service.AppUserService;
import estate.service.BaseService;
import estate.service.PropertyService;
import estate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by kangbiao on 15-9-21.
 * 登陆,登出,注册(获取验证码,核对验证码,绑定业主)
 */
@RestController
@RequestMapping("api/uc")
public class UserHandler
{
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public BasicJson login(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        String phone=request.getParameter("phone");
        String password=request.getParameter("password");
        LogUtil.E("phone:"+phone+"password:"+password);
        AppUserEntity appUserEntity= (AppUserEntity) appUserService.getByPhone(phone);
        if (appUserEntity==null)
        {
            basicJson.getErrorMsg().setDescription("用户不存在");
            return basicJson;
        }
        if (!password.equals(appUserEntity.getPasswd()))
        {
            basicJson.getErrorMsg().setDescription("密码错误");
            return basicJson;
        }

        basicJson.setStatus(true);
        request.getSession().setAttribute("phone", phone);
        request.getSession().setAttribute("username", appUserEntity.getUserName());
        request.getSession().setAttribute("role", appUserEntity.getUserRole());
        basicJson.setJsonString(request.getSession().getId());
        return basicJson;
    }

    @RequestMapping(value = "/loginOut",method = RequestMethod.GET)
    public BasicJson loginOut(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(true);
        request.getSession().removeAttribute("phone");
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("role");
        return basicJson;
    }

    //TODO 全部要改
    @RequestMapping(value = "/register/getVerifyCode/{phone}",method = RequestMethod.GET)
    public BasicJson getVerifyCode(@PathVariable String phone,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        if (phone==null)
        {
            basicJson.getErrorMsg().setDescription("请输入手机号!");
            return basicJson;
        }
        if (appUserService.getByPhone(phone)!=null)
        {
            basicJson.getErrorMsg().setDescription("手机号码已注册");
            return basicJson;
        }
        String verifyCode=VerifyCodeGenerate.create();
        LogUtil.E("Session:"+verifyCode);
//
//        if (!Message.send(phone,"感谢您注册VerPass您的验证码是"+verifyCode).equals("succ"))
//        {
//            basicJson.getErrorMsg().setDescription("验证码发送失败");
//            return basicJson;
//        }
        request.getSession().setAttribute("verifyCode", verifyCode);
        request.getSession().setAttribute("phone", phone);
        basicJson.setStatus(true);
        basicJson.setJsonString(request.getSession().getId());
        return basicJson;
    }

    @RequestMapping(value = "/register/checkVerifyCode/{verifyCode}",method = RequestMethod.GET)
    public BasicJson checkVerifyCode(HttpServletRequest request,@PathVariable String verifyCode)
    {
        BasicJson basicJson=new BasicJson(false);
        if (verifyCode==null|| Objects.equals(verifyCode, ""))
        {
            basicJson.getErrorMsg().setDescription("请输入验证码");
            return basicJson;
        }
        if (!verifyCode.equals("101010"))
        {
            LogUtil.E(request.getSession().getAttribute("verifyCode"));
            basicJson.getErrorMsg().setDescription("验证码输入错误!");
            return basicJson;
        }

        request.getSession().removeAttribute("verifyCode");
        basicJson.setStatus(true);
        return basicJson;
    }

    @RequestMapping(value = "/register/doRegister",method = RequestMethod.GET)
    public BasicJson regist(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        AppUserEntity appUserEntity=new AppUserEntity();
        String phone= (String) request.getSession().getAttribute("phone");
        String userName=request.getParameter("nickname");
        String password=request.getParameter("password");

        Object o=userService.getUserInfoBYPhone(phone, UserType.OWNER);
        if (o==null)
        {
            basicJson.getErrorMsg().setCode("100001");
        }
        else
        {
//            appUserEntity.setUserRole(UserType.OWNER);
            basicJson.getErrorMsg().setCode("100000");
        }

        appUserEntity.setPhone(phone);
        appUserEntity.setUserName(userName);
        appUserEntity.setPasswd(password);
        appUserEntity.setStatus((byte) 1);

        try
        {
            baseService.save(appUserEntity);
        }
        catch (Exception e)
        {
            LogUtil.E("错误:"+e.getMessage());
            basicJson.getErrorMsg().setDescription("注册失败");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 绑定业主
     * @param request
     * @return
     */
    @RequestMapping(value = "/register/bind",method = RequestMethod.POST)
    public BasicJson bindOwner(HttpServletRequest request)
    {
        LogUtil.E("---已进入请求---");
        BasicJson basicJson=new BasicJson(false);
        Integer propertyId=Integer.valueOf(request.getParameter("id"));
        int role=Integer.valueOf(request.getParameter("role"));
        String phone=request.getParameter("phone");

        LogUtil.E("role:" + String.valueOf(role) + "phone:" + phone + "propertyId:" + propertyId);

        AppUserEntity appUserEntity= (AppUserEntity) userService.getUserInfoBYPhone(phone,UserType.APPUSER);
        appUserEntity.setUserRole(role);
        baseService.save(appUserEntity);

//        appUserEntity= (AppUserEntity) baseService.get(id,appUserEntity);

        switch (role)
        {
            case UserType.FAMILY:
                FamilyEntity familyEntity=new FamilyEntity();
                familyEntity.setPropertyId(propertyId);
                familyEntity.setPhone(appUserEntity.getPhone());
                familyEntity.setName(appUserEntity.getUserName());
                familyEntity.setAuthStatus(Byte.valueOf("0"));
                try
                {
                    baseService.save(familyEntity);
                }
                catch (Exception e)
                {
                    basicJson.getErrorMsg().setDescription("保存到家庭时出错");
                    return basicJson;
                }
                break;
            case UserType.TENANT:
                TenantEntity tenantEntity=new TenantEntity();
                tenantEntity.setPropertyId(propertyId);
                tenantEntity.setAuthStatus(Byte.valueOf("0"));
                tenantEntity.setPhone(appUserEntity.getPhone());
                tenantEntity.setName(appUserEntity.getUserName());
                try
                {
                    baseService.save(tenantEntity);
                }
                catch (Exception e)
                {
                    basicJson.getErrorMsg().setDescription("保存到租户时出错");
                    return basicJson;
                }
                break;
            default:
                basicJson.getErrorMsg().setDescription("用户类型参数错误");
                return basicJson;

        }
        basicJson.setStatus(true);
        return basicJson;
    }

    @RequestMapping(value = "/modify/{action}")
    public BasicJson modify(@PathVariable String action,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();
        String phone= (String) request.getSession().getAttribute("phone");
        int role= (int) request.getSession().getAttribute("role");
        switch (action)
        {
            case "password":
                AppUserEntity appUserEntity= (AppUserEntity) userService.getUserInfoBYPhone(phone,UserType.APPUSER);
                String oldPassword=request.getParameter("oldPassword");
                String newPassword=request.getParameter("newPassword");
                if (!oldPassword.equals(appUserEntity.getPasswd()))
                {
                    basicJson.getErrorMsg().setDescription("原密码错误");
                    return basicJson;
                }
                appUserEntity.setPasswd(newPassword);
                try
                {
                    baseService.save(appUserEntity);
                }
                catch (Exception e)
                {
                    LogUtil.E("保存用户信息失败"+e.getMessage());
                    basicJson.getErrorMsg().setDescription("修改密码失败");
                    return basicJson;
                }
                break;
            case "getProfile":
                try
                {
                    Object o=userService.getUserInfoBYPhone(phone,role);
                    if (o==null)
                    {
                        basicJson.getErrorMsg().setDescription("获取用户信息失败");
                        return basicJson;
                    }
                    basicJson.setJsonString(o);
                }
                catch (Exception e)
                {
                    basicJson.getErrorMsg().setDescription("获取用户信息出错");
                    return basicJson;
                }
                break;
            case "submitProfile":
                Byte identityType,sex;
                String name,urgentName,urgentPhone,identityCode;
                Long birthday;
                try
                {
                    identityType= Byte.valueOf(request.getParameter("identityType"));
                    CardType.checkType(identityType);
                    name=request.getParameter("name");
                    birthday= Convert.time2num(request.getParameter("birthday"));
                    LogUtil.E("birthday"+request.getParameter("birthday"));
                    urgentName = request.getParameter("urgentName");
                    urgentPhone=request.getParameter("urgentPhone");
                    identityCode=request.getParameter("identityCode");
                    sex= Byte.valueOf(request.getParameter("sex"));
                    SexType.checkType(sex);
                }
                catch (Exception e)
                {
                    basicJson.getErrorMsg().setCode("100000");
                    basicJson.getErrorMsg().setDescription("参数错误");
                    return basicJson;
                }

                try
                {
                    if (role==UserType.FAMILY)
                    {
                        FamilyEntity familyEntity= (FamilyEntity) userService.getUserInfoBYPhone(phone, role);
                        familyEntity.setName(name);
                        familyEntity.setSex(sex);
                        familyEntity.setBirthday(birthday);
                        familyEntity.setUrgentName(urgentName);
                        familyEntity.setUrgentPhone(urgentPhone);
                        familyEntity.setIdentityType(identityType);
                        familyEntity.setIdentityCode(identityCode);
                        baseService.save(familyEntity);
                    }
                    else if (role==UserType.TENANT)
                    {
                        TenantEntity tenantEntity= (TenantEntity) userService.getUserInfoBYPhone(phone, role);
                        tenantEntity.setName(name);
                        tenantEntity.setSex(sex);
                        tenantEntity.setBirthday(birthday);
                        tenantEntity.setUrgentName(urgentName);
                        tenantEntity.setUrgentPhone(urgentPhone);
                        tenantEntity.setIdentityType(identityType);
                        tenantEntity.setIdentityCode(identityCode);
                        baseService.save(tenantEntity);
                    }
                    else if (role==UserType.OWNER)
                    {
                        OwnerEntity ownerEntity= (OwnerEntity) userService.getUserInfoBYPhone(phone, role);
                        ownerEntity.setName(name);
                        ownerEntity.setSex(sex);
                        ownerEntity.setBirthday(birthday);
                        ownerEntity.setUrgentName(urgentName);
                        ownerEntity.setUrgentPhone(urgentPhone);
                        ownerEntity.setIdentityType(identityType);
                        ownerEntity.setIdentityCode(identityCode);
                        LogUtil.E(GsonUtil.getGson().toJson(ownerEntity));
                        baseService.save(ownerEntity);
                    }

                }
                catch (Exception e)
                {
                    LogUtil.E("保存用户资料时出错"+e.getMessage());
                    basicJson.getErrorMsg().setDescription("修改密码失败");
                    return basicJson;
                }
                break;
            default:
                basicJson.getErrorMsg().setDescription("请求路径错误!");
                return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }
}
