package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostDetailCollection extends AbstractObjectCollection 
{
    public DynCostDetailCollection()
    {
        super(DynCostDetailInfo.class);
    }
    public boolean add(DynCostDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostDetailInfo item)
    {
        return removeObject(item);
    }
    public DynCostDetailInfo get(int index)
    {
        return(DynCostDetailInfo)getObject(index);
    }
    public DynCostDetailInfo get(Object key)
    {
        return(DynCostDetailInfo)getObject(key);
    }
    public void set(int index, DynCostDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostDetailInfo item)
    {
        return super.indexOf(item);
    }
}