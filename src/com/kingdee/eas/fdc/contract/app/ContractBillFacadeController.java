package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.math.BigDecimal;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractBillFacadeController extends BizController
{
    public boolean updateSplitAmt(Context ctx, String id, BigDecimal splitAmt) throws BOSException, RemoteException;
    public boolean updateTotalSplitAmount(Context ctx, String id, BigDecimal totalSplitAmount) throws BOSException, RemoteException;
    public BigDecimal getTotalSplitAmount(Context ctx, String pcID) throws BOSException, RemoteException;
}