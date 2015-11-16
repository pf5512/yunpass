package estate.entity.app;

import estate.entity.database.NoticeEntity;

/**
 * Created by kangbiao on 15-10-23.
 *
 */
public class Notice
{
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Long createTime;
    private String picturePathList;

    public Notice(){}

    public Notice(String content, Long createTime, String description, Integer id,  String title)
    {
        this.content = content;
        this.createTime = createTime;
        this.description = description;
        this.id = id;
        this.title = title;
    }

    public Notice(NoticeEntity noticeEntity)
    {
        this.id=noticeEntity.getId();
        this.createTime=noticeEntity.getTime();

    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getPicturePathList()
    {
        return picturePathList;
    }

    public void setPicturePathList(String picturePathList)
    {
        this.picturePathList = picturePathList;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
