package estate.entity.database;

/**
 * Created by kangbiao on 15-9-13.
 *
 */
public class BuildingEntity
{
    private Integer id;
    private Integer villageId;
    private String description;
    private String buildingCode;
    private String buildingName;
    private VillageEntity villageEntity;
    private String symbol;

    public Integer getVillageId()
    {
        return villageId;
    }

    public void setVillageId(Integer villageId)
    {
        this.villageId = villageId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getBuildingCode()
    {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode)
    {
        this.buildingCode = buildingCode;
    }

    public String getBuildingName()
    {
        return buildingName;
    }

    public void setBuildingName(String buildingName)
    {
        this.buildingName = buildingName;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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
