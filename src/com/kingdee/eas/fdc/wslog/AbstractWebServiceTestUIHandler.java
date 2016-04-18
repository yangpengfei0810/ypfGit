/**
 * output package name
 */
package com.kingdee.eas.fdc.wslog;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractWebServiceTestUIHandler extends com.kingdee.eas.framework.app.CoreUIObjectHandler

{
	public void handleActionTest(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTest(request,response,context);
	}
	protected void _handleActionTest(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSearch(request,response,context);
	}
	protected void _handleActionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	protected void _handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}