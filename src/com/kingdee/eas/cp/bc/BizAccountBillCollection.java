package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BizAccountBillCollection extends AbstractObjectCollection 
{
    public BizAccountBillCollection()
    {
        super(BizAccountBillInfo.class);
    }
    public boolean add(BizAccountBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BizAccountBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BizAccountBillInfo item)
    {
        return removeObject(item);
    }
    public BizAccountBillInfo get(int index)
    {
        return(BizAccountBillInfo)getObject(index);
    }
    public BizAccountBillInfo get(Object key)
    {
        return(BizAccountBillInfo)getObject(key);
    }
    public void set(int index, BizAccountBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BizAccountBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BizAccountBillInfo item)
    {
        return super.indexOf(item);
    }
}