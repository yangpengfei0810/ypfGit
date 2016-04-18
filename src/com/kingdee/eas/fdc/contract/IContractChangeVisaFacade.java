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
import com.kingdee.jdbc.rowset.IRowSet;
import java.math.BigDecimal;
import com.kingdee.bos.framework.*;

public interface IContractChangeVisaFacade extends IBizCtrl
{
    public String saveCostAmountByChngSupEntyID(String pk, BigDecimal amount) throws BOSException;
    public String saveCostAmountByChngAuditBillID(String pk) throws BOSException;
    public IRowSet getCostAmountByChngAuditBillID(String pk, String rmk) throws BOSException;
    public String getChngAuditBillNumber(String orgId, String prjId, String chngId, String prefix) throws BOSException;
    public void executeUpdate(String sql) throws BOSException;
    public String saveVisaAmountByChngAuditBillID(String pk) throws BOSException;
    public String unSaveCostAmountByChngAuditBillID(String pk) throws BOSException;
    public String unSaveVisaAmountByChngAuditBillID(String pk) throws BOSException;
    public String clearCode(String prmStr) throws BOSException;
}