package com.kingdee.eas.fdc.aimcost.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

public class PlanIndexTable  {

	private KDTable table = null;
	  private String headId = null;
	  private CellBinder binder = null;
	  private int dynRowBase = 9;
	  private AimMeasureCostEditUICTEx measureCostEditU = null;
	  private PlanIndexInfo planIndexInfo = null;
	  private MeasureCostInfo costInfo = null;
	  private KDBizPromptBox f7productType = null;
	  private ICell totalContainAreaCell = null;
	  private ICell buildAreaCell = null;
	  private ICell totalBuildAreaCell = null;
	  private ICell buildContailAreaCell = null;
	  private ICell buildDensityCell = null;
	  private ICell publicSetHouseCell = null;
	  private ICell greenAreaRateCell = null;
	  private ICell cubageRateAreaCell = null;
	  private ICell cubageRateCell = null;
	  private ICell totalRoadAreaCell = null;
	  private ICell pitchRoadCell = null;
	  private ICell concreteRoadCell = null;
	  private ICell hardRoadCell = null;
	  private ICell hardSquareCell = null;
	  private ICell hardManRoadCell = null;
	  private ICell totalGreenAreaCell = null;
	  private ICell importPubGreenAreaCell = null;
	  private ICell houseGreenAreaCell = null;
	  private ICell privateGardenCell = null;
	  private ICell warterViewAreaCell = null;
	  private int productColummnIndex = 1;
	  private int containAreaColummnIndex = 2;
	  private int cubageRateColummnIndex = 3;
	  private int constructAreaColumnIndex = 4;
	  private int buildAreaColummnIndex = 5;
	  private int sellAreaColummnIndex = 6;
	  private int productRateColummnIndex = 7;
	  private int avgHuColummnIndex = 8;
	  private int elevatorsIndex = 9;
	  private int floorsIndex = 10;
	  private int floorHeightIndex = 11;
	  private int unitColummnIndex = 12;
	  private int huColummnIndex = 13;
	  private int splitColummnIndex = 14;
	  private int hasPropertyRightColummnIndex = 15;
	  private int descColummnIndex = 16;
	  private int parksColummnIndex = 3;
	  private int houseSubIndex = 0;
	  private int businessSubIndex = 0;
	  private int publicSubIndex = 0;
	  private int parkingSubIndex = 0;
	  private int totalIndex = 0;
	  private static boolean isBuildPartLogic = false;
	  private boolean isPlanIndexLogic = false;
	  private boolean isHasSubTable = false;
	  private KDTable subTable = null;
	  private KDTable constrTable = null;

	  public PlanIndexTable(PlanIndexInfo planIndexInfo,
			AimMeasureCostEditUICTEx measureCostEditUI) throws EASBizException,
			BOSException {
		this.table = new KDTable(17, 1, 0);
		this.table.setName("规划指标表");
		initCtrlListener();
		this.binder = new CellBinder();
		this.measureCostEditU = measureCostEditUI;
		this.isHasSubTable = measureCostEditUI.isUseCustomIndex();
		this.isPlanIndexLogic = measureCostEditUI.isPlanIndexLogic();
		isBuildPartLogic = measureCostEditUI.isBuildPartLogic();
		this.costInfo = measureCostEditUI.getEditData();
		initTable(planIndexInfo);
		initConstructTable(this.costInfo);
	}

