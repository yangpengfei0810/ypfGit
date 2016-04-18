package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PaymentVoucherTempInfo extends AbstractPaymentVoucherTempInfo implements Serializable 
{
    public PaymentVoucherTempInfo()
    {
        super();
    }
    protected PaymentVoucherTempInfo(String pkField)
    {
        super(pkField);
    }
}