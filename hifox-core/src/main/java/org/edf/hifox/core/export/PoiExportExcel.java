package org.edf.hifox.core.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.edf.hifox.core.constant.ErrorCodeConstant;
import org.edf.hifox.core.constant.LogCodeConstant;
import org.edf.hifox.core.constant.SysParamConstant;
import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.log.Logger;
import org.edf.hifox.core.log.LoggerFactory;
import org.edf.hifox.core.register.excel.registry.ExcelRegistry;
import org.edf.hifox.core.register.excel.registry.xmlbean.Column;
import org.edf.hifox.core.register.excel.registry.xmlbean.ExcelDef;
import org.edf.hifox.core.util.DateUtil;
import org.edf.hifox.core.util.SwapAreaUtil;

/**
 * 
 * @author WangYang
 *
 */
public class PoiExportExcel {
	
	private static final Logger logger = LoggerFactory.getLogger(PoiExportExcel.class);
	
	private static ExcelRegistry registry;

	public void setRegistry(ExcelRegistry registry) {
		PoiExportExcel.registry = registry;
	}
	
	public static String getSheetName(String id) {
		ExcelDef def = registry.getMeta(id);
		if (def == null)
			def = SwapAreaUtil.getValue("['" + id + "']", ExcelDef.class);
		
		if (def == null)
			throw new FailureException(ErrorCodeConstant.E0001S004, new Object[]{id});
		
		return def.getSheetName();
	}
	

	public static void export(String id, String dir, String fileName, List<Object> listdata) throws Exception {
		ExcelDef def = registry.getMeta(id);
		if (def == null)
			def = SwapAreaUtil.getValue("['" + id + "']", ExcelDef.class);
		
		if (def == null)
			throw new FailureException(ErrorCodeConstant.E0001S004, new Object[]{id});
		
		FileOutputStream fos = null;
		Workbook wb = null;
		try {
			File file = new File(dir);
			if (!file.exists())
				file.mkdirs();
			
			fos = new FileOutputStream(dir + SysParamConstant.LINE_SEPARATOR + fileName);
			
			wb = new SXSSFWorkbook();
			Sheet sheet = wb.createSheet(def.getSheetName());
			
			createHead(id, sheet);
			
			int y = 1;
			for (Object item : listdata) {
				export(id, sheet, item, y);
				y++;
			}
			
			wb.write(fos);
			
		} catch (Exception e) {
			logger.error(LogCodeConstant.SYS00009, e);
			throw e;
		} finally {
			if (wb != null)
				try {
					wb.close();
				} catch (Exception e) {
				}
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}
	}
	
	public static void createHead(String id, Sheet sheet) throws Exception {
		ExcelDef def = registry.getMeta(id);
		if (def == null)
			def = SwapAreaUtil.getValue("['" + id + "']", ExcelDef.class);
		
		if (def == null)
			throw new FailureException(ErrorCodeConstant.E0001S004, new Object[]{id});
		
		List<Column> columns = def.getColumns();
		
		Row row = sheet.createRow(0);
		int x = 0;
		for (Column column : columns) {
			Cell cell = row.createCell(x);
			cell.setCellValue(column.getHeadtext());
			x++;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void export(String id, Sheet sheet, Object item, int y) throws Exception {
		ExcelDef def = registry.getMeta(id);
		if (def == null)
			def = SwapAreaUtil.getValue("['" + id + "']", ExcelDef.class);
		
		if (def == null)
			throw new FailureException(ErrorCodeConstant.E0001S004, new Object[]{id});
		
		List<Column> columns = def.getColumns();
		
		Row row = sheet.createRow(y);
		int x = 0;
		Object obj;
		for (Column column : columns) {
			Cell cell = row.createCell(x);
			
			if (item instanceof Map)
				obj = ((Map<String, Object>)item).get(column.getDatafield());
			else
				obj = PropertyUtils.getProperty(item, column.getDatafield());
			
			String cont;
			if (obj instanceof String)
				cont = (String)obj;
			else if (obj instanceof BigDecimal)
				cont = ((BigDecimal)obj).toPlainString();
			else if (obj instanceof Date)
				cont = DateFormatUtils.format((Date)obj, "yyyy-MM-dd HH:mm:ss");
			else
				cont = obj == null ? "" : obj.toString();
			
			if ("label".equals(column.getType()))
				cell.setCellValue(cont);
			else if ("number".equals(column.getType()))
				cell.setCellValue(new Double(cont));
			else if ("datetime".equals(column.getType()))
				cell.setCellValue(DateUtil.parseDate(cont, "yyyy-MM-dd HH:mm:ss"));
			else if ("formula".equals(column.getType()))
				cell.setCellFormula(cont);
			else if ("".equals(cont))
				cell.setCellValue("");
			else
				throw new RuntimeException("column type error!");
			x++;
		}
		
	}
	
}
