package com.demo.datatransfer.inbound;

import org.edf.hifox.core.datatransfer.Body;

public class A000S100Resp implements Body {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3853544925114887189L;
	
	private String text;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
