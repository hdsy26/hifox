package com.demo.datatransfer.inbound;

import org.edf.hifox.core.datatransfer.Body;

public class A000S000Req implements Body {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2081246497486563621L;
	
	private String type;
	private String text;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
