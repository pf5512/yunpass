package estate.exception;

/**
 * Created by kangbiao on 15-10-8.
 * 尸体类型错误异常
 */
public class EntityTypeErrorException extends Exception
{
    public EntityTypeErrorException(){}

    public EntityTypeErrorException(String msg)
    {
        super(msg);
    }
}
