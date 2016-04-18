/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class FDCBillStateEnum extends StringEnum
{
    public static final String SAVED_VALUE = "1SAVED";
    public static final String SUBMITTED_VALUE = "2SUBMITTED";
    public static final String AUDITTING_VALUE = "3AUDITTING";
    public static final String AUDITTED_VALUE = "4AUDITTED";
    public static final String CANCEL_VALUE = "5CANCEL";
    public static final String ANNOUNCE_VALUE = "7ANNOUNCE";
    public static final String VISA_VALUE = "8VISA";
    public static final String INVALID_VALUE = "9INVALID";
    public static final String PUBLISH_VALUE = "10PUBLISH";
    public static final String BACK_VALUE = "11BACK";
    public static final String REVISING_VALUE = "12REVISING";
    public static final String REVISE_VALUE = "12REVISE";
    public static final String REJECT_VALUE = "10REJECT";

    public static final FDCBillStateEnum SAVED = new FDCBillStateEnum("SAVED", SAVED_VALUE);
    public static final FDCBillStateEnum SUBMITTED = new FDCBillStateEnum("SUBMITTED", SUBMITTED_VALUE);
    public static final FDCBillStateEnum AUDITTING = new FDCBillStateEnum("AUDITTING", AUDITTING_VALUE);
    public static final FDCBillStateEnum AUDITTED = new FDCBillStateEnum("AUDITTED", AUDITTED_VALUE);
    public static final FDCBillStateEnum CANCEL = new FDCBillStateEnum("CANCEL", CANCEL_VALUE);
    public static final FDCBillStateEnum ANNOUNCE = new FDCBillStateEnum("ANNOUNCE", ANNOUNCE_VALUE);
    public static final FDCBillStateEnum VISA = new FDCBillStateEnum("VISA", VISA_VALUE);
    public static final FDCBillStateEnum INVALID = new FDCBillStateEnum("INVALID", INVALID_VALUE);
    public static final FDCBillStateEnum PUBLISH = new FDCBillStateEnum("PUBLISH", PUBLISH_VALUE);
    public static final FDCBillStateEnum BACK = new FDCBillStateEnum("BACK", BACK_VALUE);
    public static final FDCBillStateEnum REVISING = new FDCBillStateEnum("REVISING", REVISING_VALUE);
    public static final FDCBillStateEnum REVISE = new FDCBillStateEnum("REVISE", REVISE_VALUE);
    public static final FDCBillStateEnum REJECT = new FDCBillStateEnum("REJECT", REJECT_VALUE);

    /**
     * construct function
     * @param String fDCBillStateEnum
     */
    private FDCBillStateEnum(String name, String fDCBillStateEnum)
    {
        super(name, fDCBillStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FDCBillStateEnum getEnum(String fDCBillStateEnum)
    {
        return (FDCBillStateEnum)getEnum(FDCBillStateEnum.class, fDCBillStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FDCBillStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FDCBillStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FDCBillStateEnum.class);
    }
}