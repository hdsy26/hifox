package org.edf.hifox.security.handler.outbound;

import java.util.Map;

import org.edf.hifox.core.chain.invocation.Invocation;
import org.edf.hifox.core.datatransfer.Body;
import org.edf.hifox.core.datatransfer.Message;
import org.edf.hifox.core.datatransfer.SecurityInfo;
import org.edf.hifox.core.datatransfer.support.RequestHead;
import org.edf.hifox.core.handler.Handler;
import org.edf.hifox.core.reqinfo.OutboundRequestInfo;
import org.edf.hifox.core.util.SpringContextUtil;
import org.edf.hifox.security.cipher.Encipher;
import org.edf.hifox.security.constant.SecurityConstant;
import org.edf.hifox.security.signer.Signer;

/**
 * 
 * @author WangYang
 *
 */
public class OutboundSecurityHandler implements Handler<OutboundRequestInfo> {

	@Override
	public void handle(OutboundRequestInfo data, Invocation invocation) {
		if(data.getRequestMessage() instanceof Message) {
			messageHandle(data, invocation);
		} else if(data.getRequestMessage() instanceof Map) {
			mapHandle(data, invocation);
		} else {
			stringHandle(data, invocation);
		}
	}
	
	@SuppressWarnings({"unchecked"})
	private void messageHandle(OutboundRequestInfo data, Invocation invocation) {
		Message<RequestHead, Body> message = (Message<RequestHead, Body>)data.getRequestMessage();
		RequestHead head = message.getHead();
		String targetNodeId = head.getSysTargetNodeId();
		
		SecurityInfo info = new SecurityInfo();
		
		Signer signer = SpringContextUtil.getBean(targetNodeId + SecurityConstant.SECURITY_MESSAGE_SIGNER_SUFFIX, Signer.class);
		if (signer != null) {
			info.setMode(SecurityConstant.SECURITY_MESSAGE_MODE_SIGN);
		} else {
			Encipher encipher = SpringContextUtil.getBean(targetNodeId + SecurityConstant.SECURITY_MESSAGE_ENCIPHER_SUFFIX, Encipher.class);
			info.setMode(SecurityConstant.SECURITY_MESSAGE_MODE_ENCRYPT);
			String contentString = encipher.encrypt(data.getContentString());
			data.setContentString(contentString);
		}
		
		invocation.invoke(data);
	}
	
	private void mapHandle(OutboundRequestInfo data, Invocation invocation) {
		invocation.invoke(data);
	}
	
	private void stringHandle(OutboundRequestInfo data, Invocation invocation) {
		invocation.invoke(data);
	}

}
