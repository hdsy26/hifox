package org.edf.hifox.core.processor;

import java.util.Date;

import javax.annotation.Resource;

import org.edf.hifox.core.datatransfer.inbound.A000T000Req;
import org.edf.hifox.core.datatransfer.support.RequestHead;
import org.edf.hifox.core.datatransfer.support.RequestMessage;
import org.edf.hifox.core.editor.ClassEditor;
import org.edf.hifox.core.reqinfo.InboundRequestInfo;
import org.edf.hifox.core.util.DataConvertUtil;
import org.edf.hifox.core.util.MessageUtil;
import org.edf.hifox.core.util.OutboundUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:framework/spring/*-applicationContext.xml",
		"classpath*:application/spring/*-applicationContext.xml"})
public class InboundProcessorTest {

	@Resource(name = "inboundProcessor")
	private Processor<byte[], InboundRequestInfo> inboundProcessor;

	@Value("${local.node.id}")
	private String nodeId;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String className = "org.edf.hifox.core.editor.Log4jClassEditor";
		Class<ClassEditor> clazz = (Class<ClassEditor>) Class
				.forName(className);
		ClassEditor classEditor = clazz.newInstance();
		classEditor.edit();
	}

	@Test
	public void testProcess() {
		try {
			MessageUtil.createRequestHead("A000T000Req");
			InboundRequestInfo reqInfo = new InboundRequestInfo();
			reqInfo.setReceiveTime(new Date());
			RequestMessage requestMessage = new RequestMessage();

			RequestHead requestHead = MessageUtil.createRequestHead("A000T000");
			requestHead.setSysTargetNodeId(nodeId);
			requestMessage.setHead(requestHead);

			A000T000Req reqBody = new A000T000Req();
			requestMessage.setBody(reqBody);

			String contentString = DataConvertUtil.convert(
					"A000T000-test-request", requestMessage);

			reqInfo.setContent(contentString.getBytes("UTF-8"));
			
			inboundProcessor.process(reqInfo);
			
			OutboundUtil.sendMsg("A000T000", reqBody);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
