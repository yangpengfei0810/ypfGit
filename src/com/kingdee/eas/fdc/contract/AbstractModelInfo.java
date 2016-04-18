package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractModelInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractModelInfo()
    {
        this("id");
    }
    protected AbstractModelInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ä£°å 's ×é±ð property 
     */
    public com.kingdee.eas.fdc.contract.ModelTreeInfo getTreeid()
    {
        return (com.kingdee.eas.fdc.contract.ModelTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.fdc.contract.ModelTreeInfo item)
    {
        put("treeid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("86D6A5CE");
    }
}