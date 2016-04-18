package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ModelCollection extends AbstractObjectCollection 
{
    public ModelCollection()
    {
        super(ModelInfo.class);
    }
    public boolean add(ModelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ModelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ModelInfo item)
    {
        return removeObject(item);
    }
    public ModelInfo get(int index)
    {
        return(ModelInfo)getObject(index);
    }
    public ModelInfo get(Object key)
    {
        return(ModelInfo)getObject(key);
    }
    public void set(int index, ModelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ModelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ModelInfo item)
    {
        return super.indexOf(item);
    }
}