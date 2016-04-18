package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SaleAreaDataCollection extends AbstractObjectCollection 
{
    public SaleAreaDataCollection()
    {
        super(SaleAreaDataInfo.class);
    }
    public boolean add(SaleAreaDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SaleAreaDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SaleAreaDataInfo item)
    {
        return removeObject(item);
    }
    public SaleAreaDataInfo get(int index)
    {
        return(SaleAreaDataInfo)getObject(index);
    }
    public SaleAreaDataInfo get(Object key)
    {
        return(SaleAreaDataInfo)getObject(key);
    }
    public void set(int index, SaleAreaDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SaleAreaDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SaleAreaDataInfo item)
    {
        return super.indexOf(item);
    }
}