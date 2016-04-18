package com.kingdee.eas.cp.bc.web;

import java.util.ArrayList;
import java.util.List;

import org.operamasks.faces.component.tree.base.TreeDataProvider;

import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.waf.ctx.WafContext;
import com.kingdee.eas.cp.bc.OperationTypeCollection;
import com.kingdee.eas.cp.bc.OperationTypeFactory;
import com.kingdee.eas.cp.bc.OperationTypeInfo;

public class OperationTypeTreeDataProvider
  implements TreeDataProvider
{
  private EntityViewInfo viewInfo;

  public OperationTypeTreeDataProvider(EntityViewInfo viewInfo)
  {
    this.viewInfo = viewInfo;
  }

  public Object[] getChildren(Object userData) {
    Object[] retArray = null;
    String rootText = "业务类别";
    if (userData == null) {
      retArray = new Object[] { rootText };
    }
    else if (rootText.equals(userData)) {
      retArray = getOperationTypeNode().toArray();
    }
    return retArray;
  }

  public String getHref(Object userData)
  {
    return null;
  }

  public String getHrefTarget(Object userData)
  {
    return null;
  }

  public String getIcon(Object userData)
  {
    return null;
  }

  public String getText(Object userData) {
    if (userData == null) {
      return null;
    }

    if ((userData instanceof OperationTypeInfo)) {
      return ((OperationTypeInfo)userData).getName();
    }

    return userData.toString();
  }

  public boolean isCascade(Object userData)
  {
    return false;
  }

  public Boolean isChecked(Object userData)
  {
    return null;
  }

  public boolean isExpanded(Object userData) {
    return true;
  }

  private List<OperationTypeInfo> getOperationTypeNode() {
    List retList = new ArrayList();
    try {
      EntityViewInfo evi = new EntityViewInfo();

      if ((this.viewInfo != null) && (this.viewInfo.getFilter()!=null) && (this.viewInfo.getFilter().getFilterItems().size() > 0))
      {
        evi = this.viewInfo;
      }

      Context ctx = WafContext.getInstance().getContext();
      OperationTypeCollection otc = OperationTypeFactory.getLocalInstance(ctx).getOperationTypeCollection(evi);

      int i = 0; for (int length = otc.size(); i < length; i++) {
        OperationTypeInfo info = otc.get(i);
        retList.add(info);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return retList;
  }
}