package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConOccSplitListCollection extends AbstractObjectCollection 
{
    public ConOccSplitListCollection()
    {
        super(ConOccSplitListInfo.class);
    }
    public boolean add(ConOccSplitListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConOccSplitListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConOccSplitListInfo item)
    {
        return removeObject(item);
    }
    public ConOccSplitListInfo get(int index)
    {
        return(ConOccSplitListInfo)getObject(index);
    }
    public ConOccSplitListInfo get(Object key)
    {
        return(ConOccSplitListInfo)getObject(key);
    }
    public void set(int index, ConOccSplitListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConOccSplitListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConOccSplitListInfo item)
    {
        return super.indexOf(item);
    }
}