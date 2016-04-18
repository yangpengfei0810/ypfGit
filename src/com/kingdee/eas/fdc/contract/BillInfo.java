package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

import com.kingdee.eas.base.attachment.AttachmentInfo;

public class BillInfo implements Serializable{

	private String billType;
	
	private String billId;
	
	private String billState;
	
	private boolean isExistAttachment;
	
	private AttachmentInfo [] attachmentAry;

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public boolean isExistAttachment() {
		return isExistAttachment;
	}

	public void setExistAttachment(boolean isExistAttachment) {
		this.isExistAttachment = isExistAttachment;
	}

	public AttachmentInfo[] getAttachmentAry() {
		return attachmentAry;
	}

	public void setAttachmentAry(AttachmentInfo[] attachmentAry) {
		this.attachmentAry = attachmentAry;
	}
	
	
}
