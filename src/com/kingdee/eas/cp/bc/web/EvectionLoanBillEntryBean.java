package com.kingdee.eas.cp.bc.web;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.waf.winlet.edit.BillEntryBean;
import com.kingdee.eas.cp.bc.EvectionLoanBillEntryInfo;
import com.kingdee.eas.cp.bc.VehicleEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import java.math.BigDecimal;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;

@ManagedBean(name="cp.bc.EvectionLoanBillEntryBean", scope=ManagedBeanScope.SESSION)
public class EvectionLoanBillEntryBean extends BillEntryBean
{
  public void removeRow()
  {
    super.removeRow();
  }

  public CoreBaseInfo createNewEntry()
  {
    EvectionLoanBillEntryInfo info = new EvectionLoanBillEntryInfo();
    info.setId(BOSUuid.create(info.getBOSType()));
    info.setVehicle(VehicleEnum.AIRPLANE);

    info.setBudgetDo(new BigDecimal("0.00"));

    return info;
  }
}