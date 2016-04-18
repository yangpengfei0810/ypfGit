package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewOldAccountRelationCollection extends AbstractObjectCollection 
{
    public NewOldAccountRelationCollection()
    {
        super(NewOldAccountRelationInfo.class);
    }
    public boolean add(NewOldAccountRelationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewOldAccountRelationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewOldAccountRelationInfo item)
    {
        return removeObject(item);
    }
    public NewOldAccountRelationInfo get(int index)
    {
        return(NewOldAccountRelationInfo)getObject(index);
    }
    public NewOldAccountRelationInfo get(Object key)
    {
        return(NewOldAccountRelationInfo)getObject(key);
    }
    public void set(int index, NewOldAccountRelationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewOldAccountRelationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewOldAccountRelationInfo item)
    {
        return super.indexOf(item);
    }
}