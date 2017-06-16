package org.edf.hifox.security.handler.inbound;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.edf.hifox.core.chain.invocation.Invocation;
import org.edf.hifox.core.constant.SysParamConstant;
import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.handler.Handler;
import org.edf.hifox.core.reqinfo.InboundRequestInfo;
import org.edf.hifox.core.util.ByteArrayUtil;
import org.edf.hifox.core.util.MessageUtil;
import org.edf.hifox.core.util.StringUtil;
import org.edf.hifox.security.cipher.Decipher;
import org.edf.hifox.security.constant.ErrorCodeConstant;
import org.edf.hifox.security.signer.Verifier;


/**
 * 
 * @author WangYang
 *
 */
public class InboundSecurityHandler implements Handler<InboundRequestInfo> {
	private Map<String, Object> inSecurityPolicy;

	@Override
	public void handle(InboundRequestInfo data, Invocation invocation) {
		
		String requestEncoding = data.getRequestEncoding();
		String allContentString = data.getContentString();
		
		// 发起方节点
		String reqNodeId = StringUtils.substringAfterLast(allContentString, SysParamConstant.LINE_SEPARATOR_FIXED);
		
		String temp = StringUtils.substringBeforeLast(allContentString, SysParamConstant.LINE_SEPARATOR_FIXED);
		
		String securityInfo = StringUtils.substringAfterLast(temp, SysParamConstant.LINE_SEPARATOR_FIXED);
		
		String contentString = StringUtils.substringBeforeLast(temp, SysParamConstant.LINE_SEPARATOR_FIXED);
		
		data.setContent(StringUtil.toBytes(contentString, requestEncoding));
		data.setContentString(contentString);
		
		Object security;
		if (inSecurityPolicy != null && (security = inSecurityPolicy.get(reqNodeId + "_" + MessageUtil.getNodeId())) != null) {
			if (security instanceof Verifier) {
				Verifier verifier = (Verifier)security;
				
				byte[] decodedSecurityInfo = Base64.decodeBase64(StringUtil.toBytes(securityInfo, requestEncoding));
				String decodedSecurityInfoString = ByteArrayUtil.toString(decodedSecurityInfo, requestEncoding);
				
				boolean valid = verifier.verify(contentString, decodedSecurityInfoString);
				if (!valid)
					throw new FailureException(ErrorCodeConstant.E0001S050, new Object[]{reqNodeId});
				
			} else if (security instanceof Decipher) {
				Decipher decipher = (Decipher)security;
				
				try {
					contentString = decipher.decrypt(contentString);
				} catch (Exception e) {
					throw new FailureException(ErrorCodeConstant.E0001S050, new Object[]{reqNodeId});
				}
				
				data.setContent(StringUtil.toBytes(contentString, requestEncoding));
				data.setContentString(contentString);
			}
		}
		
		invocation.invoke(data);
	}

	public void setInSecurityPolicy(Map<String, Object> inSecurityPolicy) {
		this.inSecurityPolicy = inSecurityPolicy;
	}

}
