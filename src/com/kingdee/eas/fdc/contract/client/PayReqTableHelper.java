package com.kingdee.eas.fdc.contract.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

// page 这里是用代码初始化的表格，表格位置发生改变。所以需要改动。
// 所以替换此文件

public final class PayReqTableHelper
{
  private static final Logger logger = CoreUIObject.getLogger(PayReqUtils.class);

  public static final Color noEditColor = new Color(232, 232, 227);

  private PayRequestBillInfo editData = null;

  private PayRequestBillEditUI editUI = null;

  private HashMap bindCellMap = null;

  private KDTable table = null;

  public PayReqTableHelper(PayRequestBillEditUI editUI)
  {
    this.editUI = editUI;
    this.editData = editUI.editData;
    this.bindCellMap = editUI.bindCellMap;
  }

  KDTable createPayRequetBillTable(DeductTypeCollection deductTypeCollection)
    throws EASBizException, BOSException
  {
    Rectangle kdtRectangle = this.editUI.kdtEntrys.getBounds();
    this.editUI.kdtEntrys.setEnabled(false);
    this.editUI.kdtEntrys.setVisible(false);

    this.table = new KDTable(12, 1, 0);

    this.table.setBounds(kdtRectangle);
//    this.editUI.add(this.table, new KDLayout.Constraints(325, kdtRectangle));
    this.editUI.kDPanel2.add(this.table, BorderLayout.CENTER);
// page 这里是用代码初始化的表格，表格位置发生改变。所以需要改动。
    
    this.table.getIndexColumn().getStyleAttributes().setHided(true);
    this.table.setRefresh(false);
    this.table.getScriptManager().setAutoRun(false);
    StyleAttributes sa = this.table.getStyleAttributes();

    sa.setLocked(true);
    sa.setNumberFormat("###,##0.00");

    IRow headRow = this.table.getHeadRow(0);
    headRow.getCell(0).setValue(getRes("prjTable"));
    KDTMergeManager mm = this.table.getHeadMergeManager();
    mm.mergeBlock(0, 0, 0, 11, 4);

    initFixTable(this.table);

    initDynamicTable(this.table, deductTypeCollection);

    sa = this.table.getColumn(1).getStyleAttributes();
    sa.setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    initLayout();

    setTableCellColorAndEdit(this.table);

    this.table.setRefresh(true);

    this.table.setAutoResize(true);
    this.table.getScriptManager().setAutoRun(true);

    this.table.getIndexColumn().setWidthAdjustMode((short) 2);

    boolean isSeparate = (this.editUI.isSeparate) && (FDCUtils.isContractBill(null, this.editData.getContractId()));
    this.table.getRow(6).getStyleAttributes().setHided(!isSeparate);

    this.table.getRow(7).getStyleAttributes().setHided(true);

    ((KDTTransferAction)this.table.getActionMap().get("Paste")).setPasteMode(2);
    return this.table;
  }

