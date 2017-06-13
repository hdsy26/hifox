package org.edf.hifox.security.datatransfer.inbound;

import org.edf.hifox.core.datatransfer.Body;

public class A000S100Req implements Body {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2108241674964257564L;
	
	private String datetime;
	private String text;
	
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
