package estate.entity.json;

/**
 * Created by kangbiao on 15-9-7.
 * datatable查询数据容器,包含不同查询的过滤条件,有冗余
 */
public class TableFilter
{
    private Integer length;
    private Integer start;
    private String searchValue;
    private Integer villageId;
    private Integer controlId;
    private Long endTime;
    private Long startTime;
    private Byte status;
    private Byte type;

    public Integer getLength()
    {
        return length;
    }

    public void setLength(Integer length)
    {
        this.length = length;
    }


    public Integer getStart()
    {
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    public Integer getVillageId()
    {
        return villageId;
    }

    public void setVillageId(Integer villageId)
    {
        this.villageId = villageId;
    }

    public Long getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Long endTime)
    {
        this.endTime = endTime;
    }

    public Long getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Long startTime)
    {
        this.startTime = startTime;
    }

    public Byte getStatus()
    {
        return status;
    }

    public void setStatus(Byte status)
    {
        this.status = status;
    }

    public Byte getType()
    {
        return type;
    }

    public void setType(Byte type)
    {
        this.type = type;
    }

    public Integer getControlId()
    {
        return controlId;
    }

    public void setControlId(Integer controlId)
    {
        this.controlId = controlId;
    }
}
