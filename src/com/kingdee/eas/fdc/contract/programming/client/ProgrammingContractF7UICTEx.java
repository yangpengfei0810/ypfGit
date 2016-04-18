package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;

public class ProgrammingContractF7UICTEx extends ProgrammingContractF7UI {
	private static final long serialVersionUID = 5797573515967474968L;
	private static String Key_totalSplitAmt="totalSplitAmount";
	public ProgrammingContractF7UICTEx() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		//隐藏掉控制金额、控制余额
		tblMain.getColumn("controlAmount").getStyleAttributes().setHided(true);
		tblMain.getColumn("controlBalance").getStyleAttributes().setHided(true);
		tblMain.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("balance").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		IColumn col=tblMain.addColumn();//增加累计分配列
//		col.setKey(Key_totalSplitAmt);
		tblMain.getColumn(Key_totalSplitAmt).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(Key_totalSplitAmt).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		tblMain.getHeadRow(0).getCell(Key_totalSplitAmt).setValue("累计分配");
	}
	
	public ProgrammingContractInfo getData() throws Exception {
		 ProgrammingContractInfo info = null;
	        String id = getSelectedId();
	        if(id == null)
	        {
	            return null;
	        } else
	        {
	            EntityViewInfo view = new EntityViewInfo();
	            view.getSelector().add("id");
	            view.getSelector().add("name");
	            view.getSelector().add("longNumber");
	            view.getSelector().add("amount");
	            view.getSelector().add("number");
	            view.getSelector().add("controlAmount");
	            view.getSelector().add("balance");
	            view.getSelector().add("controlBalance");
	            view.getSelector().add("totalSplitAmount");
	            view.getSelector().add("project.id");
	            view.getSelector().add("project.isEnabled");
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("id", id));
	            view.setFilter(filter);
	            info = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(view).get(0);
	            disposeUIWindow();
	            return info;
	        }
	}
	
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}
//	public void actionQuery_actionPerformed(ActionEvent e)
//    throws Exception
//{
//    super.actionQuery_actionPerformed(e);
//    Map map = getUIContext();
//    if(map.get("view") != null)
//    {
//        EntityViewInfo entityView = (EntityViewInfo)map.get("view");
//        EntityViewInfo viewInfo = getMainQuery();
//        viewInfo.setFilter(entityView.getFilter());
//    }
//}
	
	private String getSelectedId()
    {
        int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
        if(actRowIdx < 0)
            return null;
        if(tblMain.getCell(actRowIdx, "id").getValue() != null)
            return tblMain.getCell(actRowIdx, "id").getValue().toString();
        else
            return null;
    }
}
