package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ModelInfo extends AbstractModelInfo implements Serializable 
{
    public ModelInfo()
    {
        super();
    }
    protected ModelInfo(String pkField)
    {
        super(pkField);
    }
}