package org.edf.hifox.core.service;

import org.edf.hifox.core.constant.LogCodeConstant;
import org.edf.hifox.core.datatransfer.inbound.A000T000Req;
import org.edf.hifox.core.datatransfer.inbound.A000T000Resp;
import org.edf.hifox.core.log.Logger;
import org.edf.hifox.core.log.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("testServiceImpl")
public class TestServiceImpl implements TestService {

	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

	@Override
	public A000T000Resp test(A000T000Req req) {
		
		logger.info(LogCodeConstant.SYS00000);
		
		return new A000T000Resp();
	}

	
}
