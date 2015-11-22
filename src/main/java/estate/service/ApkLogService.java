package estate.service;

import estate.entity.database.ApkLogEntity;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
public interface ApkLogService
{
    ApkLogEntity getByVersionCode(String code);

    /**
     * 获取最新版本的apk
     * @return
     */
    ApkLogEntity getNewestApk();
}
