/**
 * output package name
 */
package com.kingdee.eas.fi.cas;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class BillStatusEnum extends IntEnum
{
    public static final int SAVE_VALUE = 10;
    public static final int SUBMIT_VALUE = 11;
    public static final int AUDITED_VALUE = 12;
    public static final int RECED_VALUE = 14;
    public static final int PAYED_VALUE = 15;
    public static final int AUDITING_VALUE = 6;
    public static final int APPROVED_VALUE = 8;

    public static final BillStatusEnum SAVE = new BillStatusEnum("SAVE", SAVE_VALUE);
    public static final BillStatusEnum SUBMIT = new BillStatusEnum("SUBMIT", SUBMIT_VALUE);
    public static final BillStatusEnum AUDITED = new BillStatusEnum("AUDITED", AUDITED_VALUE);
    public static final BillStatusEnum RECED = new BillStatusEnum("RECED", RECED_VALUE);
    public static final BillStatusEnum PAYED = new BillStatusEnum("PAYED", PAYED_VALUE);
    public static final BillStatusEnum AUDITING = new BillStatusEnum("AUDITING", AUDITING_VALUE);
    public static final BillStatusEnum APPROVED = new BillStatusEnum("APPROVED", APPROVED_VALUE);

    /**
     * construct function
     * @param integer billStatusEnum
     */
    private BillStatusEnum(String name, int billStatusEnum)
    {
        super(name, billStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BillStatusEnum getEnum(String billStatusEnum)
    {
        return (BillStatusEnum)getEnum(BillStatusEnum.class, billStatusEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static BillStatusEnum getEnum(int billStatusEnum)
    {
        return (BillStatusEnum)getEnum(BillStatusEnum.class, billStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BillStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BillStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BillStatusEnum.class);
    }
}