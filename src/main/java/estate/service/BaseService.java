package estate.service;

import java.io.Serializable;

/**
 * Created by kangbiao on 15-9-16.
 * 基础的
 */
public interface BaseService
{
    void save(Object object);

    Object get(Integer id,Object object);

    Object get(Serializable id,Class cls);

    Object get(Integer id,Class object);


    Object getAll(Class cls);

    void delete(Object object);

    Integer count(Class cls);

}
