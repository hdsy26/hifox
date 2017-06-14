package org.edf.hifox.security.handler.inbound;

import org.apache.commons.codec.binary.Base64;
import org.edf.hifox.core.chain.invocation.Invocation;
import org.edf.hifox.core.datatransfer.SecurityInfo;
import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.handler.Handler;
import org.edf.hifox.core.reqinfo.InboundRequestInfo;
import org.edf.hifox.core.util.ByteArrayUtil;
import org.edf.hifox.core.util.DataConvertUtil;
import org.edf.hifox.core.util.SpringContextUtil;
import org.edf.hifox.core.util.StringUtil;
import org.edf.hifox.security.cipher.Decipher;
import org.edf.hifox.security.constant.ErrorCodeConstant;
import org.edf.hifox.security.constant.SecurityConstant;
import org.edf.hifox.security.signer.Verifier;


/**
 * 
 * @author WangYang
 *
 */
public class InboundSecurityHandler implements Handler<InboundRequestInfo> {

	@Override
	public void handle(InboundRequestInfo data, Invocation invocation) {
		String reqNodeId = data.getReqNodeId();
		String requestEncoding = data.getAgreedRequestEncoding();
		String contentString = data.getContentString();
		
		String securityInfo = data.getSecurityInfo();
		byte[] decodedSecurityInfo = Base64.decodeBase64(StringUtil.toBytes(securityInfo, requestEncoding));
		String decodedSecurityInfoString = ByteArrayUtil.toString(decodedSecurityInfo, requestEncoding);
		
		SecurityInfo info = DataConvertUtil.convert("node-security", decodedSecurityInfoString);
		
		if (SecurityConstant.SECURITY_MODE_SIGN.equals(info.getMode())) {
			Verifier verifier = SpringContextUtil.getBean(reqNodeId + "-verifier", Verifier.class);
			if (verifier == null)
				throw new FailureException(ErrorCodeConstant.E0001S050, new Object[]{reqNodeId});
			
			verifier.update(contentString);
			
			if (!verifier.verify(info.getSignature()))
				throw new FailureException(ErrorCodeConstant.E0001S050, new Object[]{reqNodeId});
			
		} else if (SecurityConstant.SECURITY_MODE_ENCRYPT.equals(info.getMode())){
			Decipher decipher = SpringContextUtil.getBean(reqNodeId + "-decipher", Decipher.class);
			if (decipher == null)
				throw new FailureException(ErrorCodeConstant.E0001S050, new Object[]{reqNodeId});
			
			try {
				contentString = decipher.decrypt(contentString);
			} catch (Exception e) {
				throw new FailureException(ErrorCodeConstant.E0001S050, new Object[]{reqNodeId});
			}
			
			data.setContent(StringUtil.toBytes(contentString, data.getAgreedRequestEncoding()));
			data.setContentString(contentString);
		}
		invocation.invoke(data);
	}

}
