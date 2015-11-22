package estate.service.impl;

import estate.dao.ApkLogDao;
import estate.entity.database.ApkLogEntity;
import estate.service.ApkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
@Service("apkLotService")
public class ApkLogServiceImpl implements ApkLogService
{
    @Autowired
    private ApkLogDao apkLogDao;

    @Override
    public ApkLogEntity getByVersionCode(String code)
    {
        return apkLogDao.getByCode(code);
    }

    @Override
    public ApkLogEntity getNewestApk()
    {
        return apkLogDao.getNewestApk();
    }
}
