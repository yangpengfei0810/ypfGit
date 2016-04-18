package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewOldOrgRelationInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractNewOldOrgRelationInfo()
    {
        this("id");
    }
    protected AbstractNewOldOrgRelationInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �¾�������֯ӳ���ϵ�� 's ��������֯ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getOldCompanyOrg()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("oldCompanyOrg");
    }
    public void setOldCompanyOrg(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("oldCompanyOrg", item);
    }
    /**
     * Object: �¾�������֯ӳ���ϵ�� 's ��������֯ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getNewCompanyOrg()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("newCompanyOrg");
    }
    public void setNewCompanyOrg(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("newCompanyOrg", item);
    }
    /**
     * Object:�¾�������֯ӳ���ϵ��'s ���property 
     */
    public int getReq()
    {
        return getInt("req");
    }
    public void setReq(int item)
    {
        setInt("req", item);
    }
    /**
     * Object:�¾�������֯ӳ���ϵ��'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8680CCEA");
    }
}