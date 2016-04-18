package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherPaymentEntryCollection extends AbstractObjectCollection 
{
    public OtherPaymentEntryCollection()
    {
        super(OtherPaymentEntryInfo.class);
    }
    public boolean add(OtherPaymentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherPaymentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherPaymentEntryInfo item)
    {
        return removeObject(item);
    }
    public OtherPaymentEntryInfo get(int index)
    {
        return(OtherPaymentEntryInfo)getObject(index);
    }
    public OtherPaymentEntryInfo get(Object key)
    {
        return(OtherPaymentEntryInfo)getObject(key);
    }
    public void set(int index, OtherPaymentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherPaymentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherPaymentEntryInfo item)
    {
        return super.indexOf(item);
    }
}