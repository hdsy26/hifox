package com.demo;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.edf.hifox.core.editor.ClassEditor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.service.CallEdengineService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:framework/spring/*-applicationContext.xml",
		"classpath*:application/spring/*-applicationContext.xml"})
public class CallEdengineServiceImplTest {
	
	@Resource(name="callEdengineServiceImpl")
	private CallEdengineService callEdengineService;

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
	public void testSend() {
		String text = "abcd你好";
		String enText = callEdengineService.callA000S000("RSA", text);
		System.out.println("encryptresult:" + enText);
		String deText = callEdengineService.callA000S100(enText);
		System.out.println("decryptresult:" + deText);
		Assert.assertEquals(text, deText);
		
		enText = callEdengineService.callA000S000("DES", text);
		System.out.println("encryptresult:" + enText);
		deText = callEdengineService.callA000S100(enText);
		System.out.println("decryptresult:" + deText);
		Assert.assertEquals(text, deText);
		
		enText = callEdengineService.callA000S000("AES", text);
		System.out.println("encryptresult:" + enText);
		deText = callEdengineService.callA000S100(enText);
		System.out.println("decryptresult:" + deText);
		Assert.assertEquals(text, deText);
		
		enText = callEdengineService.callA000S000("RSA-DES", text);
		System.out.println("encryptresult:" + enText);
		deText = callEdengineService.callA000S100(enText);
		System.out.println("decryptresult:" + deText);
		Assert.assertEquals(text, deText);
		
		enText = callEdengineService.callA000S000("DES-AES", text);
		System.out.println("encryptresult:" + enText);
		deText = callEdengineService.callA000S100(enText);
		System.out.println("decryptresult:" + deText);
		Assert.assertEquals(text, deText);
		
		enText = callEdengineService.callA000S000("DES-RANDOMKEY", text);
		System.out.println("encryptresult:" + enText);
		deText = callEdengineService.callA000S100(enText);
		System.out.println("decryptresult:" + deText);
		Assert.assertEquals(text, deText);
		
		enText = callEdengineService.callA000S000("AES-RANDOMKEY", text);
		System.out.println("encryptresult:" + enText);
		deText = callEdengineService.callA000S100(enText);
		System.out.println("decryptresult:" + deText);
		Assert.assertEquals(text, deText);
		
		enText = callEdengineService.callA000S000("MD5", text);
		System.out.println("encryptresult:" + enText);
		Assert.assertNotNull(enText);
		
		enText = callEdengineService.callA000S000("SHA256", text);
		System.out.println("encryptresult:" + enText);
		Assert.assertNotNull(enText);
	}

}
