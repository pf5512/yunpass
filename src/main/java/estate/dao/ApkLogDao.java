package estate.dao;

import estate.entity.database.ApkLogEntity;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
public interface ApkLogDao
{
    ApkLogEntity getByCode(String code);

    /**
     * 获取最新版本的apk
     * @return
     */
    ApkLogEntity getNewestApk();
}
