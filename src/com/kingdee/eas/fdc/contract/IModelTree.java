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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IModelTree extends ITreeBase
{
    public ModelTreeInfo getModelTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ModelTreeInfo getModelTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ModelTreeInfo getModelTreeInfo(String oql) throws BOSException, EASBizException;
    public ModelTreeCollection getModelTreeCollection() throws BOSException;
    public ModelTreeCollection getModelTreeCollection(EntityViewInfo view) throws BOSException;
    public ModelTreeCollection getModelTreeCollection(String oql) throws BOSException;
}