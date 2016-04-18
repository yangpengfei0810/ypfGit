package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPDCostDetailInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPDCostDetailInfo()
    {
        this("id");
    }
    protected AbstractPDCostDetailInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.aimcost.PDCostDetailEntryCollection());
    }
    /**
     * Object: ����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.PDCostDetailEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.PDCostDetailEntryCollection)get("entries");
    }
    /**
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢's ������Ŀproperty 
     */
    public String getCurProjectID()
    {
        return getString("curProjectID");
    }
    public void setCurProjectID(String item)
    {
        setString("curProjectID", item);
    }
    /**
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢's ״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢's �汾��property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    /**
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢's ����ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object:����Ʒ���Ͷ�̬�ɱ���ϸ�����ݴ洢's �汾����property 
     */
    public String getVersionName()
    {
        return getString("versionName");
    }
    public void setVersionName(String item)
    {
        setString("versionName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E1959649");
    }
}