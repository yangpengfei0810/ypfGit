package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class OtherPaymentInfo extends AbstractOtherPaymentInfo implements Serializable 
{
    public OtherPaymentInfo()
    {
        super();
    }
    protected OtherPaymentInfo(String pkField)
    {
        super(pkField);
    }
}