package estate.app;

import estate.common.config.SecretType;
import estate.common.config.SsidControlType;
import estate.common.util.LogUtil;
import estate.entity.database.OpenDoorRecordEntity;
import estate.entity.database.SsidSecretEntity;
import estate.entity.json.BasicJson;
import estate.service.AuthorityService;
import estate.service.BaseService;
import estate.service.SsidSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 * 门禁权限,门禁记录上传
 */
@RestController
@RequestMapping("api/auth")
public class AuthorityHandler
{
    @Autowired
    AuthorityService authorityService;
    @Autowired
    SsidSecretService ssidSecretService;
    @Autowired
    private BaseService baseService;

    /**
     * 获取门禁密钥
     * @param symbol
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSecret/{symbol}",method = RequestMethod.GET)
    public BasicJson getSsidSecret(@PathVariable String symbol,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        SsidSecretEntity ssidSecretEntity;

        String phone= (String) request.getSession().getAttribute("phone");
        if (symbol!=null&&!symbol.equals(""))
        {
            try
            {
                ssidSecretEntity=ssidSecretService.getSelfBySymbol(symbol);
                if (ssidSecretEntity==null||ssidSecretEntity.getControlId()==null)
                {
                    basicJson.getErrorMsg().setDescription("该密钥未配置!");
                    return basicJson;
                }
            }
            catch (Exception e)
            {
                LogUtil.E((e.getMessage()));
                basicJson.getErrorMsg().setDescription("该ssid不存在!");
                return basicJson;
            }
        }
        else
        {
            basicJson.getErrorMsg().setCode("100000");
            basicJson.getErrorMsg().setDescription("SSID不能为空");
            return basicJson;
        }

        //取出当前用户能进入的所有楼栋的ID
        ArrayList<Integer> ids;
        try
        {
            ids = authorityService.getAuthorityIDsByPhoneType(phone, SsidControlType.VILLAGE);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setDescription("获取密钥失败");
            return basicJson;
        }

        if (ids==null)
        {
            basicJson.getErrorMsg().setDescription("您没有该门禁权限");
            return basicJson;
        }

        if(ids.contains(ssidSecretEntity.getControlId()))
        {
            basicJson.setJsonString(ssidSecretEntity);
        }
        else
        {
            basicJson.getErrorMsg().setCode("12050510");
            basicJson.getErrorMsg().setDescription("您没有该门禁权限");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }


    /**
     * 上传门禁记录
     * @param openDoorRecordEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadDoorLog")
    public BasicJson uploadDoorLog(OpenDoorRecordEntity openDoorRecordEntity,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();
        HttpSession httpSession=request.getSession();

        openDoorRecordEntity.setOpenTime(System.currentTimeMillis());
        openDoorRecordEntity.setPhone((String) httpSession.getAttribute("phone"));
        try
        {
            baseService.save(openDoorRecordEntity);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setDescription("保存出错-"+e.getMessage());
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }
}
