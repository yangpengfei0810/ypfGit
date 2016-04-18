/**
 * output package name
 */
package com.kingdee.eas.cp.bc.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractDailyLoanBillEditUIHandler extends com.kingdee.eas.cp.bc.app.LoanReqEditUIHandler

{
	public void handleActionViewRrcdsOfLendAndRepay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewRrcdsOfLendAndRepay(request,response,context);
	}
	protected void _handleActionViewRrcdsOfLendAndRepay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}