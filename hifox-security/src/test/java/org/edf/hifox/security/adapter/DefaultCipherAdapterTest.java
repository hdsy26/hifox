package org.edf.hifox.security.adapter;

import javax.annotation.Resource;

import org.edf.hifox.core.editor.ClassEditor;
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
		
		String str = cipherAdapter.encrypt("RSA", "abcd你好");
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_DES() {
		
		String str = cipherAdapter.encrypt("DES", "abcd你好");
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_AES() {
		
		String str = cipherAdapter.encrypt("AES", "abcd你好");
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_RSA_DES() {
		
		String str = cipherAdapter.encrypt("RSA-DES", "abcd你好");
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_DES_AES() {
		
		String str = cipherAdapter.encrypt("DES-AES", "abcd你好");
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_DES_RANDOMKEY() {
		
		String str = cipherAdapter.encrypt("DES-RANDOMKEY", "abcd你好");
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncryptDecrypt_AES_RANDOMKEY() {
		
		String str = cipherAdapter.encrypt("AES-RANDOMKEY", "abcd你好");
		
		System.out.println("encrypt:" + str);
		
		str = cipherAdapter.decrypt(str);
		
		System.out.println("decrypt:" + str);
	}
	
	@Test
	public void testEncrypt_MD5() {
		
		String str = cipherAdapter.encrypt("MD5", "abcd你好");
		
		System.out.println("encrypt:" + str);
	}
	
	@Test
	public void testEncrypt_SHA256() {
		
		String str = cipherAdapter.encrypt("SHA256", "abcd你好");
		
		System.out.println("encrypt:" + str);
	}

}
