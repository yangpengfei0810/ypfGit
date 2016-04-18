package com.kingdee.eas.fdc.contract.app.webservice;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPHeaderElement;

import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.orm.core.ORMEngine;
import com.kingdee.bos.webservice.BeanConvertHelper;
import com.kingdee.bos.webservice.MetaDataHelper;
import com.kingdee.bos.webservice.WSConfig;
import com.kingdee.bos.webservice.WSInvokeException;

public class WSWriteBackBillStatusFacadeSrvProxy { 

    public byte[] getAttachment( String id ) throws WSInvokeException {
        try {
            return getController().getAttachment(
            id);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String getBillState( String id ) throws WSInvokeException {
        try {
            return getController().getBillState(
            id);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String modBillInfo( String billType , String billId , String state , boolean isExistAttachment , com.kingdee.eas.base.attachment.app.WSAttachment[] attachmentInfoList ) throws WSInvokeException {
        try {
            return getController().modBillInfo(
            billType,
            billId,
            state,
            isExistAttachment,
            (com.kingdee.eas.base.attachment.AttachmentCollection)getBeanConvertor().toObjectCollection( MetaDataHelper.getEntityMetaData("com.kingdee.eas.base.attachment.app.Attachment") , attachmentInfoList ));
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade getController() {
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.fdc.contract.WriteBackBillStatusFacade") ; 
        } else {
            return ( com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.contract.WriteBackBillStatusFacade" , com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.contract.WriteBackBillStatusFacade" , com.kingdee.eas.fdc.contract.IWriteBackBillStatusFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}