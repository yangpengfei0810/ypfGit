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
public abstract class AbstractNewOldOrgRelationUIHandler extends com.kingdee.eas.framework.app.TableEditUIHandler

{
	public void handleActionImportTemp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportTemp(request,response,context);
	}
	protected void _handleActionImportTemp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}