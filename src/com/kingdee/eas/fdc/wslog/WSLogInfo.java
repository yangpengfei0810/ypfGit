package com.kingdee.eas.fdc.wslog;

import java.io.Serializable;

public class WSLogInfo extends AbstractWSLogInfo implements Serializable 
{
    public WSLogInfo()
    {
        super();
    }
    protected WSLogInfo(String pkField)
    {
        super(pkField);
    }
}