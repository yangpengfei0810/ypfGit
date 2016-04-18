package com.kingdee.eas.cp.base.web;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.waf.annotation.IBOSBizCtrl;
import com.kingdee.bos.waf.ctx.WafContext;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.cp.base.ISubSystem;
import com.kingdee.eas.cp.base.SubSystemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.operamasks.faces.annotation.Accessible;
import org.operamasks.faces.annotation.BeforeRender;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;

@ManagedBean(name = "cp.base.IndexBean", scope = ManagedBeanScope.SESSION)
public class IndexBean {
	private Logger logger = Logger.getLogger(IndexBean.class);

	@Accessible
	public String modelId;

	@Accessible
	public String modelUrl;

	@IBOSBizCtrl
	private ISubSystem service;

	@Bind
	public List<CoreBaseInfo> subSysNav;

	@Accessible
	String userHasPerson = "true";

	@Accessible
	String showHeight;

	@BeforeRender
	public void onBeforeRender(boolean isPostBack) {
		if (!isPostBack) {
			try {
				this.subSysNav = getListFormCollection(this.service
						.getCollection("select id,name,number,url where visible = 1 and moduleName='cp' order by index"));
			} catch (BOSException e) {
				this.logger.info("init subSystemList error" + e.getMessage());
				this.subSysNav = new ArrayList();
			}
		}

		checkUserIsPerson();
		initSelectedModule();
		initShowHeight();
	}

	public String getModeId() {
		return this.modelId;
	}

	public void setModeId(String modeId) {
		this.modelId = modeId;
	}

	private void initSelectedModule() {
		Map params = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();

		this.modelId = ((String) params.get("modelId"));

		if (this.modelId == null) {
			if ((this.subSysNav != null) && (this.subSysNav.size() > 0)) {
				this.modelUrl = ((SubSystemInfo) this.subSysNav.get(0))
						.getUrl();
				this.modelId = ((SubSystemInfo) this.subSysNav.get(0))
						.getNumber();
			} else {
				this.modelUrl = "";
			}
		} else
			this.modelUrl = getModelUrl(this.modelId);
	}

	private void checkUserIsPerson() {
		Context ctx = WafContext.getInstance().getContext();
		UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
		if (user != null) {
			if ((user.getPerson() == null)
					|| (user.getPerson().getId() == null))
				this.userHasPerson = "false";
			else
				this.userHasPerson = "true";
		} else
			this.userHasPerson = "false";
	}

	protected void initShowHeight() {
		Map params = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		this.showHeight = ((String) params.get("clientHeight"));
		if ((this.showHeight == null) || ("".equals(this.showHeight))) {
			Object tempHeight = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("clientHeight");
			if ((tempHeight != null) && (!"".equals(tempHeight)))
				this.showHeight = tempHeight.toString();
			else {
				this.showHeight = "600";
			}
		}

		String objMethod = (String) params.get("fromMethod");
		int height = Integer.parseInt(this.showHeight);
		if ("commonFunctionPortlet".equals(objMethod)) {
			height = Integer.parseInt(this.showHeight) - 251;
			this.showHeight = String.valueOf(height + 77);
		} else if ("commonFunctionPortlet".equals(objMethod)) {
			height = Integer.parseInt(this.showHeight) - 251;
			this.showHeight = String.valueOf(height + 77);
		} else if ("fastToolbar".equals(objMethod)) {
			height = Integer.parseInt(this.showHeight) - 251;
			this.showHeight = String.valueOf(height + 77);
		} else {
			height = Integer.parseInt(this.showHeight);
			this.showHeight = String.valueOf(height);
		}

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("clientHeight", this.showHeight);
	}

	private String getModelUrl(String modelId) {
		String subUrl = "";

		if (this.subSysNav != null) {
			Iterator ite = this.subSysNav.iterator();

			while (ite.hasNext()) {
				SubSystemInfo subInfo = (SubSystemInfo) ite.next();
				if (modelId.equals(subInfo.getId().toString())) {
					subUrl = subInfo.getUrl();
				}
			}

		}

		return subUrl;
	}

	private List<CoreBaseInfo> getListFormCollection(CoreBaseCollection coll) {
		List coreBaseList = new ArrayList();

		if (coll == null) {
			return coreBaseList;
		}

		int i = 0;
		for (int size = coll.size(); i < size; i++) {
			SubSystemInfo subInfo = (SubSystemInfo) coll.get(i);
			// 只显示费用报销
			if("bc".equals(subInfo.getNumber())){
				if (subInfo.getUrl().indexOf('?') >= 0)
					subInfo.setUrl(subInfo.getUrl() + "&fromMethod=" + "cpPortlet");
				else {
					subInfo.setUrl(subInfo.getUrl() + "?fromMethod=" + "cpPortlet");
				}
				coreBaseList.add(subInfo);
			}
		}
		return coreBaseList;
	}
}