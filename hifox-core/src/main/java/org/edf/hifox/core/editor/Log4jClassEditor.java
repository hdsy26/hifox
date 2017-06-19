package org.edf.hifox.core.editor;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

import org.edf.hifox.core.exception.ClassEditException;
import org.edf.hifox.core.log.I18nLogger;

/**
 * 
 * @author WangYang
 *
 */
public class Log4jClassEditor implements ClassEditor {
	
	private static final String CLASSNAME = "org.slf4j.impl.Log4jLoggerAdapter";
	
	private final AtomicBoolean executed = new AtomicBoolean(false);
	
	public void edit() {
		try {
			if (!executed.compareAndSet(false, true))
				return;
			ClassPool cp = ClassPool.getDefault();
			cp.insertClassPath(new ClassClassPath(Log4jClassEditor.class));
			CtClass cc = cp.getCtClass(CLASSNAME);
			CtField field = cc.getDeclaredField("FQCN");
			cc.removeField(field);
			field = CtField.make("public static String FQCN;", cc);
			cc.addField(field);
			Class<?> clazz = cc.toClass();
			Field f = clazz.getDeclaredField("FQCN");
			f.set(clazz, I18nLogger.class.getName());
		} catch (Exception e) {
			throw new ClassEditException(e);
		}
	}
	
}
