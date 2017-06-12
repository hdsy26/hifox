package org.edf.hifox.core.service;

import org.edf.hifox.core.datatransfer.inbound.A000T000Req;
import org.edf.hifox.core.datatransfer.inbound.A000T000Resp;


public interface TestService {
	A000T000Resp test(A000T000Req req);
	
}
