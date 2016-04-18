/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * **************************************************
 * 类名：SetMappingUI
 * 修改日期：2012-9-11 下午12:46:25
 * 创建人：yangpengfei
 * 修改版本：@version 1.0.0
 * *************************************************
 */
public class SetMappingUI extends AbstractSetMappingUI {
	
	private static final Logger logger = CoreUIObject.getLogger(SetMappingUI.class);
	public SetMappingUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCBaseDataClientUtils.setupUITitle(this, "设置映射名称");
		Map map = getUIContext();
		String id = map.get("prjID").toString();
		String name = map.get("prjName").toString();
		System.out.println("----id:" + id + "    name:" + name);

		txtPrjName.setText(name);
		txtPrjName.setEditable(false);
		txtPrjName.setEnabled(false);
		
		btnAddNew.setVisible(false);
		btnEdit.setVisible(false);
		btnSave.setVisible(false);
		btnReset.setVisible(false);
		btnCopy.setVisible(false);
		btnRemove.setVisible(false);
		btnAttachment.setVisible(false);
		btnPrint.setVisible(false);
		btnPrintPreview.setVisible(false);
		btnFirst.setVisible(false);
		btnPre.setVisible(false);
		btnNext.setVisible(false);
		btnLast.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", getUIContext().get("prjID")));
		view.setFilter(filter);
		CurProjectCollection col;
		try {
			col = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
			CurProjectInfo info = new CurProjectInfo();
			// 取到需要更新附件的对象
			if (col != null && col.size() > 0) {
				info = col.get(0);
				info.setPrjMappingName(txtMappingName.getText());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("prjMappingName");
				try {
					// 更新附件内容
					CurProjectFactory.getRemoteInstance().updatePartial(info,selector);
					MsgBox.showInfo("添加成功");
					this.disposeUIWindow();
				} catch (Exception e1) {
					MsgBox.showInfo("添加失败");
					SysUtil.abort();
				}
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}

	protected IObjectValue createNewData() {
		CurProjectInfo info = new CurProjectInfo();
		info.setDescription("1111");
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

}