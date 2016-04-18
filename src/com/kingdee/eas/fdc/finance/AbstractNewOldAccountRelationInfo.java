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
     * Object:新旧帐套科目映射关系表's 备注property 
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
     * Object:新旧帐套科目映射关系表's 序号property 
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
     * Object: 新旧帐套科目映射关系表 's 旧帐套成本科目 property 
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
     * Object: 新旧帐套科目映射关系表 's 新帐套科目 property 
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
     * Object: 新旧帐套科目映射关系表 's 新旧帐套组织 property 
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