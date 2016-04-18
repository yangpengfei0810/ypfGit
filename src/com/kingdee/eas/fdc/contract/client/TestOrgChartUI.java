package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.uiframe.client.UIFrameUtil;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.HierarchyCollection;
import com.kingdee.eas.basedata.org.HierarchyFactory;
import com.kingdee.eas.basedata.org.HierarchyInfo;
import com.kingdee.eas.basedata.org.IHierarchy;
import com.kingdee.eas.basedata.org.PositionException;
import com.kingdee.eas.basedata.org.client.OrgInnerUtils;
import com.kingdee.eas.basedata.org.client.chart.DrawOrgUnit;
import com.kingdee.eas.basedata.org.client.chart.OrgUnit;
import com.kingdee.eas.basedata.org.client.tree.NewOrgTreeHelper;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.hr.base.client.GetDataDialog;
import com.kingdee.eas.hr.base.client.GetDataTaskAdapter;
import com.kingdee.eas.hr.base.util.HRTreeUtil;
import com.kingdee.eas.hr.org.AdminOrgUnitHierarchyFactory;
import com.kingdee.eas.hr.org.AdminOrgUnitHierarchyInfo;
import com.kingdee.eas.hr.org.IAdminOrgUnitHierarchy;
import com.kingdee.eas.hr.org.client.AbstractOrgChartUI;
import com.kingdee.eas.hr.org.client.AdminHierarchyPromptBox;
import com.kingdee.eas.hr.org.client.AllAdminPromptBox;
import com.kingdee.eas.hr.org.client.OrgCharFilterUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ComponentUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

//add by ypf on 20140625 用来做测试组织架构图形展示的
public class TestOrgChartUI extends AbstractOrgChartUI {
	public TestOrgChartUI() throws Exception {
		node = null;
		treeLoaded = false;
		mapPanel = null;
		hierarchyInfo = null;
		defaultHierarchyInfo = null;
		isRefresh = false;
		commonQueryDialog = null;
		customerPanel = null;
		mainQueryPK = new MetaDataPK("com.kingdee.eas.hr.org.app",
				"PositionTotalReportQuery");
		includeVirtualOrg = true;
		isFirstDefaultQuery = true;
		emptyPanel = null;
	}

	private void buildTree() throws Exception {
		treeLoaded = false;
		treeMain.setShowsRootHandles(true);
		DefaultKingdeeTreeNode rootNode = null;
		if (defaultHierarchyInfo != null
				&& hierarchyInfo.getId().toString().equalsIgnoreCase(
						defaultHierarchyInfo.getId().toString())) {
			rootNode = HRTreeUtil.createAdminTreeNodeByUserRange(false,
					includeVirtualOrg, false, true, null, false);
			if (rootNode != null) {
				DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
				treeMain.setModel(treeModel);
				locateCurrOrg();
				if (!treeLoaded) {
					treeLoaded = true;
					treeMain.setSelectionNode((DefaultKingdeeTreeNode) treeMain
							.getModel().getRoot());
				}
			}
		} else {
			rootNode = NewOrgTreeHelper.createAdminHierarchyTreeNode(
					hierarchyInfo.getId().toString(), false);
			if (rootNode != null) {
				DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
				treeMain.setModel(treeModel);
				treeLoaded = true;
				treeMain.setSelectionNode((DefaultKingdeeTreeNode) treeMain
						.getModel().getRoot());
			}
		}
		if (rootNode == null) {
			treeMain.setModel(new DefaultTreeModel(null));
			if (emptyPanel == null)
				emptyPanel = new KDPanel();
			pnlMain.add(emptyPanel, "right");
		}
	}

	private void locateCurrOrg() throws Exception {
		PersonInfo pInfo = SysContext.getSysContext().getCurrentUserInfo()
				.getPerson();
		if (pInfo == null)
			return;
		IPerson iPerson = PersonFactory.getRemoteInstance();
		AdminOrgUnitInfo adminInfo = iPerson.getPrimaryAdminOrgUnit(pInfo
				.getId());
		treeLoaded = true;
		if (adminInfo == null) {
			treeMain.setSelectionNode((DefaultKingdeeTreeNode) treeMain
					.getModel().getRoot());
			return;
		} else {
			ExpandTreeNode(treeMain, adminInfo.getId().toString());
			return;
		}
	}

