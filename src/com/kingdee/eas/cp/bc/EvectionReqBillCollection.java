package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvectionReqBillCollection extends AbstractObjectCollection 
{
    public EvectionReqBillCollection()
    {
        super(EvectionReqBillInfo.class);
    }
    public boolean add(EvectionReqBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvectionReqBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvectionReqBillInfo item)
    {
        return removeObject(item);
    }
    public EvectionReqBillInfo get(int index)
    {
        return(EvectionReqBillInfo)getObject(index);
    }
    public EvectionReqBillInfo get(Object key)
    {
        return(EvectionReqBillInfo)getObject(key);
    }
    public void set(int index, EvectionReqBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvectionReqBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvectionReqBillInfo item)
    {
        return super.indexOf(item);
    }
}