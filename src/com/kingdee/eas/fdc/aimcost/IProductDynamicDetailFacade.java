package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import java.util.Set;

public interface IProductDynamicDetailFacade extends IBizCtrl
{
    public ProductTypeCollection getProductTypes(Set projectIDs) throws BOSException;
    public Map getDetailData(Set projectIDs) throws BOSException;
    public Map getSplitAmount(String projectID) throws BOSException;
}