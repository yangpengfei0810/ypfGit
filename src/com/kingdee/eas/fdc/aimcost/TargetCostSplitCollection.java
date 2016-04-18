package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TargetCostSplitCollection extends AbstractObjectCollection 
{
    public TargetCostSplitCollection()
    {
        super(TargetCostSplitInfo.class);
    }
    public boolean add(TargetCostSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TargetCostSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TargetCostSplitInfo item)
    {
        return removeObject(item);
    }
    public TargetCostSplitInfo get(int index)
    {
        return(TargetCostSplitInfo)getObject(index);
    }
    public TargetCostSplitInfo get(Object key)
    {
        return(TargetCostSplitInfo)getObject(key);
    }
    public void set(int index, TargetCostSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TargetCostSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TargetCostSplitInfo item)
    {
        return super.indexOf(item);
    }
}