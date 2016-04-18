package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class PublicSetSplitEntryInfo extends AbstractPublicSetSplitEntryInfo implements Serializable 
{
    public PublicSetSplitEntryInfo()
    {
        super();
    }
    protected PublicSetSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}