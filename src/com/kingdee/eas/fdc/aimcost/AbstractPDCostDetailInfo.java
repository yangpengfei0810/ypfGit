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
     * Object: 各产品类型动态成本明细表数据存储 's 分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.PDCostDetailEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.PDCostDetailEntryCollection)get("entries");
    }
    /**
     * Object:各产品类型动态成本明细表数据存储's 工程项目property 
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
     * Object:各产品类型动态成本明细表数据存储's 状态property 
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
     * Object:各产品类型动态成本明细表数据存储's 版本号property 
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
     * Object:各产品类型动态成本明细表数据存储's 审批时间property 
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
     * Object:各产品类型动态成本明细表数据存储's 版本名称property 
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