package org.edf.hifox.core.datatransfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SecurityInfo")
@XmlRootElement(name="SecurityInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class SecurityInfo {
	@XStreamAlias("mode")
	@XmlElement(name="mode")
	private String mode;
	
	@XStreamAlias("signature")
	@XmlElement(name="signature")
	private String signature;
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
