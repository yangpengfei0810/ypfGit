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
     * Object:webservice日志's nullproperty 
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
     * Object:webservice日志's 源单据idproperty 
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
     * Object:webservice日志's 源单据类型property 
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
     * Object:webservice日志's log标题property 
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
     * Object:webservice日志's log明细property 
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
     * Object:webservice日志's 创建时间property 
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
     * Object:webservice日志's url链接property 
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
     * Object:webservice日志's 状态property 
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
     * Object:webservice日志's 是否关闭property 
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
     * Object:webservice日志's 入参property 
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
     * Object:webservice日志's 调用类型property 
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
     * Object:webservice日志's 备注property 
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
     * Object:webservice日志's 最后修改时间property 
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