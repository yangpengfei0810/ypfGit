package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TravelAccountBillEntryCollection extends AbstractObjectCollection 
{
    public TravelAccountBillEntryCollection()
    {
        super(TravelAccountBillEntryInfo.class);
    }
    public boolean add(TravelAccountBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TravelAccountBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TravelAccountBillEntryInfo item)
    {
        return removeObject(item);
    }
    public TravelAccountBillEntryInfo get(int index)
    {
        return(TravelAccountBillEntryInfo)getObject(index);
    }
    public TravelAccountBillEntryInfo get(Object key)
    {
        return(TravelAccountBillEntryInfo)getObject(key);
    }
    public void set(int index, TravelAccountBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TravelAccountBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TravelAccountBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}