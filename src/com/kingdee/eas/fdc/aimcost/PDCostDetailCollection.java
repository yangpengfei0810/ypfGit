package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PDCostDetailCollection extends AbstractObjectCollection 
{
    public PDCostDetailCollection()
    {
        super(PDCostDetailInfo.class);
    }
    public boolean add(PDCostDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PDCostDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PDCostDetailInfo item)
    {
        return removeObject(item);
    }
    public PDCostDetailInfo get(int index)
    {
        return(PDCostDetailInfo)getObject(index);
    }
    public PDCostDetailInfo get(Object key)
    {
        return(PDCostDetailInfo)getObject(key);
    }
    public void set(int index, PDCostDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PDCostDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PDCostDetailInfo item)
    {
        return super.indexOf(item);
    }
}