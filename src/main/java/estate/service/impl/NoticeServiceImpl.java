package estate.service.impl;

import estate.dao.NoticeDao;
import estate.entity.database.NoticeEntity;
import estate.entity.json.TableFilter;
import estate.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-4.
 *
 */
@Service("notice")
public class NoticeServiceImpl implements NoticeService
{
    /**
     * 根据filterEntity获取对应的公告
     */
    @Autowired
    private NoticeDao noticeDao;

    public ArrayList<NoticeEntity> getList(TableFilter filter)
    {
        ArrayList<NoticeEntity> entities=new ArrayList<NoticeEntity>();
        NoticeEntity noticeEntity=new NoticeEntity();
        noticeEntity.setNoticeId(2);
        noticeEntity.setTitle("Ds");
        noticeEntity.setExpiretime(7);
        noticeEntity.setTime("1905-5-6");
        noticeEntity.setDescription("这是描述");
        noticeEntity.setContent("<p>这是详情</p>");
        entities.add(noticeEntity);
        entities.add(noticeEntity);
        entities.add(noticeEntity);entities.add(noticeEntity);entities.add(noticeEntity);
        entities.add(noticeEntity);entities.add(noticeEntity);entities.add(noticeEntity);



        return entities;
    }

    /**
     * 添加公告信息
     *
     * @param noticeEntity 公告实体
     */
    public void add(NoticeEntity noticeEntity)
    {
        noticeDao.sava(noticeEntity);
    }

    /**
     * 根据公告ID删除公告
     *
     * @param noticeID 公告ID
     * @return 返回true或false
     */
    public boolean delete  (String noticeID)
    {
        return noticeDao.delete(noticeID);
    }

    /**
     * 根据公告ID获取对应的公告
     *
     * @param noticeID 公告ID
     * @return 返回公告
     */
    public NoticeEntity getOne(String noticeID)
    {
        return noticeDao.getNoticeByID(noticeID);
    }

    /**
     * 获取指定数量的最新的公告
     *
     * @param num 公告数
     * @return 返回公告列表
     */
    public ArrayList<NoticeEntity> getNewestNotice(Integer num)
    {
        return noticeDao.getSome(num);
    }
}
