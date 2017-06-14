package org.edf.hifox.security.adapter;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.edf.hifox.core.datatransfer.Body;
import org.edf.hifox.core.datatransfer.Message;
import org.edf.hifox.core.datatransfer.support.RequestHead;
import org.edf.hifox.core.datatransfer.support.RequestMessage;
import org.edf.hifox.core.editor.ClassEditor;
import org.edf.hifox.core.util.SwapAreaUtil;
import org.edf.hifox.security.datatransfer.inbound.A000S000Req;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:framework/spring/*-applicationContext.xml",
		"classpath*:application/spring/*-applicationContext.xml"})
public class DefaultCipherAdapterTest {
	
	@Resource(name="defaultCipherAdapter")
	private CipherAdapter cipherAdapter;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String className = "org.edf.hifox.core.editor.Log4jClassEditor";
		Class<ClassEditor> clazz = (Class<ClassEditor>) Class.forName(className);
		ClassEditor classEditor = clazz.newInstance();
		classEditor.edit();
	}

	@Test
	public void testEncryptDecrypt_RSA() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("RSA");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_DES() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("DES");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_AES() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("AES");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_RSA_DES() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("RSA-DES");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_DES_AES() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("DES-AES");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_DES_RANDOMKEY() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("DES-RANDOMKEY");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_AES_RANDOMKEY() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("AES-RANDOMKEY");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncrypt_MD5() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("MD5");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
	}
	
	@Test
	public void testEncrypt_SHA256() {
		A000S000Req req = new A000S000Req();
		req.setDatetime(DateFormatUtils.format(new Date(), "yyyyMMdd"));
		req.setText("abcd你好");
		req.setType("SHA256");
		Message<RequestHead, Body> reqMsg = new RequestMessage();
		reqMsg.setBody(req);
		
		SwapAreaUtil.setInboundRequestMessage(reqMsg);
		
		String str = cipherAdapter.encrypt(req.getText());
		
		System.out.println("encrypt:" + str);
	}

}
