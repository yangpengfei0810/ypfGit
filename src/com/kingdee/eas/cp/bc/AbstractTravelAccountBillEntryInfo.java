package com.kingdee.eas.cp.bc;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTravelAccountBillEntryInfo extends com.kingdee.eas.cp.bc.EvectionExpBillEntryBaseInfo implements Serializable 
{
    public AbstractTravelAccountBillEntryInfo()
    {
        this("id");
    }
    protected AbstractTravelAccountBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.cp.bc.TravelAccountBillInfo getBill()
    {
        return (com.kingdee.eas.cp.bc.TravelAccountBillInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.cp.bc.TravelAccountBillInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: ��¼ 's ҵ����� property 
     */
    public com.kingdee.eas.cp.bc.OperationTypeInfo getOperationType()
    {
        return (com.kingdee.eas.cp.bc.OperationTypeInfo)get("operationType");
    }
    public void setOperationType(com.kingdee.eas.cp.bc.OperationTypeInfo item)
    {
        put("operationType", item);
    }
    /**
     * Object: ��¼ 's ���óе����� property 
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
     * Object: ��¼ 's ���óе���˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:��¼'s Ӧ��״̬property 
     */
    public com.kingdee.eas.cp.bc.EntryStateEnum getReceiveState()
    {
        return com.kingdee.eas.cp.bc.EntryStateEnum.getEnum(getInt("receiveState"));
    }
    public void setReceiveState(com.kingdee.eas.cp.bc.EntryStateEnum item)
    {
		if (item != null) {
        setInt("receiveState", item.getValue());
		}
    }
    /**
     * Object:��¼'s Ӧ��״̬property 
     */
    public com.kingdee.eas.cp.bc.EntryStateEnum getPayState()
    {
        return com.kingdee.eas.cp.bc.EntryStateEnum.getEnum(getInt("payState"));
    }
    public void setPayState(com.kingdee.eas.cp.bc.EntryStateEnum item)
    {
		if (item != null) {
        setInt("payState", item.getValue());
		}
    }
    /**
     * Object:��¼'s ����property 
     */
    public int getDayCount()
    {
        return getInt("dayCount");
    }
    public void setDayCount(int item)
    {
        setInt("dayCount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("83E90A16");
    }
}