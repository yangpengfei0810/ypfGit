package com.kingdee.eas.fdc.contract;

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

public interface IContractBillFacade extends IBizCtrl
{
    public boolean updateSplitAmt(String id, BigDecimal splitAmt) throws BOSException;
    public boolean updateTotalSplitAmount(String id, BigDecimal totalSplitAmount) throws BOSException;
    public BigDecimal getTotalSplitAmount(String pcID) throws BOSException;
}