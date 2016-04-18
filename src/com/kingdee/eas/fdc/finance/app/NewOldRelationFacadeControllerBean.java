package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.cas.BillDisposerAction;
import com.kingdee.eas.fi.cas.DisposerActionEnum;
import com.kingdee.eas.fi.cas.IPaymentDisposer;
import com.kingdee.eas.fi.cas.PaymentBillEntryCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentDisposerFactory;

public class NewOldRelationFacadeControllerBean extends AbstractNewOldRelationFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.finance.app.NewOldRelationFacadeControllerBean");
    
    @Override
    public void importGroupAccountRelation(Context ctx, String company)
    		throws BOSException, EASBizException {
    	
    }
    
    //原'删除/生成凭证'功能，根据disposerActionEnum区分删除或生成
    @Override
    public void dispose(Context ctx, PaymentBillInfo paymentBillInfo,
    		DisposerActionEnum disposerActionEnum) throws BOSException,
    		EASBizException {
    	 IPaymentDisposer disposer = null;
         PaymentBillEntryCollection entryColl = paymentBillInfo.getEntries();
         PaymentBillEntryInfo entryInfo = null;
         int i = 0;
         int size = entryColl.size();
         do
         {
             if(i >= size)
                 break;
             entryInfo = entryColl.get(i);
             if(entryInfo != null)
             {
                 disposer = PaymentDisposerFactory.getInstance(ctx, entryInfo.getSourceBillId());
                 break;
             }
             i++;
         } while(true);
         if(disposer != null)
         {
             BillDisposerAction disposerAction = new BillDisposerAction();
             disposerAction.setDestBill(paymentBillInfo);
             disposerAction.setActionType(disposerActionEnum);
             disposer.dispose(ctx, disposerAction);
         }
    }
}