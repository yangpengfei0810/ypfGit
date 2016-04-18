package com.kingdee.eas.base.attachment;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBoAttchAssoInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractBoAttchAssoInfo()
    {
        this("id");
    }
    protected AbstractBoAttchAssoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������ҵ��������'s Ψһ��ʶproperty 
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
     * Object:������ҵ��������'s ҵ��idproperty 
     */
    public String getBoID()
    {
        return getString("boID");
    }
    public void setBoID(String item)
    {
        setString("boID", item);
    }
    /**
     * Object:������ҵ��������'s ����property 
     */
    public String getAssoType()
    {
        return getAssoType((Locale)null);
    }
    public void setAssoType(String item)
    {
		setAssoType(item,(Locale)null);
    }
    public String getAssoType(Locale local)
    {
        return TypeConversionUtils.objToString(get("assoType", local));
    }
    public void setAssoType(String item, Locale local)
    {
        put("assoType", item, local);
    }
    /**
     * Object: ������ҵ�������� 's ���� property 
     */
    public com.kingdee.eas.base.attachment.AttachmentInfo getAttachment()
    {
        return (com.kingdee.eas.base.attachment.AttachmentInfo)get("attachment");
    }
    public void setAttachment(com.kingdee.eas.base.attachment.AttachmentInfo item)
    {
        put("attachment", item);
    }
    /**
     * Object:������ҵ��������'s ����ҵ����������property 
     */
    public String getAssoBusObjType()
    {
        return getString("assoBusObjType");
    }
    public void setAssoBusObjType(String item)
    {
        setString("assoBusObjType", item);
    }
    /**
     * Object:������ҵ��������'s Դ����idproperty 
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
     * Object:������ҵ��������'s ����ʱ��property 
     */
    public java.sql.Timestamp getSaveTime()
    {
        return getTimestamp("saveTime");
    }
    public void setSaveTime(java.sql.Timestamp item)
    {
        setTimestamp("saveTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("172F3A47");
    }
}