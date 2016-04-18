/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.CtrlUtil.Color;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.query.server.SQLDataAccessFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class ContractCostSplitRptUI extends AbstractContractCostSplitRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractCostSplitRptUI.class);
    private String orgFilterString = "";// 树节点变化时加载的过滤条件"组织"
    private String filterString = "";// 树节点变化时加载的过滤条件"工程项目"
    
    public ContractCostSplitRptUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTree();
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	//填充table数据
    	fillTable(orgFilterString);
    	
    	//设置table的格式样式
    	setKDTableStyle();
    }

    //加载树结构
    protected void initTree() throws Exception
	{
	    /*AdminOrgUnitInfo currentAdminUnit = SysContext.getSysContext().getCurrentAdminUnit();//当前组织
	    orgFilterString =  "and curproject.fcontrolunitid='"+currentAdminUnit.getId()+"' ";
//    	OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
//    	OrgUnitInfo orgunit = (OrgUnitInfo) SysContext.getSysContext().getCurrentCompany();
//    	orgFilterString =  "and contract.forgunitid='"+currentOrgUnit.getId()+"' ";
    	
	    DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode(currentAdminUnit.getName());
	    EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit",currentAdminUnit.getId()));
		view.setFilter(filter);
		SorterItemCollection sor = new SorterItemCollection();
		SorterItemInfo sorterItem = new SorterItemInfo("Number");
		sorterItem.setSortType(SortType.DESCEND);
		sor.add(sorterItem);
		view.setSorter(sor);
	    CurProjectCollection curProjectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);//项目
	    Map map = new HashMap();
	    for (int i = 0; i <curProjectCollection.size() ; i++) {
	    	CurProjectInfo curProjectInfo = curProjectCollection.get(i);
	    	DefaultKingdeeTreeNode root1 = null;
	    	if(!curProjectInfo.isIsLeaf()){
	    		root1 = new DefaultKingdeeTreeNode(curProjectInfo.getName());
	    		root.add(root1);
	    		EntityViewInfo viewInfo = new EntityViewInfo();
	    		FilterInfo filterInfo = new FilterInfo();
	    		filterInfo.getFilterItems().add(new FilterItemInfo("parent.id",curProjectInfo.getId()));
	    		filterInfo.getFilterItems().add(new FilterItemInfo("IsLeaf",true));
	    		filterInfo.getFilterItems().add(new FilterItemInfo("isenabled",true));
	    		SorterItemCollection sorinfo = new SorterItemCollection();
	    		SorterItemInfo sorterItemInfo = new SorterItemInfo("Number");
	    		sorterItemInfo.setSortType(SortType.ASCEND);
	    		sorinfo.add(sorterItemInfo);
	    		viewInfo.setSorter(sorinfo);
	    		viewInfo.setFilter(filterInfo);
	    		
	    		CurProjectCollection projectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection(viewInfo);//子项目
	    		for (int j = 0; j < projectCollection.size(); j++) {
	    			CurProjectInfo projectInfo = projectCollection.get(j);
	    			DefaultKingdeeTreeNode root2 = new DefaultKingdeeTreeNode(projectInfo.getName());
		    		root1.add(root2);
		    		root2.setUserObject(projectInfo);
				}
	    		root1.setUserObject(curProjectInfo);
	    	}
		}
	    root.setUserObject(currentAdminUnit);
	    this.treeMain.setModel(new KingdeeTreeModel(root));
	    this.treeMain.getSelectionModel().setSelectionMode(TreeBuilderFactory.DEFAULT_EXPAND_LEVEL);
	    this.treeMain.setSelectionRow(0);
	    treeMain.addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				try {
					selectChanged(e);
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (UuidException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
	    });
	    
	  //过滤当前组织的合同
    	if(currentAdminUnit != null ){
    		Set set = new HashSet();
    		EntityViewInfo viewInfo = new EntityViewInfo();
    		FilterInfo filterInfo = new FilterInfo();
    		CurProjectCollection projectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection("where CU = '"+currentAdminUnit.getId()+"'");
    		for (int i = 0; i < projectCollection.size(); i++) {
    			CurProjectInfo curProjectInfo = projectCollection.get(i);
    			set.add(curProjectInfo.getId());
			}
    		filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id",set,CompareType.INCLUDE));
    		viewInfo.setFilter(filterInfo);
    		this.prmtcontract.setEntityViewInfo(viewInfo);
    	}*/
    	
    	//mod by ypf on 20140305 修改树的展现形式
    	OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
    	orgFilterString =  "and curproject.fcontrolunitid='"+orgUnitInfo.getId()+"' ";
    	
    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.setShowsRootHandles(true);
	}

    //mod by ypf on 20140305 修改树的展现形式
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	selectChanged(e);
    }
    
    private Object treeVal = null;
    private void selectChanged(TreeSelectionEvent e) throws BOSException, UuidException, Exception{
    	
    	if (e.getNewLeadSelectionPath() != null) {
			DefaultKingdeeTreeNode selectedNode = (DefaultKingdeeTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
			Object object = selectedNode.getUserObject();
			if(object != null){
				treeVal =  object; 
				if(object instanceof CurProjectInfo ){
					CurProjectInfo projectInfo = (CurProjectInfo) object;
					filterString =orgFilterString + " and curproject.flongNumber like '"+projectInfo.getLongNumber()+"%' ";
					this.tblMain.refresh();
					//过滤当前组织的合同
			    	if(projectInfo != null ){
			    		EntityViewInfo viewInfo = new EntityViewInfo();
			    		FilterInfo filterInfo = new FilterInfo();
			    		filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id",projectInfo.getId()));
			    		viewInfo.setFilter(filterInfo);
			    		this.prmtcontract.setEntityViewInfo(viewInfo);
			    	}
				}
				else if(object instanceof OrgStructureInfo ){
					OrgStructureInfo orgUnitInfo = (OrgStructureInfo) object;
					filterString = "and curproject.fcontrolunitid='"+orgUnitInfo.getUnit().getId()+"' ";
					this.tblMain.refresh();
					//过滤当前组织的合同
			    	if(orgUnitInfo != null ){	
			    		Set set = new HashSet();
			    		EntityViewInfo viewInfo = new EntityViewInfo();
			    		FilterInfo filterInfo = new FilterInfo();
			    		CurProjectCollection projectCollection = CurProjectFactory.getRemoteInstance().getCurProjectCollection("where CU = '"+orgUnitInfo.getId()+"'");
			    		for (int i = 0; i < projectCollection.size(); i++) {
			    			CurProjectInfo curProjectInfo = projectCollection.get(i);
			    			set.add(curProjectInfo.getId());
			    		}
			    		filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id",set));
			    		viewInfo.setFilter(filterInfo);
			    		this.prmtcontract.setEntityViewInfo(viewInfo);
			    	}
				}
			}
		}
    	
    	this.tblMain.refresh();
    	fillTable(filterString);
    	
    }

    protected void btnview_actionPerformed(ActionEvent e) throws Exception {
    	Object valtype = this.prmtcontractType.getValue();
    	Object valnumber = this.prmtcontract.getValue();
    	
    	StringBuffer filterInfo = new StringBuffer();
    	filterInfo.append(filterString);
    	if(valtype != null){
    		ContractTypeInfo typeInfo = (ContractTypeInfo) valtype;
    		filterInfo.append(" and contractType.Fid = '"+typeInfo.getId()+"'");
    	}
    	if(valnumber != null){
    		ContractBillInfo billInfo = (ContractBillInfo) valnumber;
    		filterInfo.append(" and contract.Fid = '"+billInfo.getId()+"'");
    	}
    	String filter = filterInfo.toString();
    	this.tblMain.removeRows();
    	fillTable(filter);
    }
    
    //填充表格
    private void fillTable(String filter)
    {
    	String sql=
    		"SELECT                                                                                  "+   
	    	"                                                                                        "+   
	    	"contractType.FName_l2 AS CONTRACTTYPENAME,                                              "+
	    	"contract.FState AS CONTRACTSTATE,                                                       "+   
	    	"contract.FNumber AS CONTRACTNUMBER,                                                     "+   
	    	"contract.FName AS CONTRACTNAME,                                                         "+   
	    	"contract.FAmount AS AMOUNT,                                                             "+
	    	"contract.FCostProperty AS CONTRACTCOSTPROPERTY,                                         "+   
	    	"contract.FHasSettled AS HASSETTLED,                                                     "+ 
	    	"contract.FSettleAmt AS SETTLEAMT,                                                       "+ 
	    	"contract.FContractPropert AS CONTRACTPROPERT,                                           "+ 
	    	"contract.FIsCostSplit AS ISCOSTSPLIT,                                                   "+   
	    	"partB.FName_l2 AS PARTB,                                                                "+   
	    	"developer.FName_l2 AS LANDDEVELOPER,                                                    "+
	    	"partC.FName_l2 AS PARTC,                                                                "+   
	    	"ISNULL(costSplit.FState, conNoCostSplit.FState) AS COSTSPLITSTATE,                      "+
	    	"ISNULL(ISNULL(costSplit.FIsConfirm, conNoCostSplit.FIsConfirm), 0) AS ISCONFIRM,        "+   
	    	"spEntry.FSplitScale AS spEntrySPLITSCALE,                                               "+
//	    	"spEntry.FSplitType AS spEntrySPLITTYPE,                                                 "+
	    	"apportionType.fname_l2 AS spEntrySPLITTYPE,                                               "+
	    	"spEntry.FAmount AS spEntryAMOUNT,                                                       "+
	    	"spEntry.FIsAddlAccount AS spEntryISADDLACCOUNT,                                         "+
	    	"spEntry.FLevel AS spEntryLEVEL,                                                         "+
	    	"spEntry.FIsLeaf AS spEntryISLEAF,                                                       "+
	    	"spEntry.FIndex AS spEntryINDEX,                                                         "+
	    	"productType.FNumber AS PRODUCTTYPENUMBER,                                               "+
	    	"productType.FName_l2 AS PRODUCTTYPENAME,                                                "+
	    	"costAccount.FLongNumber AS COSTACCOUNTLONGNUMBER,                                       "+
	    	"costAccount.FDisplayName_l2 AS COSTACCOUNTDISPLAYNAME                                   "+   
	    	"                                                                                        "+   
	    	"FROM T_CON_ContractBill AS contract                                                     "+   
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_FDC_ContractType AS contractType                                      "+
	    	"ON contract.FContractTypeID = contractType.FID                                          "+ 
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_BD_Supplier AS partB                                                  "+ 
	    	"ON partB.FID = contract.FPartBID                                                        "+ 
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_FDC_CurProject AS curproject                                          "+ 
	    	"ON contract.FCurProjectID = curproject.FID                                              "+ 
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_BD_Supplier AS partC                                                  "+ 
	    	"ON contract.FPartCID = partC.FID                                                        "+ 
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_FDC_LandDeveloper AS developer                                        "+
	    	"ON contract.FLandDeveloperID = developer.FID                                            "+
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_CON_ContractCostSplit AS costSplit                                    "+ 
	    	"ON contract.FID = costSplit.FContractBillID AND costSplit.FIsInvalid = 0                "+ 
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_CON_ConNoCostSplit AS conNoCostSplit                                  "+ 
	    	"ON contract.FID = conNoCostSplit.FContractBillID AND conNoCostSplit.FIsInvalid = 0      "+  
	    	"                                                                                        "+
	    	"LEFT OUTER JOIN T_CON_ContractCostSplitEntry AS spEntry                                 "+ 
	    	"ON costSplit.FID = spEntry.FParentID                                                    "+
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_FDC_ProductType AS productType                                        "+ 
	    	"ON spEntry.FProductID = productType.FID                                                 "+
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_FDC_CostAccount AS costAccount                                        "+ 
	    	"ON spEntry.FCostAccountID = costAccount.FID                                             "+
	    	"                                                                                        "+   
	    	"LEFT OUTER JOIN T_FDC_ApportionType AS apportionType                                    "+ 
	    	"ON spEntry.FIdxApportionID = apportionType.FID                                          "+
	    	"                                                                                        "+   
	    	"WHERE                                                                                   "+
	    	"1=1 	                    															 "+
	    	"and contract.FState='4AUDITTED'                                                         "+   
	    	"and (costSplit.FSplitState='3ALLSPLIT' or costSplit.FSplitState='2PARTSPLIT')           "+ 
	    	"                                                                                        ";  
	        
    	    if(filter != null && !filter.equals(""))
    	    {
    	    	sql += filter;
    	    }
    	
	    	sql +="ORDER BY contract.FNumber ASC,spEntryINDEX ASC                                     ";
    	
	    	System.out.println("--------sql:"+sql);
    	try {
			IRowSet rowSet = SQLDataAccessFactory.getRemoteInstance().getRowSet(sql);
			try {
				int i=0;
				FDCUtil util = new FDCUtil();
				while(rowSet.next())
				{
					tblMain.addRow(i);
//					tblMain.getCell(i, "CONTRACTTYPENAME").setValue(rowSet.getString("CONTRACTTYPENAME"));//合同类型
//					tblMain.getCell(i, "CONTRACTSTATE").setValue(FDCBillStateEnum.getEnum(rowSet.getString("CONTRACTSTATE")));//合同状态
//					tblMain.getCell(i, "CONTRACTNUMBER").setValue(rowSet.getString("CONTRACTNUMBER"));//合同编号
//					tblMain.getCell(i, "CONTRACTNAME").setValue(rowSet.getString("CONTRACTNAME"));//合同名称
//					tblMain.getCell(i, "AMOUNT").setValue(rowSet.getString("AMOUNT"));//合同金额
//					tblMain.getCell(i, "CONTRACTCOSTPROPERTY").setValue(CostPropertyEnum.getEnum(rowSet.getString("CONTRACTCOSTPROPERTY")));//造价性质
//					tblMain.getCell(i, "HASSETTLED").setValue(rowSet.getString("HASSETTLED").equals("1")?true:false);//是否已结算
//					tblMain.getCell(i, "SETTLEAMT").setValue(util.getDecimal(rowSet.getString("SETTLEAMT")).compareTo("0.00")==0?"":util.getDecimal(rowSet.getString("SETTLEAMT")));//结算金额
//					tblMain.getCell(i, "CONTRACTPROPERT").setValue(ContractPropertyEnum.getEnum(rowSet.getString("CONTRACTPROPERT")));//合同性质
//					tblMain.getCell(i, "ISCOSTSPLIT").setValue(rowSet.getString("ISCOSTSPLIT").equals("1")?true:false);//是否成本拆分
//					tblMain.getCell(i, "PARTB").setValue(rowSet.getString("PARTB"));//签约甲方
//					tblMain.getCell(i, "LANDDEVELOPER").setValue(rowSet.getString("LANDDEVELOPER"));//签约乙方
//					tblMain.getCell(i, "PARTC").setValue(rowSet.getString("PARTC"));//签约丙方
					tblMain.getCell(i, "COSTSPLITSTATE").setValue(FDCBillStateEnum.getEnum(rowSet.getString("COSTSPLITSTATE")));//拆分状态
					tblMain.getCell(i, "ISCONFIRM").setValue(rowSet.getString("ISCONFIRM").equals("1")?true:false);//是否确认
//					tblMain.getCell(i, "spEntrySPLITSCALE").setValue(util.getDecimal(rowSet.getString("spEntrySPLITSCALE")).compareTo("0.00")==0?"":util.getDecimal(rowSet.getString("spEntrySPLITSCALE")));//拆分比例
					tblMain.getCell(i, "spEntrySPLITSCALE").setValue(rowSet.getString("spEntrySPLITSCALE"));//拆分比例
					tblMain.getCell(i, "spEntrySPLITTYPE").setValue(rowSet.getString("spEntrySPLITTYPE"));//拆分类型
					tblMain.getCell(i, "spEntryAMOUNT").setValue(rowSet.getString("spEntryAMOUNT"));//归属金额
					tblMain.getCell(i, "spEntryISADDLACCOUNT").setValue(rowSet.getString("spEntryISADDLACCOUNT"));//是否附加科目
					tblMain.getCell(i, "spEntryLEVEL").setValue(rowSet.getString("spEntryLEVEL"));//级次
					tblMain.getCell(i, "spEntryISLEAF").setValue(rowSet.getString("spEntryISLEAF").equals("1")?true:false);//是否叶子
					tblMain.getCell(i, "spEntryINDEX").setValue(rowSet.getString("spEntryINDEX"));//下标
					tblMain.getCell(i, "PRODUCTTYPENUMBER").setValue(rowSet.getString("PRODUCTTYPENUMBER"));//产品类型编号
					tblMain.getCell(i, "PRODUCTTYPENAME").setValue(rowSet.getString("PRODUCTTYPENAME"));//产品类型名称
					tblMain.getCell(i, "COSTACCOUNTLONGNUMBER").setValue(rowSet.getString("COSTACCOUNTLONGNUMBER"));//归属成本科目编码
					tblMain.getCell(i, "COSTACCOUNTDISPLAYNAME").setValue(rowSet.getString("COSTACCOUNTDISPLAYNAME"));//归属成本科目名称
					
					//表头只显示一条记录，通过分录的下标来对齐
					if(rowSet.getString("spEntryINDEX").equals("1"))
					{
						tblMain.getCell(i, "CONTRACTTYPENAME").setValue(rowSet.getString("CONTRACTTYPENAME"));//合同类型
						tblMain.getCell(i, "CONTRACTSTATE").setValue(FDCBillStateEnum.getEnum(rowSet.getString("CONTRACTSTATE")));//合同状态
						tblMain.getCell(i, "CONTRACTNUMBER").setValue(rowSet.getString("CONTRACTNUMBER"));//合同编号
						tblMain.getCell(i, "CONTRACTNAME").setValue(rowSet.getString("CONTRACTNAME"));//合同名称
						tblMain.getCell(i, "AMOUNT").setValue(rowSet.getString("AMOUNT"));//合同金额
						tblMain.getCell(i, "CONTRACTCOSTPROPERTY").setValue(CostPropertyEnum.getEnum(rowSet.getString("CONTRACTCOSTPROPERTY")));//造价性质
						tblMain.getCell(i, "HASSETTLED").setValue(rowSet.getString("HASSETTLED").equals("1")?true:false);//是否已结算
						tblMain.getCell(i, "SETTLEAMT").setValue(util.getDecimal(rowSet.getString("SETTLEAMT")).compareTo("0.00")==0?"":util.getDecimal(rowSet.getString("SETTLEAMT")));//结算金额
						tblMain.getCell(i, "CONTRACTPROPERT").setValue(ContractPropertyEnum.getEnum(rowSet.getString("CONTRACTPROPERT")));//合同性质
						tblMain.getCell(i, "ISCOSTSPLIT").setValue(rowSet.getString("ISCOSTSPLIT").equals("1")?true:false);//是否成本拆分
						tblMain.getCell(i, "PARTB").setValue(rowSet.getString("PARTB"));//签约甲方
						tblMain.getCell(i, "LANDDEVELOPER").setValue(rowSet.getString("LANDDEVELOPER"));//签约乙方
						tblMain.getCell(i, "PARTC").setValue(rowSet.getString("PARTC"));//签约丙方
						
						tblMain.getRow(i).getStyleAttributes().setBackground(java.awt.Color.lightGray);
					}
					
					i++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    
    private void setKDTableStyle()
    {
    	//给自定义KDTable的列设置表头自动排序
    	for (int i = 0; i < tblMain.getColumnCount(); i++) {
    		tblMain.getColumn(i).setSortable(true);
		}
    	KDTSortManager sm = new KDTSortManager(tblMain);
    	sm.setSortAuto(true);
    	
    	//设置数值列居右显示
    	tblMain.getColumn("AMOUNT").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	tblMain.getColumn("SETTLEAMT").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	tblMain.getColumn("spEntryAMOUNT").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	tblMain.getColumn("spEntrySPLITSCALE").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	//设置数值列千分位及两位小数显示
    	tblMain.getColumn("AMOUNT").getStyleAttributes().setNumberFormat("%r-{#,##0.00*}f");
//    	tblMain.getColumn("SETTLEAMT").getStyleAttributes().setNumberFormat("%r-{#,##0.00*}f");
    	tblMain.getColumn("spEntryAMOUNT").getStyleAttributes().setNumberFormat("%r-{#,##0.00*}f");
    	tblMain.getColumn("spEntrySPLITSCALE").getStyleAttributes().setNumberFormat("%r-{#,##0.00*}f");
    	
    	//设置隐藏列
    	tblMain.getColumn("CONTRACTSTATE").getStyleAttributes().setHided(true);
    	tblMain.getColumn("COSTSPLITSTATE").getStyleAttributes().setHided(true);
    	tblMain.getColumn("spEntryISADDLACCOUNT").getStyleAttributes().setHided(true);
    	tblMain.getColumn("spEntryLEVEL").getStyleAttributes().setHided(true);
    	tblMain.getColumn("ISCOSTSPLIT").getStyleAttributes().setHided(true);
    	tblMain.getColumn("spEntryISLEAF").getStyleAttributes().setHided(true);
    	tblMain.getColumn("spEntryINDEX").getStyleAttributes().setHided(true);
    	tblMain.getColumn("PRODUCTTYPENUMBER").getStyleAttributes().setHided(true);
    	
    	tblMain.getColumn("CONTRACTCOSTPROPERTY").getStyleAttributes().setHided(true);
    	tblMain.getColumn("HASSETTLED").getStyleAttributes().setHided(true);
    	tblMain.getColumn("SETTLEAMT").getStyleAttributes().setHided(true);
    	tblMain.getColumn("ISCONFIRM").getStyleAttributes().setHided(true);
    }
}