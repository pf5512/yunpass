package estate.controller;

import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.entity.database.ConsoleGroupEntity;
import estate.entity.database.ConsoleUserEntity;
import estate.entity.display.AdminMenu;
import estate.entity.json.BasicJson;
import estate.service.BaseService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by kangbiao on 15-11-3.
 * 管理人员相关的控制器
 */
@RestController
@RequestMapping("/web/admin")
public class AdminController
{

    Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    private BaseService baseService;

    /**
     * 增加后台管理人员
     * @param consoleGroupEntity
     * @param request
     * @return 返回操作状态
     */
    @RequestMapping(value = "/addGroup")
    public BasicJson add(ConsoleGroupEntity consoleGroupEntity,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        AdminMenu adminMenu=new AdminMenu();

        Map<String,String[]> params=request.getParameterMap();
        try
        {
            adminMenu.setProperty(params.get("property")==null?"off":"on");
            adminMenu.setAdmin(params.get("admin")==null?"off":"on");
            adminMenu.setComplainRepair(params.get("complainRepair")==null?"off":"on");
            adminMenu.setFee(params.get("fee")==null?"off":"on");
            adminMenu.setNotice(params.get("notice")==null?"off":"on");
            adminMenu.setParkLot(params.get("parkLot")==null?"off":"on");
            adminMenu.setSecret(params.get("secret")==null?"off":"on");
            adminMenu.setUser(params.get("user")==null?"off":"on");

            LogUtil.E(GsonUtil.getGson().toJson(adminMenu));

            LogUtil.E(params.get("prr"));
            baseService.save(consoleGroupEntity);
        }
        catch (Exception e)
        {
            logger.error("添加用户组是数据库异常:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("添加用户组失败,请重试");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }


    /**
     * 添加后台管理人员
     * @param consoleUserEntity
     * @param request
     * @return 返回操作结果
     */
    public BasicJson addAdmin(ConsoleUserEntity consoleUserEntity ,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);

        try
        {

            LogUtil.E(GsonUtil.getGson().toJson(request.getParameterMap()));


        }
        catch (Exception e)
        {

        }


        basicJson.setStatus(true);
        return basicJson;
    }





}
