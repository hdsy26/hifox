package com.demo.service;

import com.demo.datatransfer.inbound.A000S000Req;
import com.demo.datatransfer.inbound.A000S000Resp;
import com.demo.datatransfer.inbound.A000S100Req;
import com.demo.datatransfer.inbound.A000S100Resp;

public interface EncryptDecryptService {
	A000S000Resp encrypt(A000S000Req text);
	A000S100Resp decrypt(A000S100Req text);
}
