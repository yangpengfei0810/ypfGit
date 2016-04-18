package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PublicToSplitEntryCollection extends AbstractObjectCollection 
{
    public PublicToSplitEntryCollection()
    {
        super(PublicToSplitEntryInfo.class);
    }
    public boolean add(PublicToSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PublicToSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PublicToSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public PublicToSplitEntryInfo get(int index)
    {
        return(PublicToSplitEntryInfo)getObject(index);
    }
    public PublicToSplitEntryInfo get(Object key)
    {
        return(PublicToSplitEntryInfo)getObject(key);
    }
    public void set(int index, PublicToSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PublicToSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PublicToSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}