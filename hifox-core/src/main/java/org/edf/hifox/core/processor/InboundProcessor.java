package org.edf.hifox.core.processor;

import org.edf.hifox.core.chain.Chain;
import org.edf.hifox.core.chain.selector.ChainSelector;
import org.edf.hifox.core.constant.LogCodeConstant;
import org.edf.hifox.core.log.Logger;
import org.edf.hifox.core.log.LoggerFactory;
import org.edf.hifox.core.reqinfo.InboundRequestInfo;
import org.edf.hifox.core.util.SwapAreaUtil;

/**
 * 
 * @author WangYang
 *
 */
public class InboundProcessor implements Processor<byte[], InboundRequestInfo> {

	private static final Logger logger = LoggerFactory.getLogger(InboundProcessor.class);

	private ChainSelector selector;
	private String requestEncoding;
	private String responseEncoding;

	public void setSelector(ChainSelector selector) {
		this.selector = selector;
	}

	public void setRequestEncoding(String requestEncoding) {
		this.requestEncoding = requestEncoding;
	}

	public void setResponseEncoding(String responseEncoding) {
		this.responseEncoding = responseEncoding;
	}

	@Override
	public byte[] process(InboundRequestInfo data) {

		byte[] bytes = new byte[0];
		try {
			data.setRequestEncoding(requestEncoding);
			data.setResponseEncoding(responseEncoding);
			
			byte[] content = data.getContent();
			String contentString = new String(content, requestEncoding);
			
			data.setContentString(contentString);

			logger.info(LogCodeConstant.SYS00002, new Object[] {contentString});

			Chain chain = selector.select(data);
			chain.doChain(data);

			String respMsgStr = SwapAreaUtil.getInboundResponseMsgStr();
			
			logger.info(LogCodeConstant.SYS00003, new Object[]{respMsgStr});

			bytes = respMsgStr.getBytes(responseEncoding);

		} catch (Throwable t) {
			logger.error(LogCodeConstant.SYS00009, t);
		}
		return bytes;
	}

}
