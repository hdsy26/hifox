package org.edf.hifox.security.handler.outbound;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.edf.hifox.core.chain.invocation.Invocation;
import org.edf.hifox.core.constant.SysParamConstant;
import org.edf.hifox.core.datatransfer.Body;
import org.edf.hifox.core.datatransfer.Message;
import org.edf.hifox.core.datatransfer.support.RequestHead;
import org.edf.hifox.core.handler.Handler;
import org.edf.hifox.core.reqinfo.OutboundRequestInfo;
import org.edf.hifox.core.util.MessageUtil;
import org.edf.hifox.security.cipher.Encipher;
import org.edf.hifox.security.signer.Signer;

/**
 * 
 * @author WangYang
 *
 */
public class OutboundSecurityHandler implements Handler<OutboundRequestInfo> {
	private Map<String, Object> outSecurityPolicy;

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
		
		Object security;
		if (outSecurityPolicy != null && (security = outSecurityPolicy.get(MessageUtil.getNodeId() + "_" + targetNodeId)) != null) {
			String contentString = data.getContentString();
			
			if (security instanceof Signer) {
				Signer signer = (Signer)security;
				String securityInfo = signer.signString(contentString);
				
				contentString = StringUtils.join(contentString, SysParamConstant.LINE_SEPARATOR_FIXED, securityInfo);
				
			} else if (security instanceof Encipher) {
				Encipher encipher = (Encipher)security;
				String encryptedContentString = encipher.encrypt(contentString);
				contentString = StringUtils.join(encryptedContentString, SysParamConstant.LINE_SEPARATOR_FIXED, StringUtils.EMPTY);
			}
			data.setContentString(contentString);
		}
		
		String contentString = data.getContentString();
		data.setContentString(StringUtils.join(contentString, SysParamConstant.LINE_SEPARATOR_FIXED, targetNodeId));
		
		invocation.invoke(data);
	}
	
	private void mapHandle(OutboundRequestInfo data, Invocation invocation) {
		invocation.invoke(data);
	}
	
	private void stringHandle(OutboundRequestInfo data, Invocation invocation) {
		invocation.invoke(data);
	}

	public void setOutSecurityPolicy(Map<String, Object> outSecurityPolicy) {
		this.outSecurityPolicy = outSecurityPolicy;
	}

}
