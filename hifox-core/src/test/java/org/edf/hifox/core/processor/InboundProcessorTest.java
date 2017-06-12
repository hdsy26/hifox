package org.edf.hifox.core.processor;

import javax.annotation.Resource;

import org.edf.hifox.core.reqinfo.InboundRequestInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:framework/spring/*-applicationContext.xml",
		"classpath*:application/spring/*-applicationContext.xml"})
public class InboundProcessorTest {
	
	@Resource(name="inboundProcessor")
	private Processor<byte[], InboundRequestInfo> inboundProcessor;

	@Test
	public void testProcess() {
		InboundRequestInfo reqInfo = new InboundRequestInfo();
		byte[] result = inboundProcessor.process(reqInfo);
		
		System.out.println(result);
	}

}
