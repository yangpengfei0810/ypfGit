package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ModelTreeCollection extends AbstractObjectCollection 
{
    public ModelTreeCollection()
    {
        super(ModelTreeInfo.class);
    }
    public boolean add(ModelTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ModelTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ModelTreeInfo item)
    {
        return removeObject(item);
    }
    public ModelTreeInfo get(int index)
    {
        return(ModelTreeInfo)getObject(index);
    }
    public ModelTreeInfo get(Object key)
    {
        return(ModelTreeInfo)getObject(key);
    }
    public void set(int index, ModelTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ModelTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ModelTreeInfo item)
    {
        return super.indexOf(item);
    }
}