package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewOldAccountRelationInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractNewOldAccountRelationInfo()
    {
        this("id");
    }
    protected AbstractNewOldAccountRelationInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�¾����׿�Ŀӳ���ϵ��'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:�¾����׿�Ŀӳ���ϵ��'s ���property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object: �¾����׿�Ŀӳ���ϵ�� 's �����׳ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getOldCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("oldCostAccount");
    }
    public void setOldCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("oldCostAccount", item);
    }
    /**
     * Object: �¾����׿�Ŀӳ���ϵ�� 's �����׿�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getNewAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("newAccount");
    }
    public void setNewAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("newAccount", item);
    }
    /**
     * Object: �¾����׿�Ŀӳ���ϵ�� 's �¾�������֯ property 
     */
    public com.kingdee.eas.fdc.finance.NewOldOrgRelationInfo getNOORID()
    {
        return (com.kingdee.eas.fdc.finance.NewOldOrgRelationInfo)get("nOORID");
    }
    public void setNOORID(com.kingdee.eas.fdc.finance.NewOldOrgRelationInfo item)
    {
        put("nOORID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("20034A33");
    }
}