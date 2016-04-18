package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class AttachmentInfo implements Serializable{
	
	private String attachmentId;
	
	private String attachmentName;
	
	private byte[] attachment;
	
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	

}
