package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class PublicToSplitEntryInfo extends AbstractPublicToSplitEntryInfo implements Serializable 
{
    public PublicToSplitEntryInfo()
    {
        super();
    }
    protected PublicToSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}