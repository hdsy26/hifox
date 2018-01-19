package org.edf.hifox.core.handler.inbound;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.edf.hifox.core.chain.invocation.Invocation;
import org.edf.hifox.core.constant.ConvertConstant;
import org.edf.hifox.core.constant.ErrorCodeConstant;
import org.edf.hifox.core.datatransfer.Body;
import org.edf.hifox.core.datatransfer.Head;
import org.edf.hifox.core.datatransfer.Message;
import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.handler.Handler;
import org.edf.hifox.core.reqinfo.InboundRequestInfo;
import org.edf.hifox.core.util.DataConvertUtil;
import org.edf.hifox.core.util.SwapAreaUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

/**
 * 
 * @author WangYang
 *
 */
public class InboundDataConvertHandler implements Handler<InboundRequestInfo> {
	private String serviceIdPath;
	
	@Override
	public void handle(InboundRequestInfo data, Invocation invocation) {
		Document document;
		try {
			document = DocumentHelper.parseText(data.getContentString());
			Node serviceIdNode = document.selectSingleNode(serviceIdPath);
			String serviceId = serviceIdNode.getText();
			data.setServiceId(serviceId);
		} catch (DocumentException e) {
			try {
				JSONObject jsonObj = JSON.parseObject(data.getContentString());
				String serviceId = (String)JSONPath.eval(jsonObj, "$" + serviceIdPath.replace("/", "."));
				data.setServiceId(serviceId);
			} catch (Exception e1) {
				throw new FailureException(ErrorCodeConstant.E0001S061, e);
			}
		}
		
		Message<Head, Body> requestMessage = DataConvertUtil.convert(data.getServiceId() + ConvertConstant.REQ, data.getContentString());
		data.setRequestMessage(requestMessage);
		
		invocation.invoke(data);
		
		Message<Head, Body> respMsg = SwapAreaUtil.getInboundResponseMessage();
		String respMsgStr = DataConvertUtil.convert(data.getServiceId() + ConvertConstant.RESP, respMsg);
		SwapAreaUtil.setInboundResponseMsgStr(respMsgStr);
	}

	public void setServiceIdPath(String serviceIdPath) {
		this.serviceIdPath = serviceIdPath;
	}

}
