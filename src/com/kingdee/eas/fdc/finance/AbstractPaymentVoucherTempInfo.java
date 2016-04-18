package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentVoucherTempInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPaymentVoucherTempInfo()
    {
        this("id");
    }
    protected AbstractPaymentVoucherTempInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ƾ֤�м��'s ���IDproperty 
     */
    public String getPaymentID()
    {
        return getString("paymentID");
    }
    public void setPaymentID(String item)
    {
        setString("paymentID", item);
    }
    /**
     * Object:����ƾ֤�м��'s ƾ֤IDproperty 
     */
    public String getVoucherID()
    {
        return getString("voucherID");
    }
    public void setVoucherID(String item)
    {
        setString("voucherID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D9F550B");
    }
}