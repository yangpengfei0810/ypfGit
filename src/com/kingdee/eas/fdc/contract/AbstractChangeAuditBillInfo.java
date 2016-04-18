package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeAuditBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractChangeAuditBillInfo()
    {
        this("id");
    }
    protected AbstractChangeAuditBillInfo(String pkField)
    {
        super(pkField);
        put("suppEntry", new com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection());
        put("specialtyTypeEntry", new com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection());
    }
    /**
     * Object: ��������� 's ������ݷ�¼ property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection)get("entrys");
    }
    /**
     * Object: ��������� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: ��������� 's ������� property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getAuditType()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("auditType");
    }
    public void setAuditType(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("auditType", item);
    }
    /**
     * Object: ��������� 's ���ԭ�� property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeReasonInfo getChangeReason()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeReasonInfo)get("changeReason");
    }
    public void setChangeReason(com.kingdee.eas.fdc.basedata.ChangeReasonInfo item)
    {
        put("changeReason", item);
    }
    /**
     * Object:���������'s ״̬property 
     */
    public com.kingdee.eas.fdc.contract.ChangeBillStateEnum getChangeState()
    {
        return com.kingdee.eas.fdc.contract.ChangeBillStateEnum.getEnum(getString("changeState"));
    }
    public void setChangeState(com.kingdee.eas.fdc.contract.ChangeBillStateEnum item)
    {
		if (item != null) {
        setString("changeState", item.getValue());
		}
    }
    /**
     * Object:���������'s �����̶�property 
     */
    public com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum getUrgentDegree()
    {
        return com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum.getEnum(getString("urgentDegree"));
    }
    public void setUrgentDegree(com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum item)
    {
		if (item != null) {
        setString("urgentDegree", item.getValue());
		}
    }
    /**
     * Object: ��������� 's ����������·���λ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection getSuppEntry()
    {
        return (com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection)get("suppEntry");
    }
    /**
     * Object: ��������� 's �а����� property 
     */
    public com.kingdee.eas.fdc.basedata.JobTypeInfo getJobType()
    {
        return (com.kingdee.eas.fdc.basedata.JobTypeInfo)get("jobType");
    }
    public void setJobType(com.kingdee.eas.fdc.basedata.JobTypeInfo item)
    {
        put("jobType", item);
    }
    /**
     * Object: ��������� 's רҵ���� property 
     */
    public com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo getSpecialtyType()
    {
        return (com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo)get("specialtyType");
    }
    public void setSpecialtyType(com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo item)
    {
        put("specialtyType", item);
    }
    /**
     * Object:���������'s �������property 
     */
    public String getChangeSubject()
    {
        return getString("changeSubject");
    }
    public void setChangeSubject(String item)
    {
        setString("changeSubject", item);
    }
    /**
     * Object:���������'s ��ͼ���property 
     */
    public com.kingdee.eas.fdc.contract.GraphCountEnum getGraphCount()
    {
        return com.kingdee.eas.fdc.contract.GraphCountEnum.getEnum(getString("graphCount"));
    }
    public void setGraphCount(com.kingdee.eas.fdc.contract.GraphCountEnum item)
    {
		if (item != null) {
        setString("graphCount", item.getValue());
		}
    }
    /**
     * Object:���������'s �·���λ����property 
     */
    public int getSuppCount()
    {
        return getInt("suppCount");
    }
    public void setSuppCount(int item)
    {
        setInt("suppCount", item);
    }
    /**
     * Object:���������'s �Ƿ���ǰ�·�property 
     */
    public boolean isIsRegister()
    {
        return getBoolean("isRegister");
    }
    public void setIsRegister(boolean item)
    {
        setBoolean("isRegister", item);
    }
    /**
     * Object:���������'s �ɱ����������property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:���������'s �漰��Ч�ɱ��Ľ��property 
     */
    public java.math.BigDecimal getCostNouse()
    {
        return getBigDecimal("costNouse");
    }
    public void setCostNouse(java.math.BigDecimal item)
    {
        setBigDecimal("costNouse", item);
    }
    /**
     * Object:���������'s �漰��Ч�ɱ�ԭ��property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:���������'s �׷��е�����property 
     */
    public java.math.BigDecimal getAmountA()
    {
        return getBigDecimal("amountA");
    }
    public void setAmountA(java.math.BigDecimal item)
    {
        setBigDecimal("amountA", item);
    }
    /**
     * Object:���������'s ���ε�λ�е�����property 
     */
    public java.math.BigDecimal getAmountDutySupp()
    {
        return getBigDecimal("amountDutySupp");
    }
    public void setAmountDutySupp(java.math.BigDecimal item)
    {
        setBigDecimal("amountDutySupp", item);
    }
    /**
     * Object: ��������� 's ������� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getConductDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("conductDept");
    }
    public void setConductDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("conductDept", item);
    }
    /**
     * Object:���������'s ��ǰ�·�ԭ��property 
     */
    public String getAheadReason()
    {
        return getString("aheadReason");
    }
    public void setAheadReason(String item)
    {
        setString("aheadReason", item);
    }
    /**
     * Object:���������'s ��׼��property 
     */
    public String getValidator()
    {
        return getString("validator");
    }
    public void setValidator(String item)
    {
        setString("validator", item);
    }
    /**
     * Object:���������'s ��ͨ��ʽproperty 
     */
    public String getConnectType()
    {
        return getString("connectType");
    }
    public void setConnectType(String item)
    {
        setString("connectType", item);
    }
    /**
     * Object:���������'s ������Ŀproperty 
     */
    public String getCurProjectName()
    {
        return getString("curProjectName");
    }
    public void setCurProjectName(String item)
    {
        setString("curProjectName", item);
    }
    /**
     * Object:���������'s �������property 
     */
    public String getAuditTypeName()
    {
        return getString("auditTypeName");
    }
    public void setAuditTypeName(String item)
    {
        setString("auditTypeName", item);
    }
    /**
     * Object:���������'s �а�����property 
     */
    public String getJobTypeName()
    {
        return getString("jobTypeName");
    }
    public void setJobTypeName(String item)
    {
        setString("jobTypeName", item);
    }
    /**
     * Object:���������'s רҵ����property 
     */
    public String getSpecialtyTypeName()
    {
        return getString("specialtyTypeName");
    }
    public void setSpecialtyTypeName(String item)
    {
        setString("specialtyTypeName", item);
    }
    /**
     * Object:���������'s �Ƿ������Ч�ɱ�property 
     */
    public boolean isIsNoUse()
    {
        return getBoolean("isNoUse");
    }
    public void setIsNoUse(boolean item)
    {
        setBoolean("isNoUse", item);
    }
    /**
     * Object: ��������� 's ��Ч�ɱ�ԭ�� property 
     */
    public com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo getInvalidCostReason()
    {
        return (com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo)get("invalidCostReason");
    }
    public void setInvalidCostReason(com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo item)
    {
        put("invalidCostReason", item);
    }
    /**
     * Object:���������'s ������Դ��ʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.SourceTypeEnum getSourceType()
    {
        return com.kingdee.eas.fdc.basedata.SourceTypeEnum.getEnum(getInt("sourceType"));
    }
    public void setSourceType(com.kingdee.eas.fdc.basedata.SourceTypeEnum item)
    {
		if (item != null) {
        setInt("sourceType", item.getValue());
		}
    }
    /**
     * Object:���������'s �����property 
     */
    public com.kingdee.eas.fdc.contract.OfferEnum getOffer()
    {
        return com.kingdee.eas.fdc.contract.OfferEnum.getEnum(getString("offer"));
    }
    public void setOffer(com.kingdee.eas.fdc.contract.OfferEnum item)
    {
		if (item != null) {
        setString("offer", item.getValue());
		}
    }
    /**
     * Object: ��������� 's ʩ����λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConstrUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("constrUnit");
    }
    public void setConstrUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("constrUnit", item);
    }
    /**
     * Object: ��������� 's ��Ƶ�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getDesignUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("designUnit");
    }
    public void setDesignUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("designUnit", item);
    }
    /**
     * Object:���������'s ʩ����λ property 
     */
    public String getConstrSite()
    {
        return getString("constrSite");
    }
    public void setConstrSite(String item)
    {
        setString("constrSite", item);
    }
    /**
     * Object:���������'s ԭ��˵��property 
     */
    public String getReaDesc()
    {
        return getString("reaDesc");
    }
    public void setReaDesc(String item)
    {
        setString("reaDesc", item);
    }
    /**
     * Object: ��������� 's �����λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConductUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("conductUnit");
    }
    public void setConductUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("conductUnit", item);
    }
    /**
     * Object:���������'s �Ƿ��ش���property 
     */
    public boolean isIsImportChange()
    {
        return getBoolean("isImportChange");
    }
    public void setIsImportChange(boolean item)
    {
        setBoolean("isImportChange", item);
    }
    /**
     * Object:���������'s �鵵��property 
     */
    public String getOwnID()
    {
        return getString("ownID");
    }
    public void setOwnID(String item)
    {
        setString("ownID", item);
    }
    /**
     * Object: ��������� 's רҵ���� property 
     */
    public com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection getSpecialtyTypeEntry()
    {
        return (com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection)get("specialtyTypeEntry");
    }
    /**
     * Object:���������'s רҵ����property 
     */
    public String getSpecialName()
    {
        return getString("specialName");
    }
    public void setSpecialName(String item)
    {
        setString("specialName", item);
    }
    /**
     * Object:���������'s �Ƿ���OA����property 
     */
    public boolean isIsOAAudit()
    {
        return getBoolean("isOAAudit");
    }
    public void setIsOAAudit(boolean item)
    {
        setBoolean("isOAAudit", item);
    }
    /**
     * Object:���������'s oa����ģ��idproperty 
     */
    public String getBillTempletID()
    {
        return getString("billTempletID");
    }
    public void setBillTempletID(String item)
    {
        setString("billTempletID", item);
    }
    /**
     * Object:���������'s oa����idproperty 
     */
    public String getOABillID()
    {
        return getString("OABillID");
    }
    public void setOABillID(String item)
    {
        setString("OABillID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("70116117");
    }
}