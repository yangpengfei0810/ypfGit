package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConOccurSplitListEntryCollection extends AbstractObjectCollection 
{
    public ConOccurSplitListEntryCollection()
    {
        super(ConOccurSplitListEntryInfo.class);
    }
    public boolean add(ConOccurSplitListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConOccurSplitListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConOccurSplitListEntryInfo item)
    {
        return removeObject(item);
    }
    public ConOccurSplitListEntryInfo get(int index)
    {
        return(ConOccurSplitListEntryInfo)getObject(index);
    }
    public ConOccurSplitListEntryInfo get(Object key)
    {
        return(ConOccurSplitListEntryInfo)getObject(key);
    }
    public void set(int index, ConOccurSplitListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConOccurSplitListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConOccurSplitListEntryInfo item)
    {
        return super.indexOf(item);
    }
}