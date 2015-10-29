package estate.entity.database;

/**
 * Created by kangbiao on 15-10-14.
 *
 */
public class BrakeEntity
{
    private int id;
    private Integer parkLotNum;
    private String code;
    private String name;
    private String description;
    private Integer villageId;
    private VillageEntity villageEntity;
    private String symbol;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getVillageId()
    {
        return villageId;
    }

    public void setVillageId(Integer villageId)
    {
        this.villageId = villageId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Integer getParkLotNum()
    {
        return parkLotNum;
    }

    public void setParkLotNum(Integer parkLotNum)
    {
        this.parkLotNum = parkLotNum;
    }

    public VillageEntity getVillageEntity()
    {
        return villageEntity;
    }

    public void setVillageEntity(VillageEntity villageEntity)
    {
        this.villageEntity = villageEntity;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
}
