package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvectionReqBillEntryCollection extends AbstractObjectCollection 
{
    public EvectionReqBillEntryCollection()
    {
        super(EvectionReqBillEntryInfo.class);
    }
    public boolean add(EvectionReqBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvectionReqBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvectionReqBillEntryInfo item)
    {
        return removeObject(item);
    }
    public EvectionReqBillEntryInfo get(int index)
    {
        return(EvectionReqBillEntryInfo)getObject(index);
    }
    public EvectionReqBillEntryInfo get(Object key)
    {
        return(EvectionReqBillEntryInfo)getObject(key);
    }
    public void set(int index, EvectionReqBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvectionReqBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvectionReqBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}