package estate.entity.database;

/**
 * Created by kangbiao on 15-11-1.
 *
 */
public class UserBillEntity
{
    private String propertyBill;
    private String parkLotBill;
    private String serviceBill;

    public String getPropertyBill()
    {
        return propertyBill;
    }

    public void setPropertyBill(String propertyBill)
    {
        this.propertyBill = propertyBill;
    }

    public String getParkLotBill()
    {
        return parkLotBill;
    }

    public void setParkLotBill(String parkLotBill)
    {
        this.parkLotBill = parkLotBill;
    }

    public String getServiceBill()
    {
        return serviceBill;
    }

    public void setServiceBill(String serviceBill)
    {
        this.serviceBill = serviceBill;
    }
}
