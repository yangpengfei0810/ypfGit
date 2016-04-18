package com.kingdee.eas.cp.bc;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ma.bg.BgCtrlParam;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IEvectionReqBill extends IBizCollBillBase
{
    public EvectionReqBillInfo getEvectionReqBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public EvectionReqBillInfo getEvectionReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public EvectionReqBillInfo getEvectionReqBillInfo(String oql) throws BOSException, EASBizException;
    public EvectionReqBillCollection getEvectionReqBillCollection() throws BOSException;
    public EvectionReqBillCollection getEvectionReqBillCollection(EntityViewInfo view) throws BOSException;
    public EvectionReqBillCollection getEvectionReqBillCollection(String oql) throws BOSException;
    public void setPassState(BOSUuid id) throws BOSException, EASBizException;
    public void setNotPassState(BOSUuid id) throws BOSException, EASBizException;
    public BgCtrlParam[] getBgParam(BOSUuid id) throws BOSException, EASBizException;
    public void setCloseState(BOSUuid id) throws BOSException, EASBizException;
    public boolean needBgAudit(BOSUuid id) throws BOSException, EASBizException;
    public void reBackBg(BOSUuid id) throws BOSException, EASBizException;
    public void setCheckingState(BOSUuid id) throws BOSException, EASBizException;
    public void setDraftState(BOSUuid id) throws BOSException, EASBizException;
    public List getCanDeleteIDs(List list) throws BOSException, EASBizException;
    public boolean isCanModify(BOSUuid id) throws BOSException, EASBizException;
}