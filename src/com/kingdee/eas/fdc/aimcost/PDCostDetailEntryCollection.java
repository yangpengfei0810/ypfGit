package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PDCostDetailEntryCollection extends AbstractObjectCollection 
{
    public PDCostDetailEntryCollection()
    {
        super(PDCostDetailEntryInfo.class);
    }
    public boolean add(PDCostDetailEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PDCostDetailEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PDCostDetailEntryInfo item)
    {
        return removeObject(item);
    }
    public PDCostDetailEntryInfo get(int index)
    {
        return(PDCostDetailEntryInfo)getObject(index);
    }
    public PDCostDetailEntryInfo get(Object key)
    {
        return(PDCostDetailEntryInfo)getObject(key);
    }
    public void set(int index, PDCostDetailEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PDCostDetailEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PDCostDetailEntryInfo item)
    {
        return super.indexOf(item);
    }
}