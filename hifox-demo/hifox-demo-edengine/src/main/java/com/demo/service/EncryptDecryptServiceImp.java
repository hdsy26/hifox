package com.demo.service;

import javax.annotation.Resource;

import org.edf.hifox.security.adapter.CipherAdapter;
import org.springframework.stereotype.Service;

import com.demo.datatransfer.inbound.A000S000Req;
import com.demo.datatransfer.inbound.A000S000Resp;
import com.demo.datatransfer.inbound.A000S100Req;
import com.demo.datatransfer.inbound.A000S100Resp;

@Service("encryptDecryptServiceImp")
public class EncryptDecryptServiceImp implements EncryptDecryptService {
	
	@Resource(name="defaultCipherAdapter")
	private CipherAdapter cipherAdapter;
	
	@Override
	public A000S000Resp encrypt(A000S000Req req) {
		String enTest = cipherAdapter.encrypt(req.getType(), req.getText());
		
		A000S000Resp resp = new A000S000Resp();
		resp.setText(enTest);
		
		return resp;
	}

	@Override
	public A000S100Resp decrypt(A000S100Req req) {
		String deTest = cipherAdapter.decrypt(req.getText());
		
		A000S100Resp resp = new A000S100Resp();
		resp.setText(deTest);
		
		return resp;
	}

}
