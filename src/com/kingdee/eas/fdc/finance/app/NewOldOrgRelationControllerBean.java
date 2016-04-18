package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.INewOldOrgRelation;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationCollection;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationFactory;
import com.kingdee.eas.fdc.finance.NewOldOrgRelationInfo;

public class NewOldOrgRelationControllerBean extends AbstractNewOldOrgRelationControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.NewOldOrgRelationControllerBean");
    
    @Override
    public void submitAll(Context ctx, NewOldOrgRelationCollection objColl)
    		throws BOSException, EASBizException {
    	if(objColl == null )
            return;
        int size = objColl.size();
       // NewOldOrgRelationCollection newCollection = (NewOldOrgRelationCollection)objColl;
        
        INewOldOrgRelation noor=NewOldOrgRelationFactory.getLocalInstance(ctx);
        FilterInfo filter=new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("id",null,CompareType.NOTEQUALS));
        noor.delete(filter);
        NewOldOrgRelationInfo tempInfo = new NewOldOrgRelationInfo();
        IORMappingDAO dao = ORMappingDAO.getInstance(tempInfo.getBOSType(), ctx, getConnection(ctx));
        for(int i = 0; i < size; i++)
        {
        	NewOldOrgRelationInfo info = (NewOldOrgRelationInfo)objColl.getObject(i);
        	/*if(info.getId()==null){
        		info.setId(BOSUuid.create(tempInfo.getBOSType()));
        		 dao.addNewBatch(info);
        	}else{
        		dao.updateBatch(new ObjectUuidPK(info.getId()),info);
        	}*/
        	
        	dao.addNewBatch(info);
           
        }
        dao.executeBatch();
    }
}