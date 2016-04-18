package com.kingdee.eas.fdc.aimcost.app;

import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;

public class AimHleperFacadeControllerBeanEx extends com.kingdee.eas.fdc.aimcost.app.AimHleperFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.AimHleperFacadeControllerBeanEx");
    protected Map _getApportionIndex(Context ctx, String projectID)throws BOSException
    {
	    return  super._getApportionIndex(ctx, projectID);
    }
}				
