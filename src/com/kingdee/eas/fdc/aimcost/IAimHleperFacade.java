package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.framework.*;

public interface IAimHleperFacade extends IBizCtrl
{
    public Map getApportionIndex(String projectID) throws BOSException;
    public Object getParam(String id) throws BOSException, EASBizException;
}