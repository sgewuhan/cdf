package com.sg.cdf.officeconverter.officeprovider;

public class FileUtil {

	private static final String[] WORD_FILE = new String[] { ".doc", ".docx" };
	private static final String[] EXCEL_FILE = new String[] { ".xls", ".xlsx" };
	private static final String[] PPT_FILE = new String[] { ".ppt", ".pptx" };
	public static final int FILETYPE_UNKONWN = 0;
	public static final int FILETYPE_WORD_FILE = 1;
	public static final int FILETYPE_EXCEL_FILE = 2;
	public static final int FILETYPE_PPT_FILE = 3;

	public static int getFileType(String name) {
		String fileName = name.toLowerCase();
		int type = checkFileType(fileName, WORD_FILE, FILETYPE_WORD_FILE);
		if (type == FILETYPE_UNKONWN) {
			type = checkFileType(fileName, EXCEL_FILE, FILETYPE_EXCEL_FILE);
		} else {
			return type;
		}
		if (type == FILETYPE_UNKONWN) {
			type = checkFileType(fileName, PPT_FILE, FILETYPE_PPT_FILE);
		} else {
		}
		return type;
	}

	private static int checkFileType(String fileName, String[] p, int result) {
		for (int i = 0; i < p.length; i++) {
			if (fileName.endsWith(p[i])) {
				return result;
			}
		}
		return FILETYPE_UNKONWN;
	}
}
