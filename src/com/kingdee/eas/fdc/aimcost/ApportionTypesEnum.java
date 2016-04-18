/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ApportionTypesEnum extends StringEnum
{
    public static final String BYCUSTOM_VALUE = "1";
    public static final String BYBUILDAREA_VALUE = "2";
    public static final String BYSELLAREA_VALUE = "3";
    public static final String BYCONTAINAREA_VALUE = "4";

    public static final ApportionTypesEnum BYCUSTOM = new ApportionTypesEnum("BYCUSTOM", BYCUSTOM_VALUE);
    public static final ApportionTypesEnum BYBUILDAREA = new ApportionTypesEnum("BYBUILDAREA", BYBUILDAREA_VALUE);
    public static final ApportionTypesEnum BYSELLAREA = new ApportionTypesEnum("BYSELLAREA", BYSELLAREA_VALUE);
    public static final ApportionTypesEnum BYCONTAINAREA = new ApportionTypesEnum("BYCONTAINAREA", BYCONTAINAREA_VALUE);

    /**
     * construct function
     * @param String apportionTypesEnum
     */
    private ApportionTypesEnum(String name, String apportionTypesEnum)
    {
        super(name, apportionTypesEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ApportionTypesEnum getEnum(String apportionTypesEnum)
    {
        return (ApportionTypesEnum)getEnum(ApportionTypesEnum.class, apportionTypesEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ApportionTypesEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ApportionTypesEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ApportionTypesEnum.class);
    }
}