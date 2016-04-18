package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class NewOldAccountRelationInfo extends AbstractNewOldAccountRelationInfo implements Serializable 
{
    public NewOldAccountRelationInfo()
    {
        super();
    }
    protected NewOldAccountRelationInfo(String pkField)
    {
        super(pkField);
    }
}