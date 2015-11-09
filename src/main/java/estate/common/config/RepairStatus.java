package estate.common.config;

import estate.exception.TypeErrorException;

/**
 * Created by kangbiao on 15-10-10.
 * 报修状态类
 */
public class RepairStatus
{
    public final static Byte FORPROCESS=0;
    public final static Byte PROCESSING=1;
    public final static Byte FORCOMMENT=2;
    public final static Byte HADCOMMENT=3;

    public static boolean checkType(Byte type) throws TypeErrorException
    {
        if (!(type.equals(FORPROCESS)||type.equals(PROCESSING)||type.equals(HADCOMMENT)||type.equals(FORCOMMENT)))
            throw new TypeErrorException("报修状态参数错误");
        return true;
    }
}
