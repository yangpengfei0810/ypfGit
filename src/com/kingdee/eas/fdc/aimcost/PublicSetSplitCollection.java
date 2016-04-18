package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PublicSetSplitCollection extends AbstractObjectCollection 
{
    public PublicSetSplitCollection()
    {
        super(PublicSetSplitInfo.class);
    }
    public boolean add(PublicSetSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PublicSetSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PublicSetSplitInfo item)
    {
        return removeObject(item);
    }
    public PublicSetSplitInfo get(int index)
    {
        return(PublicSetSplitInfo)getObject(index);
    }
    public PublicSetSplitInfo get(Object key)
    {
        return(PublicSetSplitInfo)getObject(key);
    }
    public void set(int index, PublicSetSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PublicSetSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PublicSetSplitInfo item)
    {
        return super.indexOf(item);
    }
}