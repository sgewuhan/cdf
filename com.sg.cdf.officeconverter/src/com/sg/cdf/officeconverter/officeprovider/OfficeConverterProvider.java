package com.sg.cdf.officeconverter.officeprovider;

import java.io.File;

public class OfficeConverterProvider {

	public void convert(File inputFile, File outputFile) throws Exception {
		String filename = inputFile.getPath();
		AbstractOfficeConverter converter = getComponent(filename);
		if (converter != null) {
			converter.convert(inputFile, outputFile);
		}
	}

	public static AbstractOfficeConverter getComponent(String filename) {
		int fileType = FileUtil.getFileType(filename);

		if (FileUtil.FILETYPE_WORD_FILE == fileType) {
			return new WordConverter();
		}
		if (FileUtil.FILETYPE_EXCEL_FILE == fileType) {
			return new ExcelConverter();
		}
		if (FileUtil.FILETYPE_PPT_FILE == fileType) {
			return new PPTConverter();
		}

		return null;
	}
}
