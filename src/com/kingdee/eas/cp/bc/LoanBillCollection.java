package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LoanBillCollection extends AbstractObjectCollection 
{
    public LoanBillCollection()
    {
        super(LoanBillInfo.class);
    }
    public boolean add(LoanBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LoanBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LoanBillInfo item)
    {
        return removeObject(item);
    }
    public LoanBillInfo get(int index)
    {
        return(LoanBillInfo)getObject(index);
    }
    public LoanBillInfo get(Object key)
    {
        return(LoanBillInfo)getObject(key);
    }
    public void set(int index, LoanBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LoanBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LoanBillInfo item)
    {
        return super.indexOf(item);
    }
}