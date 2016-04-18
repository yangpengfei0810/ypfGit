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
     * Object:付款凭证中间表's 付款单IDproperty 
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
     * Object:付款凭证中间表's 凭证IDproperty 
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