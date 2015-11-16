package estate.entity.app;

import estate.entity.database.PropertyEntity;
import estate.entity.database.PropertyOwnerInfoEntity;

/**
 * Created by kangbiao on 15-10-21.
 *
 */
public class MyProperty
{
    private Integer id;
    private Byte status;
    private Byte userRole;
    private PropertyEntity propertyEntity;

    public MyProperty(PropertyOwnerInfoEntity propertyOwnerInfoEntity)
    {
        this.id=propertyOwnerInfoEntity.getId();
        this.status=propertyOwnerInfoEntity.getStatus();
        this.userRole=propertyOwnerInfoEntity.getUserRole();
        this.propertyEntity=propertyOwnerInfoEntity.getPropertyEntity();
    }

    public MyProperty(){}

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public PropertyEntity getPropertyEntity()
    {
        return propertyEntity;
    }

    public void setPropertyEntity(PropertyEntity propertyEntity)
    {
        this.propertyEntity = propertyEntity;
    }

    public Byte getStatus()
    {
        return status;
    }

    public void setStatus(Byte status)
    {
        this.status = status;
    }

    public Byte getUserRole()
    {
        return userRole;
    }

    public void setUserRole(Byte userRole)
    {
        this.userRole = userRole;
    }
}
