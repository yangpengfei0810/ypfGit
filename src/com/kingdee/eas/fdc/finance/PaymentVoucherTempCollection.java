package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentVoucherTempCollection extends AbstractObjectCollection 
{
    public PaymentVoucherTempCollection()
    {
        super(PaymentVoucherTempInfo.class);
    }
    public boolean add(PaymentVoucherTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentVoucherTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentVoucherTempInfo item)
    {
        return removeObject(item);
    }
    public PaymentVoucherTempInfo get(int index)
    {
        return(PaymentVoucherTempInfo)getObject(index);
    }
    public PaymentVoucherTempInfo get(Object key)
    {
        return(PaymentVoucherTempInfo)getObject(key);
    }
    public void set(int index, PaymentVoucherTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentVoucherTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentVoucherTempInfo item)
    {
        return super.indexOf(item);
    }
}