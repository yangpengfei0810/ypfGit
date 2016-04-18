package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostDetailInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractDynCostDetailInfo()
    {
        this("id");
    }
    protected AbstractDynCostDetailInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.aimcost.DynCostDetailDataCollection());
    }
    /**
     * Object:��̬�ɱ���ϸ�汾��Ϣ's �汾��property 
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
     * Object: ��̬�ɱ���ϸ�汾��Ϣ 's ������Ŀ property 
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
     * Object: ��̬�ɱ���ϸ�汾��Ϣ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostDetailDataCollection getEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostDetailDataCollection)get("entries");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B9A821E0");
    }
}