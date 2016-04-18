/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.UIHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.master.account.AccountTools;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.INewOrgViewFacade;
import com.kingdee.eas.basedata.org.IOrgUnit;
import com.kingdee.eas.basedata.org.NewOrgViewFacadeFactory;
import com.kingdee.eas.basedata.org.OUPartFIFactory;
import com.kingdee.eas.basedata.org.OUPartFIInfo;
import com.kingdee.eas.basedata.org.OrgF7InnerUtils;
import com.kingdee.eas.basedata.org.OrgStructureCollection;
import com.kingdee.eas.basedata.org.OrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.CompanyPromptBox;
import com.kingdee.eas.basedata.org.client.f7.CompanyF7;
import com.kingdee.eas.basedata.org.stepadd.StepAddParamInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ObjectValueRenderImpl;
import com.kingdee.eas.fdc.finance.INewOldOrgRelation;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationCollection;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationFactory;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class NewOldOrgRelationUI extends AbstractNewOldOrgRelationUI
{
    private static final Logger logger = CoreUIObject.getLogger(NewOldOrgRelationUI.class);
	private NewOldOrgRelationCollection newOldOrgRelationCollection;
    private boolean isModify;
    private DefaultKingdeeTreeNode lastSelectNode;
    private String currentSelectNode;
    private boolean isFirstIn;
    /**
     * output class constructor
     */
    public NewOldOrgRelationUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    @Override
    public void onLoad() throws Exception {
    	if(!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals("00000000-0000-0000-0000-000000000000CCE7AED4")){
    		this.disposeUIWindow();
    	}
    	super.onLoad();
    	setTblMainEditStatus();
    	SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("oldCompanyOrg.*"));
        sic.add(new SelectorItemInfo("newCompanyOrg.*"));
        EntityViewInfo view=new EntityViewInfo();
        view.setSelector(sic);
    	NewOldOrgRelationCollection newCollection = null;
        INewOldOrgRelation noor=NewOldOrgRelationFactory.getRemoteInstance();
    	newCollection=noor.getNewOldOrgRelationCollection(view);
    	for(int i=0;i<newCollection.size();i++){
    		NewOldOrgRelationInfo info=newCollection.get(i);
    		IRow row=tblMain.addRow();
    		ICell cellValueAttribute = row.getCell("oldCompanyOrg");
            cellValueAttribute.getStyleAttributes().setLocked(false);
            KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(createOrgViewF7());
            cellValueAttribute.setEditor(ceEnable);
            ObjectValueRenderImpl orgAvr = new ObjectValueRenderImpl();
            orgAvr.setFormat(new BizDataFormat("$number$"));
     		tblMain.getColumn("oldCompanyOrg").setRenderer(orgAvr);
            cellValueAttribute.setValue(info.getOldCompanyOrg());
             
            cellValueAttribute = row.getCell("newCompanyOrg");
            cellValueAttribute.getStyleAttributes().setLocked(false);
            ceEnable = new KDTDefaultCellEditor(createOrgViewF7());
            cellValueAttribute.setEditor(ceEnable);
            tblMain.getColumn("newCompanyOrg").setRenderer(orgAvr);
            cellValueAttribute.setValue(info.getNewCompanyOrg());
            row.getCell("oldCompanyName").getStyleAttributes().setLocked(true);
            row.getCell("newCompanyName").getStyleAttributes().setLocked(true);
            row.getCell("remark").getStyleAttributes().setLocked(false);
    		
    		
    		row.getCell("id").setValue(info.getId());
    		row.getCell("oldCompanyName").setValue(info.getOldCompanyOrg().getDisplayName());
    		if(info.getNewCompanyOrg()!=null)
    			row.getCell("newCompanyName").setValue(info.getNewCompanyOrg().getDisplayName());
    		row.getCell("remark").setValue(info.getRemark());
    	}
    	actionImportTemp.putValue("SmallIcon", EASResource.getIcon("imgTbtn_importcyclostyle"));
    }

    private void setTblMainEditStatus()
    {
        tblMain.setEditable(btnSave.isEnabled());
        actionImportTemp.setEnabled(btnAddLine.isEnabled());
    }
    
    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	verifyInput(e);
    	INewOldOrgRelation newOldOrgRelation=NewOldOrgRelationFactory.getRemoteInstance();
    	newOldOrgRelation.delete(new FilterInfo());
    	for(int i = 0; i < tblMain.getRowCount(); i++)
        {
            NewOldOrgRelationInfo newOldOrgRelationInfo = new NewOldOrgRelationInfo();
            newOldOrgRelationInfo.setOldCompanyOrg((CompanyOrgUnitInfo)tblMain.getRow(i).getCell("oldCompanyOrg").getValue());
            newOldOrgRelationInfo.setNewCompanyOrg((CompanyOrgUnitInfo)tblMain.getRow(i).getCell("newCompanyOrg").getValue());
            if(tblMain.getRow(i).getCell("id").getValue() != null)
            	newOldOrgRelationInfo.setId(BOSUuid.read(tblMain.getRow(i).getCell("id").getValue().toString()));
            if(tblMain.getRow(i).getCell("remark").getValue() != null)
            {
            	newOldOrgRelationInfo.setRemark(tblMain.getRow(i).getCell("remark").getValue().toString());
            }
            newOldOrgRelation.addnew(newOldOrgRelationInfo);
        }

    	
    	
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	verifyInput(e);
    	NewOldOrgRelationCollection newOldOrgRelationColl=new NewOldOrgRelationCollection();
    	INewOldOrgRelation newOldOrgRelation=NewOldOrgRelationFactory.getRemoteInstance();
    	
    	for(int i = 0; i < tblMain.getRowCount(); i++)
        {
            NewOldOrgRelationInfo newOldOrgRelationInfo = new NewOldOrgRelationInfo();
            newOldOrgRelationInfo.setOldCompanyOrg((CompanyOrgUnitInfo)tblMain.getRow(i).getCell("oldCompanyOrg").getValue());
            newOldOrgRelationInfo.setNewCompanyOrg((CompanyOrgUnitInfo)tblMain.getRow(i).getCell("newCompanyOrg").getValue());
            if(tblMain.getRow(i).getCell("id").getValue() != null)
            	newOldOrgRelationInfo.setId(BOSUuid.read(tblMain.getRow(i).getCell("id").getValue().toString()));
            if(tblMain.getRow(i).getCell("remark").getValue() != null)
            {
            	newOldOrgRelationInfo.setRemark(tblMain.getRow(i).getCell("remark").getValue().toString());
            }
            newOldOrgRelationColl.add(newOldOrgRelationInfo);
        }
    	newOldOrgRelation.submitAll(newOldOrgRelationColl);
    	
    	MsgBox.showInfo(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Save_Successed"));
        this.isModify = false;
    }
    

    private KDBizPromptBox createOrgViewF7() {
	   KDBizPromptBox f7 = new KDBizPromptBox();
	  
	   f7.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
 
       EntityViewInfo evi = new EntityViewInfo();
       CompanyF7 comp = new CompanyF7(this);
       f7.setSelector(comp);
       f7.setEntityViewInfo(evi);
       f7.setDisplayFormat("$number$");
       f7.setEditFormat("$number$");
       f7.setCommitFormat("$number$");
       return f7;
	}
    
    @Override
    protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
    	Object obj=e.getValue();
    	if(obj instanceof CompanyOrgUnitInfo ){
    		CompanyOrgUnitInfo company=(CompanyOrgUnitInfo)obj;
    		if( e.getColIndex()==2){
    			tblMain.getCell(e.getRowIndex(), "oldCompanyName").setValue(company.getDisplayName());
    		}else if( e.getColIndex()==4){
    			tblMain.getCell(e.getRowIndex(), "newCompanyName").setValue(company.getDisplayName());
    		}
    	}
    }
    
    
    @Override
    protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
    	Object obj=e.getValue();
    	if(obj ==null && (e.getColIndex()==2||e.getColIndex()==4)){
    		if( e.getColIndex()==2){
    			tblMain.getCell(e.getRowIndex(), "oldCompanyName").setValue(null);
    		}else if( e.getColIndex()==4){
    			tblMain.getCell(e.getRowIndex(), "newCompanyName").setValue(null);
    		}
    	}
    	if(obj instanceof CompanyOrgUnitInfo && (e.getColIndex()==2||e.getColIndex()==4)){
    		CompanyOrgUnitInfo company=(CompanyOrgUnitInfo)obj;
    		if( e.getColIndex()==2){
    			for(int i=0;i<tblMain.getRowCount();i++){
        			if(company.equals(tblMain.getRow(i).getCell("oldCompanyOrg").getValue())&& e.getRowIndex()!=i){
        				MsgBox.showInfo("旧帐套组织已存在，请重新选择！");
        				tblMain.getCell(e.getRowIndex(), "oldCompanyOrg").setValue(null);
        				tblMain.getCell(e.getRowIndex(), "oldCompanyName").setValue(null);
        				return;
        			}
        		}
    			tblMain.getCell(e.getRowIndex(), "oldCompanyName").setValue(company.getDisplayName());
    		}else if( e.getColIndex()==4){
    			tblMain.getCell(e.getRowIndex(), "newCompanyName").setValue(company.getDisplayName());
    		}
    	}
    }
    
    
    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
    	 tblMain.checkParsed();
         int index = -1;
         index = tblMain.getRowCount();
         IRow row;
         if(index != -1)
             row = tblMain.addRow(index);
         else
             row = tblMain.addRow();
         ICell cellValueAttribute = row.getCell("oldCompanyOrg");
         cellValueAttribute.getStyleAttributes().setLocked(false);
         cellValueAttribute.setValue(null);
         KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(createOrgViewF7());
         cellValueAttribute.setEditor(ceEnable);
         cellValueAttribute = row.getCell("newCompanyOrg");
         cellValueAttribute.getStyleAttributes().setLocked(false);
         cellValueAttribute.setValue(null);
         ceEnable = new KDTDefaultCellEditor(createOrgViewF7());
         cellValueAttribute.setEditor(ceEnable);
        
         row.getCell("oldCompanyName").getStyleAttributes().setLocked(true);
         row.getCell("newCompanyName").getStyleAttributes().setLocked(true);
         row.getCell("remark").getStyleAttributes().setLocked(false);
       //  isModify = true;
    }


	/**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    	 int i = -1;
         i = tblMain.getSelectManager().getActiveRowIndex();
         if(i == -1)
         {
             MsgBox.showError(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Insert"));
             return;
         } else
         {
             IRow row = tblMain.addRow(i);
             ICell cellValueAttribute = row.getCell("oldCompanyOrg");
             cellValueAttribute.getStyleAttributes().setLocked(false);
             cellValueAttribute.setValue(null);
             KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(createOrgViewF7());
             cellValueAttribute.setEditor(ceEnable);
             cellValueAttribute = row.getCell("newCompanyOrg");
             cellValueAttribute.getStyleAttributes().setLocked(false);
             cellValueAttribute.setValue(null);
             ceEnable = new KDTDefaultCellEditor(createOrgViewF7());
             cellValueAttribute.setEditor(ceEnable);
         
             row.getCell("oldCompanyName").getStyleAttributes().setLocked(true);
             row.getCell("newCompanyName").getStyleAttributes().setLocked(true);
             row.getCell("remark").getStyleAttributes().setLocked(false);
      //       isModify = true;
             return;
         }
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    	int i = -1;
        i = tblMain.getSelectManager().getActiveRowIndex();
        if(i == -1)
        {
            MsgBox.showError(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Selected_Delete"));
            return;
        }
       // checkRef(tblMain);
        tblMain.removeRow(i);
        if(tblMain.getRowCount() <= 0 && newOldOrgRelationCollection.size() <= 0)
        {
            btnSave.setEnabled(false);
        } else
        {
            isModify = true;
            btnSave.setEnabled(true);
        }
    }
    
    @Override
    public void actionImportTemp_actionPerformed(ActionEvent e)
    		throws Exception {
    	EntityViewInfo viewInfo = new EntityViewInfo();
        FilterInfo filterInfo = new FilterInfo();
        
        // ZHDC!001DC  ZHDC!002WY  ZHDC!003WY  ZHDC!004DC   ZHDC!009QT
        filterInfo.getFilterItems().add(new FilterItemInfo("longNumber","ZHDC!001DC%",CompareType.NOTLIKE));
        filterInfo.getFilterItems().add(new FilterItemInfo("longNumber","ZHDC!002WY%",CompareType.NOTLIKE));
        filterInfo.getFilterItems().add(new FilterItemInfo("longNumber","ZHDC!003WY%",CompareType.NOTLIKE));
        filterInfo.getFilterItems().add(new FilterItemInfo("longNumber","ZHDC!004DC%",CompareType.NOTLIKE));
        filterInfo.getFilterItems().add(new FilterItemInfo("longNumber","ZHDC!009QT%",CompareType.NOTLIKE));
        filterInfo.setMaskString(" #0 and #1 and #2 and #3 and #4 ");
        viewInfo.setFilter(filterInfo);
        viewInfo.getSorter().add(new SorterItemInfo("longNumber"));
        
    	ICompanyOrgUnit iCompany = CompanyOrgUnitFactory.getRemoteInstance();
        CompanyOrgUnitCollection companyCol = iCompany.getCompanyOrgUnitCollection(viewInfo);
        IFullOrgUnit iOrg=FullOrgUnitFactory.getRemoteInstance();
        if(companyCol.size()>0){
        	tblMain.removeRows();
        }
        for(int i = 0; i < companyCol.size(); i++){
        	CompanyOrgUnitInfo company=companyCol.get(i);
        	FullOrgUnitInfo unitInfo = iOrg.getFullOrgUnitInfo(new ObjectUuidPK(company.getId().toString()));
            OUPartFIInfo fiInfo = unitInfo.getPartFI();
            fiInfo=OUPartFIFactory.getRemoteInstance().getOUPartFIInfo(new ObjectUuidPK(fiInfo.getId().toString()));
            if(unitInfo.getPartFI() == null || fiInfo.getAccountTable() == null || fiInfo.getAccountPeriodType() == null 
            		|| fiInfo.getAccountTable().getId() == null || fiInfo.getAccountPeriodType().getId() == null ){
            	
            }else{
            	IRow row=tblMain.addRow();
				ICell cellValueAttribute = row.getCell("oldCompanyOrg");
				ICell orgNameCell = row.getCell("oldCompanyName");
				cellValueAttribute.getStyleAttributes().setLocked(false);
				KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(createOrgViewF7());
				cellValueAttribute.setEditor(ceEnable);
				cellValueAttribute.setValue(company);
				orgNameCell.setValue(company.getDisplayName());
				cellValueAttribute = row.getCell("newCompanyOrg");
	            cellValueAttribute.getStyleAttributes().setLocked(false);
	            ceEnable = new KDTDefaultCellEditor(createOrgViewF7());
	            cellValueAttribute.setEditor(ceEnable);
	            cellValueAttribute.setValue(null);
	         
	            row.getCell("oldCompanyName").getStyleAttributes().setLocked(true);
	            row.getCell("newCompanyName").getStyleAttributes().setLocked(true);
	            row.getCell("remark").getStyleAttributes().setLocked(false);
            }
                
        }
    }
    
    @Override
    protected void verifyInput(ActionEvent e) throws Exception {
    	 for(int i = 0; i < tblMain.getRowCount(); i++)
         {
             if(tblMain.getRow(i).getCell("oldCompanyOrg").getValue() == null)
             {
                 MsgBox.showError("第"+(i+1)+"行旧帐套组织不允许为空！");
                 this.abort();
             }
             if(tblMain.getRow(i).getCell("newCompanyOrg").getValue() == null)
             {
            	 MsgBox.showError("第"+(i+1)+"行新帐套映射组织不允许为空！");
                 this.abort();
             }
         }
    }

	@Override
	protected IObjectCollection createNewCollection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return NewOldOrgRelationFactory.getRemoteInstance();
	}

}