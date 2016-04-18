package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCurProjectInfo extends com.kingdee.eas.fdc.basedata.ProjectInfo implements Serializable 
{
    public AbstractCurProjectInfo()
    {
        this("id");
    }
    protected AbstractCurProjectInfo(String pkField)
    {
        super(pkField);
        put("curProjProductEntries", new com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection());
    }
    /**
     * Object: ������Ŀ 's ��ǰ������Ŀ��Ʒ���÷�¼ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection getCurProjProductEntries()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection)get("curProjProductEntries");
    }
    /**
     * Object: ������Ŀ 's ����� property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ������Ŀ 's ��Ŀ״̬ property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectStatusInfo getProjectStatus()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectStatusInfo)get("projectStatus");
    }
    public void setProjectStatus(com.kingdee.eas.fdc.basedata.ProjectStatusInfo item)
    {
        put("projectStatus", item);
    }
    /**
     * Object: ������Ŀ 's ��Ŀϵ�� property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectTypeInfo getProjectType()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectTypeInfo)get("projectType");
    }
    public void setProjectType(com.kingdee.eas.fdc.basedata.ProjectTypeInfo item)
    {
        put("projectType", item);
    }
    /**
     * Object:������Ŀ's ����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:������Ŀ's ��Ŀ����property 
     */
    public String getProjectPeriod()
    {
        return getString("projectPeriod");
    }
    public void setProjectPeriod(String item)
    {
        setString("projectPeriod", item);
    }
    /**
     * Object: ������Ŀ 's �ɱ�������֯ property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostOrg()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costOrg");
    }
    public void setCostOrg(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costOrg", item);
    }
    /**
     * Object:������Ŀ's ��ĿIDproperty 
     */
    public String getBdProjectID()
    {
        return getString("bdProjectID");
    }
    public void setBdProjectID(String item)
    {
        setString("bdProjectID", item);
    }
    /**
     * Object:������Ŀ's ������Ŀ������property 
     */
    public String getCodingNumber()
    {
        return getString("codingNumber");
    }
    public void setCodingNumber(String item)
    {
        setString("codingNumber", item);
    }
    /**
     * Object: ������Ŀ 's ������Ŀ��Ӧ�ĳɱ����� property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostCenter()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costCenter");
    }
    public void setCostCenter(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costCenter", item);
    }
    /**
     * Object:������Ŀ's �Ƿ񿪷���Ŀproperty 
     */
    public boolean isIsDevPrj()
    {
        return getBoolean("isDevPrj");
    }
    public void setIsDevPrj(boolean item)
    {
        setBoolean("isDevPrj", item);
    }
    /**
     * Object:������Ŀ's ��Ŀ��ַproperty 
     */
    public String getProjectAddress()
    {
        return getString("projectAddress");
    }
    public void setProjectAddress(String item)
    {
        setString("projectAddress", item);
    }
    /**
     * Object:������Ŀ's ����ӳ������property 
     */
    public String getPrjMappingName()
    {
        return getString("prjMappingName");
    }
    public void setPrjMappingName(String item)
    {
        setString("prjMappingName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F9E5E92B");
    }
}