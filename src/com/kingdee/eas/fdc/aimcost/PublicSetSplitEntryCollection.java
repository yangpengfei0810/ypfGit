package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PublicSetSplitEntryCollection extends AbstractObjectCollection 
{
    public PublicSetSplitEntryCollection()
    {
        super(PublicSetSplitEntryInfo.class);
    }
    public boolean add(PublicSetSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PublicSetSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PublicSetSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public PublicSetSplitEntryInfo get(int index)
    {
        return(PublicSetSplitEntryInfo)getObject(index);
    }
    public PublicSetSplitEntryInfo get(Object key)
    {
        return(PublicSetSplitEntryInfo)getObject(key);
    }
    public void set(int index, PublicSetSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PublicSetSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PublicSetSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}