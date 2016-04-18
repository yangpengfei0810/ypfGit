package com.kingdee.eas.cp.bc.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;

import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.component.tree.impl.UITreeNode;

import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.waf.resource.Resources;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.hr.emp.web.base.AbstractTreeBaseF7Bean;

@ManagedBean(name = "cp.bc.ExpenseTypeF7Bean", scope = ManagedBeanScope.SESSION)
public class ExpenseTypeF7Bean extends AbstractTreeBaseF7Bean {
	protected void pageOnload() {
		super.pageOnload();
		this.f7Title = Resources.getText(ResourceUtils.CP_BC_WEB_RES,
				"expenseType");

		this.showAllOrg = false;
	}

	protected void buildTreeFilter() {
		UITreeNode treeNode = this.leftTree.getSelectedNode();
		
		this.innerViewInfo = ((EntityViewInfo) this.entityViewInfo.clone());
		FilterInfo filterInfo = new FilterInfo();
		if ((treeNode != null)
				&& ((treeNode.getUserData() instanceof CoreBaseInfo))) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("operationType.id",
							((CoreBaseInfo) treeNode.getUserData()).getId()
									.toString()));
		}else{
			// 如果选中的是根节点（业务类别），则取出所有节点作为过滤条件
			if(leftTree.getRootNode() == null){
				filterInfo.getFilterItems().add(
						new FilterItemInfo("operationType.id","xxxxxxxxxxxxxxxxx"));
			}else{
				List<UIComponent> list = leftTree.getRootNode().getChildren();
				if(list != null && list.size() > 0){
					Set<String> ids = new HashSet<String>();
					for (int i = 0; i < list.size(); i++) {
						UITreeNode node = (UITreeNode)list.get(i);
						ids.add(((CoreBaseInfo)node.getUserData()).getId().toString());
					}
					filterInfo.getFilterItems().add(new FilterItemInfo("operationType.id", ids, CompareType.INCLUDE));
				}else{
					filterInfo.getFilterItems().add(
							new FilterItemInfo("operationType.id","xxxxxxxxxxxxxxxxx"));
				}
			}
			
		}
		try {
			if (this.innerViewInfo != null && this.innerViewInfo.getFilter() != null) {
				this.innerViewInfo.getFilter().mergeFilter(filterInfo, "and");
			} else {
				this.innerViewInfo = new EntityViewInfo();
				this.innerViewInfo.setFilter(filterInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void initTree() {
		this.treeData = new OperationTypeTreeDataProvider(
				(EntityViewInfo) this.entityViewInfo.clone());

		this.leftTree.setValue(this.treeData);
	}

	protected MetaDataPK getQueryPK() {
		return new MetaDataPK("com.kingdee.eas.cp.bc.app.F7ExpenseTypeQuery");
	}
}