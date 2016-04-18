package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConOccSplitListEntryCollection extends AbstractObjectCollection 
{
    public ConOccSplitListEntryCollection()
    {
        super(ConOccSplitListEntryInfo.class);
    }
    public boolean add(ConOccSplitListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConOccSplitListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConOccSplitListEntryInfo item)
    {
        return removeObject(item);
    }
    public ConOccSplitListEntryInfo get(int index)
    {
        return(ConOccSplitListEntryInfo)getObject(index);
    }
    public ConOccSplitListEntryInfo get(Object key)
    {
        return(ConOccSplitListEntryInfo)getObject(key);
    }
    public void set(int index, ConOccSplitListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConOccSplitListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConOccSplitListEntryInfo item)
    {
        return super.indexOf(item);
    }
}