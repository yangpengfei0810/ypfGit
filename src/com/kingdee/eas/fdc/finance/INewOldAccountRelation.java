package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.HashSet;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface INewOldAccountRelation extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public NewOldAccountRelationInfo getNewOldAccountRelationInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewOldAccountRelationInfo getNewOldAccountRelationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewOldAccountRelationInfo getNewOldAccountRelationInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(NewOldAccountRelationInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, NewOldAccountRelationInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, NewOldAccountRelationInfo model) throws BOSException, EASBizException;
    public void updatePartial(NewOldAccountRelationInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, NewOldAccountRelationInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public NewOldAccountRelationCollection getNewOldAccountRelationCollection() throws BOSException;
    public NewOldAccountRelationCollection getNewOldAccountRelationCollection(EntityViewInfo view) throws BOSException;
    public NewOldAccountRelationCollection getNewOldAccountRelationCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void submitAll(NewOldAccountRelationCollection accountRelationColl, String company) throws BOSException, EASBizException;
    public void importGroupData(HashSet prjIdSet, CompanyOrgUnitInfo company) throws BOSException, EASBizException;
}