package com.sg.cdf.officeconverter.officeprovider;

public class ExcelConverter extends AbstractOfficeConverter {

	@Override
	protected String getApplicationName() {
		return "Excel.Application";
//		return "KET.Application";
	}

}
