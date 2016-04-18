package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TravelAccountBillCollection extends AbstractObjectCollection 
{
    public TravelAccountBillCollection()
    {
        super(TravelAccountBillInfo.class);
    }
    public boolean add(TravelAccountBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TravelAccountBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TravelAccountBillInfo item)
    {
        return removeObject(item);
    }
    public TravelAccountBillInfo get(int index)
    {
        return(TravelAccountBillInfo)getObject(index);
    }
    public TravelAccountBillInfo get(Object key)
    {
        return(TravelAccountBillInfo)getObject(key);
    }
    public void set(int index, TravelAccountBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TravelAccountBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TravelAccountBillInfo item)
    {
        return super.indexOf(item);
    }
}