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
import com.kingdee.jdbc.rowset.IRowSet;
import java.math.BigDecimal;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractChangeVisaFacadeController extends BizController
{
    public String saveCostAmountByChngSupEntyID(Context ctx, String pk, BigDecimal amount) throws BOSException, RemoteException;
    public String saveCostAmountByChngAuditBillID(Context ctx, String pk) throws BOSException, RemoteException;
    public IRowSet getCostAmountByChngAuditBillID(Context ctx, String pk, String rmk) throws BOSException, RemoteException;
    public String getChngAuditBillNumber(Context ctx, String orgId, String prjId, String chngId, String prefix) throws BOSException, RemoteException;
    public void executeUpdate(Context ctx, String sql) throws BOSException, RemoteException;
    public String saveVisaAmountByChngAuditBillID(Context ctx, String pk) throws BOSException, RemoteException;
    public String unSaveCostAmountByChngAuditBillID(Context ctx, String pk) throws BOSException, RemoteException;
    public String unSaveVisaAmountByChngAuditBillID(Context ctx, String pk) throws BOSException, RemoteException;
    public String clearCode(Context ctx, String prmStr) throws BOSException, RemoteException;
}