package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface ISaleAreaData extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public SaleAreaDataInfo getSaleAreaDataInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SaleAreaDataInfo getSaleAreaDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SaleAreaDataInfo getSaleAreaDataInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(SaleAreaDataInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, SaleAreaDataInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, SaleAreaDataInfo model) throws BOSException, EASBizException;
    public void updatePartial(SaleAreaDataInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, SaleAreaDataInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public SaleAreaDataCollection getSaleAreaDataCollection() throws BOSException;
    public SaleAreaDataCollection getSaleAreaDataCollection(EntityViewInfo view) throws BOSException;
    public SaleAreaDataCollection getSaleAreaDataCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}