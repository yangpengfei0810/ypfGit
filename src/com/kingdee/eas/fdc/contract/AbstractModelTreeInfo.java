package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractModelTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractModelTreeInfo()
    {
        this("id");
    }
    protected AbstractModelTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 模板组别 's 父节点 property 
     */
    public com.kingdee.eas.fdc.contract.ModelTreeInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ModelTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ModelTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4C7AF68C");
    }
}