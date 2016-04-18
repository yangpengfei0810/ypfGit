package com.kingdee.eas.cp.bc;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DailyLoanBillCollection extends AbstractObjectCollection 
{
    public DailyLoanBillCollection()
    {
        super(DailyLoanBillInfo.class);
    }
    public boolean add(DailyLoanBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DailyLoanBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DailyLoanBillInfo item)
    {
        return removeObject(item);
    }
    public DailyLoanBillInfo get(int index)
    {
        return(DailyLoanBillInfo)getObject(index);
    }
    public DailyLoanBillInfo get(Object key)
    {
        return(DailyLoanBillInfo)getObject(key);
    }
    public void set(int index, DailyLoanBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DailyLoanBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DailyLoanBillInfo item)
    {
        return super.indexOf(item);
    }
}