  private void initLayout()
  {
    this.table.getCell(0, 1).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.LEFT);
    this.table.getCell(2, 1).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);

    this.table.getCell(0, 6).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(1, 6).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(2, 6).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(14, 6).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(3, 6).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.CENTER);

    this.table.getCell(0, 7).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(1, 7).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(2, 7).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);

    this.table.getCell(0, 9).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(1, 9).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(2, 9).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);

    this.table.getCell(2, 3).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(1, 3).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(3, 2).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.CENTER);
    this.table.getCell(3, 4).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.CENTER);
    this.table.getCell(2, 4).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(1, 4).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);

    this.table.getCell(3, 8).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.CENTER);
    this.table.getCell(3, 10).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.CENTER);

    this.table.getCell(0, 10).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(1, 10).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(2, 10).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);

    int rowCount = this.table.getRowCount();
    for (int i = 2; i < this.table.getColumnCount(); i++) {
      this.table.getCell(4, i).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.CENTER);
      for (int j = 5; j < rowCount - 4; j++) {
        this.table.getCell(j, i).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
      }
    }

    this.table.getCell(rowCount - 1, 4).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(rowCount - 2, 4).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(rowCount - 2, 7).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(rowCount - 4, 10).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    this.table.getCell(rowCount - 4, 11).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
  }

  public static Map handleAttachment(Set boIDS)
    throws BOSException
  {
    Map attachMap = new HashMap();
    FDCSQLBuilder builder = new FDCSQLBuilder();
    builder.appendSql("select fboid from t_bas_boattchasso where ");
    builder.appendParam("fboid", boIDS.toArray());
    try
    {
      IRowSet rowSet = builder.executeQuery();
      while (rowSet.next())
      {
        attachMap.put(rowSet.getString("fboid"), Boolean.TRUE);
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    return attachMap;
  }

  public static Map handleAuditPersonTime(Set payReqIds) {
    Map auditMap = new HashMap();
    try {
      auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(payReqIds);
    } catch (EASBizException e) {
      e.printStackTrace();
    } catch (BOSException e) {
      e.printStackTrace();
    }
    return auditMap;
  }

  private void initFixTable(KDTable table)
  {
    table.addRows(16);

    PayReqUtils.bindCell(table, 0, 0, getRes("contractBill.contractName"), "contractName", this.bindCellMap);
    table.getCell(0, 1).getStyleAttributes().setNumberFormat("@");

    PayReqUtils.bindCell(table, 3, 0, getRes("payTimes"), "payTimes", this.bindCellMap);

    table.getCell(5, 0).setValue(getRes("scheduleAmt"));

    table.getCell(10, 0).setValue(getRes("payPartAMatlAmt"));

    table.getCell(11, 0).setValue(getRes("paid"));

    table.getCell(12, 0).setValue(getRes("residue"));

    PayReqUtils.bindCell(table, 13, 0, getRes("curPlannedPayment"), "curPlannedPayment", this.bindCellMap);

    PayReqUtils.bindCell(table, 14, 0, getRes("curReqPercent"), "curReqPercent", this.bindCellMap);

    table.getCell(15, 0).setValue("应付申请%");

    table.getCell(5, 1).setValue(getRes("projectPriceInContract"));

    table.getCell(6, 1).setValue("预付款");

    table.getCell(7, 1).setValue(getRes("addProjectAmt"));

    table.getCell(8, 1).setValue("奖励");

    table.getCell(9, 1).setValue("违约金");

    table.getCell(3, 2).setValue(getRes("lstAllPaid"));
    table.getCell(4, 2).setValue("原币");

    PayReqUtils.bindCell(table, 1, 3, "变更指令金额本币", "changeAmt", this.bindCellMap);
    PayReqUtils.bindCell(table, 2, 3, "结算金额本币", "settleAmt", this.bindCellMap);
    table.getCell(4, 3).setValue("本币");

    PayReqUtils.bindCell(table, 13, 3, getRes("curBackPay"), "curBackPay", this.bindCellMap);

    table.getCell(14, 3).setValue(getRes("allReqPercent"));

    table.getCell(15, 3).setValue("累计应付申请%");

    table.getCell(3, 4).setValue(getRes("lstAllReq"));
    table.getCell(4, 4).setValue("原币");

    table.getCell(4, 5).setValue("本币");

    table.getCell(3, 6).setValue(getRes("curOccur"));

    table.getCell(4, 6).setValue("原币");

    PayReqUtils.bindCell(table, 14, 6, getRes("imageSchedule"), "imageSchedule", this.bindCellMap);

    table.getCell(4, 7).setValue("本币");

    table.getCell(3, 8).setValue(getRes("curAllReq"));

    table.getCell(4, 8).setValue("原币");

    PayReqUtils.bindCell(table, 0, 9, "合同造价本币", "contractPrice", this.bindCellMap);

    PayReqUtils.bindCell(table, 1, 9, "最新造价本币", "latestPrice", this.bindCellMap);

    PayReqUtils.bindCell(table, 2, 9, "本申请单已付本币", "payedAmt", this.bindCellMap);

    table.getCell(4, 9).setValue("本币");

    table.getCell(3, 10).setValue(getRes("curAllPaid"));
    table.getCell(4, 10).setValue("原币");

    table.getCell(4, 11).setValue("本币");

    IRow row = table.getRow(5);
    ICell cell = row.getCell(3);
    PayReqUtils.bindCell(cell, "LstPrjAllPaidAmt", this.bindCellMap);
    cell = row.getCell(5);
    PayReqUtils.bindCell(cell, "lstPrjAllReqAmt", this.bindCellMap);
    cell = row.getCell(6);
    cell.getStyleAttributes().setLocked(false);
    PayReqUtils.bindCell(cell, "projectPriceInContractOri", this.bindCellMap, true);
    cell = row.getCell(7);
    cell.getStyleAttributes().setLocked(true);
    PayReqUtils.bindCell(cell, "projectPriceInContract", this.bindCellMap, true);
    cell = row.getCell(9);
    PayReqUtils.bindCell(cell, "prjAllReqAmt", this.bindCellMap);

    row = table.getRow(6);
    cell = row.getCell(3);
    PayReqUtils.bindCell(cell, "lstAdvanceAllPaid", this.bindCellMap);
    cell = row.getCell(5);
    PayReqUtils.bindCell(cell, "lstAdvanceAllReq", this.bindCellMap);
    cell = row.getCell(6);
    cell.getStyleAttributes().setLocked(false);
    PayReqUtils.bindCell(cell, "advance", this.bindCellMap, true);
    cell = row.getCell(7);
    cell.getStyleAttributes().setLocked(true);
    PayReqUtils.bindCell(cell, "locAdvance", this.bindCellMap, true);
    cell = row.getCell(9);
    PayReqUtils.bindCell(cell, "advanceAllReq", this.bindCellMap);
    cell = row.getCell(11);
    PayReqUtils.bindCell(cell, "advanceAllPaid", this.bindCellMap);

    row = table.getRow(7);
    cell = row.getCell(3);
    PayReqUtils.bindCell(cell, "LstAddPrjAllPaidAmt", this.bindCellMap);
    cell = row.getCell(5);
    PayReqUtils.bindCell(cell, "lstAddPrjAllReqAmt", this.bindCellMap);
    cell = row.getCell(6);
    cell.getStyleAttributes().setLocked(false);
    PayReqUtils.bindCell(cell, "addProjectOriAmt", this.bindCellMap, true);
    cell = row.getCell(7);
    cell.getStyleAttributes().setLocked(true);
    PayReqUtils.bindCell(cell, "addProjectAmt", this.bindCellMap, true);
    cell = row.getCell(9);
    PayReqUtils.bindCell(cell, "addPrjAllReqAmt", this.bindCellMap);

    row = table.getRow(8);
    cell = row.getCell(3);
    PayReqUtils.bindCell(cell, "lstGuerdonPaidAmt", this.bindCellMap);
    cell = row.getCell(5);
    PayReqUtils.bindCell(cell, "lstGuerdonReqAmt", this.bindCellMap);
    cell = row.getCell(6);
    cell.getStyleAttributes().setLocked(true);
    PayReqUtils.bindCell(cell, "guerdonOriginalAmt", this.bindCellMap, true);
    cell = row.getCell(7);
    cell.getStyleAttributes().setLocked(true);
    PayReqUtils.bindCell(cell, "guerdonAmt", this.bindCellMap, true);
    cell = row.getCell(9);
    PayReqUtils.bindCell(cell, "allGuerdonAmt", this.bindCellMap);

    row = table.getRow(9);
    cell = row.getCell(3);
    PayReqUtils.bindCell(cell, "lstCompensationPaidAmt", this.bindCellMap);
    cell = row.getCell(5);
    PayReqUtils.bindCell(cell, "lstCompensationReqAmt", this.bindCellMap);
    cell = row.getCell(6);
    cell.getStyleAttributes().setLocked(true);
    PayReqUtils.bindCell(cell, "compensationOriginalAmt", this.bindCellMap, true);
    cell = row.getCell(7);
    cell.getStyleAttributes().setLocked(true);
    PayReqUtils.bindCell(cell, "compensationAmt", this.bindCellMap, true);
    cell = row.getCell(9);
    PayReqUtils.bindCell(cell, "allCompensationAmt", this.bindCellMap);

    row = table.getRow(10);
    cell = row.getCell(3);
    PayReqUtils.bindCell(cell, "LstAMatlAllPaidAmt", this.bindCellMap);
    cell = row.getCell(5);
    PayReqUtils.bindCell(cell, "lstAMatlAllReqAmt", this.bindCellMap);
    cell = row.getCell(6);
    PayReqUtils.bindCell(cell, "payPartAMatlOriAmt", this.bindCellMap, true);
    cell = row.getCell(7);
    PayReqUtils.bindCell(cell, "payPartAMatlAmt", this.bindCellMap, true);
    cell = row.getCell(9);
    PayReqUtils.bindCell(cell, "payPartAMatlAllReqAmt", this.bindCellMap);
    cell = row.getCell(11);
    PayReqUtils.bindCell(cell, "payPartAMatlAllPaidAmt", this.bindCellMap);

    row = table.getRow(11);
    cell = row.getCell(6);
    PayReqUtils.bindCell(cell, "curPaid", this.bindCellMap, true);
    cell = row.getCell(7);
    PayReqUtils.bindCell(cell, "curpaidlocal", this.bindCellMap, true);

    addOrgPriceForEntryTable(table);

    StyleAttributes sa = ((ICell)this.bindCellMap.get("payTimes")).getStyleAttributes();

    sa.setNumberFormat("###,##0");

    sa = ((ICell)this.bindCellMap.get("imageSchedule")).getStyleAttributes();
    KDFormattedTextField txt = new KDFormattedTextField(1);
    txt.setPrecision(2);
    txt.setMaximumValue(FDCHelper.ONE_HUNDRED);
    txt.setMinimumValue(FDCHelper._ONE_HUNDRED);
    KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txt);
    ((ICell)this.bindCellMap.get("imageSchedule")).setEditor(editor);
    sa.setLocked(false);

    KDTMergeManager mm = table.getMergeManager();

    mm.mergeBlock(0, 1, 0, 5, 4);
    mm.mergeBlock(0, 7, 0, 8, 4);
    mm.mergeBlock(0, 10, 0, 11, 4);

    mm.mergeBlock(1, 1, 1, 2, 4);
    mm.mergeBlock(1, 4, 1, 5, 4);
    mm.mergeBlock(1, 7, 1, 8, 4);
    mm.mergeBlock(1, 10, 1, 11, 4);

    mm.mergeBlock(2, 1, 2, 2, 4);
    mm.mergeBlock(2, 4, 2, 5, 4);
    mm.mergeBlock(2, 7, 2, 8, 4);
    mm.mergeBlock(2, 10, 2, 11, 4);

    mm.mergeBlock(3, 0, 4, 0, 4);
    mm.mergeBlock(3, 1, 4, 1, 4);
    mm.mergeBlock(3, 2, 3, 3, 4);
    mm.mergeBlock(3, 4, 3, 5, 4);
    mm.mergeBlock(3, 6, 3, 7, 4);
    mm.mergeBlock(3, 8, 3, 9, 4);
    mm.mergeBlock(3, 10, 3, 11, 4);

    mm.mergeBlock(5, 0, 9, 0, 4);

    mm.mergeBlock(10, 0, 10, 1, 4);

    mm.mergeBlock(11, 0, 11, 1, 4);

    mm.mergeBlock(12, 0, 12, 9, 4);

    mm.mergeBlock(13, 1, 13, 2, 4);
    mm.mergeBlock(13, 4, 13, 5, 4);
    mm.mergeBlock(13, 7, 13, 11, 4);
    mm.mergeBlock(14, 1, 14, 2, 4);
    mm.mergeBlock(14, 4, 14, 5, 4);
    mm.mergeBlock(14, 7, 14, 11, 4);
    mm.mergeBlock(15, 1, 15, 2, 4);
    mm.mergeBlock(15, 4, 15, 5, 4);
    mm.mergeBlock(15, 7, 15, 11, 4);
    mm = null;
  }

  private void addOrgPriceForEntryTable(KDTable table)
  {
    PayReqUtils.bindCell(table, 0, 6, "合同造价原币", "contractOrgPrice", this.bindCellMap);
    PayReqUtils.bindCell(table, 1, 6, "最新造价原币", "latestOrgPrice", this.bindCellMap);
    PayReqUtils.bindCell(table, 2, 6, "本申请单已付原币", "payedOrgAmt", this.bindCellMap);

    PayReqUtils.bindCell(table, 1, 0, "变更指令金额原币", "changeOrgAmt", this.bindCellMap);

    PayReqUtils.bindCell(table, 2, 0, "结算金额原币", "settleOrgAmt", this.bindCellMap);
  }

  private int initDynamicTable(KDTable table, DeductTypeCollection deductTypeCollection)
  {
    int base = 10;
    int rows = 0;
    KDTMergeManager mm = table.getMergeManager();

    String contractId = this.editData.getContractId();
    if ((contractId == null) || (PayReqUtils.isConWithoutTxt(contractId))) {
      return base;
    }

    if ((FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) || (FDCBillStateEnum.AUDITTING.equals(this.editData.getState())))
    {
      DeductOfPayReqBillInfo info = null;
      EntityViewInfo view = new EntityViewInfo();
      FilterInfo filter = new FilterInfo();
      FilterItemCollection items = filter.getFilterItems();
      items.add(new FilterItemInfo("payRequestBill.id", this.editData.getId().toString()));
      view.setFilter(filter);
      SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
      sorterItemInfo.setSortType(SortType.ASCEND);
      view.getSorter().add(sorterItemInfo);
      view.getSelector().add("deductType.number");
      view.getSelector().add("deductType.name");
      view.getSelector().add("*");
      try
      {
        DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
        rows = c.size();
        for (int i = 0; i < rows; i++) {
          info = c.get(i);

          if ((info.getAllPaidAmt() != null) && (info.getAllPaidAmt().compareTo(FDCHelper.ZERO) == 0)) {
            info.setAllPaidAmt(null);
          }
          if ((info.getAllReqAmt() != null) && (info.getAllReqAmt().compareTo(FDCHelper.ZERO) == 0)) {
            info.setAllReqAmt(null);
          }
          if ((info.getAmount() != null) && (info.getAmount().compareTo(FDCHelper.ZERO) == 0)) {
            info.setAmount(null);
          }
          if ((info.getOriginalAmount() != null) && (info.getOriginalAmount().compareTo(FDCHelper.ZERO) == 0)) {
            info.setOriginalAmount(null);
          }
          if ((info.getAllReqOriAmt() != null) && (info.getAllReqOriAmt().compareTo(FDCHelper.ZERO) == 0)) {
            info.setAllReqOriAmt(null);
          }
          if ((info.getAllPaidOriAmt() != null) && (info.getAllPaidOriAmt().compareTo(FDCHelper.ZERO) == 0)) {
            info.setAllPaidOriAmt(null);
          }
          IRow row = table.addRow(base + i);
          row.getCell(1).setValue(info.getDeductType().getName());
          row.getCell(1).getStyleAttributes().setNumberFormat("@");
          row.getCell(3).setValue(info.getAllPaidAmt());
          row.getCell(2).setValue(info.getAllPaidOriAmt());
          row.getCell(4).setValue(info.getAllReqOriAmt());
          row.getCell(5).setValue(info.getAllReqAmt());
          row.getCell(6).setValue(info.getOriginalAmount());
          row.getCell(7).setValue(info.getAmount());
          row.getCell(8).setExpressions("=E" + (base + i + 1) + "+G" + (base + i + 1));
          row.getCell(9).setExpressions("=F" + (base + i + 1) + "+H" + (base + i + 1));

          row.getCell(10).setExpressions("=IF(SUM(K6)>0,(C" + (base + i + 1) + "+G" + (base + i + 1) + "),0" + ")");
          row.getCell(11).setExpressions("=IF(SUM(L6)>0,(D" + (base + i + 1) + "+H" + (base + i + 1) + "),0" + ")");
        }

      }
      catch (BOSException e)
      {
        handUIException(e);
      }
    }
    else {
      HashMap map = getDeductData(this.editData, deductTypeCollection);
      if (map.size() > 0) {
        DeductTypeInfo info = null;
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
        view.setFilter(filter);
        SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
        sorterItemInfo.setSortType(SortType.ASCEND);
        view.getSorter().add(sorterItemInfo);
        view.getSelector().add("number");
        view.getSelector().add("name");
        try
        {
          DeductTypeCollection c = deductTypeCollection;
          rows = c.size();

          for (int i = 0; i < c.size(); i++) {
            info = c.get(i);
            Object[] arrays = (Object[])map.get(info.getId().toString());
            for (int j = 0; j < 5; j++) {
              if (((arrays[j] instanceof BigDecimal)) && (((BigDecimal)arrays[j]).compareTo(FDCHelper.ZERO) == 0)) {
                arrays[j] = null;
              }
            }
            IRow row = table.addRow(base + i);
            row.getCell(1).setValue(info.getName());
            row.getCell(1).getStyleAttributes().setNumberFormat("@");
            row.getCell(2).setValue(arrays[4]);
            row.getCell(3).setValue(arrays[0]);
            row.getCell(4).setValue(arrays[5]);
            row.getCell(5).setValue(arrays[1]);
            row.getCell(6).setValue(arrays[3]);
            row.getCell(7).setValue(arrays[2]);

            row.getCell(8).setExpressions("=E" + (base + i + 1) + "+G" + (base + i + 1));
            row.getCell(9).setExpressions("=F" + (base + i + 1) + "+H" + (base + i + 1));

            row.getCell(10).setExpressions("=IF(SUM(K6)>0,(C" + (base + i + 1) + "+G" + (base + i + 1) + "),0" + ")");

            row.getCell(11).setExpressions("=IF(SUM(L6)>0,(D" + (base + i + 1) + "+H" + (base + i + 1) + "),0" + ")");
          }

        }
        catch (Exception e)
        {
          handUIException(e);
        }
      }

    }

    int lastRowIdx = base + rows;
    IRow row = table.addRow(lastRowIdx);
    row.getCell(1).setValue(getRes("subtotal"));
    if (rows != 0)
    {
      for (char c = 'C'; c <= 'L'; c = (char)(c + '\001')) {
        StringBuffer exp = new StringBuffer("=sum(");
        exp.append(c).append(base + 1).append(':');
        exp.append(c).append(lastRowIdx);
        exp.append(')');
        row.getCell(c - 'A').setExpressions(exp.toString());
      }
      table.getCell(base, 0).setValue(getRes("deductAmtItem"));
      mm.mergeBlock(base, 0, base + rows, 0, 4);
    }

    return lastRowIdx;
  }

  void calcTable()
  {
    IRow row = null;

    row = this.table.getRow(5);
    row.getCell(8).setExpressions("=sum(E6,G6)");

    row.getCell(10).setExpressions("=sum(C6)");
    row.getCell(9).setExpressions("=sum(F6,H6)");

    row.getCell(11).setExpressions("=sum(D6)");

    row = this.table.getRow(6);
    row.getCell(8).setExpressions("=sum(E7,G7)");

    row.getCell(10).setExpressions("=sum(C7)");
    row.getCell(9).setExpressions("=sum(F7,H7)");

    row.getCell(11).setExpressions("=sum(D7)");

    row = this.table.getRow(7);
    row.getCell(8).setExpressions("=sum(E8,G8)");

    row.getCell(10).setExpressions("=sum(C8)");
    row.getCell(9).setExpressions("=sum(F8,H8)");

    row.getCell(11).setExpressions("=sum(D8)");

    row = this.table.getRow(8);
    row.getCell(8).setExpressions("=sum(E9,G9)");

    row.getCell(10).setExpressions("=sum(C9)");
    row.getCell(9).setExpressions("=sum(F9,H9)");

    row.getCell(11).setExpressions("=sum(D9)");

    row = this.table.getRow(9);
    row.getCell(8).setExpressions("=sum(E10,G10)");

    row.getCell(10).setExpressions("=sum(C10)");
    row.getCell(9).setExpressions("=sum(F10,H10)");

    row.getCell(11).setExpressions("=sum(D10)");

    int lastRowIdx = this.table.getRowCount() - 1;

    row = this.table.getRow(lastRowIdx - 5);

    row.getCell(6).getStyleAttributes().setLocked(true);
    row.getCell(8).setExpressions("=E" + (lastRowIdx - 4) + "+G" + (lastRowIdx - 4));
    row.getCell(9).setExpressions("=F" + (lastRowIdx - 4) + "+H" + (lastRowIdx - 4));

    row.getCell(10).setExpressions("=(C" + (lastRowIdx - 4) + ")");

    row.getCell(11).setExpressions("=(D" + (lastRowIdx - 4) + ")");

    int paidRowIdx = lastRowIdx - 4;
    row = this.table.getRow(paidRowIdx);
    ICell cell = null;

    for (char c = 'C'; c <= 'L'; c = (char)(c + '\001')) {
      cell = row.getCell(c - 'A');
      StringBuffer exp = new StringBuffer("=");
      exp.append(c).append(6).append("+");
      exp.append(c).append(7).append("+");
      exp.append(c).append(9).append("-");
      exp.append(c).append(10).append("-");
      exp.append(c).append(paidRowIdx).append("-");
      exp.append(c).append(paidRowIdx - 1);
      cell.setExpressions(exp.toString());
    }

    this.table.getCell(paidRowIdx + 1, 11).setExpressions("=K2-L6");
    this.table.getCell(paidRowIdx + 1, 10).setExpressions("=H2-K6");

    row = this.table.getRow(lastRowIdx - 1);

    if ((this.editData.getLatestPrice() != null) && (this.editData.getLatestPrice().compareTo(FDCHelper.ZERO) > 0))
    {
      StringBuffer exp = new StringBuffer("=(");
      exp.append("H").append(6).append("/");
      exp.append("K2)*100");
      row.getCell(1).setExpressions(exp.toString());

      exp = new StringBuffer("=(");
      exp.append("J").append(6).append("/");
      exp.append("K2)*100");
      row.getCell(4).setExpressions(exp.toString());

      row = this.table.getRow(lastRowIdx);
      exp = new StringBuffer("=(");
      exp.append("H").append(lastRowIdx - 3).append("/");
      exp.append("K2)*100");
      row.getCell(1).setExpressions(exp.toString());

      exp = new StringBuffer("=(");
      exp.append("J").append(lastRowIdx - 3).append("/");
      exp.append("K2)*100");
      row.getCell(4).setExpressions(exp.toString());
    }
  }

  void setTableCellColorAndEdit(KDTable table)
  {
    int lastRowIdx = table.getRowCount() - 1;
    table.setRefresh(false);
    table.setEditable(true);
    table.setEnabled(true);

    StyleAttributes sa = table.getStyleAttributes();

    sa.setBackground(noEditColor);

    table.getCell(5, 6).getStyleAttributes().setLocked(false);
    table.getCell(5, 6).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
    table.getCell(6, 6).getStyleAttributes().setLocked(false);
  }

  void reloadDeductTable(PayRequestBillInfo editData, KDTable table, DeductTypeCollection c)
    throws Exception
  {
    int base = 10;

    HashMap map = getDeductData(editData, c);
    if (map.size() > 0) {
      DeductTypeInfo info = null;
      try
      {
        for (int i = 0; i < c.size(); i++) {
          info = c.get(i);
          Object[] arrays = (Object[])map.get(info.getId().toString());
          for (int j = 0; j < 4; j++) {
            if (((arrays[j] instanceof BigDecimal)) && (((BigDecimal)arrays[j]).compareTo(FDCHelper.ZERO) == 0)) {
              arrays[j] = null;
            }
          }
          IRow row = table.getRow(base + i);
          if (row == null) return;
          row.getCell(1).setValue(info.getName());
          row.getCell(1).getStyleAttributes().setNumberFormat("@");
          row.getCell(2).setValue(arrays[4]);
          row.getCell(3).setValue(arrays[0]);
          row.getCell(4).setValue(arrays[5]);
          row.getCell(5).setValue(arrays[1]);
          row.getCell(6).setValue(arrays[3]);
          row.getCell(7).setValue(arrays[2]);
        }
      } catch (Exception e) {
        handUIException(e);
      }
    }
  }

  private HashMap getDeductData(PayRequestBillInfo editData, DeductTypeCollection deductTypeCollection)
  {
    String contractId = editData.getContractId();
    if (contractId == null) return null;

    HashMap map = new HashMap();

    EntityViewInfo view = new EntityViewInfo();
    FilterInfo filter = new FilterInfo();
    filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    view.setFilter(filter);
    SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
    sorterItemInfo.setSortType(SortType.ASCEND);
    view.getSorter().add(sorterItemInfo);
    view.getSelector().add("id");
    try
    {
      DeductTypeInfo info = null;
      DeductTypeCollection c = deductTypeCollection;
      for (int i = 0; i < c.size(); i++)
      {
        info = c.get(i);
        Object[] o = new Object[6];
        map.put(info.getId().toString(), o);
      }
    } catch (Exception e) {
      handUIException(e);
    }

    if (map.size() <= 0) {
      return map;
    }

    if (this.editUI.getOprtState().equals(OprtState.ADDNEW))
    {
      Set notIncludeSet = new HashSet();
      view = new EntityViewInfo();
      FilterInfo filter2 = new FilterInfo();
      FilterItemCollection items2 = filter2.getFilterItems();
      items2.add(new FilterItemInfo("parent.PayRequestBill.contractId", contractId, CompareType.EQUALS));
      SelectorItemCollection selector = new SelectorItemCollection();
      selector.add("deductBillEntry.id");
      view.setFilter(filter2);
      try
      {
        DeductOfPayReqBillEntryCollection c = DeductOfPayReqBillEntryFactory.getRemoteInstance().getDeductOfPayReqBillEntryCollection(view);

        for (int i = 0; i < c.size(); i++) {
          DeductOfPayReqBillEntryInfo info = c.get(i);
          notIncludeSet.add(info.getDeductBillEntry().getId().toString());
        }
      } catch (BOSException e1) {
        handUIException(e1);
      }

      view = new EntityViewInfo();
      filter = new FilterInfo();
      FilterItemCollection items = filter.getFilterItems();
      items = filter.getFilterItems();
      items.add(new FilterItemInfo("contractId", contractId, CompareType.EQUALS));
      items.add(new FilterItemInfo("hasApplied", Boolean.FALSE, CompareType.EQUALS));
      items.add(new FilterItemInfo("Parent.state", "4AUDITTED", CompareType.EQUALS));
      if (notIncludeSet.size() > 0) {
        items.add(new FilterItemInfo("id", notIncludeSet, CompareType.NOTINCLUDE));
      }
      view.setFilter(filter);
      try
      {
        DeductBillEntryCollection c = DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryCollection(view);
        for (int i = 0; i < c.size(); i++) {
          DeductBillEntryInfo info = c.get(i);
          Object o = map.get(info.getDeductType().getId().toString());
          if (o != null) {
            Object[] arrays = (Object[])o;

            if (arrays[2] == null) {
              arrays[2] = info.getDeductAmt();
            } else {
              BigDecimal v = (BigDecimal)arrays[2];
              arrays[2] = v.add(info.getDeductAmt());
            }

            if (arrays[3] == null) {
              arrays[3] = info.getDeductOriginalAmt();
            } else {
              BigDecimal v = (BigDecimal)arrays[3];
              arrays[3] = v.add(info.getDeductOriginalAmt());
            }
          }
        }
      } catch (BOSException e) {
        handUIException(e);
      }

      view = new EntityViewInfo();
      filter = new FilterInfo();
      items = filter.getFilterItems();
      items = filter.getFilterItems();
      items.add(new FilterItemInfo("payRequestBill.contractId", contractId, CompareType.EQUALS));
      view.setFilter(filter);
      try {
        DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);

        for (int i = 0; i < c.size(); i++) {
          DeductOfPayReqBillInfo info = c.get(i);
          Object o = map.get(info.getDeductType().getId().toString());
          if (o != null) {
            Object[] arrays = (Object[])o;

            BigDecimal amount = info.getAmount();

            if (arrays[1] == null) {
              arrays[1] = amount;
            } else {
              BigDecimal v = (BigDecimal)arrays[1];
              arrays[1] = v.add(amount);
            }

            BigDecimal oriAmount = info.getOriginalAmount();
            if (arrays[5] == null) {
              arrays[5] = oriAmount;
            } else {
              BigDecimal ov = (BigDecimal)arrays[5];
              arrays[5] = ov.add(oriAmount);
            }

            if (!info.isHasPaid())
            {
              continue;
            }
            if (arrays[0] == null) {
              arrays[0] = amount;
            } else {
              BigDecimal v = (BigDecimal)arrays[0];
              arrays[0] = v.add(amount);
            }

            if (arrays[4] == null) {
              arrays[4] = oriAmount;
            } else {
              BigDecimal ov = (BigDecimal)arrays[4];
              arrays[4] = ov.add(oriAmount);
            }
          }
        }
      }
      catch (BOSException e)
      {
        handUIException(e);
      }
    }
    else
    {
      try {
        DeductOfPayReqBillFactory.getRemoteInstance().reCalcAmount(editData.getId().toString());
      } catch (Exception e1) {
        handUIException(e1);
      }

      view = new EntityViewInfo();
      filter = new FilterInfo();
      FilterItemCollection items = filter.getFilterItems();
      items = filter.getFilterItems();

      items.add(new FilterItemInfo("payRequestBill.id", editData.getId().toString(), CompareType.EQUALS));
      view.setFilter(filter);
      try
      {
        DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
        for (int i = 0; i < c.size(); i++) {
          DeductOfPayReqBillInfo info = c.get(i);
          Object o = map.get(info.getDeductType().getId().toString());
          if (o != null) {
            Object[] arrays = (Object[])o;

            arrays[5] = info.getAllReqOriAmt();

            arrays[4] = info.getAllPaidOriAmt();

            arrays[3] = info.getOriginalAmount();

            arrays[2] = info.getAmount();

            arrays[1] = info.getAllReqAmt();

            arrays[0] = info.getAllPaidAmt();
          }
        }
      }
      catch (BOSException e) {
        handUIException(e);
      }
    }

    return map;
  }

  void updateDynamicValue(PayRequestBillInfo editData, ContractBillInfo contractBill, ContractChangeBillCollection collection, BillBaseCollection billBaseCollection)
    throws Exception
  {
    boolean isUpdateCell = this.bindCellMap.get("changeAmt") != null;

    BigDecimal amount = FDCHelper.ZERO;
    BigDecimal orgAmount = FDCHelper.ZERO;

    for (Iterator iter = collection.iterator(); iter.hasNext(); )
    {
      ContractChangeBillInfo billInfo = (ContractChangeBillInfo)iter.next();
      if (billInfo.isHasSettled()) {
        amount = amount.add(FDCHelper.toBigDecimal(billInfo.getBalanceAmount()));
        orgAmount = orgAmount.add(FDCHelper.toBigDecimal(billInfo.getOriBalanceAmount())); continue;
      }
      amount = amount.add(FDCHelper.toBigDecimal(billInfo.getAmount()));
      orgAmount = orgAmount.add(FDCHelper.toBigDecimal(billInfo.getOriBalanceAmount()));
    }

    editData.setChangeAmt(amount);
    if (isUpdateCell)
    {
      if ((amount != null) && (amount.compareTo(FDCHelper.ZERO) != 0))
      {
        ((ICell)this.bindCellMap.get("changeAmt")).setValue(amount);
      }
      else ((ICell)this.bindCellMap.get("changeAmt")).setValue(null);

      if ((orgAmount != null) && (orgAmount.compareTo(FDCHelper.ZERO) != 0))
      {
        ((ICell)this.bindCellMap.get("changeOrgAmt")).setValue(orgAmount);
      }
      else ((ICell)this.bindCellMap.get("changeOrgAmt")).setValue(null);

    }

    editData.setSettleAmt(contractBill.getSettleAmt());
    if (isUpdateCell)
    {
      if (contractBill.getSettleAmt() != null)
      {
        ((ICell)this.bindCellMap.get("settleAmt")).setValue(contractBill.getSettleAmt());
      }
      else ((ICell)this.bindCellMap.get("settleAmt")).setValue(null);

    }

    amount = FDCHelper.ZERO;

    if (!contractBill.isHasSettled())
    {
      BigDecimal guerdonAmt = FDCHelper.ZERO;
      BigDecimal guerdonOriginalAmt = FDCHelper.ZERO;
      BigDecimal compenseAmt = FDCHelper.ZERO;
      BigDecimal compenseOriginalAmt = FDCHelper.ZERO;
      BigDecimal deductAmt = FDCHelper.ZERO;
      BigDecimal deductOriginalAmt = FDCHelper.ZERO;
      FDCSQLBuilder builder = new FDCSQLBuilder();
      builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_GuerdonBill where  fcontractid =? AND fstate = ? AND fisGuerdoned = 1");
      builder.addParam(contractBill.getId().toString());
      builder.addParam("4AUDITTED");
      IRowSet rowSet = builder.executeQuery();
      if (rowSet.size() == 1) {
        rowSet.next();
        guerdonAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
        guerdonOriginalAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
      }

      builder.clear();
      builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_CompensationBill where  fcontractid =? AND fstate = ? AND fisCompensated = 1");
      builder.addParam(contractBill.getId().toString());
      builder.addParam("4AUDITTED");
      rowSet = builder.executeQuery();
      if (rowSet.size() == 1) {
        rowSet.next();
        compenseAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
        compenseOriginalAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
      }

      builder.clear();
      builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_DeductOfPayReqBill where fpayRequestBillId in (select fid from T_CON_PayRequestBill where fcontractid=?)");

      builder.addParam(contractBill.getId().toString());
      rowSet = builder.executeQuery();
      if (rowSet.size() == 1) {
        rowSet.next();
        deductAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
        deductOriginalAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
      }

      amount = (BigDecimal)FDCUtils.getLastAmt_Batch(null, new String[] { contractBill.getId().toString() }).get(contractBill.getId().toString());
    }
    else
    {
      amount = contractBill.getSettleAmt();
    }

    editData.setLatestPrice(amount);
    if (isUpdateCell) {
      if (amount != null)
      {
        ((ICell)this.bindCellMap.get("latestPrice")).setValue(amount);
      }
      else {
        ((ICell)this.bindCellMap.get("latestPrice")).setValue(null);
      }
    }

    if (isUpdateCell)
    {
      int lastRowIdx = this.editUI.getDetailTable().getRowCount() - 1;
      IRow row = this.editUI.getDetailTable().getRow(lastRowIdx);

      if (FDCHelper.isPositiveBigDecimal(editData.getLatestPrice()))
      {
        StringBuffer exp = new StringBuffer("=(");
        exp.append("H").append(6).append("/");
        exp.append("K2)*100");
        row.getCell(1).setExpressions(exp.toString());

        exp = new StringBuffer("=(");
        exp.append("J").append(6).append("/");
        exp.append("K2)*100");
        row.getCell(4).setExpressions(exp.toString());

        row = this.table.getRow(lastRowIdx);
        exp = new StringBuffer("=(");
        exp.append("H").append(lastRowIdx - 3).append("/");
        exp.append("K2)*100");
        row.getCell(1).setExpressions(exp.toString());
        exp = new StringBuffer("=(");
        exp.append("J").append(lastRowIdx - 3).append("/");
        exp.append("K2)*100");
        row.getCell(4).setExpressions(exp.toString());
      }
      else
      {
        row.getCell(1).setValue(null);
        row.getCell(4).setValue(null);
      }
    }
    updateLstReqAmt(editData, isUpdateCell);

    int size = billBaseCollection.size();
    amount = FDCHelper.ZERO;
    Iterator iter;
    if ((editData != null) && (editData.getId() != null)) {
      for (iter = billBaseCollection.iterator(); iter.hasNext(); ) {
        PaymentBillInfo tmp = (PaymentBillInfo)iter.next();
        if (tmp.getFdcPayReqID().equals(editData.getId().toString())) {
          amount.add(FDCHelper.toBigDecimal(tmp.getAmount()));
        }
      }
    }
    editData.setPayedAmt(amount);

    editData.setPayTimes(size);
    if (isUpdateCell) {
      ((ICell)this.bindCellMap.get("payTimes")).setValue(String.valueOf(size));
      ((ICell)this.bindCellMap.get("payedAmt")).setValue(amount);
    }
  }

  void updateLstReqAmt(PayRequestBillInfo editData, boolean isUpdateCell)
    throws BOSException
  {
    BigDecimal lstReqAmt = FDCHelper.ZERO;
    BigDecimal lstaddProjectAmt = FDCHelper.ZERO;
    BigDecimal lstpayPartAMatAmt = FDCHelper.ZERO;

    EntityViewInfo view = new EntityViewInfo();
    view.getSelector().add("projectPriceInContract");
    view.getSelector().add("addProjectAmt");
    view.getSelector().add("payPartAMatlAmt");
    view.getSelector().add("hasClosed");
    view.getSelector().add("entrys.projectPriceInContract");
    view.getSelector().add("entrys.addProjectAmt");
    view.getSelector().add("entrys.payPartAMatlAmt");
    FilterInfo filter = new FilterInfo();
    filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(), CompareType.LESS));
    filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));

    view.setFilter(filter);
    PayRequestBillCollection cols = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

    for (Iterator it = cols.iterator(); it.hasNext(); ) {
      PayRequestBillInfo info = (PayRequestBillInfo)it.next();
      Iterator iter;
      if (info.isHasClosed()) {
        for (iter = info.getEntrys().iterator(); iter.hasNext(); ) {
          PayRequestBillEntryInfo entry = (PayRequestBillEntryInfo)iter.next();
          lstReqAmt = FDCHelper.add(lstReqAmt, entry.getProjectPriceInContract());
          lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt, entry.getAddProjectAmt());
          lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt, entry.getPayPartAMatlAmt());
        }
      } else {
        lstReqAmt = FDCHelper.add(lstReqAmt, info.getProjectPriceInContract());
        lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt, info.getAddProjectAmt());
        lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt, info.getPayPartAMatlAmt());
      }
    }

    FDCSQLBuilder builder = new FDCSQLBuilder();
    editData.setLstPrjAllReqAmt(lstReqAmt);
    editData.setLstAddPrjAllReqAmt(lstaddProjectAmt);
    editData.setLstAMatlAllReqAmt(lstpayPartAMatAmt);

    if (isUpdateCell) {
      ((ICell)this.bindCellMap.get("lstPrjAllReqAmt")).setValue(lstReqAmt);
      ((ICell)this.bindCellMap.get("lstAddPrjAllReqAmt")).setValue(lstaddProjectAmt);

      ((ICell)this.bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstpayPartAMatAmt);
    }

    if (((editData.getState() == FDCBillStateEnum.SAVED) || (editData.getState() == FDCBillStateEnum.SUBMITTED)) && 
      (PayReqUtils.isContractBill(editData.getContractId()))) {
      builder.clear();
      builder = new FDCSQLBuilder();
      builder.appendSql("select fprjPriceInConPaid as amount from T_CON_ContractBill where fid=?");
      builder.addParam(editData.getContractId());
      IRowSet rowSet = builder.executeQuery();
      if ((rowSet != null) && (rowSet.size() == 1))
        try {
          rowSet.next();
          setCellValue("LstPrjAllPaidAmt", rowSet.getBigDecimal("amount"));
          editData.setLstPrjAllPaidAmt(rowSet.getBigDecimal("amount"));
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }
  }

  void updateLstAdvanceAmt(PayRequestBillInfo editData, boolean isUpdate)
    throws BOSException, EASBizException
  {
    BigDecimal lstAdvanceAllPaid = FDCHelper.ZERO;
    BigDecimal lstAdvanceAllReq = FDCHelper.ZERO;
    BigDecimal advance = FDCHelper.ZERO;
    BigDecimal locAdvance = FDCHelper.ZERO;
    BigDecimal advanceAllReq = FDCHelper.ZERO;
    if (!isUpdate) {
      EntityViewInfo view = new EntityViewInfo();
      FilterInfo filter = new FilterInfo();
      view.setFilter(filter);
      filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
      filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(), CompareType.LESS));

      view.getSelector().add("prjPayEntry.id");
      view.getSelector().add("prjPayEntry.locAdvance");
      view.getSelector().add("entrys.paymentBill.billStatus");
      view.getSelector().add("entrys.paymentBill.prjPayEntry.advance");
      view.getSelector().add("entrys.paymentBill.prjPayEntry.locAdvance");
      PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

      for (int i = 0; i < c.size(); i++) {
        PayRequestBillInfo info = c.get(i);
        if (info.getId().equals(editData.getId())) {
          continue;
        }
        PayRequestBillEntryCollection entry = info.getEntrys();
        if ((entry != null) && (entry.size() > 0)) {
          for (int j = 0; j < entry.size(); j++) {
            PaymentBillInfo payment = info.getEntrys().get(j).getPaymentBill();
            if ((!BillStatusEnum.PAYED.equals(payment.getBillStatus())) || 
              (payment.getPrjPayEntry() == null)) continue;
            lstAdvanceAllPaid = FDCHelper.add(lstAdvanceAllPaid, payment.getPrjPayEntry().getLocAdvance());
          }

        }

        if (info.getPrjPayEntry() != null) {
          lstAdvanceAllReq = FDCHelper.add(lstAdvanceAllReq, info.getPrjPayEntry().getLocAdvance());
        }
      }
      if (FDCHelper.ZERO.compareTo(lstAdvanceAllPaid) == 0) {
        lstAdvanceAllPaid = null;
      }
      if (FDCHelper.ZERO.compareTo(lstAdvanceAllReq) == 0) {
        lstAdvanceAllReq = null;
      }
      ((ICell)this.bindCellMap.get("lstAdvanceAllPaid")).setValue(lstAdvanceAllPaid);
      ((ICell)this.bindCellMap.get("lstAdvanceAllReq")).setValue(lstAdvanceAllReq);
      if (editData.getPrjPayEntry() != null)
      {
        ((ICell)this.bindCellMap.get("advance")).setValue(editData.getPrjPayEntry().getAdvance());
        ((ICell)this.bindCellMap.get("locAdvance")).setValue(editData.getPrjPayEntry().getLocAdvance());
        ((ICell)this.bindCellMap.get("advanceAllReq")).setValue(editData.getPrjPayEntry().getAdvanceAllReq());
      }

      ((ICell)this.bindCellMap.get("advanceAllPaid")).setValue(lstAdvanceAllPaid);
    } else {
      lstAdvanceAllPaid = FDCHelper.toBigDecimal(((ICell)this.bindCellMap.get("lstAdvanceAllPaid")).getValue());
      lstAdvanceAllReq = FDCHelper.toBigDecimal(((ICell)this.bindCellMap.get("lstAdvanceAllReq")).getValue());
      advance = FDCHelper.toBigDecimal(((ICell)this.bindCellMap.get("advance")).getValue());
      locAdvance = FDCHelper.toBigDecimal(((ICell)this.bindCellMap.get("locAdvance")).getValue());
      advanceAllReq = FDCHelper.toBigDecimal(((ICell)this.bindCellMap.get("advanceAllReq")).getValue());
      PayReqPrjPayEntryInfo prjPayEntry = editData.getPrjPayEntry();
      if (prjPayEntry == null) {
        prjPayEntry = new PayReqPrjPayEntryInfo();
      }
      prjPayEntry.setLstAdvanceAllPaid(lstAdvanceAllPaid);
      prjPayEntry.setLstAdvanceAllReq(lstAdvanceAllReq);
      prjPayEntry.setAdvance(advance);
      prjPayEntry.setLocAdvance(locAdvance);
      prjPayEntry.setAdvanceAllReq(advanceAllReq);
      prjPayEntry.setAdvanceAllPaid(lstAdvanceAllPaid);
      editData.setPrjPayEntry(prjPayEntry);
    }
  }

  void checkAdvance(PayRequestBillInfo editData, Map bindCellMap)
    throws BOSException
  {
    EntityViewInfo view = new EntityViewInfo();
    FilterInfo filter = new FilterInfo();
    view.setFilter(filter);
    filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
    filter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
    if (editData.getId() != null) {
      filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
    }
    view.getSelector().add("prjPayEntry.advance");
    PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

    BigDecimal advance = FDCHelper.ZERO;
    if (c != null) {
      for (int i = 0; i < c.size(); i++) {
        PayRequestBillInfo info = c.get(i);
        if (info.getPrjPayEntry() != null) {
          advance = FDCHelper.add(advance, info.getPrjPayEntry().getAdvance());
        }
      }
    }
    Object cellValue = ((ICell)bindCellMap.get("advance")).getValue();
    advance = FDCHelper.add(advance, cellValue);
    if (FDCHelper.ZERO.compareTo(advance) == 1) {
      MsgBox.showError("合同下已审批状态的付款申请单的 预付款本次申请原币 + 本张单据的预付款本次申请原币 必须大于0");
      SysUtil.abort();
    }
  }

  public void getAdvanceValueFromCell(PayRequestBillInfo editData, HashMap bindCellMap)
  {
  }

  void reloadGuerdonValue(PayRequestBillInfo editData, BigDecimal amount)
    throws Exception
  {
    if (!PayReqUtils.isContractBill(editData.getContractId())) {
      return;
    }

    BigDecimal originalamount = FDCHelper.ZERO;
    amount = FDCHelper.ZERO;
    BigDecimal lstPaidAmt = FDCHelper.ZERO;
    BigDecimal lstReqAmt = FDCHelper.ZERO;
    EntityViewInfo view = new EntityViewInfo();
    view.getSelector().add("amount");
    view.getSelector().add("originalAmount");
    view.getSelector().add("hasPaid");
    FilterInfo filter = new FilterInfo();
    filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    view.setFilter(filter);

    Timestamp createTime = editData.getCreateTime();
    filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime", createTime, CompareType.LESS_EQUALS));
    GuerdonOfPayReqBillCollection c = GuerdonOfPayReqBillFactory.getRemoteInstance().getGuerdonOfPayReqBillCollection(view);
    for (int i = 0; i < c.size(); i++) {
      GuerdonOfPayReqBillInfo info = c.get(i);
      if (info.getAmount() != null) {
        if (info.getPayRequestBill().getId().equals(editData.getId())) {
          amount = amount.add(FDCHelper.toBigDecimal(info.getAmount()));
          originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
        } else {
          if (info.isHasPaid()) {
            lstPaidAmt = info.getAmount().add(lstPaidAmt);
          }

          lstReqAmt = info.getAmount().add(lstReqAmt);
        }
      }
    }

    if (amount.compareTo(FDCHelper.ZERO) == 0) {
      amount = null;
    }
    if (originalamount.compareTo(FDCHelper.ZERO) == 0) {
      originalamount = null;
    }
    if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstPaidAmt = null;
    }
    if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstReqAmt = null;
    }
    ((ICell)this.bindCellMap.get("guerdonAmt")).setValue(amount);
    ((ICell)this.bindCellMap.get("guerdonOriginalAmt")).setValue(originalamount);
    ((ICell)this.bindCellMap.get("lstGuerdonPaidAmt")).setValue(lstPaidAmt);
    ((ICell)this.bindCellMap.get("lstGuerdonReqAmt")).setValue(lstReqAmt);
  }

  void updateGuerdonValue(PayRequestBillInfo objectValue, String contractBillId, GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection, GuerdonBillCollection guerdonBillCollection)
    throws BOSException
  {
    BigDecimal lstPaidAmt = FDCHelper.ZERO;
    BigDecimal lstReqAmt = FDCHelper.ZERO;

    for (int i = 0; i < guerdonOfPayReqBillCollection.size(); i++) {
      GuerdonOfPayReqBillInfo info = guerdonOfPayReqBillCollection.get(i);
      if (info.getAmount() != null) {
        if (info.isHasPaid()) {
          lstPaidAmt = info.getAmount().add(lstPaidAmt);
        }

        lstReqAmt = info.getAmount().add(lstReqAmt);
      }
    }
    if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstPaidAmt = null;
    }
    if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstReqAmt = null;
    }
    objectValue.put("lstGuerdonPaidAmt", lstPaidAmt);
    objectValue.put("lstGuerdonReqAmt", lstReqAmt);

    BigDecimal sum = FDCHelper.ZERO;
    BigDecimal sumOriginal = FDCHelper.ZERO;
    for (int i = 0; i < guerdonBillCollection.size(); i++) {
      GuerdonBillInfo item = guerdonBillCollection.get(i);
      if (item.getAmount() != null) {
        sum = sum.add(item.getAmount());
      }
      if (item.getOriginalAmount() != null) {
        sumOriginal = sumOriginal.add(item.getOriginalAmount());
      }
    }
    if (sum.compareTo(FDCHelper.ZERO) == 0) {
      sum = null;
    }
    if (sumOriginal.compareTo(FDCHelper.ZERO) == 0) {
      sumOriginal = null;
    }
    objectValue.put("guerdonAmt", sum);
    objectValue.put("guerdonOriginalAmt", sumOriginal);

    if ((this.bindCellMap.get("guerdonAmt") != null) && (this.bindCellMap.get("guerdonOriginalAmt") != null)) {
      ((ICell)this.bindCellMap.get("guerdonAmt")).setValue(sum);
      ((ICell)this.bindCellMap.get("guerdonOriginalAmt")).setValue(sumOriginal);
      ((ICell)this.bindCellMap.get("lstGuerdonPaidAmt")).setValue(lstPaidAmt);
      ((ICell)this.bindCellMap.get("lstGuerdonReqAmt")).setValue(lstReqAmt);
    }
  }

  void reloadCompensationValue(PayRequestBillInfo editData, BigDecimal amount)
    throws Exception
  {
    if (!PayReqUtils.isContractBill(editData.getContractId())) {
      return;
    }
    BigDecimal originalamount = FDCHelper.ZERO;
    amount = FDCHelper.ZERO;
    BigDecimal lstPaidAmt = FDCHelper.ZERO;
    BigDecimal lstReqAmt = FDCHelper.ZERO;
    EntityViewInfo view = new EntityViewInfo();
    FilterInfo filter = new FilterInfo();
    filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    view.setFilter(filter);

    Timestamp createTime = editData.getCreateTime();
    filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime", createTime, CompareType.LESS_EQUALS));

    CompensationOfPayReqBillCollection c = CompensationOfPayReqBillFactory.getRemoteInstance().getCompensationOfPayReqBillCollection(view);
    for (int i = 0; i < c.size(); i++) {
      CompensationOfPayReqBillInfo info = c.get(i);
      if (info.getAmount() != null) {
        if (info.getPayRequestBill().getId().equals(editData.getId())) {
          amount = amount.add(FDCHelper.toBigDecimal(info.getAmount()));
          originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
        } else {
          if (info.isHasPaid()) {
            lstPaidAmt = info.getAmount().add(lstPaidAmt);
          }

          lstReqAmt = info.getAmount().add(lstReqAmt);
        }
      }
    }
    if (amount.compareTo(FDCHelper.ZERO) == 0) {
      amount = null;
    }
    if (originalamount.compareTo(FDCHelper.ZERO) == 0) {
      originalamount = null;
    }
    if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstPaidAmt = null;
    }
    if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstReqAmt = null;
    }
    ((ICell)this.bindCellMap.get("compensationAmt")).setValue(amount);
    ((ICell)this.bindCellMap.get("compensationOriginalAmt")).setValue(originalamount);
    ((ICell)this.bindCellMap.get("lstCompensationPaidAmt")).setValue(lstPaidAmt);
    ((ICell)this.bindCellMap.get("lstCompensationReqAmt")).setValue(lstReqAmt);
  }

  void updateCompensationValue(PayRequestBillInfo objectValue, String contractBillId, CompensationOfPayReqBillCollection compensationOfPayReqBillCollection)
    throws BOSException
  {
    BigDecimal lstPaidAmt = FDCHelper.ZERO;
    BigDecimal lstReqAmt = FDCHelper.ZERO;
    for (int i = 0; i < compensationOfPayReqBillCollection.size(); i++) {
      CompensationOfPayReqBillInfo info = compensationOfPayReqBillCollection.get(i);
      if (info.getAmount() != null) {
        if (info.isHasPaid()) {
          lstPaidAmt = info.getAmount().add(lstPaidAmt);
        }

        lstReqAmt = info.getAmount().add(lstReqAmt);
      }
    }
    if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstPaidAmt = null;
    }
    if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstReqAmt = null;
    }
    objectValue.put("lstCompensationPaidAmt", lstPaidAmt);
    objectValue.put("lstCompensationReqAmt", lstReqAmt);

    BigDecimal sum = FDCHelper.ZERO;
    BigDecimal sumOriginal = FDCHelper.ZERO;

    if (sum.compareTo(FDCHelper.ZERO) == 0) {
      sum = null;
    }
    if (sumOriginal.compareTo(FDCHelper.ZERO) == 0) {
      sumOriginal = null;
    }
    objectValue.put("compensationAmt", sum);
    objectValue.put("compensationOriginalAmt", sumOriginal);

    if ((this.bindCellMap.get("compensationAmt") != null) && (this.bindCellMap.get("compensationOriginalAmt") != null)) {
      ((ICell)this.bindCellMap.get("compensationAmt")).setValue(sum);
      ((ICell)this.bindCellMap.get("compensationOriginalAmt")).setValue(sumOriginal);
      ((ICell)this.bindCellMap.get("lstCompensationPaidAmt")).setValue(lstPaidAmt);
      ((ICell)this.bindCellMap.get("lstCompensationReqAmt")).setValue(lstReqAmt);
    }
  }

  private String getRes(String resName)
  {
    return PayReqUtils.getRes(resName);
  }

  private void handUIException(Exception e) {
    this.editUI.handUIException(e);
  }

  public ICell getCell(String key) {
    Object object = this.bindCellMap.get(key);
    if ((object instanceof ICell)) {
      return (ICell)object;
    }

    return null;
  }

  public Object getCellValue(String key) {
    ICell cell = getCell(key);
    if (cell != null) {
      return cell.getValue();
    }
    return null;
  }

  public void setCellValue(String key, Object value) {
    ICell cell = getCell(key);
    if (cell != null)
      cell.setValue(value);
  }

  public void debugCellExp()
  {
  }

  void setLstPrict()
  {
    if (this.editUI.getOprtState() == OprtState.ADDNEW)
    {
      BigDecimal latestPrice = FDCHelper.toBigDecimal(getCellValue("latestPrice"));
      BigDecimal gueronAmt = FDCHelper.toBigDecimal(getCellValue("guerdonAmt"));
      BigDecimal compensationAmt = FDCHelper.toBigDecimal(getCellValue("compensationAmt"));
      ICell cell = this.table.getCell(this.table.getRowCount() - 6, 5);
      BigDecimal psubTotal = FDCHelper.toBigDecimal(cell.getValue());
      latestPrice = latestPrice.add(gueronAmt).subtract(compensationAmt).subtract(psubTotal);
      setCellValue("latestPrice", latestPrice);
    }
  }

  void reloadPartAValue(PayRequestBillInfo editData, BigDecimal amount)
    throws Exception
  {
    BigDecimal originalAmount = FDCHelper.ZERO;
    if (amount == null) {
      amount = FDCHelper.ZERO;
    }
    if (amount.compareTo(FDCHelper.ZERO) == 1) {
      amount = FDCHelper.ZERO;
      ((ICell)this.bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    }

    BigDecimal lstPaidAmt = FDCHelper.ZERO;
    BigDecimal lstReqAmt = FDCHelper.ZERO;
    BigDecimal allReqAmt = FDCHelper.ZERO;
    EntityViewInfo view = new EntityViewInfo();
    FilterInfo filter = new FilterInfo();
    filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    view.setFilter(filter);

    Timestamp createTime = editData.getCreateTime();
    filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime", createTime, CompareType.LESS_EQUALS));

    PartAOfPayReqBillCollection c = PartAOfPayReqBillFactory.getRemoteInstance().getPartAOfPayReqBillCollection(view);
    for (int i = 0; i < c.size(); i++) {
      PartAOfPayReqBillInfo info = c.get(i);
      if (info.getAmount() == null)
        continue;
      if (info.getPayRequestBill().getId().equals(editData.getId())) {
        amount = amount.add(FDCHelper.toBigDecimal(info.getAmount()));
        BigDecimal tem = FDCHelper.toBigDecimal(info.getOriginalAmount()).compareTo(FDCHelper.ZERO) > 0 ? FDCHelper.toBigDecimal(info.getOriginalAmount()) : FDCHelper.toBigDecimal(info.getAmount());

        originalAmount = originalAmount.add(tem);
      } else {
        if (info.isHasPaid()) {
          lstPaidAmt = info.getAmount().add(lstPaidAmt);
        }
        lstReqAmt = info.getAmount().add(lstReqAmt);
      }
    }

    allReqAmt = lstReqAmt.add(amount);
    if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstPaidAmt = null;
    }
    if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstReqAmt = null;
    }
    if (originalAmount.compareTo(FDCHelper.ZERO) == 0) {
      originalAmount = null;
    }
    if (allReqAmt.compareTo(FDCHelper.ZERO) == 0) {
      allReqAmt = null;
    }
    ((ICell)this.bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    ((ICell)this.bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    ((ICell)this.bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    ((ICell)this.bindCellMap.get("payPartAMatlOriAmt")).setValue(originalAmount);
    ((ICell)this.bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
    ((ICell)this.bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
  }

  void updatePartAValue(PayRequestBillInfo objectValue, String contractBillId, PartAOfPayReqBillCollection partAOfPayReqBillCollection)
    throws BOSException
  {
    if (partAOfPayReqBillCollection == null) {
      return;
    }
    BigDecimal amount = FDCHelper.ZERO;
    BigDecimal originalamount = FDCHelper.ZERO;
    BigDecimal lstPaidAmt = FDCHelper.ZERO;
    BigDecimal lstReqAmt = FDCHelper.ZERO;
    BigDecimal allReqAmt = FDCHelper.ZERO;
    for (int i = 0; i < partAOfPayReqBillCollection.size(); i++) {
      PartAOfPayReqBillInfo info = partAOfPayReqBillCollection.get(i);
      if (info.getAmount() != null) {
        if (info.isHasPaid()) {
          lstPaidAmt = info.getAmount().add(lstPaidAmt);
        }
        lstReqAmt = info.getAmount().add(lstReqAmt);
        allReqAmt = lstReqAmt.add(amount);
      }
    }
    if (this.bindCellMap.get("payPartAMatlAmt") != null) {
      ((ICell)this.bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
      ((ICell)this.bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
      ((ICell)this.bindCellMap.get("payPartAMatlAmt")).setValue(amount);
      ((ICell)this.bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
      ((ICell)this.bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
      ((ICell)this.bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
    }
  }

  void reloadPartAConfmValue(PayRequestBillInfo editData, BigDecimal amount)
    throws Exception
  {
    if (amount == null) {
      amount = FDCHelper.ZERO;
    }
    if (amount.compareTo(FDCHelper.ZERO) == 1) {
      amount = FDCHelper.ZERO;
      ((ICell)this.bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    }

    amount = FDCHelper.ZERO;
    BigDecimal originalamount = FDCHelper.ZERO;
    BigDecimal lstPaidAmt = FDCHelper.ZERO;
    BigDecimal lstReqAmt = FDCHelper.ZERO;
    BigDecimal allReqAmt = FDCHelper.ZERO;
    EntityViewInfo view = new EntityViewInfo();
    FilterInfo filter = new FilterInfo();
    filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    view.setFilter(filter);

    Timestamp createTime = editData.getCreateTime();
    filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime", createTime, CompareType.LESS_EQUALS));

    PartAConfmOfPayReqBillCollection c = PartAConfmOfPayReqBillFactory.getRemoteInstance().getPartAConfmOfPayReqBillCollection(view);
    for (int i = 0; i < c.size(); i++) {
      PartAConfmOfPayReqBillInfo info = c.get(i);
      if (info.getAmount() != null) {
        if (info.getPayRequestBill().getId().equals(editData.getId())) {
          amount = amount.add(FDCHelper.toBigDecimal(info.getAmount()));

          originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
        }
        else {
          if (info.isHasPaid()) {
            lstPaidAmt = info.getAmount().add(lstPaidAmt);
          }
          lstReqAmt = info.getAmount().add(lstReqAmt);
        }
      }
    }
    allReqAmt = amount.add(lstReqAmt);
    if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstPaidAmt = FDCHelper.ZERO;
    }
    if (originalamount.compareTo(FDCHelper.ZERO) == 0) {
      originalamount = FDCHelper.ZERO;
    }
    if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
      lstReqAmt = FDCHelper.ZERO;
    }

    ((ICell)this.bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    ((ICell)this.bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    ((ICell)this.bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    ((ICell)this.bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
    ((ICell)this.bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
    ((ICell)this.bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
  }

  void updatePartAConfmValue(PayRequestBillInfo objectValue, String contractBillId, PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection)
    throws BOSException
  {
    if (partAConfmOfPayReqBillCollection == null) {
      return;
    }
    BigDecimal amount = FDCHelper.ZERO;
    BigDecimal originalamount = FDCHelper.ZERO;
    BigDecimal lstPaidAmt = FDCHelper.ZERO;
    BigDecimal lstReqAmt = FDCHelper.ZERO;
    for (int i = 0; i < partAConfmOfPayReqBillCollection.size(); i++) {
      PartAConfmOfPayReqBillInfo info = partAConfmOfPayReqBillCollection.get(i);
      if (info.getAmount() != null) {
        if (info.isHasPaid()) {
          lstPaidAmt = info.getAmount().add(lstPaidAmt);
        }
        lstReqAmt = info.getAmount().add(lstReqAmt);
      }
    }

    if (this.bindCellMap.get("payPartAMatlAmt") != null) {
      ((ICell)this.bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
      ((ICell)this.bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
      ((ICell)this.bindCellMap.get("payPartAMatlAmt")).setValue(amount);
      ((ICell)this.bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
      ((ICell)this.bindCellMap.get("payPartAMatlAllReqAmt")).setValue(lstReqAmt);
      ((ICell)this.bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
    }
  }

  protected void setBeforeAction()
  {
    this.table.setBeforeAction(new BeforeActionListener() {
      public void beforeAction(BeforeActionEvent e) {
        if (7 == e.getType())
        {
          KDTSelectBlock block;
          int rowIndex;
          for (int i = 0; i < PayReqTableHelper.this.table.getSelectManager().size(); i++) {
            block = PayReqTableHelper.this.table.getSelectManager().get(i);

            for (rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); )
            {
              for (int colIndex = block.getBeginCol(); colIndex <= block.getEndCol(); )
              {
                if ((colIndex != PayReqTableHelper.this.editUI.columnIndex) || (rowIndex != PayReqTableHelper.this.editUI.rowIndex) || (PayReqTableHelper.this.table.getCell(rowIndex, colIndex).getStyleAttributes().isLocked())) {
                  e.setCancel(true);
                }
                else {
                  KDTEditEvent event = new KDTEditEvent(PayReqTableHelper.this.table, null, null, rowIndex, colIndex, true, 1);
                  try
                  {
                    PayReqTableHelper.this.editUI.kdtEntrys_editStopped(event);
                  } catch (Exception e1) {
                    PayReqTableHelper.this.handUIException(e1);
                  }
                }
                colIndex++;
              }
              rowIndex++;
            }

          }

        }
        else if (5 == e.getType()) {
          PayReqTableHelper.this.table.putClientProperty("ACTION_PASTE", "ACTION_PASTE");
        }
      }
    });
    this.table.setAfterAction(new BeforeActionListener() {
      public void beforeAction(BeforeActionEvent e) {
        if (5 == e.getType())
          PayReqTableHelper.this.table.putClientProperty("ACTION_PASTE", null);
      }
    });
    this.table.addKDTPropertyChangeListener(new KDTPropertyChangeListener()
    {
      public void propertyChange(KDTPropertyChangeEvent evt) {
        if ((evt.getType() == 1) && (evt.getPropertyName().equals("cellValue")))
        {
          if (PayReqTableHelper.this.table.getClientProperty("ACTION_PASTE") != null)
          {
            int rowIndex = evt.getRowIndex();
            int colIndex = evt.getColIndex();
            if ((rowIndex != PayReqTableHelper.this.editUI.rowIndex) || (colIndex != PayReqTableHelper.this.editUI.columnIndex))
            {
              return;
            }
            KDTEditEvent event = new KDTEditEvent(PayReqTableHelper.this.table);
            event.setColIndex(colIndex);
            event.setRowIndex(rowIndex);
            event.setOldValue(null);
            ICell cell = PayReqTableHelper.this.table.getCell(rowIndex, colIndex);
            if (cell == null) {
              return;
            }

            event.setValue(cell.getValue());
            try {
              PayReqTableHelper.this.editUI.kdtEntrys_editStopped(event);
            }
            catch (Exception e1) {
              PayReqTableHelper.this.handUIException(e1);
            }
          }
        }
      }
    });
  }
}