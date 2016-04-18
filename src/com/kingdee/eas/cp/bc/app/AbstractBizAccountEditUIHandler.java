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
public abstract class AbstractBizAccountEditUIHandler extends com.kingdee.eas.cp.bc.app.ExpenseAccountEditUIHandler

{
	public void handleActionViewRrcdsOfLendAndRepay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewRrcdsOfLendAndRepay(request,response,context);
	}
	protected void _handleActionViewRrcdsOfLendAndRepay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}