	public void ExpandTreeNode(KDTree tree, String orgUnitId) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		findNodeByUnitID((DefaultKingdeeTreeNode) model.getRoot(), orgUnitId);
		if (node != null) {
			javax.swing.tree.TreeNode nodes[] = (javax.swing.tree.TreeNode[]) node
					.getPath();
			TreePath path = new TreePath(nodes);
			tree.setSelectionPath(path);
		} else {
			tree.setSelectionNode((DefaultKingdeeTreeNode) tree.getModel()
					.getRoot());
		}
	}

	private DefaultKingdeeTreeNode findNodeByUnitID(
			DefaultKingdeeTreeNode parentNode, String orgUnitId) {
		if ((parentNode.getUserObject() instanceof AdminOrgUnitInfo)
				&& ((AdminOrgUnitInfo) parentNode.getUserObject()).getId()
						.toString().equals(orgUnitId)) {
			node = parentNode;
		} else {
			for (int i = 0; i < parentNode.getChildCount(); i++)
				findNodeByUnitID((DefaultKingdeeTreeNode) parentNode
						.getChildAt(i), orgUnitId);

		}
		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setButtonDefaultStyl(btnRefresh);
		btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		menuItemRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		btnImportAdmin.setIcon(EASResource.getIcon("imgTbtn_jobselect"));
		btnDeleteAdmin.setIcon(EASResource.getIcon("imgTbtn_delete"));
		btnMoveAdmin.setIcon(EASResource.getIcon("imgTbtn_move"));
		btnMoveAdmin.setBorder(BorderFactory.createLineBorder(new Color(
				10658466)));
		btnMoveAdmin.setFactType(1);
		btnMoveAdmin.setText(null);
		treeView.getControlPane().add(btnMoveAdmin);
		btnQuery.setIcon(EASResource.getIcon("imgTbtn_filter"));
		menuItemQuery.setIcon(EASResource.getIcon("imgTbtn_filter"));
		setButtonDefaultStyl(btnRefresh);
		setButtonDefaultStyl(btnQuery);
		loadHierarchyData();
		actionQuery_actionPerformed(null);
	}

	protected boolean confirmRemove() {
		return MsgBox
				.isYes(MsgBox
						.showConfirm2(
								this,
								EASResource
										.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}

	public void actionDeleteAdmin_actionPerformed(ActionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode selectNode = OrgInnerUtils
				.getSelectTreeNode2(treeMain);
		if (selectNode != null
				&& (selectNode.getUserObject() instanceof AdminOrgUnitInfo)
				&& confirmRemove()) {
			AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) selectNode
					.getUserObject();
			String adminId = adminInfo.getId().toString();
			String hierarchyId = ((HierarchyInfo) cbxMain.getSelectedItem())
					.getId().toString();
			IAdminOrgUnitHierarchy iAH = AdminOrgUnitHierarchyFactory
					.getRemoteInstance();
			iAH.deleteAdminOrgunit(hierarchyId, adminId);
			buildTree();
		}
	}

	public void actionImportAdmin_actionPerformed(ActionEvent e)
			throws Exception {
		AllAdminPromptBox pmt = null;
		Component owner = KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.getActiveWindow();
		java.awt.Window ownerWindow = ComponentUtil.getOwnerWindow(owner);
		if (ownerWindow instanceof Frame)
			pmt = new AllAdminPromptBox((Frame) ownerWindow);
		else if (ownerWindow instanceof Dialog)
			pmt = new AllAdminPromptBox((Dialog) ownerWindow);
		else
			pmt = new AllAdminPromptBox((Frame) null);
		pmt.setIsSingleSelect(false);
		pmt.setModal(true);
		pmt.show();
		if (!pmt.isCanceled()) {
			IAdminOrgUnitHierarchy iAH = AdminOrgUnitHierarchyFactory
					.getRemoteInstance();
			AdminOrgUnitHierarchyInfo ahInfo = new AdminOrgUnitHierarchyInfo();
			ahInfo.setHierarchy((HierarchyInfo) cbxMain.getSelectedItem());
			Object adminColl[] = (Object[]) pmt.getData();
			AdminOrgUnitInfo parentAdminInfo = null;
			int i = 0;
			for (int length = adminColl.length; i < length; i++) {
				ahInfo.setChild((AdminOrgUnitInfo) adminColl[i]);
				DefaultKingdeeTreeNode selectNode = OrgInnerUtils
						.getSelectTreeNode2(treeMain);
				if (selectNode != null) {
					parentAdminInfo = (AdminOrgUnitInfo) selectNode
							.getUserObject();
					ahInfo.setParent(parentAdminInfo);
				} else {
					ahInfo.setParent((AdminOrgUnitInfo) adminColl[i]);
				}
				try {
					iAH.addnew(ahInfo);
				} catch (EASBizException e1) {
					buildTree();
					throw e1;
				}
			}

			MsgBox.showInfo(EASResource.getString(
					"com.kingdee.eas.hr.org.ORGAutoGenerateResource",
					"39_OrgChartUI"));
			buildTree();
		}
	}

	public void actionMoveAdmin_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode selectNode = OrgInnerUtils
				.getSelectTreeNode2(treeMain);
		if (selectNode != null
				&& (selectNode.getUserObject() instanceof AdminOrgUnitInfo)) {
			KDTreeNode rootNode = (KDTreeNode) treeMain.getModel().getRoot();
			AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) selectNode
					.getUserObject();
			if (((AdminOrgUnitInfo) rootNode.getUserObject()).getId()
					.toString().equalsIgnoreCase(adminInfo.getId().toString()))
				throw new PositionException(PositionException.ERROR_PARENT_5);
			HashMap ctx = new HashMap();
			ctx.put("Hierarchy.ID", ((HierarchyInfo) cbxMain.getSelectedItem())
					.getId().toString());
			Frame frame = UIFrameUtil.getCurrMainFrame(this);
			AdminHierarchyPromptBox pmt = new AdminHierarchyPromptBox(frame,
					ctx);
			pmt.setModal(true);
			pmt.show();
			if (!pmt.isCanceled()) {
				Object tt[] = (Object[]) pmt.getData();
				IAdminOrgUnitHierarchy iAH = AdminOrgUnitHierarchyFactory
						.getRemoteInstance();
				String oql = "where child.id ='" + adminInfo.getId()
						+ "' and hierarchy.id='" + hierarchyInfo.getId() + "'";
				if (!iAH.exists(oql))
					return;
				AdminOrgUnitHierarchyInfo ahInfo = iAH
						.getAdminOrgUnitHierarchyInfo(oql);
				ahInfo.setParent((AdminOrgUnitInfo) tt[0]);
				iAH.update(new ObjectUuidPK(ahInfo.getId()), ahInfo);
				MsgBox.showInfo(EASResource.getString(
						"com.kingdee.eas.hr.org.ORGAutoGenerateResource",
						"40_OrgChartUI"));
				buildTree();
			}
		}
	}

	protected void cbxMain_itemStateChanged(ItemEvent e) throws Exception {
		if (e.getStateChange() == 1) {
			hierarchyInfo = (HierarchyInfo) cbxMain.getSelectedItem();
			if (defaultHierarchyInfo != null
					&& hierarchyInfo.getId().toString().equalsIgnoreCase(
							defaultHierarchyInfo.getId().toString())) {
				actionImportAdmin.setVisible(false);
				actionMoveAdmin.setVisible(false);
				actionDeleteAdmin.setVisible(false);
			} else {
				actionImportAdmin.setVisible(true);
				actionMoveAdmin.setVisible(true);
				actionDeleteAdmin.setVisible(true);
			}
			if (isFirstDefaultQuery)
				return;
			GetDataDialog dlg = new GetDataDialog((Frame) SwingUtilities
					.getWindowAncestor(this));
			dlg.setGetDataTask(new GetDataTaskAdapter() {

				public Object exec() throws Exception {
					buildTree();
					return null;
				}

				public void afterExec(Object obj) throws Exception {
				}
			});
			dlg.show();
		}
	}

	private void loadHierarchyData() throws Exception {
		IHierarchy iHierarchy = HierarchyFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(getCUFilterForQuery());
		SorterItemInfo sortInfo = new SorterItemInfo("isDefault");
		sortInfo.setSortType(SortType.DESCEND);
		evi.getSorter().add(sortInfo);
		HierarchyCollection hierarchyCollection = iHierarchy
				.getHierarchyCollection(evi);
		cbxMain.removeAllItems();
		for (int i = 0; i < hierarchyCollection.size(); i++) {
			if (hierarchyCollection.get(i).isIsDefault())
				defaultHierarchyInfo = hierarchyCollection.get(i);
			cbxMain.addItem(hierarchyCollection.get(i));
		}

		cbxMain.setSelectedIndex(-1);
	}

	protected FilterInfo getCUFilterForQuery() {
		FilterInfo filter = new FilterInfo();
		if (SysContext.getSysContext().getCurrentCtrlUnit() == null) {
			return filter;
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("CU.id", SysContext.getSysContext()
							.getCurrentCtrlUnit().getId().toString(),
							CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("CU.id",
							"11111111-1111-1111-1111-111111111111CCE7AED4",
							CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("CU.id",
							"00000000-0000-0000-0000-000000000000CCE7AED4",
							CompareType.EQUALS));
			filter.setMaskString("#0 or #1 or #2");
			return filter;
		}
	}

	public static DefaultKingdeeTreeNode getSelectTreeNode(KDTree tree) {
		DefaultKingdeeTreeNode selectNode = null;
		if (tree != null) {
			TreePath path = tree.getSelectionPath();
			if (path != null)
				selectNode = (DefaultKingdeeTreeNode) path
						.getLastPathComponent();
		}
		return selectNode;
	}

	private void getChildrenNodes(DefaultKingdeeTreeNode parent,
			DefaultMutableTreeNode root) {
		if (parent.getUserObject() instanceof AdminOrgUnitInfo) {
			AdminOrgUnitInfo adminInfo = (AdminOrgUnitInfo) parent
					.getUserObject();
			OrgUnit unit = new OrgUnit();
			unit.ID = adminInfo.getId().toString();
			unit.level = adminInfo.getLevel();
			unit.name = adminInfo.getName();
			unit.number = adminInfo.getNumber();
			if (!HRTreeUtil.isTreeNodeDisable(parent)
					&& adminInfo.get("CAN_VIEW") != null)
				unit.canView = true;
			root.setUserObject(unit);
			DefaultMutableTreeNode child = null;
			for (int i = parent.getChildCount() - 1; i >= 0; i--) {
				child = new DefaultMutableTreeNode();
				getChildrenNodes((DefaultKingdeeTreeNode) parent.getChildAt(i),
						child);
				root.add(child);
			}

		}
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		if (treeLoaded) {
			DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode();
			DefaultKingdeeTreeNode selectNode = getSelectTreeNode(treeMain);
			if (selectNode == null)
				return;
			getChildrenNodes(selectNode, root);
			if (mapPanel == null) {
				mapPanel = new DrawOrgUnit(getWidth(), getHeight(), root, 0,
						true, level, horizontal, displayPositionAndPerson, true);
				mapPanel.setUserComponents(getUserComponents());
				mapPanel.preButton.setVisible(false);
				mapPanel.nextButton.setVisible(false);
			} else {
				mapPanel.setRootNode(root);
			}
			if (isRefresh)
				mapPanel.clearCacheData();
			pnlMain.setDividerLocation(240);
			pnlMain.add(mapPanel, "right");
			pnlMain.revalidate();
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		isRefresh = true;
		buildTree();
		isRefresh = false;
	}

	protected OrgUnit getSelectNode() {
		return mapPanel.getSelectNode();
	}

	protected Component[] getUserComponents() {
		return null;
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
		IQuerySolutionFacade iQuery = QuerySolutionFacadeFactory
				.getRemoteInstance();
		String queryName = getQueryInfo(mainQueryPK).getFullName();
		if (isFirstDefaultQuery
				&& iQuery.hasDefaultSolution(getMetaDataPK().getFullName(),
						queryName)) {
			isFirstDefaultQuery = false;
			QuerySolutionInfo solution = iQuery.getDefaultSolution(
					getMetaDataPK().getFullName(), queryName);
			if (solution.getQueryPanelInfo().get(0).getCustomerParams() != null) {
				CustomerParams params = CustomerParams
						.getCustomerParams2(solution.getQueryPanelInfo().get(0)
								.getCustomerParams());
				level = params.getInt("Level");
				horizontal = params.getBoolean("DisplayType");
				displayPositionAndPerson = params.getBoolean("DisplayPP");
				includeVirtualOrg = params.getBoolean("IncludeVirtualOrg");
				String hId = params.getCustomerParam("Hierarchy_ID");
				Object hObj = null;
				int i = 0;
				for (int size = cbxMain.getItemCount(); i < size; i++) {
					hObj = cbxMain.getItemAt(i);
					if (((HierarchyInfo) hObj).getId().toString().equals(hId)) {
						cbxMain.setSelectedIndex(i);
						if (mapPanel != null)
							mapPanel.setRepaintData(level, horizontal,
									displayPositionAndPerson, true);
						return;
					}
				}

				showFilterDialog();
			} else {
				showFilterDialog();
			}
		} else {
			showFilterDialog();
		}
		isFirstDefaultQuery = false;
	}

	private QueryInfo getQueryInfo(IMetaDataPK queryPK) {
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		return loader.getQuery(queryPK);
	}

	private void showFilterDialog() throws Exception {
		isFirstDefaultQuery = false;
		if (commonQueryDialog == null) {
			commonQueryDialog = new CommonQueryDialog();
			commonQueryDialog.setHeight(350);
			commonQueryDialog.setWidth(400);
			if (getUIWindow() == null)
				commonQueryDialog.setOwner((Component) getUIContext().get(
						"OwnerWindow"));
			else
				commonQueryDialog.setOwner(this);
			commonQueryDialog.setParentUIClassName(getMetaDataPK()
					.getFullName());
			commonQueryDialog.setQueryObjectPK(mainQueryPK);
			commonQueryDialog.setShowFilter(false);
			commonQueryDialog.setShowSorter(false);
			commonQueryDialog.setShowToolbar(true);
			customerPanel = new OrgCharFilterUI();
			commonQueryDialog.addUserPanel(customerPanel);
			String commonQueryRes = "com.kingdee.eas.hr.time.TimeResource";
			commonQueryDialog.setTitle(getUITitle() + " - "
					+ EASResource.getString(commonQueryRes, "CONDITION_QUERY"));
		}
		if (commonQueryDialog.show()) {
			CustomerParams params = customerPanel.getCustomerParams();
			level = params.getInt("Level");
			horizontal = params.getBoolean("DisplayType");
			displayPositionAndPerson = params.getBoolean("DisplayPP");
			String hId = params.getCustomerParam("Hierarchy_ID");
			Object hObj = null;
			int i = 0;
			for (int size = cbxMain.getItemCount(); i < size; i++) {
				hObj = cbxMain.getItemAt(i);
				if (((HierarchyInfo) hObj).getId().toString().equals(hId)) {
					if (includeVirtualOrg != params
							.getBoolean("IncludeVirtualOrg"))
						cbxMain.setSelectedIndex(-1);
					includeVirtualOrg = params.getBoolean("IncludeVirtualOrg");
					cbxMain.setSelectedIndex(i);
					if (mapPanel != null)
						mapPanel.setRepaintData(level, horizontal,
								displayPositionAndPerson, true);
					return;
				}
			}

			cbxMain.setSelectedIndex(0);
		} else {
			SysUtil.abort();
		}
	}

	static Class _mthclass$(String x0) throws Throwable {
		try {
			return Class.forName(x0);
		} catch (ClassNotFoundException x1) {
			throw (new NoClassDefFoundError()).initCause(x1);
		}
	}

	private static final Logger logger;
	private DefaultKingdeeTreeNode node;
	private boolean treeLoaded;
	private DrawOrgUnit mapPanel;
	private HierarchyInfo hierarchyInfo;
	private HierarchyInfo defaultHierarchyInfo;
	private boolean isRefresh;
	private CommonQueryDialog commonQueryDialog;
	private OrgCharFilterUI customerPanel;
	private IMetaDataPK mainQueryPK;
	private int level;
	private boolean horizontal;
	private boolean displayPositionAndPerson;
	private boolean includeVirtualOrg;
	private boolean isFirstDefaultQuery;
	private KDPanel emptyPanel;

	static {
		logger = CoreUIObject
				.getLogger(com.kingdee.eas.hr.org.client.OrgChartUI.class);
	}
}
