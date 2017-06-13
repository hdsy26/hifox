package org.edf.hifox.security.signer.support;

import javax.annotation.Resource;

import org.edf.hifox.core.editor.ClassEditor;
import org.edf.hifox.core.util.SwapAreaUtil;
import org.edf.hifox.security.cipher.Encipher;
import org.edf.hifox.security.signer.Signer;
import org.edf.hifox.security.signer.Verifier;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:framework/spring/*-applicationContext.xml",
		"classpath*:application/spring/*-applicationContext.xml"})
public class DefaultSignerVerifierTest {
	@Resource(name = "aesEncipher")
	private Encipher encipher;
	
	@Resource(name = "n0001-signer")
	private Signer signer;
	
	@Resource(name = "n0001-verifier")
	private Verifier verifier;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String className = "org.edf.hifox.core.editor.Log4jClassEditor";
		Class<ClassEditor> clazz = (Class<ClassEditor>) Class.forName(className);
		ClassEditor classEditor = clazz.newInstance();
		classEditor.edit();
	}

	@Test
	public void testSignVerifyString() {
		String signString = signer.signString("abcd你好abcd你好");
		System.out.println("sign:" + signString);
		
		SwapAreaUtil.releaseCurrentSwapArea();
		
		boolean result = verifier.verify("abcd你好abcd你好", signString);
		System.out.println("verify:" + result);
	}

}
