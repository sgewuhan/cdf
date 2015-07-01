package com.sg.cdf.officeconverter.officeprovider;

import java.io.File;

import com.sg.cdf.officeconverter.OfficeActivator;
import com.sg.cdf.officeconverter.officeprovider.AbstractOfficeConverter;

public class OfficeConverterProvider {

	public void convert(File inputFile, File outputFile) throws Exception {
		String filename = inputFile.getPath();
		AbstractOfficeConverter converter = OfficeActivator
				.getComponent(filename);
//		AbstractOfficeConverter converter = getComponent(filename);
		if (converter != null) {
			converter.convert(inputFile, outputFile);
			converter.clear();
		}
	}

//	public static void main(String[] args) {
//		File inputFile = new File("H:/加密文件/34FB4E44A2F9EEEA48257D90000BADF0新材业务联系.doc");
//		File outputFile = new File("H:/加密文件/34FB4E44A2F9EEEA48257D90000BADF0新材业务联系.pdf");
//		OfficeConverterProvider officeConverterProvider = new OfficeConverterProvider();
//		try {
//			officeConverterProvider.convert(inputFile, outputFile);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public AbstractOfficeConverter getComponent(String filename) {
//		int fileType = FileUtil.getFileType(filename);
//
//		if (FileUtil.FILETYPE_WORD_FILE == fileType) {
//			return new WordConverter();
//		}
//		if (FileUtil.FILETYPE_EXCEL_FILE == fileType) {
//			return new ExcelConverter();
//		}
//		if (FileUtil.FILETYPE_PPT_FILE == fileType) {
//			return new PPTConverter();
//		}
//
//		return null;
//	}

}
