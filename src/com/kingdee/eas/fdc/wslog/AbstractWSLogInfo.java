package com.kingdee.eas.fdc.wslog;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWSLogInfo extends AbstractObjectValue implements Serializable 
{
    public AbstractWSLogInfo()
    {
        this("id");
    }
    protected AbstractWSLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:webservice��־'s nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object:webservice��־'s Դ����idproperty 
     */
    public String getSourceBillID()
    {
        return getString("sourceBillID");
    }
    public void setSourceBillID(String item)
    {
        setString("sourceBillID", item);
    }
    /**
     * Object:webservice��־'s Դ��������property 
     */
    public String getSourceBillType()
    {
        return getString("sourceBillType");
    }
    public void setSourceBillType(String item)
    {
        setString("sourceBillType", item);
    }
    /**
     * Object:webservice��־'s log����property 
     */
    public String getLogTitle()
    {
        return getString("logTitle");
    }
    public void setLogTitle(String item)
    {
        setString("logTitle", item);
    }
    /**
     * Object:webservice��־'s log��ϸproperty 
     */
    public String getLogDetail()
    {
        return getString("logDetail");
    }
    public void setLogDetail(String item)
    {
        setString("logDetail", item);
    }
    /**
     * Object:webservice��־'s ����ʱ��property 
     */
    public java.sql.Timestamp getCreateTime()
    {
        return getTimestamp("createTime");
    }
    public void setCreateTime(java.sql.Timestamp item)
    {
        setTimestamp("createTime", item);
    }
    /**
     * Object:webservice��־'s url����property 
     */
    public String getUrl()
    {
        return getString("url");
    }
    public void setUrl(String item)
    {
        setString("url", item);
    }
    /**
     * Object:webservice��־'s ״̬property 
     */
    public String getState()
    {
        return getString("state");
    }
    public void setState(String item)
    {
        setString("state", item);
    }
    /**
     * Object:webservice��־'s �Ƿ�ر�property 
     */
    public String getIsClosed()
    {
        return getString("isClosed");
    }
    public void setIsClosed(String item)
    {
        setString("isClosed", item);
    }
    /**
     * Object:webservice��־'s ���property 
     */
    public String getInParam()
    {
        return getString("inParam");
    }
    public void setInParam(String item)
    {
        setString("inParam", item);
    }
    /**
     * Object:webservice��־'s ��������property 
     */
    public String getCallType()
    {
        return getString("callType");
    }
    public void setCallType(String item)
    {
        setString("callType", item);
    }
    /**
     * Object:webservice��־'s ��עproperty 
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
     * Object:webservice��־'s ����޸�ʱ��property 
     */
    public java.sql.Timestamp getLastUpdateTime()
    {
        return getTimestamp("lastUpdateTime");
    }
    public void setLastUpdateTime(java.sql.Timestamp item)
    {
        setTimestamp("lastUpdateTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FCF80554");
    }
}