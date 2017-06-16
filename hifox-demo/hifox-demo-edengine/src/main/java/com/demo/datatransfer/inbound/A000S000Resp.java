package com.demo.datatransfer.inbound;

import org.edf.hifox.core.datatransfer.Body;

public class A000S000Resp implements Body {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1389671026997892234L;
	
	private String text;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
