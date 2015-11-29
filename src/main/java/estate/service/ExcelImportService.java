package estate.service;

import estate.entity.json.ExcelImportReport;

import java.util.List;
import java.util.Map;

/**
 * Created by kangbiao on 15-10-24.
 * 导入excel
 */
public interface ExcelImportService
{
    /**
     * 导入物业信息
     * @param result
     * @return
     */
    ExcelImportReport importProperty(List<Map<String,String>> result);


    /**
     * 导入业主和物业的绑定信息
     * @param result
     * @return
     */
    ExcelImportReport importBind(List<Map<String,String>> result);

    /**
     * 导入密钥
     * @param result
     * @return
     */
    ExcelImportReport importSecret(List<Map<String,String>> result);

    /**
     * 导入园区信息
     * @param result
     * @return
     */
    ExcelImportReport importVillage(List<Map<String,String>> result);

    /**
     * 导入楼栋信息
     * @param result
     * @return
     */
    ExcelImportReport importBuilding(List<Map<String,String>> result);

}
