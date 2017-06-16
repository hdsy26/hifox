package com.demo.datatransfer.inbound;

import org.edf.hifox.core.datatransfer.Body;

public class A000S100Req implements Body {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2117369395483585694L;
	
	private String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
