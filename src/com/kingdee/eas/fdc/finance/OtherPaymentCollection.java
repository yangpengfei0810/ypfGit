package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherPaymentCollection extends AbstractObjectCollection 
{
    public OtherPaymentCollection()
    {
        super(OtherPaymentInfo.class);
    }
    public boolean add(OtherPaymentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherPaymentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherPaymentInfo item)
    {
        return removeObject(item);
    }
    public OtherPaymentInfo get(int index)
    {
        return(OtherPaymentInfo)getObject(index);
    }
    public OtherPaymentInfo get(Object key)
    {
        return(OtherPaymentInfo)getObject(key);
    }
    public void set(int index, OtherPaymentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherPaymentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherPaymentInfo item)
    {
        return super.indexOf(item);
    }
}