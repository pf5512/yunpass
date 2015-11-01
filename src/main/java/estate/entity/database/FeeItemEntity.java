package estate.entity.database;

import java.math.BigDecimal;

/**
 * Created by kangbiao on 15-11-1.
 */
public class FeeItemEntity
{
    private int fiId;
    private String name;
    private String decription;
    private Byte feeType;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal overdueUnitPrice;
    private String overdueUnit;
    private Long payStartTime;
    private Long payEndTime;
    private Long effectiveStartTime;
    private Long effectiveEndTime;
    private Byte isPeriodic;
    private Integer villageId;
    private String extendInfo;
    private Long addTime;
    private Byte isEffective;

    public int getFiId()
    {
        return fiId;
    }

    public void setFiId(int fiId)
    {
        this.fiId = fiId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDecription()
    {
        return decription;
    }

    public void setDecription(String decription)
    {
        this.decription = decription;
    }

    public Byte getFeeType()
    {
        return feeType;
    }

    public void setFeeType(Byte feeType)
    {
        this.feeType = feeType;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getOverdueUnitPrice()
    {
        return overdueUnitPrice;
    }

    public void setOverdueUnitPrice(BigDecimal overdueUnitPrice)
    {
        this.overdueUnitPrice = overdueUnitPrice;
    }

    public String getOverdueUnit()
    {
        return overdueUnit;
    }

    public void setOverdueUnit(String overdueUnit)
    {
        this.overdueUnit = overdueUnit;
    }

    public Long getPayStartTime()
    {
        return payStartTime;
    }

    public void setPayStartTime(Long payStartTime)
    {
        this.payStartTime = payStartTime;
    }

    public Long getPayEndTime()
    {
        return payEndTime;
    }

    public void setPayEndTime(Long payEndTime)
    {
        this.payEndTime = payEndTime;
    }

    public Long getEffectiveStartTime()
    {
        return effectiveStartTime;
    }

    public void setEffectiveStartTime(Long effectiveStartTime)
    {
        this.effectiveStartTime = effectiveStartTime;
    }

    public Long getEffectiveEndTime()
    {
        return effectiveEndTime;
    }

    public void setEffectiveEndTime(Long effectiveEndTime)
    {
        this.effectiveEndTime = effectiveEndTime;
    }

    public Byte getIsPeriodic()
    {
        return isPeriodic;
    }

    public void setIsPeriodic(Byte isPeriodic)
    {
        this.isPeriodic = isPeriodic;
    }

    public Integer getVillageId()
    {
        return villageId;
    }

    public void setVillageId(Integer villageId)
    {
        this.villageId = villageId;
    }

    public String getExtendInfo()
    {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo)
    {
        this.extendInfo = extendInfo;
    }

    public Long getAddTime()
    {
        return addTime;
    }

    public void setAddTime(Long addTime)
    {
        this.addTime = addTime;
    }

    public Byte getIsEffective()
    {
        return isEffective;
    }

    public void setIsEffective(Byte isEffective)
    {
        this.isEffective = isEffective;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeeItemEntity that = (FeeItemEntity) o;

        if (fiId != that.fiId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (decription != null ? !decription.equals(that.decription) : that.decription != null) return false;
        if (feeType != null ? !feeType.equals(that.feeType) : that.feeType != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (unitPrice != null ? !unitPrice.equals(that.unitPrice) : that.unitPrice != null) return false;
        if (overdueUnitPrice != null ? !overdueUnitPrice.equals(that.overdueUnitPrice) : that.overdueUnitPrice != null)
            return false;
        if (overdueUnit != null ? !overdueUnit.equals(that.overdueUnit) : that.overdueUnit != null) return false;
        if (payStartTime != null ? !payStartTime.equals(that.payStartTime) : that.payStartTime != null) return false;
        if (payEndTime != null ? !payEndTime.equals(that.payEndTime) : that.payEndTime != null) return false;
        if (effectiveStartTime != null ? !effectiveStartTime.equals(that.effectiveStartTime) : that.effectiveStartTime != null)
            return false;
        if (effectiveEndTime != null ? !effectiveEndTime.equals(that.effectiveEndTime) : that.effectiveEndTime != null)
            return false;
        if (isPeriodic != null ? !isPeriodic.equals(that.isPeriodic) : that.isPeriodic != null) return false;
        if (villageId != null ? !villageId.equals(that.villageId) : that.villageId != null) return false;
        if (extendInfo != null ? !extendInfo.equals(that.extendInfo) : that.extendInfo != null) return false;
        if (addTime != null ? !addTime.equals(that.addTime) : that.addTime != null) return false;
        if (isEffective != null ? !isEffective.equals(that.isEffective) : that.isEffective != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = fiId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (decription != null ? decription.hashCode() : 0);
        result = 31 * result + (feeType != null ? feeType.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (overdueUnitPrice != null ? overdueUnitPrice.hashCode() : 0);
        result = 31 * result + (overdueUnit != null ? overdueUnit.hashCode() : 0);
        result = 31 * result + (payStartTime != null ? payStartTime.hashCode() : 0);
        result = 31 * result + (payEndTime != null ? payEndTime.hashCode() : 0);
        result = 31 * result + (effectiveStartTime != null ? effectiveStartTime.hashCode() : 0);
        result = 31 * result + (effectiveEndTime != null ? effectiveEndTime.hashCode() : 0);
        result = 31 * result + (isPeriodic != null ? isPeriodic.hashCode() : 0);
        result = 31 * result + (villageId != null ? villageId.hashCode() : 0);
        result = 31 * result + (extendInfo != null ? extendInfo.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (isEffective != null ? isEffective.hashCode() : 0);
        return result;
    }
}
