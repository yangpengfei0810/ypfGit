/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractOtherPaymentListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

{
	public void handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrint(request,response,context);
	}
	protected void _handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrintPreview(request,response,context);
	}
	protected void _handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRcontract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRcontract(request,response,context);
	}
	protected void _handleActionRcontract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemRelation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemRelation(request,response,context);
	}
	protected void _handleActionRemRelation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionaudit(request,response,context);
	}
	protected void _handleActionaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}