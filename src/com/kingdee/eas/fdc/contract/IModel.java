package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IModel extends IDataBase
{
    public ModelInfo getModelInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ModelInfo getModelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ModelInfo getModelInfo(String oql) throws BOSException, EASBizException;
    public ModelCollection getModelCollection() throws BOSException;
    public ModelCollection getModelCollection(EntityViewInfo view) throws BOSException;
    public ModelCollection getModelCollection(String oql) throws BOSException;
}