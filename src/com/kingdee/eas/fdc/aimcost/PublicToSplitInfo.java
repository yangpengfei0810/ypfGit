package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;

public class PublicToSplitInfo extends AbstractPublicToSplitInfo implements Serializable 
{
    public PublicToSplitInfo()
    {
        super();
    }
    protected PublicToSplitInfo(String pkField)
    {
        super(pkField);
    }
}