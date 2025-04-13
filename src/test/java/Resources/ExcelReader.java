package Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
//	To Fetch properties or elements from excel sheet as an input

	private String path = System.getProperty("user.dir") + "\\src\\test\\java\\Resources\\";

	private final HashMap<String, String> hmap;
	private static Logger logs = LogManager.getLogger(ExcelReader.class);
	private String sheetname;

	public ExcelReader(String newpath, String sheetname) {
		hmap = new HashMap<String, String>();
		this.path = path + newpath + ".xlsx";
		this.sheetname = sheetname;
	}

	public HashMap<String, String> readExcel() throws IOException {
		try (FileInputStream fis = new FileInputStream(path); XSSFWorkbook wb = new XSSFWorkbook(fis)) {
			XSSFSheet sheet = wb.getSheet(sheetname);
			if (sheet == null) {
				logs.error("Sheet '" + sheetname + "' is not available in: " + path);
				throw new IOException("Sheet '" + sheetname + "' not found in file: " + path);
			} else {
				int r = sheet.getLastRowNum();
				for (int i = 0; i <= r; i++) {
					XSSFRow row = sheet.getRow(i);
					if (row != null && row.getCell(0) != null && row.getCell(1) != null) {
						String key = row.getCell(0).toString();
						String val = row.getCell(1).toString();
						hmap.put(key, val);// where key is in the first column and value is in the second column
					}
				}
			}
		} catch (Exception e) {
			logs.error("Error while reading the path: " + path);
			throw new IOException("Error while reading the path: " + path, e);
		}
		return hmap;
	}
}