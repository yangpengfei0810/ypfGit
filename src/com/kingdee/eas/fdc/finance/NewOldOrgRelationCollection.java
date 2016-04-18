package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewOldOrgRelationCollection extends AbstractObjectCollection 
{
    public NewOldOrgRelationCollection()
    {
        super(NewOldOrgRelationInfo.class);
    }
    public boolean add(NewOldOrgRelationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewOldOrgRelationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewOldOrgRelationInfo item)
    {
        return removeObject(item);
    }
    public NewOldOrgRelationInfo get(int index)
    {
        return(NewOldOrgRelationInfo)getObject(index);
    }
    public NewOldOrgRelationInfo get(Object key)
    {
        return(NewOldOrgRelationInfo)getObject(key);
    }
    public void set(int index, NewOldOrgRelationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewOldOrgRelationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewOldOrgRelationInfo item)
    {
        return super.indexOf(item);
    }
}