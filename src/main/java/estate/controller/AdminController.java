package estate.controller;

import estate.common.util.LogUtil;
import estate.entity.database.ConsoleUserEntity;
import estate.entity.json.BasicJson;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kangbiao on 15-11-3.
 * 管理人员相关的控制器
 */
@RestController
@RequestMapping("/web/admin")
public class AdminController
{

    Logger logger= LogUtil.getLogger(this.getClass());

    /**
     * 增加后台管理人员
     * @param consoleUserEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/add")
    public BasicJson add(ConsoleUserEntity consoleUserEntity,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        basicJson.setStatus(true);
        return basicJson;
    }





}
