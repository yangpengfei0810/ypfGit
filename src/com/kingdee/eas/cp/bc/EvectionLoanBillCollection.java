package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvectionLoanBillCollection extends AbstractObjectCollection 
{
    public EvectionLoanBillCollection()
    {
        super(EvectionLoanBillInfo.class);
    }
    public boolean add(EvectionLoanBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvectionLoanBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvectionLoanBillInfo item)
    {
        return removeObject(item);
    }
    public EvectionLoanBillInfo get(int index)
    {
        return(EvectionLoanBillInfo)getObject(index);
    }
    public EvectionLoanBillInfo get(Object key)
    {
        return(EvectionLoanBillInfo)getObject(key);
    }
    public void set(int index, EvectionLoanBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvectionLoanBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvectionLoanBillInfo item)
    {
        return super.indexOf(item);
    }
}