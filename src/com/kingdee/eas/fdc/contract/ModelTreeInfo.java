package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ModelTreeInfo extends AbstractModelTreeInfo implements Serializable 
{
    public ModelTreeInfo()
    {
        super();
    }
    protected ModelTreeInfo(String pkField)
    {
        super(pkField);
    }
}