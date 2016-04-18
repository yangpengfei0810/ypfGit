package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureCollectDataCollection extends AbstractObjectCollection 
{
    public MeasureCollectDataCollection()
    {
        super(MeasureCollectDataInfo.class);
    }
    public boolean add(MeasureCollectDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureCollectDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureCollectDataInfo item)
    {
        return removeObject(item);
    }
    public MeasureCollectDataInfo get(int index)
    {
        return(MeasureCollectDataInfo)getObject(index);
    }
    public MeasureCollectDataInfo get(Object key)
    {
        return(MeasureCollectDataInfo)getObject(key);
    }
    public void set(int index, MeasureCollectDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureCollectDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureCollectDataInfo item)
    {
        return super.indexOf(item);
    }
}