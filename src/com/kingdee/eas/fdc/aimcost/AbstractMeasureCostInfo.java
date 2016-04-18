package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMeasureCostInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMeasureCostInfo()
    {
        this("id");
    }
    protected AbstractMeasureCostInfo(String pkField)
    {
        super(pkField);
        put("targetCostSplit", new com.kingdee.eas.fdc.aimcost.TargetCostSplitCollection());
        put("constrEntrys", new com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection());
        put("measureCollectData", new com.kingdee.eas.fdc.aimcost.MeasureCollectDataCollection());
        put("saleAreaData", new com.kingdee.eas.fdc.aimcost.SaleAreaDataCollection());
        put("costEntry", new com.kingdee.eas.fdc.aimcost.MeasureEntryCollection());
    }
    /**
     * Object: �ɱ����� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: �ɱ����� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureEntryCollection getCostEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureEntryCollection)get("costEntry");
    }
    /**
     * Object:�ɱ�����'s �汾��property 
     */
    public String getVersionNumber()
    {
        return getString("versionNumber");
    }
    public void setVersionNumber(String item)
    {
        setString("versionNumber", item);
    }
    /**
     * Object:�ɱ�����'s �汾����property 
     */
    public String getVersionName()
    {
        return getString("versionName");
    }
    public void setVersionName(String item)
    {
        setString("versionName", item);
    }
    /**
     * Object:�ɱ�����'s �޶�����property 
     */
    public java.util.Date getRecenseDate()
    {
        return getDate("recenseDate");
    }
    public void setRecenseDate(java.util.Date item)
    {
        setDate("recenseDate", item);
    }
    /**
     * Object:�ɱ�����'s �Ƿ����հ汾property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    /**
     * Object: �ɱ����� 's ��Ŀϵ�� property 
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
     * Object:�ɱ�����'s �Ƿ�Ŀ�����property 
     */
    public boolean isIsAimMeasure()
    {
        return getBoolean("isAimMeasure");
    }
    public void setIsAimMeasure(boolean item)
    {
        setBoolean("isAimMeasure", item);
    }
    /**
     * Object:�ɱ�����'s ���汾��property 
     */
    public int getMainVerNo()
    {
        return getInt("mainVerNo");
    }
    public void setMainVerNo(int item)
    {
        setInt("mainVerNo", item);
    }
    /**
     * Object:�ɱ�����'s �Ӱ汾��property 
     */
    public int getSubVerNo()
    {
        return getInt("subVerNo");
    }
    public void setSubVerNo(int item)
    {
        setInt("subVerNo", item);
    }
    /**
     * Object: �ɱ����� 's ����׶� property 
     */
    public com.kingdee.eas.fdc.basedata.MeasureStageInfo getMeasureStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasureStageInfo)get("measureStage");
    }
    public void setMeasureStage(com.kingdee.eas.fdc.basedata.MeasureStageInfo item)
    {
        put("measureStage", item);
    }
    /**
     * Object:�ɱ�����'s ���޶�����IDproperty 
     */
    public String getSrcMeasureCostId()
    {
        return getString("srcMeasureCostId");
    }
    public void setSrcMeasureCostId(String item)
    {
        setString("srcMeasureCostId", item);
    }
    /**
     * Object: �ɱ����� 's ����ָ�� property 
     */
    public com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection getConstrEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection)get("constrEntrys");
    }
    /**
     * Object: �ɱ����� 's ��������Ŀ��ɱ���̯ property 
     */
    public com.kingdee.eas.fdc.aimcost.TargetCostSplitCollection getTargetCostSplit()
    {
        return (com.kingdee.eas.fdc.aimcost.TargetCostSplitCollection)get("targetCostSplit");
    }
    /**
     * Object: �ɱ����� 's ���۵������ܱ� property 
     */
    public com.kingdee.eas.fdc.aimcost.SaleAreaDataCollection getSaleAreaData()
    {
        return (com.kingdee.eas.fdc.aimcost.SaleAreaDataCollection)get("saleAreaData");
    }
    /**
     * Object: �ɱ����� 's �����������ܱ� property 
     */
    public com.kingdee.eas.fdc.aimcost.MeasureCollectDataCollection getMeasureCollectData()
    {
        return (com.kingdee.eas.fdc.aimcost.MeasureCollectDataCollection)get("measureCollectData");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("99193494");
    }
}