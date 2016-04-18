package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostDetailDataCollection extends AbstractObjectCollection 
{
    public DynCostDetailDataCollection()
    {
        super(DynCostDetailDataInfo.class);
    }
    public boolean add(DynCostDetailDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostDetailDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostDetailDataInfo item)
    {
        return removeObject(item);
    }
    public DynCostDetailDataInfo get(int index)
    {
        return(DynCostDetailDataInfo)getObject(index);
    }
    public DynCostDetailDataInfo get(Object key)
    {
        return(DynCostDetailDataInfo)getObject(key);
    }
    public void set(int index, DynCostDetailDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostDetailDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostDetailDataInfo item)
    {
        return super.indexOf(item);
    }
}