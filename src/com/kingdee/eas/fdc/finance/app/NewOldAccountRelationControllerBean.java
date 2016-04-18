package com.kingdee.eas.fdc.finance.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.master.account.AccountTools;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.INewOldAccountRelation;
import com.kingdee.eas.fdc.finance.NewOldAccountRelationCollection;
import com.kingdee.eas.fdc.finance.NewOldAccountRelationFactory;
import com.kingdee.eas.fdc.finance.NewOldAccountRelationInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class NewOldAccountRelationControllerBean extends AbstractNewOldAccountRelationControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.NewOldAccountRelationControllerBean");
    
    @Override
    public void submitAll(Context ctx,
    		NewOldAccountRelationCollection accountRelationColl,String company)
    		throws BOSException, EASBizException {
    	if(accountRelationColl == null )
            return;
        int size = accountRelationColl.size();
        EntityViewInfo evi = new EntityViewInfo();
        INewOldAccountRelation noar=NewOldAccountRelationFactory.getLocalInstance(ctx);
        FilterInfo filter=new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("oldCostAccount.fullOrgUnit.id",company));
        filter.getFilterItems().add(new FilterItemInfo("oldCostAccount.curProject.id", company));
        filter.setMaskString("#0 or #1");
        evi.setFilter(filter);
        noar.delete(filter);
        NewOldAccountRelationCollection oldCollection = noar.getNewOldAccountRelationCollection(evi);
        for(int i = 0; i < oldCollection.size(); i++)
            if(!accountRelationColl.containsKey(oldCollection.get(i).getId()))
                _delete(ctx, new ObjectUuidPK(oldCollection.get(i).getId().toString()));

        for(int i = 0; i < size; i++)
        {
        	NewOldAccountRelationInfo info = (NewOldAccountRelationInfo)accountRelationColl.getObject(i);
        	 _submit(ctx, info);
           
        }
    }
    
    @Override
    public void importGroupData(Context ctx, HashSet prjIdSet,CompanyOrgUnitInfo company)
    		throws BOSException, EASBizException {
    	 FDCSQLBuilder builder;
         AccountTableInfo tableInfo;
         TempTablePool pool;
         String createSql;
         String temptable;
         builder = new FDCSQLBuilder(ctx);
         builder.appendSql("select 1 from T_BD_AccountView where fcompanyid in ( select cfneworg from ct_fnc_newOldOrgRelation where cfoldorg in  (select ffullorgunit from T_FDC_CurProject where ");
         builder.appendParam("fid", prjIdSet.toArray());
         builder.appendSql("))");
         if(!builder.isExist())
             throw new EASBizException(new NumericExceptionSubItem("100", "\u8BE5\u8D22\u52A1\u7EC4\u7EC7\u6CA1\u6709\u4F1A\u8BA1\u79D1\u76EE!"));
         builder.clear();
         tableInfo = null;
         CompanyOrgUnitInfo fiOrgInfo = company;
         if(fiOrgInfo != null && fiOrgInfo.getAccountTable() != null)
             tableInfo = fiOrgInfo.getAccountTable();
         else
             tableInfo = AccountTools.getDefaultAccountTableByCU(company.getCU());
         pool = TempTablePool.getInstance(ctx);
         createSql = "create table temptable (foldaccount varchar(44),fnewAccount varchar(44), fcompanyid varchar(44))";
         temptable = null;
         try
         {
             temptable = pool.createTempTable(createSql);
             String desc = "\u96C6\u56E2\u6570\u636E\u5F15\u5165";
             builder.appendSql("insert into " + temptable + " (foldaccount,fnewAccount,fcompanyid)  \n");
             builder.appendSql("( \n");
             builder.appendSql("select prjCostAcct.fid,acct.fid,acct.fcompanyid from ct_fnc_newOldAccountRelation noAcct \n");
             builder.appendSql("inner join T_FDC_CostAccount groupCostAcct on groupCostAcct.fid=noAcct.cfoldaccount   \n");
             builder.appendSql("inner join T_FDC_CostAccount prjCostAcct on prjCostAcct.flongnumber=groupCostAcct.flongnumber \n");
             builder.appendSql("inner join T_FDC_CurProject prj on prj.fid=prjCostAcct.fcurProject  \n");
             builder.appendSql("left outer join T_BD_AccountView groupAcct on groupAcct.fid=noAcct.cfnewAccount  \n");
             builder.appendSql("left outer join T_BD_AccountView acct on acct.flongnumber=groupAcct.flongnumber and acct.fcompanyid=?  and acct.faccounttableid=? \n");
             builder.appendSql("where groupCostAcct.ffullorgunit=? and \n");
             builder.addParam(fiOrgInfo.getId().toString());
             builder.addParam(tableInfo.getId().toString());
             builder.addParam("00000000-0000-0000-0000-000000000000CCE7AED4");
             builder.appendParam("prjCostAcct.fcurProject", prjIdSet.toArray());
             builder.appendSql("and not exists (select 1 from ct_fnc_newOldAccountRelation where cfoldaccount=prjCostAcct.fid) \n");
             builder.appendSql(" and (acct.fid is not null ) \n");
             builder.appendSql(") \n");
             builder.execute();
             builder.clear();
             builder.appendSql("  UPDATE " + temptable + " SET fnewAccount = (  \n");
             builder.appendSql("  select fid from T_BD_AccountView  where flongnumber=(  \n");
             builder.appendSql("  SELECT min(childAcct.flongnumber) FROM T_BD_AccountView parentAcct   \n");
             builder.appendSql("  INNER JOIN T_BD_AccountView childAcct ON childAcct.fcompanyid = parentAcct.fcompanyid   \n");
             builder.appendSql("  WHERE ((parentAcct.fid = " + temptable + ".fnewAccount  ");
             builder.appendSql("  AND charindex((parentAcct.flongnumber || '!'),(childAcct.flongnumber || '!')) = 1)   \n");
             builder.appendSql("  AND childAcct.fisleaf = 1)) and fcompanyid=" + temptable + ".fcompanyid  \n");
             builder.appendSql("  )  WHERE EXISTS (SELECT 1 FROM T_BD_Accountview WHERE (fid = " + temptable + ".fnewAccount AND fisleaf = 0))  \n");
             builder.execute();
             builder.clear();
             builder.clear();
             builder.appendSql("insert into ct_fnc_newOldAccountRelation (fid,cfoldaccount,cfnewAccount,cfremark) (select newbosid('256C7E39'),foldaccount,fnewAccount,'" + desc + "' from " + temptable + " order by fcompanyid) \n");
             builder.execute();
         }
         catch(Exception e1)
         {
             throw new BOSException(e1);
         }
         if(temptable != null)
             pool.releaseTable(temptable);
         Map param = new HashMap();
         for(Iterator iter = prjIdSet.iterator(); iter.hasNext(); )//CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).update(param)
         {
             String prjId = (String)iter.next();
             param.put("PROJECTID", prjId);
         }

         return;
    }
}