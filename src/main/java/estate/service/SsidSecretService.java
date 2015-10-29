package estate.service;

import estate.entity.database.SsidSecretEntity;
import estate.entity.json.Select2;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 *
 */
public interface SsidSecretService
{
    /**
     * 根据symbol返回整个SsidSecretEntity对象
     * @param symbol
     * @return
     */
    SsidSecretEntity getSelfBySymbol(String symbol);

    /**
     *
     */
    TableData getList(TableFilter tableFilter);

    /**
     * 返回未使用的密钥
     * @return
     */
    ArrayList<Select2> getSelect2();
}
