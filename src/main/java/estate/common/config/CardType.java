package estate.common.config;

import estate.exception.TypeErrorException;

/**
 * Created by kangbiao on 15-10-9.
 * 证件类型,身份郑,军官证,护照
 */
public class CardType
{
    public final static Byte IDCARD=0;
    public final static Byte SOLDIERCARD=1;
    public final static Byte PASSPORT=2;

    public static boolean checkType(Byte type) throws TypeErrorException
    {
        if (!(type.equals(IDCARD)||type.equals(PASSPORT)||type.equals(SOLDIERCARD)))
            throw new TypeErrorException("证件类型参数错误");
        return true;
    }
}
