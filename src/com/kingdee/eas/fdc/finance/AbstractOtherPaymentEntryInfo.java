package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherPaymentEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOtherPaymentEntryInfo()
    {
        this("id");
    }
    protected AbstractOtherPaymentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.finance.OtherPaymentInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.OtherPaymentInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.OtherPaymentInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6EB5500D");
    }
}