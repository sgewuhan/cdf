package com.sg.cdf.contentconverter;

import java.io.File;

import com.sg.cdf.officeconverter.officeprovider.AbstractOfficeConverter;
import com.sg.cdf.officeconverter.officeprovider.OfficeConverterProvider;

public class OfficeConverter {

	public void convert(File inputFile, File outputFile) throws Exception {
		String filename = inputFile.getPath();
		AbstractOfficeConverter converter = OfficeConverterProvider
				.getComponent(filename);
		if (converter != null) {
			converter.convert(inputFile, outputFile);
		}
	}

}
