package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillFacadeControllerBean extends AbstractContractBillFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillFacadeControllerBean");
    
    protected boolean _updateSplitAmt(Context ctx, String id, BigDecimal splitAmt) throws BOSException {
    	DbUtil.execute(ctx, "update T_CON_ContractBill set fsplitamt = "+splitAmt+" where fid = '"+id+"'");
    	return true;
    }
    
    protected boolean _updateTotalSplitAmount(Context ctx, String id, BigDecimal totalSplitAmount) throws BOSException {
    	DbUtil.execute(ctx, "update T_CON_ProgrammingContract set FTotalSplitAmount = "+totalSplitAmount+" where fid = '"+id+"'");
    	return true;
    }
    
    protected BigDecimal _getTotalSplitAmount(Context ctx, String pcID) throws BOSException {
    	IRowSet rs = DbUtil.executeQuery(ctx, "select FTotalSplitAmount from T_CON_ProgrammingContract where fid = '"+pcID+"'");
    	BigDecimal totalSplitAmount = null;
    	try {
			if(rs.next()){
				totalSplitAmount = rs.getBigDecimal("FTotalSplitAmount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return totalSplitAmount;
    }
}