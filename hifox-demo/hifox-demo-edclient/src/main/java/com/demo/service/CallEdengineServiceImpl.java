package com.demo.service;

import org.edf.hifox.core.datatransfer.Head;
import org.edf.hifox.core.datatransfer.Message;
import org.edf.hifox.core.util.OutboundUtil;
import org.springframework.stereotype.Service;

import com.demo.datatransfer.inbound.A000S000Req;
import com.demo.datatransfer.inbound.A000S000Resp;
import com.demo.datatransfer.inbound.A000S100Req;
import com.demo.datatransfer.inbound.A000S100Resp;

@Service("callEdengineServiceImpl")
public class CallEdengineServiceImpl implements CallEdengineService {

	@Override
	public String callA000S000(String type, String text) {
		A000S000Req reqBody = new A000S000Req();
		reqBody.setType(type);
		reqBody.setText(text);
		
		Message<Head, A000S000Resp> message = OutboundUtil.sendMsg("A000S000", reqBody);
		A000S000Resp resp = message.getBody();

		return resp.getText();
	}

	@Override
	public String callA000S100(String text) {
		A000S100Req reqBody = new A000S100Req();
		reqBody.setText(text);
		
		Message<Head, A000S100Resp> message = OutboundUtil.sendMsg("A000S100", reqBody);
		A000S100Resp resp = message.getBody();

		return resp.getText();
	}

}
