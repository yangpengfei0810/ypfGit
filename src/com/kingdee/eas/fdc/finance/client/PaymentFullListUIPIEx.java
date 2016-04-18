package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGFactory;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo;
import com.kingdee.eas.basedata.master.auxacct.IAssistantHG;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgInnerUtils;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.IContractCostSplitEntry;
import com.kingdee.eas.fdc.finance.INewOldAccountRelation;
import com.kingdee.eas.fdc.finance.INewOldOrgRelation;
import com.kingdee.eas.fdc.finance.INewOldRelationFacade;
import com.kingdee.eas.fdc.finance.IPaymentVoucherTemp;
import com.kingdee.eas.fdc.finance.NewOldAccountRelationCollection;
import com.kingdee.eas.fdc.finance.NewOldAccountRelationFactory;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationFactory;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationInfo;
import com.kingdee.eas.fdc.finance.NewOldRelationFacadeFactory;
import com.kingdee.eas.fdc.finance.PaymentVoucherTempCollection;
import com.kingdee.eas.fdc.finance.PaymentVoucherTempFactory;
import com.kingdee.eas.fdc.finance.PaymentVoucherTempInfo;
import com.kingdee.eas.fi.cas.DisposerActionEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.IVoucherEntry;
import com.kingdee.eas.fi.gl.VoucherAssistRecordCollection;
import com.kingdee.eas.fi.gl.VoucherEntryCollection;
import com.kingdee.eas.fi.gl.VoucherEntryFactory;
import com.kingdee.eas.fi.gl.VoucherEntryInfo;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.fi.gl.client.VoucherEditUI;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class PaymentFullListUIPIEx extends PaymentFullListUI {
	private static final Logger logger = CoreUIObject
	.getLogger(PaymentFullListUI.class);
	public PaymentFullListUIPIEx() throws Exception {
		super();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
	}
	
	@Override
	public void onShow() throws Exception {
		super.onShow();
		CompanyOrgUnitInfo company=SysContext.getSysContext().getCurrentFIUnit();
		if(company.getLongNumber().contains("ZHDC!001DC")||company.getLongNumber().contains("ZHDC!002WY")){
			btnNewVoucher.setVisible(false);
			btnBreakVoucher.setVisible(false);
			btnLinkVoucher.setVisible(false);
		}else{
			btnNewVoucher.setVisible(true);
			btnBreakVoucher.setVisible(true);
			btnLinkVoucher.setVisible(true);
		}
		btnNewVoucher.setEnabled(btnVoucher.isEnabled());
		btnNewVoucher.setIcon(EASResource.getIcon("imgTbtn_scvoucher"));
		btnNewVoucher.setText("����������ƾ֤");
	}
	
	@Override
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		btnNewVoucher.setEnabled(btnVoucher.isEnabled());
		btnBreakVoucher.setEnabled(btnDelVoucher.isEnabled());
		btnLinkVoucher.setEnabled(btnVoucher.isEnabled());
		
	}

	/**
	 * ����������ƾ֤
	 * 1�����жϿ����׵���֯��ϵ�Ϳ�Ŀ��ϵ�Ƿ�������
	 * 2������ƾ֤
	 * 3��������֯��ϵ���¿�Ŀ��ϵ�滻����֯���ɿ�Ŀ��������
	 */
	@Override
	protected void btnNewVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		List ids=getSelectedIdValues();
		Set idset=new HashSet();
		int i=0;
		for(i=0;i<ids.size();i++){
			idset.add(ids.get(i));
		}
		CompanyOrgUnitInfo company=SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo vcView=new EntityViewInfo();		
		INewOldOrgRelation newOldOrgRelation=NewOldOrgRelationFactory.getRemoteInstance();
		vcView.setFilter(new FilterInfo());
		vcView.getFilter().getFilterItems().add(new FilterItemInfo("oldCompanyOrg.id",company.getId().toString()));
		vcView.setSelector(new SelectorItemCollection());
		vcView.getSelector().add(new SelectorItemInfo("*"));
		vcView.getSelector().add(new SelectorItemInfo("newCompanyOrg.*"));
		NewOldOrgRelationInfo newOldOrgRelationInfo=newOldOrgRelation.getNewOldOrgRelationCollection(vcView).get(0);
		if(newOldOrgRelationInfo==null||newOldOrgRelationInfo.getNewCompanyOrg()==null||newOldOrgRelationInfo.getNewCompanyOrg().getId()==null){
			MsgBox.showInfo("δ�����¾�������֯ӳ���ϵ��");
			abort();
		}
		PeriodInfo periodInfo=GlUtils.getCurrentPeriod(null, newOldOrgRelationInfo.getNewCompanyOrg().getId().toString());
		
		Map costAccountMap=new HashMap();
		vcView=new EntityViewInfo();
		vcView.setFilter(new FilterInfo());
		vcView.getFilter().getFilterItems().add(new FilterItemInfo("id",idset,CompareType.INCLUDE));
		IPaymentBill payment=PaymentBillFactory.getRemoteInstance();
		PaymentBillCollection paymentBillColl = payment.getPaymentBillCollection(vcView);
		for( i=0;i<paymentBillColl.size();i++){
			String contractId=paymentBillColl.get(i).getContractBillId();
			String curProjectId=paymentBillColl.get(i).getCurProject().getId().toString();
		/*	int month=paymentBillColl.get(i).getBizDate().getMonth();
			int year=paymentBillColl.get(i).getBizDate().getYear();*/
			Date bizDate = paymentBillColl.get(i).getBizDate();
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(bizDate); 
			int month = cal.get(Calendar.MONTH)+1; 
			int year = cal.get(Calendar.YEAR); 
			if(month!=periodInfo.getPeriodNumber()||year!=periodInfo.getPeriodYear()){
				MsgBox.showInfo("�������Ϊ"+paymentBillColl.get(i).getNumber()+"��ҵ������ �ꡢ������������֯��ǰ�ڼ䲻�ܶ�Ӧ,���ܿ���������ƾ֤��");
				abort();
			}
			EntityViewInfo splitEntryView=new EntityViewInfo();
			splitEntryView.setFilter(new FilterInfo());
			splitEntryView.getFilter().getFilterItems().add(new FilterItemInfo("parent.contractBill.id",contractId));
			splitEntryView.getFilter().getFilterItems().add(new FilterItemInfo("parent.curProject.id",curProjectId));
			splitEntryView.getFilter().getFilterItems().add(new FilterItemInfo("splitScale",0,CompareType.GREATER));
			IContractCostSplitEntry conCostSplitEntry=ContractCostSplitEntryFactory.getRemoteInstance();
			//��ȡ��ͬ�������ĳɱ���Ŀ
			ContractCostSplitEntryCollection splitEntryColl=conCostSplitEntry.getContractCostSplitEntryCollection(splitEntryView);
			for(int j=0;j<splitEntryColl.size();j++){
				costAccountMap.put(splitEntryColl.get(j).getCostAccount().getId().toString() ,splitEntryColl.get(j).getAmount());
			}
		}
		Map accountSet=new HashMap();
		Iterator keys=costAccountMap.keySet().iterator();
		for(;keys.hasNext();){
			String costAccountid=keys.next().toString();
			EntityViewInfo caaView=new EntityViewInfo();
			caaView.setFilter(new FilterInfo());
			caaView.getFilter().getFilterItems().add(new FilterItemInfo("costAccount.id",costAccountid));
			ICostAccountWithAccount costAccountWithAccount=CostAccountWithAccountFactory.getRemoteInstance();
			//��ȡ�ɱ���Ŀ��Ӧ��ƿ�Ŀ��ϵ�������ı�ƾ֤�Ŀ�ĿΪ�����׿�Ŀ���õ�
			CostAccountWithAccountInfo cawainfo=costAccountWithAccount.getCostAccountWithAccountCollection(caaView).get(0);
			accountSet.put(costAccountid,cawainfo.getAccount().getId().toString());
		}
		Map noAccountMap=new HashMap();
		Iterator accountIterator=accountSet.keySet().iterator();
		INewOldAccountRelation newOldAccountRelation=NewOldAccountRelationFactory.getRemoteInstance();
		while(accountIterator.hasNext()){
			EntityViewInfo noView=new EntityViewInfo();
			noView.setFilter(new FilterInfo());
			noView.getFilter().getFilterItems().add(new FilterItemInfo("oldCostAccount.id",accountIterator.next()));
			noView.setSelector(new SelectorItemCollection());
			noView.getSelector().add(new SelectorItemInfo("newAccount.*"));
			noView.getSelector().add(new SelectorItemInfo("*"));
			NewOldAccountRelationCollection noAccountRelationColl=newOldAccountRelation.getNewOldAccountRelationCollection(noView);
			if(noAccountRelationColl.size()<1||noAccountRelationColl.get(0).getNewAccount()==null){
				MsgBox.showInfo("���¾����׿�Ŀӳ���ϵ���¾ɿ�Ŀ��Ӧ����ȱʧ��������ƾ֤���ɹ�����Ҫ�Ŀ�Ŀ�ڹ�ϵ���н��в��䣡");
				abort();
			}else{
				noAccountMap.put(noAccountRelationColl.get(0).getOldCostAccount().getId().toString(), noAccountRelationColl.get(0).getNewAccount());
			}
		}
		
		super.actionVoucher_actionPerformed(e);
		
		paymentBillColl = payment.getPaymentBillCollection(vcView);
		IVoucher voucher=VoucherFactory.getRemoteInstance();
		VoucherInfo voucherInfo=paymentBillColl.get(0).getVoucher();
		EntityViewInfo vouView=new EntityViewInfo();
		vouView.setFilter(new FilterInfo());
		vouView.getFilter().getFilterItems().add(new FilterItemInfo("id",voucherInfo.getId().toString()));
		vouView.setSelector(new SelectorItemCollection());
		vouView.getSelector().add(new SelectorItemInfo("*"));
		vouView.getSelector().add(new SelectorItemInfo("entries.*"));
		vouView.getSelector().add(new SelectorItemInfo("entries.assistRecords.*"));
		vouView.getSelector().add(new SelectorItemInfo("entries.assistRecords.assGrp.*"));
		
		//�����滻��˾�����Ƶ�Ԫ
		voucherInfo=voucher.getVoucherCollection(vouView).get(0);
		voucherInfo.setCompany(newOldOrgRelationInfo.getNewCompanyOrg());
		voucherInfo.setCU(newOldOrgRelationInfo.getNewCompanyOrg().getCU());
		
		//voucherInfo.setPeriod(periodInfo);
		VoucherEntryCollection  voucherEntryColl=voucherInfo.getEntries();
		IVoucherEntry voucherEntry=VoucherEntryFactory.getRemoteInstance();
		String accounttableId=null;
		for(i=0;i<voucherEntryColl.size();i++){
			VoucherEntryInfo voucherEntryInfo=voucherEntryColl.get(i);
			AccountViewInfo voucherOldAccount=voucherEntryInfo.getAccount();
			AccountViewInfo voucherNewAccount=null;
			if(voucherOldAccount==null)
				continue;
			Iterator it=accountSet.keySet().iterator();
			for(;it.hasNext();){
				String itValue=it.next().toString();
				if(accountSet.get(itValue).equals(voucherOldAccount.getId().toString())){
					voucherNewAccount=(AccountViewInfo)noAccountMap.get(itValue);
					accounttableId=voucherNewAccount.getAccountTableID().getId().toString();
				}
			}
			if(voucherNewAccount==null){
				EntityViewInfo eView=new EntityViewInfo();
				eView.setFilter(new FilterInfo());
				eView.getFilter().getFilterItems().add(new FilterItemInfo("accountTableID.id",accounttableId));
				eView.getFilter().getFilterItems().add(new FilterItemInfo("companyId.id",newOldOrgRelationInfo.getNewCompanyOrg().getId()));
				eView.getFilter().getFilterItems().add(new FilterItemInfo("number","1002.01.01"));
				eView.setSelector(new SelectorItemCollection());
				eView.getSelector().add(new SelectorItemInfo("*"));
				eView.getSelector().add(new SelectorItemInfo("caa.*"));
				voucherNewAccount=AccountViewFactory.getRemoteInstance().getAccountViewCollection(eView).get(0);
			}
			
			if(voucherNewAccount==null){
				continue;
			}
			
			//�����滻��ƿ�Ŀ
			voucherEntryInfo.setAccount(voucherNewAccount);
			//VoucherAssistRecord������Ŀ���
			VoucherAssistRecordCollection varColl = voucherEntryInfo.getAssistRecords();
			//mod by ypf on 20141218 ����������ƾ֤ʱ�����ƾ֤��¼��Ӧ�ĸ�����û�����ԣ��п�ָ������.�����ʽ������������֧�жϴ���
			if(varColl.size()>0){
				AssistantHGInfo assHGInfo=varColl.get(0).getAssGrp();
				AsstAccountInfo asstAccountInfo=voucherNewAccount.getCAA();//
				IAssistantHG assistantHG=AssistantHGFactory.getRemoteInstance();
				if(asstAccountInfo==null){
					/*assistantHG.delete(new ObjectUuidPK(assHGInfo.getId().toString()));
					voucherEntryInfo.setAssistRecords(null);*/
				}else{
					//�����滻ƾ֤�����ˣ���Ŀ�����Ƶ�Ԫ�������˺ţ�
					assHGInfo.setAsstAccount(asstAccountInfo);
					assHGInfo.setCU(newOldOrgRelationInfo.getNewCompanyOrg().getCU());
					assHGInfo.setBankAccount(null);
					assHGInfo.setRegion(null);
					
					//mod by ypf on 20141128  ������������ɵ�ƾ֤������Ŀ��һ������
					//����������.������Ŀ.������='N'+������.������Ŀ.������
					if(assHGInfo.getCurProject()!=null && assHGInfo.getCurProject().getId()!=null){
						CurProjectInfo curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(" where id='"+assHGInfo.getCurProject().getId()+"'");;//ȡ�������׵Ĺ�����Ŀ
						//���������׹�����Ŀ�������ȡ�������׵Ĺ�����Ŀ����
						CurProjectInfo curProjectInfoNew = CurProjectFactory.getRemoteInstance().getCurProjectInfo(" where longNumber='N"+curProject.getLongNumber()+"'");
						if(curProjectInfoNew!=null){
							assHGInfo.setCurProject(curProjectInfoNew);//�滻�����׵Ĺ�����Ŀ
						}else{
							assHGInfo.setCurProject(null);//������չ���û���ҵ���������Ϊ�գ��������û��������׵�ƾ֤������в���ά��
						}
					}
					
					//����ƾ֤������
					assistantHG.update(new ObjectUuidPK(assHGInfo.getId().toString()), assHGInfo);
				}
				voucherEntry.update(new ObjectUuidPK(voucherEntryInfo.getId().toString()), voucherEntryInfo);
			}else
			{
				System.out.println("-----------ƾ֤��¼û�и�����-------------");
			}
			
		}
		voucher.update(new ObjectUuidPK(voucherInfo.getId().toString()),voucherInfo);
		
		btnNewVoucher.setEnabled(btnVoucher.isEnabled());
		UIContext uiContext = new UIContext(this);
        uiContext.put("ID", voucherInfo.getId().toString());
        IUIWindow window = getUIWindow();
        uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(VoucherEditUI.class.getName(), uiContext, null, "FINDVIEW");
        //uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(getEditUIName(), uiContext, null, "FINDVIEW");
        uiWindow.show();
	}
	
	@Override
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * �Ͽ�ƾ֤
	 */
	@Override
	protected void btnBreakVoucher_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(currentUserInfo!=null && !currentUserInfo.getNumber().equals("xuying"))
		{
			MsgBox.showWarning("��û�н��иò�����Ȩ�ޣ�����ϵϵͳ����Ա��");
			abort();
		}
		
		INewOldRelationFacade facade=NewOldRelationFacadeFactory.getRemoteInstance();
		IPaymentBill paymentBill=PaymentBillFactory.getRemoteInstance();
		PaymentBillInfo obj=paymentBill.getPaymentBillInfo(new ObjectUuidPK(getSelectedKeyValue()));
		String id=obj.getId().toString();
		if(obj.getVoucher()!=null){
			String voucherID=obj.getVoucher().getId().toString();
			IPaymentVoucherTemp pvTemp=PaymentVoucherTempFactory.getRemoteInstance();
			PaymentVoucherTempInfo pvTempInfo=new PaymentVoucherTempInfo();
			pvTempInfo.setVoucherID(voucherID);
			pvTempInfo.setPaymentID(id);
			//����Ͽ�ƾ֤��id�͸��id
			pvTemp.addnew(pvTempInfo);
			obj.setVoucher(null);
			obj.setFiVouchered(false);
			paymentBill.save(obj);
			obj=paymentBill.getPaymentBillInfo(new ObjectUuidPK(id),getBLSelector());
			
			//����ԭ'ɾ��ƾ֤'����
			facade.dispose( obj, DisposerActionEnum.DEL_VOUCHER);
			refreshList();
		}else{
			MsgBox.showInfo("���û������ƾ֤�����ƾ֤��");
			abort();
		}
	}
	
	/**
	 * ����ƾ֤
	 */
	@Override
	protected void btnLinkVoucher_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(currentUserInfo!=null && !currentUserInfo.getNumber().equals("xuying"))
		{
			MsgBox.showWarning("��û�н��иò�����Ȩ�ޣ�����ϵϵͳ����Ա��");
			abort();
		}
		
		INewOldRelationFacade facade=NewOldRelationFacadeFactory.getRemoteInstance();
		IPaymentBill paymentBill=PaymentBillFactory.getRemoteInstance();
		PaymentBillInfo obj=paymentBill.getPaymentBillInfo(new ObjectUuidPK(getSelectedKeyValue()));
		String id=obj.getId().toString();
		IPaymentVoucherTemp pvTemp=PaymentVoucherTempFactory.getRemoteInstance();
		EntityViewInfo pvtView=new EntityViewInfo();
		pvtView.setFilter(new FilterInfo());
		pvtView.getFilter().getFilterItems().add(new FilterItemInfo("paymentID",id));
		PaymentVoucherTempCollection pvTempColl=pvTemp.getPaymentVoucherTempCollection(pvtView);
		if(pvTempColl.size()>0){
			String voucherid=pvTempColl.get(0).getVoucherID();
			VoucherInfo voucher=(VoucherInfo) VoucherFactory.getRemoteInstance().getVoucherWithoutPermissionCheck(new ObjectUuidPK(voucherid));
			obj=paymentBill.getPaymentBillInfo(new ObjectUuidPK(obj.getId().toString()));
			obj.setVoucher(voucher);
			obj.setFiVouchered(true);
			paymentBill.save(obj);
			obj=paymentBill.getPaymentBillInfo(new ObjectUuidPK(id),getBLSelector());
			
			//����ԭ'����ƾ֤'����
			facade.dispose( obj, DisposerActionEnum.VOUCHER);
			
			//����ҵ������Ӧƾ֤id�����ڹ����ɹ���Ըü�¼����ɾ������
			pvTemp.delete(new ObjectUuidPK(pvTempColl.get(0).getId().toString()));
			refreshList();
		}else{
			MsgBox.showInfo("���û�в����Ͽ�ƾ֤��");
			abort();
		}
		
	}
	
	private SelectorItemCollection getBLSelector()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("payerBank.*"));
        sic.add(new SelectorItemInfo("company.*"));
        sic.add(new SelectorItemInfo("settlementType.ntType.*"));
        sic.add(new SelectorItemInfo("entries.*"));
        sic.add(new SelectorItemInfo("payerAccountBank.*"));
        sic.add(new SelectorItemInfo("payerAccountBank.InnerAcct.*"));
        sic.add(new SelectorItemInfo("payerAccountBank.InnerAcct.clearingHouse.*"));
        sic.add(new SelectorItemInfo("bizType.*"));
        sic.add(new SelectorItemInfo("actRecAccountBank.*"));
        sic.add(new SelectorItemInfo("payerInAcctID.*"));
        sic.add(new SelectorItemInfo("payBillType.*"));
        return sic;
    }
}
