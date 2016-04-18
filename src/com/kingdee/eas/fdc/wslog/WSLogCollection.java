package com.kingdee.eas.fdc.wslog;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WSLogCollection extends AbstractObjectCollection 
{
    public WSLogCollection()
    {
        super(WSLogInfo.class);
    }
    public boolean add(WSLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WSLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WSLogInfo item)
    {
        return removeObject(item);
    }
    public WSLogInfo get(int index)
    {
        return(WSLogInfo)getObject(index);
    }
    public WSLogInfo get(Object key)
    {
        return(WSLogInfo)getObject(key);
    }
    public void set(int index, WSLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WSLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WSLogInfo item)
    {
        return super.indexOf(item);
    }
}