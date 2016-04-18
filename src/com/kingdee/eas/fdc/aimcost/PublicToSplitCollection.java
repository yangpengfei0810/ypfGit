package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PublicToSplitCollection extends AbstractObjectCollection 
{
    public PublicToSplitCollection()
    {
        super(PublicToSplitInfo.class);
    }
    public boolean add(PublicToSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PublicToSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PublicToSplitInfo item)
    {
        return removeObject(item);
    }
    public PublicToSplitInfo get(int index)
    {
        return(PublicToSplitInfo)getObject(index);
    }
    public PublicToSplitInfo get(Object key)
    {
        return(PublicToSplitInfo)getObject(key);
    }
    public void set(int index, PublicToSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PublicToSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PublicToSplitInfo item)
    {
        return super.indexOf(item);
    }
}