package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvectionReqBillEntryInfo extends com.kingdee.eas.cp.bc.EvectionExpBillEntryBaseInfo implements Serializable 
{
    public AbstractEvectionReqBillEntryInfo()
    {
        this("id");
    }
    protected AbstractEvectionReqBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��¼'s ͬ����property 
     */
    public String getPartner()
    {
        return getString("partner");
    }
    public void setPartner(String item)
    {
        setString("partner", item);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.cp.bc.EvectionReqBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.EvectionReqBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.EvectionReqBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object:��¼'s Ԥ�Ƴ�������property 
     */
    public int getPreDays()
    {
        return getInt("PreDays");
    }
    public void setPreDays(int item)
    {
        setInt("PreDays", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5902774E");
    }
}