/**
 * output package name
 */
package com.kingdee.eas.cp.bc;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class EducateEnum extends StringEnum
{
    public static final String EDUCATE_VALUE = "0";
    public static final String UNEDUCATE_VALUE = "1";

    public static final EducateEnum EDUCATE = new EducateEnum("EDUCATE", EDUCATE_VALUE);
    public static final EducateEnum UNEDUCATE = new EducateEnum("UNEDUCATE", UNEDUCATE_VALUE);

    /**
     * construct function
     * @param String educateEnum
     */
    private EducateEnum(String name, String educateEnum)
    {
        super(name, educateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static EducateEnum getEnum(String educateEnum)
    {
        return (EducateEnum)getEnum(EducateEnum.class, educateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(EducateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(EducateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(EducateEnum.class);
    }
}