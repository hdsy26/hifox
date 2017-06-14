package org.edf.hifox.core.handler.inbound;

import org.apache.commons.lang3.StringUtils;
import org.edf.hifox.core.chain.invocation.Invocation;
import org.edf.hifox.core.constant.SysParamConstant;
import org.edf.hifox.core.handler.Handler;
import org.edf.hifox.core.reqinfo.InboundRequestInfo;
import org.edf.hifox.core.util.SpringContextUtil;
import org.edf.hifox.core.util.StringUtil;

/**
 * 
 * @author WangYang
 *
 */
public class InboundSecurityHandler implements Handler<InboundRequestInfo> {

	@Override
	public void handle(InboundRequestInfo data, Invocation invocation) {
		
		String orgContentString = data.getContentString();
		
		String contentString = StringUtils.substringBeforeLast(orgContentString, SysParamConstant.LINE_SEPARATOR_FIXED);
		
		String encodedSgin = StringUtils.substringAfterLast(orgContentString, SysParamConstant.LINE_SEPARATOR_FIXED);
		
		data.setContent(StringUtil.toBytes(contentString, data.getAgreedRequestEncoding()));
		data.setContentString(contentString);
		
		invocation.invoke(data);
	}

}