	  protected void initCtrlListener() {
	    table.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
			public void editStopped(
					com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
				table_editStopped(e);
			}
		});
		table.addKDTMouseListener(new KDTMouseListener(){
			public void tableClicked(KDTMouseEvent e) {
				table_tableClicked(e);
			}
		});
		table.setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					int colIndex=table.getSelectManager().getActiveColumnIndex();
					if(colIndex==1){
						e.setCancel(true);
					}
				}
			}
		});
		
		table.setAfterAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					KDTEditEvent event=new KDTEditEvent(table);
					int activeColumnIndex = table.getSelectManager().getActiveColumnIndex();
					int activeRowIndex =table.getSelectManager().getActiveRowIndex();
					event.setColIndex(activeColumnIndex);
					event.setRowIndex(activeRowIndex);
					event.setOldValue(FDCHelper.ONE);
					event.setValue(null);
					table_editStopped(event);
				}
			}
		});
	  }

	  public KDTable getTable()
	  {
	    return this.table;
	  }

	  public void initTable(PlanIndexInfo info) {
	    ((KDTTransferAction)this.table.getActionMap().get("Paste")).setPasteMode(2);

	    this.table.getHeadRow(0).getCell(0).setValue("规划指标表");
	    this.table.getHeadRow(0).getCell(0).setUserObject("planIndex");

	    this.table.getColumn(3).setWidth(150);
	    this.table.getColumn(7).setWidth(120);
	    this.table.getColumn(8).setWidth(120);
	    this.table.getColumn(10).setWidth(50);
	    this.table.getColumn(11).setWidth(50);

	    this.table.getColumn(12).setWidth(50);
	    this.table.getColumn(13).setWidth(50);
	    this.table.getColumn(14).getStyleAttributes().setLocked(false);
	    initFixTable(info);
	    initDynTable(info);
	    ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
	    this.table.getColumn(1).setEditor(f7Editor);

	    Color lockColor = new Color(15789529);
	    StyleAttributes sa = this.table.getRow(3).getStyleAttributes();
	    sa.setHorizontalAlign(HorizontalAlignment.CENTER);

	    sa = this.table.getRow(5).getStyleAttributes();
	    sa.setHorizontalAlign(HorizontalAlignment.CENTER);

	    sa = this.table.getRow(7).getStyleAttributes();
	    sa.setLocked(true);
	    sa.setBackground(new Color(15789553));
	    sa = this.table.getRow(8).getStyleAttributes();
	    sa.setHorizontalAlign(HorizontalAlignment.CENTER);
	    sa.setBackground(lockColor);
	    sa.setLocked(true);

	    this.table.getCell(this.table.getRowCount() - 1, 0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);

	    this.table.getColumn(1).getStyleAttributes().setNumberFormat("@");
	    KDTextField textField = new KDTextField();
	    textField.setMaxLength(300);

	    this.table.getColumn(16).setEditor(new KDTDefaultCellEditor(textField));
	    for (int i = 0; i < 7; ++i)
	      for (int j = 9; j < this.table.getColumnCount(); ++j) {
	        if ((i < 3) && (j == 9))
	        	continue;

	        if (this.table.getCell(i, j) == null) continue;
	        if ((isPlanIndexLogic()) && 
	          (j == 8)) { if (i != 6) if (i == 7);
	        }
	        else
	        {
	          this.table.getCell(i, j).getStyleAttributes().setLocked(true);
	          table.getCell(i, j).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	        }
	      }
	    this.table.getCell(2, 6).getStyleAttributes().setLocked(true);
	    this.table.getCell(2, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	    this.table.getCell(2, 7).getStyleAttributes().setLocked(true);
	    this.table.getCell(2, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	    this.table.getCell(3, 8).getStyleAttributes().setLocked(true);
	    this.table.getCell(3, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	    this.table.getCell(4, 8).getStyleAttributes().setLocked(true);
	    this.table.getCell(4, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	    if (isPlanIndexLogic()) {
	      this.table.getCell(3, 6).getStyleAttributes().setLocked(true);
	      this.table.getCell(3, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

	      this.table.getCell(3, 7).getStyleAttributes().setLocked(true);
	      this.table.getCell(3, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

	      this.table.getCell(4, 6).getStyleAttributes().setLocked(true);
	      this.table.getCell(4, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

	      this.table.getCell(4, 7).getStyleAttributes().setLocked(true);
	      this.table.getCell(4, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	    }
	    else {
	      this.table.getCell(5, 7).getStyleAttributes().setLocked(true);
	      this.table.getCell(6, 7).getStyleAttributes().setLocked(true);
	      this.table.getCell(5, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	      this.table.getCell(6, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	      this.table.getCell(5, 8).getStyleAttributes().setLocked(true);
	      this.table.getCell(5, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	      this.table.getCell(6, 8).getStyleAttributes().setLocked(true);
	      this.table.getCell(6, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	    }

	    this.table.getCell(2, 5).getStyleAttributes().setLocked(true);
	    this.table.getCell(2, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

	    this.table.getCell(4, 0).getStyleAttributes().setLocked(true);
	    this.table.getCell(4, 0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

	    this.table.getCell(6, 0).getStyleAttributes().setLocked(true);
	    this.table.getCell(6, 0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

	    this.table.getCell(1, 7).getStyleAttributes().setNumberFormat("0.00%");
	    this.table.getCell(2, 7).getStyleAttributes().setNumberFormat("0.00%");
	    reSetExpressions();

	    KDTEditEvent e = new KDTEditEvent(this.table, null, null, this.dynRowBase + 1, 1, false, 10000);
	    calc(e);
	    if (isHasSubTable())
	      initSubTable(info);
	    
	    if (this.measureCostEditU.getOprtState().equals(OprtState.VIEW)) {
	        this.table.getStyleAttributes().setLocked(true);
	  }
	  }

	  private void initFixTable(PlanIndexInfo info)
	  {
	    this.table.addRows(9);

	    this.binder.bindCell(this.table, 0, 1, "总占地面积(m2)", "totalContainArea", true);
	    this.binder.bindCell(this.table, 0, 4, "建设用地面积(m2)", "buildArea", true);
	    this.binder.bindCell(this.table, 0, 6, "总建筑面积(m2)", "totalBuildArea", true);
	    this.binder.bindCell(this.table, 0, 8, "规划总建筑面积(m2)", "totalConstructArea", true);

	    this.binder.bindCell(this.table, 1, 1, "建筑物占地面积(m2)", "buildContainArea", true);
	    this.binder.bindCell(this.table, 1, 4, "公共配套用房面积(m2)", "publicSetHouse", true);
	    this.binder.bindCell(this.table, 1, 6, "绿地率", "greenAreaRate", true);
	    this.binder.bindCell(this.table, 1, 8, "地下室面积(m2)", "basementArea", true);

	    this.binder.bindCell(this.table, 2, 1, "计容积率面积(m2)", "cubageRateArea", true);
	    this.binder.bindCell(this.table, 2, 4, "容积率", "cubageRate", true);
	    this.binder.bindCell(this.table, 2, 6, "建筑密度", "buildDensity", true);
	    this.binder.bindCell(this.table, 2, 8, "精装修面积(m2)", "decorationArea", true);

	    this.binder.bindCell(this.table, 3, 0, "道路用地合计", "totalRoadArea", true, true);
	    this.binder.bindCell(this.table, 3, 2, "沥青路面车行道 ", "pitchRoad", true, true);
	    this.binder.bindCell(this.table, 3, 3, "砼路面车行道（停车场）", "concreteRoad", true, true);
	    this.binder.bindCell(this.table, 3, 5, "硬质铺装车行道 ", "hardRoad", true, true);
	    if (isPlanIndexLogic()) {
	      this.binder.bindCell(this.table, 5, 7, "硬质铺装广场 ", "hardSquare", true, true);
	      this.binder.bindCell(this.table, 5, 8, "硬质铺装人行道  ", "hardManRoad", true, true);
	    } else {
	      this.binder.bindCell(this.table, 3, 6, "硬质铺装广场 ", "hardSquare", true, true);
	      this.binder.bindCell(this.table, 3, 7, "硬质铺装人行道  ", "hardManRoad", true, true);
	    }
	    this.binder.bindCell(this.table, 5, 0, "绿化用地合计 ", "totalGreenArea", true, true);
	    this.binder.bindCell(this.table, 5, 2, "重要公共绿地 ", "importPubGreenArea", true, true);
	    this.binder.bindCell(this.table, 5, 3, "组团宅间绿化  ", "houseGreenArea", true, true);
	    this.binder.bindCell(this.table, 5, 5, "底层私家花园 ", "privateGarden", true, true);
	    this.binder.bindCell(this.table, 5, 6, "水景面积 ", "warterViewArea", true, true);
	    KDTMergeManager mm = this.table.getMergeManager();
	    for (int i = 0; i < 8; ++i) {
	      mm.mergeBlock(i, 0, i, 1, 4);
	      mm.mergeBlock(i, 3, i, 4, 4);
	    }
	    mm.mergeBlock(7, 0, 7, 13);

	    this.table.getCell(8, 0).setValue("产品构成");
	    this.table.getCell(8, 1).setValue("产品类型");
	    this.table.getCell(8, 2).setValue("占地面积");
	    this.table.getCell(8, 3).setValue("容积率");
	    this.table.getCell(8, 4).setValue("规划建筑面积");
	    this.table.getCell(8, 5).setValue("建筑面积");
	    this.table.getCell(8, 6).setValue("可售面积");
	    this.table.getCell(8, 7).setValue("产品比例");
	    this.table.getCell(8, 8).setValue("平均每户面积");
	    this.table.getCell(8, 9).setValue("电梯");
	    this.table.getCell(8, 10).setValue("层数");
	    this.table.getCell(8, 11).setValue("层高");
	    this.table.getCell(8, 12).setValue("单元数");
	    this.table.getCell(8, 13).setValue("户数");
	    this.table.getCell(8, 14).setValue("是否分摊");
	    this.table.getCell(8, 15).setValue("是否有产权");
	    this.table.getCell(8, 16).setValue("备注");

	    this.binder.bindCell(this.table, 3, 0, "道路用地合计", "totalRoadArea", true, true);
	    if (info != null)
	      this.binder.setCellsValue(info);
	  }

	  private void initDynTable(PlanIndexInfo info) {
	    int base = this.dynRowBase;
	    int start = base;
	    int rows = base;
	    PlanIndexTypeEnum lastType = null;
	    KDTMergeManager mm = this.table.getMergeManager();
	    if (info == null) {
	      info = new PlanIndexInfo();
	      PlanIndexEntryInfo entry = new PlanIndexEntryInfo();
	      entry.setType(PlanIndexTypeEnum.house);
	      info.getEntrys().add(entry);

	      entry = new PlanIndexEntryInfo();
	      entry.setType(PlanIndexTypeEnum.business);
	      info.getEntrys().add(entry);

	      entry = new PlanIndexEntryInfo();
	      entry.setType(PlanIndexTypeEnum.publicBuild);
	      info.getEntrys().add(entry);

	      entry = new PlanIndexEntryInfo();
	      entry.setType(PlanIndexTypeEnum.parking);
	      info.getEntrys().add(entry);
	    }
	    else {
	      PlanIndexEntryCollection entrys = new PlanIndexEntryCollection();
	      boolean isOldData = true;
	      for (int i = 0; i < info.getEntrys().size(); ++i) {
	        if (PlanIndexTypeEnum.business == info.getEntrys().get(i).getType())
	          isOldData = false;

	        entrys.add(info.getEntrys().get(i));
	      }
	      if (isOldData) {
	        info.getEntrys().clear();
	        PlanIndexEntryCollection newEntrys = new PlanIndexEntryCollection();
	        for (int i = 0; i < entrys.size(); ++i) {
	          if (PlanIndexTypeEnum.publicBuild == entrys.get(i).getType()) {
	            PlanIndexEntryInfo entry = new PlanIndexEntryInfo();
	            entry.setType(PlanIndexTypeEnum.business);
	            newEntrys.add(entry);
	          }
	          newEntrys.add(entrys.get(i));
	        }
	        info.getEntrys().addCollection(newEntrys);
	      }
	    }
	    for (int i = 0; i < info.getEntrys().size(); ++i)
	    {
	      PlanIndexEntryInfo entry = info.getEntrys().get(i);
	      IRow row = this.table.addRow(rows);
	      if ((entry.getType() != lastType) && (lastType != null)) {
	        row.getCell(0).setValue(lastType);
	        row.getCell(1).setValue("小计");
	        loadRow(null, row);

	        row = this.table.addRow(++rows);
	        start = rows;

	        if (entry.getType() == PlanIndexTypeEnum.parking) {
	          mm.mergeBlock(rows, 0, rows, 1);
	          row.getCell(2).setValue("占地面积");
	          row.getCell(3).setValue("车位数");
	          row.getCell(4).setValue("规划建筑面积");
	          row.getCell(5).setValue("实际建造面积");
	          row.getCell(6).setValue("可售面积");
	          StyleAttributes sa = row.getStyleAttributes();
	          sa.setHorizontalAlign(HorizontalAlignment.CENTER);
	          sa.setLocked(true);
	          sa.setBackground(new Color(15789529));
	          row = this.table.addRow(++rows);
	          start = rows;
	        }
	      }
	      loadRow(entry, row);
	      lastType = entry.getType();
	      ++rows;
	    }

	    IRow row = this.table.addRow(rows);
	    row.getCell(0).setValue(lastType);
	    row.getCell(1).setValue("小计");
	    loadRow(null, row);

	    row = this.table.addRow(++rows);
	    row.getCell(0).setValue("合计");
	    loadRow(null, row);

	    mm.mergeBlock(base, 0, rows - 1, 0, 1);
	    mm.mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), 1);
	  }

	  private void loadRow(PlanIndexEntryInfo entry, IRow row) {
	    StyleAttributes sa = row.getStyleAttributes();

	    if (isSubTotalRow(row)) {
	      sa.getNumberFormat();
	      sa.setNumberFormat("#,##0.00;-#,##0.00");
	      sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
	      sa.setLocked(true);
	      sa.setBackground(FDCTableHelper.yearTotalColor);
	      row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
	      row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
	      row.getCell(9).getStyleAttributes().setNumberFormat("");
	      row.getCell(10).getStyleAttributes().setNumberFormat("");

	      return; }
	    if (isTotalRow(row)) {
	      sa.setNumberFormat("#,##0.00;-#,##0.00");
	      sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
	      sa.setLocked(true);
	      sa.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	      return;
	    }

	    if (entry == null)
	    {
	      row.getCell(14).setValue(Boolean.FALSE);
	      row.getCell(15).setValue(Boolean.FALSE);    //是否有产权
	    } else {
	      row.getCell(0).setValue(entry.getType());
	      row.getCell(0).getStyleAttributes().setLocked(true);

	      if (entry.isIsProduct())
	        row.getCell(1).setValue(entry.getProduct());
	      else {
	        row.getCell(1).setValue(entry.getName());
	      }

	      row.getCell(2).setValue(entry.getContainArea());
	      row.getCell(3).setValue(entry.getCubageRate());
	      row.getCell(4).setValue(entry.getConstructArea());
	      row.getCell(5).setValue(entry.getBuildArea());
	      row.getCell(6).setValue(entry.getSellArea());

	      if ((entry.getType() == PlanIndexTypeEnum.house) || (entry.getType() == PlanIndexTypeEnum.business)) {
	        row.getCell(7).setValue(entry.getProductRate());
	        row.getCell(8).setValue(entry.getUnitArea());
	        if (entry.getElevators() > 0)
	          row.getCell(9).setValue(new Integer(entry.getElevators()));

	        if (entry.getFloors() > 0)
	          row.getCell(10).setValue(new Integer(entry.getFloors()));

	        row.getCell(11).setValue(entry.getFloorHeight());
	        row.getCell(12).setValue(entry.getUnits());
	        row.getCell(13).setValue(entry.getDoors());
	      }

	      row.getCell(14).setValue(Boolean.valueOf(entry.isIsSplit()));
	      row.getCell(15).setValue(Boolean.valueOf(entry.isIsHasPropertyRight()));   //是否有产权
	      row.getCell(16).setValue(entry.getDesc());
	    }

	    for (int i = 2; i < 14; ++i) {
	      sa = row.getCell(i).getStyleAttributes();
	      sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
	      if ((i == 9) || (i == 10)) {
	        row.getCell(i).setEditor(CellBinder.getCellIntegerNumberEdit());
	      } else {
	        row.getCell(i).setEditor(CellBinder.getCellNumberEdit());
	        sa.setNumberFormat("#,##0.00;-#,##0.00");
	      }

	    }

	    boolean isPark = false;
	    if ((row.getCell(0) != null) && (row.getCell(0).getValue() instanceof PlanIndexTypeEnum)) {
	      PlanIndexTypeEnum type = (PlanIndexTypeEnum)row.getCell(0).getValue();
	      if (type == PlanIndexTypeEnum.parking) {
	        isPark = true;
	      }

	      if ((type == PlanIndexTypeEnum.parking) || (type == PlanIndexTypeEnum.publicBuild)) {
	        for (int i = 7; i < this.table.getColumnCount() - 3; ++i) {
	          row.getCell(i).getStyleAttributes().setLocked(true);
	          row.getCell(i).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	        }
	      }else if ((type == PlanIndexTypeEnum.house) || ((type == PlanIndexTypeEnum.business) && (((entry == null) || (entry.getProduct() == null))))) {
	        row.getCell(14).setValue(Boolean.TRUE);
	      }

	    }

	    if (!(isPark))
	    {
	      row.getCell(3).getStyleAttributes().setLocked(true);
	      row.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	      row.getCell(7).getStyleAttributes().setLocked(true);
	      row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
	      row.getCell(8).getStyleAttributes().setLocked(true);
	      row.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

	      row.getCell(3).setEditor(getCellNumberEdit(8));
	    }

	    row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
	    row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
	  }

	  public KDTDefaultCellEditor getCellEditor()
	  {
	    KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor();
	    KDFormattedTextField formatText = new KDFormattedTextField();
	    formatText.setPrecision(8);
	    cellEditor = new KDTDefaultCellEditor(formatText);
	    return cellEditor;
	  }

	  public void addConstrIndexRow(ActionEvent e) {
	    int index = this.constrTable.getSelectManager().getActiveRowIndex();
	    if (index == -1)
	      this.constrTable.addRow();
	    else
	      this.constrTable.addRow(index + 1);
	  }

	  public void deleteConstrIndexRow(ActionEvent e) {
	    int index = this.constrTable.getSelectManager().getActiveRowIndex();
	    if (index == -1)
	      return;

	    this.constrTable.removeRow(index);
	  }

	  public void addRow(ActionEvent e)
	  {
	    boolean hasAdd = false;
	    int index = this.table.getSelectManager().getActiveRowIndex();
	    if (index == -1)
	      return;

	    IRow selectRow = this.table.getRow(index);
	    if (selectRow.getCell(0).getValue() instanceof PlanIndexTypeEnum)
	      for (int i = index; i < this.table.getRowCount() - 1; ++i) {
	        IRow row = this.table.getRow(i);
	        if (isSubTotalRow(row)) {
	          row = this.table.addRow(i);

	          this.table.getScriptManager().removeAll();
	          row.getCell(0).setValue(selectRow.getCell(0).getValue());
	          loadRow(null, row);
	          hasAdd = true;
	          break;
	        }
	      }

	    if (hasAdd)
	      reSetExpressions();
	  }

	  public void deleteRow(ActionEvent e)
	  {
	    boolean hasDelete = false;
	    KDTSelectManager selectManager = this.table.getSelectManager();
	    if ((selectManager == null) || (selectManager.size() == 0)) {
	      return;
	    }

	    boolean mustAdd = false;
	    for (int i = 0; i < selectManager.size(); ++i) {
	      KDTSelectBlock selectBlock = selectManager.get(i);
	      for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); ++j) {
	        IRow selectRow = this.table.getRow(j);
	        if (selectRow == null)
	        	continue;

	        if (!(selectRow.getCell(0).getValue() instanceof PlanIndexTypeEnum)) break;
	        if ((selectRow.getCell(1).getValue() != null) && (selectRow.getCell(1).getValue().equals("小计"))) {
	        	continue;
	        }

	        if ((this.table.getCell(j - 1, 1).getValue() != null) && (this.table.getCell(j - 1, 1).getValue().equals("小计")) && (this.table.getCell(j + 1, 1).getValue() != null) && (this.table.getCell(j + 1, 1).getValue().equals("小计")))
	        {
	          mustAdd = true;
	        }
	        else if ((this.table.getCell(j - 1, 2).getValue() != null) && (this.table.getCell(j - 1, 2).getValue().equals("占地面积")) && (this.table.getCell(j + 1, 1).getValue() != null) && (this.table.getCell(j + 1, 1).getValue().equals("小计")))
	        {
	          mustAdd = true;
	        }

	        this.table.getScriptManager().removeAll();
	        if (mustAdd)
	          addRow(e);

	        IRow removeRow = this.table.removeRow(selectRow.getRowIndex());
	        hasDelete = true;
	        Object value = removeRow.getCell(1).getValue();
	        if (value instanceof ProductTypeInfo) {
	          ProductTypeInfo product = (ProductTypeInfo)value;
	          if (getPlanIndexEntryInfo(product.getId().toString()) != null) {
	            this.measureCostEditU.deleteProductTypeTable(product);
	            if (this.measureCostEditU.getplTables().getSelectedIndex() != 1)
	              this.measureCostEditU.getplTables().setSelectedIndex(1);  } }
	        label449: break;
	      }

	    }

	    if ((hasDelete) && (!(mustAdd))) {
	      reSetExpressions();
	    }

	    KDTEditEvent event = new KDTEditEvent(this.table);
	    int activeColumnIndex = 4;
	    int activeRowIndex = this.table.getSelectManager().getActiveRowIndex();
	    event.setColIndex(activeColumnIndex);
	    event.setRowIndex(activeRowIndex);
	    event.setOldValue(FDCHelper.ONE);
	    event.setValue(null);
	    table_editStopped(event);
	  }

	  private void reSetExpressions()
	  {
	  }

	  public void save(String headID)
	    throws EASBizException, BOSException
	  {
	    Object obj = this.table.getUserObject();
	    PlanIndexInfo planIndexInfo = null;
	    if (obj instanceof PlanIndexInfo) {
	      planIndexInfo = (PlanIndexInfo)obj;
	      planIndexInfo.getEntrys().clear();
	    } else {
	      planIndexInfo = new PlanIndexInfo();
	    }
	    planIndexInfo.setHeadID(headID);
	    this.binder.setObjectValue(planIndexInfo);

	    PlanIndexEntryInfo entry = new PlanIndexEntryInfo();
	    entry.setType(PlanIndexTypeEnum.house);
	    planIndexInfo.getEntrys().add(entry);

	    entry = new PlanIndexEntryInfo();
	    entry.setType(PlanIndexTypeEnum.business);
	    planIndexInfo.getEntrys().add(entry);

	    entry = new PlanIndexEntryInfo();
	    entry.setType(PlanIndexTypeEnum.publicBuild);
	    planIndexInfo.getEntrys().add(entry);

	    entry = new PlanIndexEntryInfo();
	    entry.setType(PlanIndexTypeEnum.parking);
	    planIndexInfo.getEntrys().add(entry);

	    IObjectPK pk = PlanIndexFactory.getRemoteInstance().save(planIndexInfo);
	    planIndexInfo.setId(BOSUuid.read(pk.toString()));
	    this.table.setUserObject(planIndexInfo);
	  }

	  public Set getProductIdSet()
	  {
	    Set idSet = new HashSet();
	    for (int i = this.dynRowBase; i < this.table.getRowCount() - 1; ++i) {
	      IRow row = this.table.getRow(i);
	      Object value = row.getCell(1).getValue();
	      if (value instanceof ProductTypeInfo)
	        idSet.add(((ProductTypeInfo)value).getId().toString());
	    }

	    return idSet;
	  }

	  public PlanIndexInfo getPlanIndexInfo() {
		if (this.planIndexInfo == null) {
			this.planIndexInfo = new PlanIndexInfo();
		}
		this.planIndexInfo.getEntrys().clear();
		this.binder.setObjectValue(this.planIndexInfo);
		for (int i = this.dynRowBase; i < this.table.getRowCount() - 1; ++i) {
			IRow row = this.table.getRow(i);
			if (row.getCell(0).getValue() instanceof PlanIndexTypeEnum) {
				Object value = row.getCell(1).getValue();
				if (value != null) {
					PlanIndexEntryInfo entry;
					if (value.toString().trim().length() < 1){
						continue;
					}
					if (value.equals("小计")) {
						entry = new PlanIndexEntryInfo();
						entry.setType((PlanIndexTypeEnum) row.getCell(0)
								.getValue());
						entry.setIsProduct(false);
						entry.setIndex(i);
						entry.setIsSplit(false);
						entry.setIsHasPropertyRight(false);
						planIndexInfo.getEntrys().add(entry);
					} else {
						entry = new PlanIndexEntryInfo();
						entry.setIndex(i);
						entry.setType((PlanIndexTypeEnum) row.getCell(0).getValue());
						if (value instanceof ProductTypeInfo) {
							entry.setIsProduct(true);
							entry.setProduct((ProductTypeInfo) value);
						} else {
							if (FDCHelper.isEmpty(value)) {
								boolean isEmpty = true;
								for (int h = 2; h < this.table.getColumnCount() - 2; ++h) {
									ICell cell = row.getCell(h);
									if ((cell != null) && (FDCHelper.toBigDecimal(cell.getValue()).compareTo(FDCHelper.ZERO) == 0)) {
										isEmpty = true;
									} else {
										isEmpty = false;
										break;
									}
								}
								if (isEmpty){
									continue;
								}
							}
							entry.setName(value.toString());
							entry.setIsProduct(false);
						}
						if (FDCHelper.toBigDecimal(row.getCell(2).getValue()) instanceof BigDecimal){
							entry.setContainArea(FDCHelper.toBigDecimal(row.getCell(2).getValue()));
						}
						if (row.getCell(3).getValue() instanceof BigDecimal){
							entry.setCubageRate((BigDecimal) row.getCell(3).getValue());
						}
						if (row.getCell(4).getValue() instanceof BigDecimal){
							entry.setConstructArea((BigDecimal) row.getCell(4).getValue());
						}
						if (row.getCell(5).getValue() instanceof BigDecimal){
							entry.setBuildArea((BigDecimal) row.getCell(5).getValue());
						}
						if (row.getCell(6).getValue() instanceof BigDecimal){
							entry.setSellArea((BigDecimal) row.getCell(6)
									.getValue());
						}
						if (FDCHelper.toBigDecimal(row.getCell(7).getValue()) instanceof BigDecimal){
							entry.setProductRate(FDCHelper.toBigDecimal(row
									.getCell(7).getValue()));
						}
						if (FDCHelper.toBigDecimal(row.getCell(8).getValue()) instanceof BigDecimal){
							entry.setUnitArea(FDCHelper.toBigDecimal(row.getCell(8).getValue()));
						}
						if (row.getCell(9).getValue() instanceof Integer){
							entry.setElevators(((Integer) row.getCell(9).getValue()).intValue());
						}
						if (row.getCell(10).getValue() instanceof Integer){
							entry.setFloors(((Integer) row.getCell(10).getValue()).intValue());
						}
						if (row.getCell(11).getValue() instanceof BigDecimal){
							entry.setFloorHeight((BigDecimal) row.getCell(11).getValue());
						}
						if (row.getCell(12).getValue() instanceof BigDecimal){
							entry.setUnits((BigDecimal) row.getCell(12).getValue());
						}
						if (row.getCell(13).getValue() instanceof BigDecimal){
							entry.setDoors((BigDecimal) row.getCell(13).getValue());
						}
						if (row.getCell(14).getValue() instanceof Boolean){
							entry.setIsSplit(((Boolean) row.getCell(14).getValue()).booleanValue());
						}else{
							entry.setIsSplit(false);
						}
						if (row.getCell(15).getValue() instanceof Boolean){ // 是否有产权
							entry.setIsHasPropertyRight(((Boolean) row.getCell(15).getValue()).booleanValue());
						}else{
							entry.setIsHasPropertyRight(false);
						}
						entry.setDesc((String) row.getCell(16).getValue());
						entry.setParent(this.planIndexInfo);
						this.planIndexInfo.getEntrys().add(entry);
					}
				}
			}

		}

		storeCustom(this.planIndexInfo);

		return this.planIndexInfo;
	}

	  public PlanIndexEntryInfo getPlanIndexEntryInfo(String productId) {
	    if ((this.planIndexInfo == null) || (productId == null))
	      return null;

	    for (int i = 0; i < this.planIndexInfo.getEntrys().size(); ++i) {
	      PlanIndexEntryInfo entry = this.planIndexInfo.getEntrys().get(i);
	      if ((entry.getProduct() != null) && (entry.getProduct().getId().toString().equals(productId)))
	        return entry;
	    }

	    return null;
	  }

	  public CustomPlanIndexEntryCollection getCustomPlanIndexs(String productId)
	  {
	    CustomPlanIndexEntryCollection entrys = new CustomPlanIndexEntryCollection();
	    if ((this.planIndexInfo == null) || (!(isHasSubTable())))
	      return entrys;

	    for (int i = 0; i < this.planIndexInfo.getCustomEntrys().size(); ++i) {
	      CustomPlanIndexEntryInfo entry = this.planIndexInfo.getCustomEntrys().get(i);
	      if (productId == null) {
	        if (entry.getProductType() == null)
	          entrys.add(entry);

	      }
	      else if (entry.getProductType() != null)
	      {
	        entrys.add(entry);
	      }
	    }

	    return entrys;
	  }

	  public CustomPlanIndexEntryInfo getCustomPlanIndexEntryInfo(String apportTypeId, String productId)
	  {
	    if ((this.planIndexInfo == null) || (!(isHasSubTable())))
	      return null;

	    for (int i = 0; i < this.planIndexInfo.getCustomEntrys().size(); ++i) {
	      CustomPlanIndexEntryInfo entry = this.planIndexInfo.getCustomEntrys().get(i);
	      if ((productId == null) && (entry.getProductType() == null) && (entry.getApportType().getId().toString().equals(apportTypeId)))
	      {
	        return entry;
	      }
	      if ((productId != null) && (entry.getProductType() != null) && (entry.getProductType().getId().toString().equals(productId)) && (entry.getApportType().getId().toString().equals(apportTypeId)))
	        return entry;
	    }

	    return null;
	  }

	  protected void table_editStopped(KDTEditEvent e) {
	    if (this.table == null)
	      return;

	    Object objOld = e.getOldValue();
	    Object objNew = e.getValue();
	    if ((objOld == null) && (objNew == null))
	      return;

	    if ((objOld != null) && (objNew != null) && (objOld.equals(objNew))) {
	      return;
	    }

	    calc(e);
	    this.measureCostEditU.setDataChange(true);
	  }

	  public KDBizPromptBox getF7productType()
	  {
	    f7productType = new KDBizPromptBox()
		{
			protected Object stringToValue(String t) {
				 Object obj= super.stringToValue(t);
				 if(obj instanceof  IObjectValue){
					 return obj;
				 }else{
					 return t;
				 }
				 
			}
		};
	    this.f7productType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");

	    EntityViewInfo view = new EntityViewInfo();
	    FilterInfo filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));

	    view.setFilter(filter);
	    this.f7productType.setEntityViewInfo(view);
	    this.f7productType.setEditable(true);
	    this.f7productType.setDisplayFormat("$name$");
	    this.f7productType.setEditFormat("$number$");
	    this.f7productType.setCommitFormat("$number$");
	    
	    f7productType.addPreChangeListener(new PreChangeListener(){
			public void preChange(PreChangeEvent e) {
				//如果产品值重复则不进行更改
				Object objNew=e.getData();
				if(objNew instanceof ProductTypeInfo){
//					PlanIndexInfo info=getPlanIndexInfo();
					int count=0;
					int flag=-1;//判断是否选择了本身
					for(int i=dynRowBase;i<table.getRowCount()-1;i++){
						IRow row2=table.getRow(i);
						Object value = row2.getCell(1).getValue();
						if(value instanceof ProductTypeInfo){
							if(((ProductTypeInfo)value).getId().equals(((ProductTypeInfo)objNew).getId())){
								count++;
								flag=i;
							}
						}
					}
					int rowIndex=KDTableUtil.getSelectedRow(table);
					if(count==1&&rowIndex!=flag) {
						MsgBox.showWarning(measureCostEditU, "该产品类型已经设置了指标");
						e.setResult(PreChangeEvent.S_FALSE);
					}
					
				}
			}
		});
	    
	    f7productType.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				int rowIndex=KDTableUtil.getSelectedRow(table);
				
				Object objOld=table.getCell(rowIndex,1).getValue();//e.getOldValue();
				Object objNew=e.getNewValue();
				if(objOld==null&&objNew==null) return;
				if(objOld!=null&&objOld.equals(objNew)) return;
				PlanIndexInfo info=getPlanIndexInfo();
				if(objNew!=null&&objNew.equals(f7productType.getUserObject())){
					return;
				}
				if(objNew instanceof ProductTypeInfo){
					measureCostEditU.addProductTypeTable((ProductTypeInfo)objNew);
				}
				
				if(objOld instanceof ProductTypeInfo){
					int count=0;
					if(info!=null){
						for(int i=0;i<info.getEntrys().size();i++){
							PlanIndexEntryInfo entry=info.getEntrys().get(i);
							if(entry.getProduct()!=null&&entry.getProduct().getId().equals(((ProductTypeInfo)objOld).getId())){
								count++;
								if(count>1) {
									break;
								}
							}
						}
					}
					if(count==1) measureCostEditU.deleteProductTypeTable((ProductTypeInfo)objOld);
				}
				
			}
		});
	    
	    return this.f7productType;
	  }

	  private void table_tableClicked(KDTMouseEvent e) {
	    if ((this.measureCostEditU == null) || (this.measureCostEditU.getOprtState().equals(OprtState.VIEW)))
	      return;

	    if ((e.getColIndex() == 10) && (e.getRowIndex() >= 9)) {
	      ICell cell = this.table.getCell(e.getRowIndex(), 0);
	      if ((cell.getValue() != null) && (cell.getValue() instanceof PlanIndexTypeEnum) && 
	        (cell.getValue() == PlanIndexTypeEnum.house)) {
	        return;
	      }

	      cell = this.table.getCell(e.getRowIndex(), e.getColIndex());
	      if (cell.getValue() instanceof Boolean)
	        cell.setValue(Boolean.valueOf(!(((Boolean)cell.getValue()).booleanValue())));
	    }
	  }

	  public static BigDecimal getAllSellArea(PlanIndexInfo info)
	  {
	    BigDecimal amount = FDCHelper.ZERO;
	    for (int i = 0; i < info.getEntrys().size(); ++i)
	    {
	      if ((info.getEntrys().get(i).isIsProduct()) && (info.getEntrys().get(i).isIsSplit()))
	        amount = amount.add(FDCHelper.toBigDecimal(info.getEntrys().get(i).getSellArea()));

	    }

	    return amount;
	  }

	  public static BigDecimal getAllBuildArea(PlanIndexInfo info)
	  {
	    BigDecimal amount = FDCHelper.ZERO;
	    if (isBuildPartLogic())
	      for (int i = 0; i < info.getEntrys().size(); ++i)
	      {
	        if ((info.getEntrys().get(i).isIsProduct()) && (info.getEntrys().get(i).isIsSplit()))
	          amount = amount.add(FDCHelper.toBigDecimal(info.getEntrys().get(i).getBuildArea()));
	      }

	    else {
	      amount = amount.add(FDCHelper.toBigDecimal(info.getTotalBuildArea()));
	    }

	    return amount; }

	  private boolean isSubTotalRow(IRow row) {
	    boolean isSubTotalRow = false;
	    if ((row.getCell(1).getValue() != null) && (row.getCell(1).getValue().equals("小计")))
	      isSubTotalRow = true;

	    return isSubTotalRow;
	  }

	  private boolean isTotalRow(IRow row) {
	    boolean isTotalRow = false;
	    Object value = row.getCell(0).getValue();
	    if ((value != null) && (value.equals("合计")))
	      isTotalRow = true;

	    return isTotalRow;
	  }

	  private void initVariable() {
		KDTable table = getTable();
		this.totalContainAreaCell = table.getCell(0, 2);
		this.buildAreaCell = table.getCell(0, 5);
		this.totalBuildAreaCell = table.getCell(0, 7);
		this.buildContailAreaCell = table.getCell(1, 2);
		this.publicSetHouseCell = table.getCell(1, 5);
		this.greenAreaRateCell = table.getCell(1, 7);
		this.cubageRateAreaCell = table.getCell(2, 2);
		this.cubageRateCell = table.getCell(2, 5);
		this.buildDensityCell = table.getCell(2, 7);
		this.totalRoadAreaCell = table.getCell(4, 0);
		this.pitchRoadCell = table.getCell(4, 2);
		this.concreteRoadCell = table.getCell(4, 3);
		this.hardRoadCell = table.getCell(4, 5);
		if (isPlanIndexLogic()) {
			this.hardSquareCell = table.getCell(6, 7);
			this.hardManRoadCell = table.getCell(6, 8);
		} else {
			this.hardSquareCell = table.getCell(4, 6);
			this.hardManRoadCell = table.getCell(4, 7);
		}
		this.totalGreenAreaCell = table.getCell(6, 0);
		this.importPubGreenAreaCell = table.getCell(6, 2);
		this.houseGreenAreaCell = table.getCell(6, 3);
		this.privateGardenCell = table.getCell(6, 5);
		this.warterViewAreaCell = table.getCell(6, 6);

		this.houseSubIndex = this.dynRowBase;
		this.businessSubIndex = this.dynRowBase;
		this.publicSubIndex = this.dynRowBase;
		this.parkingSubIndex = (getTable().getRowCount() - 2);
		for (int i = this.dynRowBase; i < getTable().getRowCount() - 1; ++i) {
			IRow row = getTable().getRow(i);
			if ((row.getCell(0).getValue() == PlanIndexTypeEnum.house) && (isSubTotalRow(row))) {
				this.houseSubIndex = i;
			}
			if ((row.getCell(0).getValue() == PlanIndexTypeEnum.business) && (isSubTotalRow(row))) {
				this.businessSubIndex = i;
			}
			if ((row.getCell(0).getValue() == PlanIndexTypeEnum.publicBuild) && (isSubTotalRow(row))) {
				this.publicSubIndex = i;
				break;
			}
		}
		this.totalIndex = (table.getRowCount() - 1);
		calcArea();
	}

	  private void calc(KDTEditEvent e) {
	    initVariable();
	    KDTable table = getTable();
	    if((e != null) && (e.getRowIndex() < this.dynRowBase)) {
			BigDecimal oldGreenAmt = FDCHelper.toBigDecimal(this.totalGreenAreaCell.getValue());
			BigDecimal oldContainAmt = FDCHelper.toBigDecimal(this.totalContainAreaCell.getValue());
			if ((e != null) && (e.getRowIndex() == this.totalContainAreaCell.getRowIndex()) 
					&& (e.getColIndex() == this.totalContainAreaCell.getColumnIndex())){
				oldContainAmt = FDCHelper.toBigDecimal(e.getOldValue());
			}
			BigDecimal buildAreaAmt = FDCHelper.toBigDecimal(this.buildAreaCell.getValue());
			if (buildAreaAmt.signum() == 0) {
				this.buildDensityCell.setValue(null);
				this.cubageRateCell.setValue(null);
			} else {
				this.buildDensityCell.setValue(FDCHelper.divide(this.buildContailAreaCell.getValue(), buildAreaAmt, 4, 4));
				this.cubageRateCell.setValue(FDCHelper.divide(this.cubageRateAreaCell.getValue(), buildAreaAmt, 2, 4));
			}
			BigDecimal amt = FDCHelper.ZERO;
			amt = FDCNumberHelper.add(amt, this.pitchRoadCell.getValue());
			amt = FDCNumberHelper.add(amt, this.concreteRoadCell.getValue());
			amt = FDCNumberHelper.add(amt, this.hardRoadCell.getValue());
			if (!(isPlanIndexLogic())) {
				amt = FDCNumberHelper.add(amt, this.hardSquareCell.getValue());
				amt = FDCNumberHelper.add(amt, this.hardManRoadCell.getValue());
			}
			this.totalRoadAreaCell.setValue(amt);
			amt = FDCHelper.ZERO;
			amt = FDCNumberHelper.add(amt, this.importPubGreenAreaCell.getValue());
			amt = FDCNumberHelper.add(amt, this.houseGreenAreaCell.getValue());
			amt = FDCNumberHelper.add(amt, this.privateGardenCell.getValue());
			amt = FDCNumberHelper.add(amt, this.warterViewAreaCell.getValue());
			if (isPlanIndexLogic()) {
				amt = FDCNumberHelper.add(amt, this.hardSquareCell.getValue());
				amt = FDCNumberHelper.add(amt, this.hardManRoadCell.getValue());
			}
			this.totalGreenAreaCell.setValue(amt);
			if ((amt.compareTo(oldGreenAmt) != 0) || (FDCHelper.toBigDecimal(this.totalContainAreaCell.getValue()).compareTo(oldContainAmt) != 0)){
				this.greenAreaRateCell.setValue(FDCNumberHelper.divide(amt,this.totalContainAreaCell.getValue(), 4, 4));
			}
		} else {
//			BigDecimal tmp;
			BigDecimal oldtotalBuildArea = FDCHelper.toBigDecimal(table.getCell(this.totalIndex, this.buildAreaColummnIndex).getValue());
			boolean isChange = true;
			if ((e.getOldValue() instanceof BigDecimal) && (e.getValue() instanceof BigDecimal)) {
				BigDecimal old = (BigDecimal) e.getOldValue();
				BigDecimal value = (BigDecimal) e.getValue();
				if ((old != null) && (value != null)){
					isChange = old.floatValue() != value.floatValue();
				}
			}

			Map sumMap = new HashMap();
			int currentRow = e.getRowIndex();
			for (int i = this.dynRowBase; i < this.houseSubIndex; ++i) {
				if ((i == currentRow) && (isChange)) {
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(table.getCell(i, this.buildAreaColummnIndex).getValue());
					if (changedCol == 2) {
						BigDecimal tmpCubRate = FDCHelper.ZERO;
						if (!(FDCHelper.isNullZero(buildArea))) {
							tmpCubRate = FDCNumberHelper.divide(table.getCell(i, this.buildAreaColummnIndex).getValue(),
									table.getCell(i,this.containAreaColummnIndex).getValue());
							table.getCell(i, this.cubageRateColummnIndex).setValue(tmpCubRate);
						} else {
							tmpCubRate = FDCNumberHelper.multiply(table.getCell(i, this.containAreaColummnIndex).getValue(),
									table.getCell(i,this.cubageRateColummnIndex).getValue());
							table.getCell(i, this.buildAreaColummnIndex).setValue(tmpCubRate);
						}
					} else {
						BigDecimal tmpConArea;
						if (changedCol == 3) {
							tmpConArea = FDCHelper.ZERO;
							if (!(FDCHelper.isNullZero(buildArea))) {
								tmpConArea = FDCNumberHelper.divide(table.getCell(i,this.buildAreaColummnIndex).getValue(), 
										table.getCell(i, this.cubageRateColummnIndex).getValue());
								table.getCell(i, this.containAreaColummnIndex).setValue(tmpConArea);
							} else {
								tmpConArea = FDCNumberHelper.multiply(table.getCell(i,this.containAreaColummnIndex).getValue(), 
										table.getCell(i,this.cubageRateColummnIndex).getValue());
								table.getCell(i, this.buildAreaColummnIndex).setValue(tmpConArea);
							}
						} else if (changedCol == 5) {
							tmpConArea = FDCHelper.ZERO;
							if (!(FDCHelper.isNullZero(FDCNumberHelper.toBigDecimal(table.getCell(i,this.cubageRateColummnIndex).getValue())))) {
								tmpConArea = FDCNumberHelper.divide(table.getCell(i,this.buildAreaColummnIndex).getValue(), 
										table.getCell(i,this.cubageRateColummnIndex).getValue());
								table.getCell(i, this.containAreaColummnIndex).setValue(tmpConArea);
							} else {
								tmpConArea = FDCNumberHelper.divide(table.getCell(i, this.buildAreaColummnIndex).getValue(), 
										table.getCell(i,this.containAreaColummnIndex).getValue());
								table.getCell(i, this.cubageRateColummnIndex).setValue(tmpConArea);
							}
						}
					}
					BigDecimal tmp = FDCHelper.ZERO;
					tmp = FDCNumberHelper.divide(table.getCell(i,this.sellAreaColummnIndex).getValue(), table.getCell(i, this.huColummnIndex).getValue());
					table.getCell(i, this.avgHuColummnIndex).setValue(tmp);
				}
				sumMap.put("cubageRate", FDCNumberHelper.add(sumMap.get("cubageRate"), table.getCell(i,this.cubageRateColummnIndex).getValue()));
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i,this.containAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i,this.constructAreaColumnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i,this.buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i,this.sellAreaColummnIndex).getValue()));
				sumMap.put("elevators", FDCNumberHelper.add(sumMap.get("elevators"), table.getCell(i, this.elevatorsIndex).getValue()));
				sumMap.put("floors", FDCNumberHelper.add(sumMap.get("floors"),table.getCell(i, this.floorsIndex).getValue()));
				sumMap.put("floorHeight", FDCNumberHelper.add(sumMap.get("floorHeight"), table.getCell(i,this.floorHeightIndex).getValue()));				
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"),table.getCell(i, this.unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, this.huColummnIndex).getValue()));
			}

			BigDecimal max = FDCHelper.ZERO;
			int maxProductRate = this.dynRowBase;
			for (int i = this.dynRowBase; i < this.houseSubIndex; ++i) {
				BigDecimal tmp = FDCNumberHelper.divide(table.getCell(i,this.sellAreaColummnIndex).getValue(), sumMap.get("sellArea"));
				table.getCell(i, this.productRateColummnIndex).setValue(tmp);
				if ((tmp != null) && (tmp.compareTo(max) > 0)) {
					max = tmp;
					maxProductRate = i;
				}
				sumMap.put("productRate", FDCNumberHelper.add(sumMap.get("productRate"), tmp));
			}
			BigDecimal diff = FDCHelper.ONE.subtract(FDCNumberHelper.toBigDecimal(sumMap.get("productRate")));
			if (diff.signum() != 0) {
				table.getCell(maxProductRate, this.productRateColummnIndex).setValue(
						FDCNumberHelper.add(table.getCell(maxProductRate,this.productRateColummnIndex).getValue(), diff));
			}
			table.getCell(this.houseSubIndex, this.containAreaColummnIndex).setValue(sumMap.get("containArea"));
			table.getCell(this.houseSubIndex, this.constructAreaColumnIndex).setValue(sumMap.get("constructArea"));
			table.getCell(this.houseSubIndex, this.buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			table.getCell(this.houseSubIndex, this.sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			table.getCell(this.houseSubIndex, this.elevatorsIndex).setValue(sumMap.get("elevators"));
			table.getCell(this.houseSubIndex, this.floorsIndex).setValue(sumMap.get("floors"));
			table.getCell(this.houseSubIndex, this.floorHeightIndex).setValue(sumMap.get("floorHeight"));
			table.getCell(this.houseSubIndex, this.unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(this.houseSubIndex, this.huColummnIndex).setValue(sumMap.get("hu"));
			table.getCell(this.houseSubIndex, this.productRateColummnIndex).setValue(FDCNumberHelper.ONE);

			table.getCell(this.houseSubIndex, this.cubageRateColummnIndex).setValue(
							FDCNumberHelper.divide(sumMap.get("buildArea"),sumMap.get("containArea")));
			
			table.getCell(this.houseSubIndex, this.avgHuColummnIndex).setValue(
					FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));

			sumMap.clear();
			for (int i = this.houseSubIndex + 1; i < this.businessSubIndex; ++i) {
				if ((i == currentRow) && (isChange)) {
					BigDecimal tmp = FDCHelper.ZERO;
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(table.getCell(i, this.buildAreaColummnIndex).getValue());
					if (changedCol == 2) {
						BigDecimal tmpCubRate = FDCHelper.ZERO;
						if (!(FDCHelper.isNullZero(buildArea))) {
							tmpCubRate = FDCNumberHelper.divide(table.getCell(i, this.buildAreaColummnIndex).getValue(),
									table.getCell(i,this.containAreaColummnIndex).getValue());

							table.getCell(i, this.cubageRateColummnIndex).setValue(tmpCubRate);
						} else {
							tmpCubRate = FDCNumberHelper.multiply(
									table.getCell(i, this.containAreaColummnIndex).getValue(), table.getCell(i,this.cubageRateColummnIndex).getValue());

							table.getCell(i, this.buildAreaColummnIndex).setValue(tmpCubRate);
						}
					} else {
						BigDecimal tmpConArea;
						if (changedCol == 3) {
							tmpConArea = FDCHelper.ZERO;
							if (!(FDCHelper.isNullZero(buildArea))) {
								tmpConArea = FDCNumberHelper.divide(table.getCell(i,this.buildAreaColummnIndex).getValue(), 
										table.getCell(i,this.cubageRateColummnIndex).getValue());

								table.getCell(i, this.containAreaColummnIndex).setValue(tmpConArea);
							} else {
								tmpConArea = FDCNumberHelper.multiply(
										table.getCell(i,this.containAreaColummnIndex).getValue(), table.getCell(i,this.cubageRateColummnIndex).getValue());

								table.getCell(i, this.buildAreaColummnIndex).setValue(tmpConArea);
							}
						} else if (changedCol == 5) {
							tmpConArea = FDCHelper.ZERO;
							if (!(FDCHelper.isNullZero(FDCNumberHelper.toBigDecimal(table.getCell(i,this.cubageRateColummnIndex).getValue())))) {
								tmpConArea = FDCNumberHelper.divide(
										table.getCell(i,this.buildAreaColummnIndex).getValue(), table.getCell(i,this.cubageRateColummnIndex).getValue());

								table.getCell(i, this.containAreaColummnIndex).setValue(tmpConArea);
							} else {
								tmpConArea = FDCNumberHelper.divide(table.getCell(i, this.buildAreaColummnIndex).getValue(), 
										table.getCell(i,this.containAreaColummnIndex).getValue());

								table.getCell(i, this.cubageRateColummnIndex).setValue(tmpConArea);
							}
						}
					}
					tmp = FDCNumberHelper.divide(table.getCell(i,this.sellAreaColummnIndex).getValue(), table.getCell(i, this.huColummnIndex).getValue());
					table.getCell(i, this.avgHuColummnIndex).setValue(tmp);
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i,this.containAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i,this.constructAreaColumnIndex).getValue()));			
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i,this.buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i,this.sellAreaColummnIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"),table.getCell(i, this.unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, this.huColummnIndex).getValue()));
			}
			table.getCell(this.businessSubIndex, this.containAreaColummnIndex).setValue(sumMap.get("containArea"));
			table.getCell(this.businessSubIndex, this.constructAreaColumnIndex).setValue(sumMap.get("constructArea"));
			table.getCell(this.businessSubIndex, this.buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			table.getCell(this.businessSubIndex, this.sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			table.getCell(this.businessSubIndex, this.unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(this.businessSubIndex, this.huColummnIndex).setValue(sumMap.get("hu"));

			table.getCell(this.businessSubIndex, this.cubageRateColummnIndex).setValue(
							FDCNumberHelper.divide(sumMap.get("buildArea"),sumMap.get("containArea")));

			table.getCell(this.businessSubIndex, this.avgHuColummnIndex).setValue(
							FDCNumberHelper.divide(sumMap.get("sellArea"),sumMap.get("hu")));

			sumMap.clear();
			for (int i = this.businessSubIndex + 1; i < this.publicSubIndex; ++i) {
				if ((i == currentRow) && (isChange)) {
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(table
							.getCell(i, this.buildAreaColummnIndex).getValue());

					if (changedCol == 2) {
						BigDecimal tmpCubRate = FDCHelper.ZERO;
						if (!(FDCHelper.isNullZero(buildArea))) {
							tmpCubRate = FDCNumberHelper.divide(table.getCell(
									i, this.buildAreaColummnIndex).getValue(),
									table.getCell(i,
											this.containAreaColummnIndex)
											.getValue());

							table.getCell(i, this.cubageRateColummnIndex)
									.setValue(tmpCubRate);
						} else {
							tmpCubRate = FDCNumberHelper.multiply(table
									.getCell(i, this.containAreaColummnIndex)
									.getValue(), table.getCell(i,
									this.cubageRateColummnIndex).getValue());

							table.getCell(i, this.buildAreaColummnIndex)
									.setValue(tmpCubRate);
						}
					} else {
						BigDecimal tmpConArea;
						if (changedCol == 3) {
							tmpConArea = FDCHelper.ZERO;
							if (!(FDCHelper.isNullZero(buildArea))) {
								tmpConArea = FDCNumberHelper
										.divide(table.getCell(i,
												this.buildAreaColummnIndex)
												.getValue(), table.getCell(i,
												this.cubageRateColummnIndex)
												.getValue());

								table.getCell(i, this.containAreaColummnIndex)
										.setValue(tmpConArea);
							} else {
								tmpConArea = FDCNumberHelper
										.multiply(table.getCell(i,
												this.containAreaColummnIndex)
												.getValue(), table.getCell(i,
												this.cubageRateColummnIndex)
												.getValue());

								table.getCell(i, this.buildAreaColummnIndex)
										.setValue(tmpConArea);
							}
						} else if (changedCol == 5) {
							tmpConArea = FDCHelper.ZERO;
							if (!(FDCHelper.isNullZero(FDCNumberHelper
									.toBigDecimal(table.getCell(i,
											this.cubageRateColummnIndex)
											.getValue())))) {
								tmpConArea = FDCNumberHelper
										.divide(table.getCell(i,
												this.buildAreaColummnIndex)
												.getValue(), table.getCell(i,
												this.cubageRateColummnIndex)
												.getValue());

								table.getCell(i, this.containAreaColummnIndex)
										.setValue(tmpConArea);
							} else {
								tmpConArea = FDCNumberHelper.divide(table
										.getCell(i, this.buildAreaColummnIndex)
										.getValue(), table.getCell(i,
										this.containAreaColummnIndex)
										.getValue());

								table.getCell(i, this.cubageRateColummnIndex)
										.setValue(tmpConArea);
							}
						}
					}
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap
						.get("containArea"), table.getCell(i,
						this.containAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap
						.get("constructArea"), table.getCell(i,
						this.constructAreaColumnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap
						.get("buildArea"), table.getCell(i,
						this.buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap
						.get("sellArea"), table.getCell(i,
						this.sellAreaColummnIndex).getValue()));
			}
			table.getCell(this.publicSubIndex, this.containAreaColummnIndex)
					.setValue(sumMap.get("containArea"));
			table.getCell(this.publicSubIndex, this.constructAreaColumnIndex)
					.setValue(sumMap.get("constructArea"));
			table.getCell(this.publicSubIndex, this.buildAreaColummnIndex)
					.setValue(sumMap.get("buildArea"));
			table.getCell(this.publicSubIndex, this.sellAreaColummnIndex)
					.setValue(sumMap.get("sellArea"));
			table.getCell(this.publicSubIndex, this.cubageRateColummnIndex)
					.setValue(
							FDCNumberHelper.divide(sumMap.get("buildArea"),
									sumMap.get("containArea")));

			sumMap.clear();
			for (int i = this.publicSubIndex + 2; i < this.parkingSubIndex; ++i) {
				sumMap.put("containArea", FDCNumberHelper.add(sumMap
						.get("containArea"), table.getCell(i,
						this.containAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap
						.get("constructArea"), table.getCell(i,
						this.constructAreaColumnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap
						.get("buildArea"), table.getCell(i,
						this.buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap
						.get("sellArea"), table.getCell(i,
						this.sellAreaColummnIndex).getValue()));
				sumMap.put("park", FDCNumberHelper.add(sumMap.get("park"),
						table.getCell(i, this.parksColummnIndex).getValue()));
			}
			table.getCell(this.parkingSubIndex, this.containAreaColummnIndex)
					.setValue(sumMap.get("containArea"));
			table.getCell(this.parkingSubIndex, this.constructAreaColumnIndex)
					.setValue(sumMap.get("constructArea"));
			table.getCell(this.parkingSubIndex, this.buildAreaColummnIndex)
					.setValue(sumMap.get("buildArea"));
			table.getCell(this.parkingSubIndex, this.sellAreaColummnIndex)
					.setValue(sumMap.get("sellArea"));
			table.getCell(this.parkingSubIndex, this.parksColummnIndex)
					.setValue(sumMap.get("park"));

			BigDecimal total = FDCHelper.ZERO;
			total = FDCNumberHelper.add(total, table.getCell(
					this.houseSubIndex, this.containAreaColummnIndex)
					.getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.businessSubIndex, this.containAreaColummnIndex)
					.getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.publicSubIndex, this.containAreaColummnIndex)
					.getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.parkingSubIndex, this.containAreaColummnIndex)
					.getValue());
			table.getCell(this.totalIndex, this.containAreaColummnIndex)
					.setValue(total);

			total = FDCHelper.ZERO;
			total = FDCNumberHelper.add(total, table.getCell(
					this.houseSubIndex, this.constructAreaColumnIndex)
					.getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.businessSubIndex, this.constructAreaColumnIndex)
					.getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.publicSubIndex, this.constructAreaColumnIndex)
					.getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.parkingSubIndex, this.constructAreaColumnIndex)
					.getValue());
			table.getCell(this.totalIndex, this.constructAreaColumnIndex)
					.setValue(total);

			total = FDCHelper.ZERO;
			total = FDCNumberHelper.add(total, table.getCell(
					this.houseSubIndex, this.buildAreaColummnIndex).getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.businessSubIndex, this.buildAreaColummnIndex)
					.getValue());
			total = FDCNumberHelper
					.add(total, table.getCell(this.publicSubIndex,
							this.buildAreaColummnIndex).getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.parkingSubIndex, this.buildAreaColummnIndex)
					.getValue());
			table.getCell(this.totalIndex, this.buildAreaColummnIndex)
					.setValue(total);

			total = FDCHelper.ZERO;
			total = FDCNumberHelper.add(total, table.getCell(
					this.houseSubIndex, this.sellAreaColummnIndex).getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.businessSubIndex, this.sellAreaColummnIndex)
					.getValue());
			total = FDCNumberHelper.add(total, table.getCell(
					this.publicSubIndex, this.sellAreaColummnIndex).getValue());
			total = FDCNumberHelper
					.add(total, table.getCell(this.parkingSubIndex,
							this.sellAreaColummnIndex).getValue());
			table.getCell(this.totalIndex, this.sellAreaColummnIndex).setValue(
					total);

			BigDecimal newtotalBuildArea = FDCHelper.toBigDecimal(table
					.getCell(this.totalIndex, this.buildAreaColummnIndex)
					.getValue());
			if ((this.totalBuildAreaCell != null) && (e.getType() != 10000)
					&& (oldtotalBuildArea.compareTo(newtotalBuildArea) != 0))
				this.totalBuildAreaCell.setValue(newtotalBuildArea);
		}
	  }

	  private void calcArea()
	  {
	    BigDecimal oldGreenAmt = FDCHelper.toBigDecimal(this.totalGreenAreaCell.getValue());
	    BigDecimal oldContainAmt = FDCHelper.toBigDecimal(this.totalContainAreaCell.getValue());
	    BigDecimal amt = FDCHelper.ZERO;
	    amt = FDCNumberHelper.add(amt, this.pitchRoadCell.getValue());
	    amt = FDCNumberHelper.add(amt, this.concreteRoadCell.getValue());
	    amt = FDCNumberHelper.add(amt, this.hardRoadCell.getValue());
	    if (!(isPlanIndexLogic())) {
	      amt = FDCNumberHelper.add(amt, this.hardSquareCell.getValue());
	      amt = FDCNumberHelper.add(amt, this.hardManRoadCell.getValue());
	    }
	    this.totalRoadAreaCell.setValue(amt);
	    amt = FDCHelper.ZERO;
	    amt = FDCNumberHelper.add(amt, this.importPubGreenAreaCell.getValue());
	    amt = FDCNumberHelper.add(amt, this.houseGreenAreaCell.getValue());
	    amt = FDCNumberHelper.add(amt, this.privateGardenCell.getValue());
	    amt = FDCNumberHelper.add(amt, this.warterViewAreaCell.getValue());
	    if (isPlanIndexLogic()) {
	      amt = FDCNumberHelper.add(amt, this.hardSquareCell.getValue());
	      amt = FDCNumberHelper.add(amt, this.hardManRoadCell.getValue());
	    }
	    this.totalGreenAreaCell.setValue(amt);
	    if ((amt.compareTo(oldGreenAmt) != 0) || (FDCHelper.toBigDecimal(this.totalContainAreaCell.getValue()).compareTo(oldContainAmt) != 0))
	      this.greenAreaRateCell.setValue(FDCNumberHelper.divide(amt, this.totalContainAreaCell.getValue(), 4, 4));
	  }

	  void clear() {
	    EventListener[] listeners = this.f7productType.getListeners(PreChangeListener.class);
	    for (int i = 0; i < listeners.length; ++i)
	      this.f7productType.removePreChangeListener((PreChangeListener)listeners[i]);

	    listeners = this.f7productType.getListeners(DataChangeListener.class);
	    for (int i = 0; i < listeners.length; ++i)
	      this.f7productType.removeDataChangeListener((DataChangeListener)listeners[i]);

	    this.f7productType.removeAll();
	    this.f7productType = null;
	    listeners = this.table.getListeners(KDTEditListener.class);
	    for (int i = 0; i < listeners.length; ++i) {
	      this.table.removeKDTEditListener((KDTEditListener)listeners[i]);
	    }

	    listeners = this.table.getListeners(KDTMouseListener.class);
	    for (int i = 0; i < listeners.length; ++i)
	      this.table.removeKDTMouseListener((KDTMouseListener)listeners[i]);

	    this.table.setBeforeAction(null);
	    this.table.setAfterAction(null);
	    this.table.removeAll();
	    this.table = null;
	    this.headId = null;
	    this.binder = null;
	    this.measureCostEditU = null;
	    this.planIndexInfo = null;
	  }

	  public JComponent getContentPanel()
	  {
	    if (isHasSubTable()) {
	      KDSplitPane panel = new KDSplitPane();
	      panel.setOrientation(0);
	      panel.setDividerLocation(300);
	      panel.setOneTouchExpandable(true);

	      panel.add(getTable(), "top");
	      KDContainer con = new KDContainer();
	      con.setContainerType(1);
	      con.setEnableActive(false);
	      con.setTitle("自定义指标");
	      con.getContentPane().setLayout(new BorderLayout());
	      con.getContentPane().add(getSubTable(), "Center");
	      
	      JButton btnAdd=con.add(new AbstractAction(){
				{
					putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
					putValue(Action.SHORT_DESCRIPTION, "添加行");
				}
				public void actionPerformed(ActionEvent e) {
					subTable.addRow();
				}
			});
			
			JButton btnDel=con.add(new AbstractAction(){
				{
					putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
					putValue(Action.SHORT_DESCRIPTION, "删除行");
				}
				public void actionPerformed(ActionEvent e) {
					int[] selectedRows = KDTableUtil.getSelectedRows(subTable);
					for(int i=selectedRows.length-1;i>=0;i--){
						subTable.removeRow(selectedRows[i]);
					}
				}
			});
	      btnAdd.setEnabled(true);
	      btnAdd.setVisible(true);
	      btnDel.setEnabled(true);
	      btnDel.setVisible(true);
	      panel.add(con, "bottom");

	      if (this.measureCostEditU.getOprtState().equals(OprtState.VIEW)) {
	        this.subTable.getStyleAttributes().setLocked(true);
	        btnDel.setEnabled(false);
	        btnAdd.setEnabled(false);
	      }
	      return panel;
	    }
	    return getTable();
	  }

	  private static boolean isBuildPartLogic()
	  {
	    return (!(isBuildPartLogic));
	  }

	  private boolean isPlanIndexLogic() {
	    return this.isPlanIndexLogic;
	  }

	  private boolean isHasSubTable() {
	    return this.isHasSubTable;
	  }

	  private void initSubTable(PlanIndexInfo info)
	  {
	    this.subTable = new KDTable(5, 1, 0);
	    this.subTable.setName("自定义指标");
	    IRow headRow = this.subTable.getHeadRow(0);
	    this.subTable.getColumn(0).setKey("id");
	    this.subTable.getColumn(0).getStyleAttributes().setHided(true);
	    this.subTable.getColumn(1).setKey("number");
	    this.subTable.getColumn(2).setKey("name");
	    this.subTable.getColumn(3).setKey("value");
	    this.subTable.getColumn(4).setKey("productType");
	    this.subTable.getColumn("name").getStyleAttributes().setLocked(true);
	    this.subTable.getColumn("number").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	    this.subTable.getColumn("value").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	    subTable.addKDTEditListener(new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
				
				int rowIndex = e.getRowIndex();
				int colIndex=e.getColIndex();
				IRow row=subTable.getRow(rowIndex);
				if(colIndex==subTable.getColumnIndex("number")){
					ApportionTypeInfo info=(ApportionTypeInfo)row.getCell("number").getValue();
					if(info!=null){
						row.getCell("name").setValue(info.getName());
					}else{
						row.getCell("name").setValue(null);
					}
				}
			}
		});
	    subTable.addKDTMouseListener(new KDTMouseListener(){
			public void tableClicked(KDTMouseEvent e) {/*
				int rowIndex = e.getRowIndex();
				int colIndex=e.getColIndex();
				IRow row=subTable.getRow(rowIndex);
				if(colIndex==subTable.getColumnIndex("product")){
					Object object = row.getCell("isProduct").getValue();
					row.getCell("isProduct").setValue(Boolean.valueOf(object==Boolean.FALSE));
				}
			*/}
		});
	    headRow.getCell("id").setValue("ID");
	    headRow.getCell("number").setValue("编码");
	    headRow.getCell("name").setValue("名称");
	    headRow.getCell("value").setValue("指标值");
	    headRow.getCell("productType").setValue("产品");

	    final String indexSql = "select fapportiontypeid from t_fdc_measureindex where fisenabled=1 and ftype='1CUSTOM' ";

	    final Set nameSet = new HashSet();
	    String[] name = { "总占地面积(m2)", "建筑用地面积(m2)", "总建筑面积(m2)", "建筑物占地面积(m2)", "建筑密度", "绿地率", "计容积率面积(m2)", "容积率", "沥青路面车行道", "砼路面车行道（停车场）", "硬质铺装车行道", "硬质铺装广场", "硬质铺装人行道", "重要公共绿地", "组团宅间绿化", "底层私家花园", "水景面积", "占地面积", "建筑面积", "可售面积", "产品比例", "平均每户面积", "单元数", "户数" };

	    for (int i = 0; i < name.length; ++i)
	      nameSet.add(name[i]);

	    KDBizPromptBox f7 = new KDBizPromptBox();
	    f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ApportionTypeQuery");
	    f7.setDisplayFormat("$number$");
	    f7.setCommitFormat("$number$");
	    f7.setEditFormat("$number$");
	    f7.setRequired(true);
	    f7.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				try {
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.buildAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.sellAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.placeAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.aimCostType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.appointType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("name", nameSet,
									CompareType.NOTINCLUDE));
					filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
					//TODO 成本分摊过滤
//					filter.getFilterItems().add(new FilterItemInfo("forCostApportion",Boolean.TRUE));
					//TODO 有点性能问题 过滤集团指标
					boolean isMeasureIndex = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MEASUREINDEX);
					if(isMeasureIndex){
						filter.getFilterItems().add(new FilterItemInfo("id",indexSql,CompareType.INNER));
					}
					filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
				} catch (BOSException e1) {
					measureCostEditU.handUIException(e1);
				} catch (EASBizException e2) {
					measureCostEditU.handUIException(e2);
				}
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
	    f7.addCommitListener(new CommitListener(){
			public void willCommit(CommitEvent e) {
				KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				try {
					filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
				} catch (BOSException e1) {
					measureCostEditU.handUIException(e1);
				}
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
	    KDTDefaultCellEditor editor = new KDTDefaultCellEditor(f7);
	    this.subTable.getColumn("number").setEditor(editor);

	    ObjectValueRender render = new ObjectValueRender();
	    render.setFormat(new BizDataFormat("$number$"));
	    this.subTable.getColumn("number").setRenderer(render);

	    f7 = new KDBizPromptBox();
	    f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");

	    f7.setDisplayFormat("$name$");
	    f7.setCommitFormat("$number$");
	    f7.setEditFormat("$number$");

	    Set productSet = getProductIdSet();
	    EntityViewInfo view = new EntityViewInfo();
	    view.setFilter(new FilterInfo());
	    if (productSet.size() > 0)
	      view.getFilter().getFilterItems().add(new FilterItemInfo("id", productSet, CompareType.INCLUDE));

	    f7.setEntityViewInfo(view);
	    f7.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				Set productSet=getProductIdSet();
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				if(productSet.size()>0){
					view.getFilter().getFilterItems().add(new FilterItemInfo("id",productSet,CompareType.INCLUDE));
				}
				((KDBizPromptBox)e.getSource()).setEntityViewInfo(view);
				
			}
		});
	    editor = new KDTDefaultCellEditor(f7);
	    this.subTable.getColumn("productType").setEditor(editor);
	    this.subTable.getColumn("value").setEditor(getCellNumberEdit(8));
	    FDCHelper.formatTableNumber(this.subTable, "value");
	    if ((info == null) || (info.getCustomEntrys() == null) || (info.getCustomEntrys().size() == 0))
	      return;

	    for (Iterator iter = info.getCustomEntrys().iterator(); iter.hasNext(); ) {
	      IRow row = this.subTable.addRow();
	      CustomPlanIndexEntryInfo entry = (CustomPlanIndexEntryInfo)iter.next();
	      row.getCell("id").setValue(entry.getId().toString());
	      row.getCell("number").setValue(entry.getApportType());
	      row.getCell("name").setValue(entry.getApportType().getName());
	      row.getCell("productType").setValue(entry.getProductType());
	      row.getCell("value").setValue(entry.getValue());
	      row.setUserObject(entry);
	    }
	  }

	  public KDTable getConstructTable()
	  {
	    return this.constrTable; }

	  private void initConstructTable(MeasureCostInfo info) {
	    this.constrTable = new KDTable(4, 1, 0);
	    this.constrTable.setName("建造标准");
	    IRow headRow = this.constrTable.getHeadRow(0);
	    this.constrTable.getColumn(0).setKey("id");
	    this.constrTable.getColumn(0).getStyleAttributes().setHided(true);
	    this.constrTable.getColumn(1).setKey("indexName1");
	    this.constrTable.getColumn(2).setKey("indexName2");
	    this.constrTable.getColumn(3).setKey("indexName3");

	    headRow.getCell(0).setUserObject("construct");
	    headRow.getCell(0).setValue("ID");
	    headRow.getCell(1).setValue("一级指标名称");
	    headRow.getCell(2).setValue("二级指标名称");
	    headRow.getCell(3).setValue("三级指标名称");

	    if ((info == null) || (info.getConstrEntrys() == null) || (info.getConstrEntrys().size() == 0)) {
	      return;
	    }

	    Iterator iter2 = info.getConstrEntrys().iterator();
	    while (true) { ConstructPlanIndexEntryInfo indexEntry;
	      IRow row;
	      while (true) { if (!(iter2.hasNext())) return;
	        indexEntry = (ConstructPlanIndexEntryInfo)iter2.next();

	        if (indexEntry.getProductType() != null)
	          break;
	      }
	      ProductTypeInfo indexProduct = indexEntry.getProductType();
	      String productId = indexProduct.getId().toString();
	      String entryKey = indexEntry.getIndexName1() + indexEntry.getIndexName2() + indexEntry.getIndexName3();
	      if (this.constrTable.getRowCount() == 0) {
	        IColumn column = this.constrTable.addColumn();
	        column.setWidth(150);
	        column.setKey(productId);
	        headRow.getCell(productId).setValue(indexProduct.getName());
	        headRow.getCell(productId).setUserObject(indexProduct);
	        row = this.constrTable.addRow();
	        row.getCell("id").setValue(indexEntry.getId().toString());
	        row.getCell("indexName1").setValue(indexEntry.getIndexName1());
	        row.getCell("indexName2").setValue(indexEntry.getIndexName2());
	        row.getCell("indexName3").setValue(indexEntry.getIndexName3());
	        row.getCell(productId).setValue(indexEntry.getDesc());
	        row.getCell(productId).setUserObject(indexEntry);
	      }
	      boolean isAdd = false;
	      for (int i = 0; i < this.constrTable.getRowCount(); ++i) {
	        IRow idxRow = this.constrTable.getRow(i);
	        String idxKey = ((String)idxRow.getCell("indexName1").getValue()) + idxRow.getCell("indexName2").getValue() + idxRow.getCell("indexName3").getValue();
	        if (entryKey.equals(idxKey)) {
	          isAdd = true;
	          if (headRow.getCell(productId) == null) {
	            IColumn column = this.constrTable.addColumn();
	            column.setWidth(150);
	            column.setKey(productId);
	            headRow.getCell(productId).setValue(indexProduct.getName());
	            headRow.getCell(productId).setUserObject(indexProduct);
	          }
	          idxRow.getCell(productId).setValue(indexEntry.getDesc());
	          idxRow.getCell(productId).setUserObject(indexEntry);
	        }
	      }
	      if (!(isAdd)) {
	        row = this.constrTable.addRow();
	        row.getCell("id").setValue(indexEntry.getId().toString());
	        row.getCell("indexName1").setValue(indexEntry.getIndexName1());
	        row.getCell("indexName2").setValue(indexEntry.getIndexName2());
	        row.getCell("indexName3").setValue(indexEntry.getIndexName3());
	        if (headRow.getCell(productId) == null) {
	          IColumn column = this.constrTable.addColumn();
	          column.setWidth(150);
	          column.setKey(productId);
	          headRow.getCell(productId).setValue(indexProduct.getName());
	          headRow.getCell(productId).setUserObject(indexProduct);
	        }
	        row.getCell(productId).setValue(indexEntry.getDesc());
	        row.getCell(productId).setUserObject(indexEntry);
	      }
	    }
	  }

	  public KDTable getSubTable()
	  {
	    return this.subTable;
	  }

	  private void storeCustom(PlanIndexInfo planIndexInfo) {
	    if (!(isHasSubTable()))
	      return;

	    verifyCustom();
	    CustomPlanIndexEntryCollection customEntrys = planIndexInfo.getCustomEntrys();
	    if (customEntrys == null)
	      customEntrys = new CustomPlanIndexEntryCollection();

	    customEntrys.clear();

	    for (int i = 0; i < this.subTable.getRowCount(); ++i) {
	      IRow row = this.subTable.getRow(i);
	      CustomPlanIndexEntryInfo entry = (CustomPlanIndexEntryInfo)row.getUserObject();
	      if (entry == null) {
	        entry = new CustomPlanIndexEntryInfo();
	        entry.setId(BOSUuid.create(entry.getBOSType()));
	        row.setUserObject(entry);
	      }
	      entry.setApportType((ApportionTypeInfo)row.getCell("number").getValue());
	      entry.setProductType((ProductTypeInfo)row.getCell("productType").getValue());
	      entry.setValue((BigDecimal)row.getCell("value").getValue());
	      entry.setParent(planIndexInfo);
	      if (entry.getProductType() != null)
	        entry.setIsProduct(true);
	      else
	        entry.setIsProduct(false);

	      customEntrys.add(entry);
	    }
	  }

	  public ConstructPlanIndexEntryCollection getConstrEntrys()
	  {
	    ConstructPlanIndexEntryCollection constructEntrys = this.costInfo.getConstrEntrys();
	    if (constructEntrys == null)
	      constructEntrys = new ConstructPlanIndexEntryCollection();

	    constructEntrys.clear();

	    for (int j = 4; j < this.constrTable.getColumnCount(); ++j) {
	      IRow headRow = this.constrTable.getHeadRow(0);
	      ProductTypeInfo product = (ProductTypeInfo)headRow.getCell(j).getUserObject();
	      String productId = product.getId().toString();

	      for (int i = 0; i < this.constrTable.getRowCount(); ++i) {
	        IRow row = this.constrTable.getRow(i);
	        if (row == null) continue;
	        ConstructPlanIndexEntryInfo entry = (row.getCell(productId) == null) ? null : (ConstructPlanIndexEntryInfo)row.getCell(productId).getUserObject();
	        if (entry == null) {
	          entry = new ConstructPlanIndexEntryInfo();
	          entry.setId(BOSUuid.create(entry.getBOSType()));
	          row.getCell(productId).setUserObject(entry);
	        }
	        entry.setIndexName1((String)row.getCell("indexName1").getValue());
	        entry.setIndexName2((String)row.getCell("indexName2").getValue());
	        entry.setIndexName3((String)row.getCell("indexName3").getValue());
	        entry.setDesc((String)row.getCell(productId).getValue());
	        entry.setProductType(product);
	        label290: constructEntrys.add(entry);
	      }
	    }
	    return constructEntrys;
	  }

	  private void verifyConstruct()
	  {
	  }

	  private void verifyCustom()
	  {
	    IRow row;
	    ApportionTypeInfo apportType;
	    for (int i = 0; i < this.subTable.getRowCount(); ++i) {
	      row = this.subTable.getRow(i);
	      apportType = (ApportionTypeInfo)row.getCell("number").getValue();
	      if (apportType == null) {
	        FDCMsgBox.showError(this.measureCostEditU, "编码必录");
	        this.subTable.getSelectManager().select(i, 1, i, 1);
	        this.measureCostEditU.getplTables().setSelectedIndex(1);
	        SysUtil.abort();
	      }

	      if (row.getCell("value").getValue() == null) {
	        FDCMsgBox.showError(this.measureCostEditU, "指标值必录");
	        this.subTable.getSelectManager().select(i, 3, i, 3);
	        this.measureCostEditU.getplTables().setSelectedIndex(1);
	        SysUtil.abort();
	      }
	    }

	    for (int i = 0; i < this.subTable.getRowCount(); ++i) {
	      row = this.subTable.getRow(i);
	      apportType = (ApportionTypeInfo)row.getCell("number").getValue();
	      ProductTypeInfo productType = (ProductTypeInfo)row.getCell("productType").getValue();
	      for (int j = i + 1; j < this.subTable.getRowCount(); ++j) {
	        IRow row2 = this.subTable.getRow(j);
	        ApportionTypeInfo apportType2 = (ApportionTypeInfo)row2.getCell("number").getValue();
	        ProductTypeInfo productType2 = (ProductTypeInfo)row2.getCell("productType").getValue();
	        if (apportType.getId().toString().equals(apportType2.getId().toString())) {
	          boolean isDup = false;
	          if ((productType == null) && (productType2 == null))
	            isDup = true;

	          if ((productType != null) && (productType2 != null) && (productType.getId().toString().equals(productType2.getId().toString())))
	            isDup = true;

	          if (isDup) {
	            FDCMsgBox.showError(this.measureCostEditU, "第" + (i + 1) + "行与第" + (j + 1) + "行存在重复");
	            SysUtil.abort();
	          }
	        }
	      }
	    }
	  }

	  public static KDTDefaultCellEditor getCellNumberEdit(int Precision)
	  {
	    KDFormattedTextField kdc = new KDFormattedTextField();
	    kdc.setDataType(1);
	    kdc.setPrecision(Precision);
	    kdc.setRequired(true);
	    kdc.setMinimumValue(FDCHelper.ZERO);
	    kdc.setMaximumValue(FDCHelper.MAX_VALUE);
	    kdc.setHorizontalAlignment(4);
	    kdc.setSupportedEmpty(true);
	    kdc.setVisible(true);
	    kdc.setEnabled(true);

	    KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
	    return editor;
	  }
	}