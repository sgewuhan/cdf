package com.sg.cdf.contentconverter;

import java.io.File;

import com.sg.cdf.officeconverter.OfficeActivator;
import com.sg.cdf.officeconverter.officeprovider.AbstractOfficeConverter;

public class OfficeConverter {

	public void convert(File inputFile, File outputFile) throws Exception {
		String filename = inputFile.getPath();
		AbstractOfficeConverter converter = OfficeActivator
				.getComponent(filename);
		if (converter != null) {
			converter.convert(inputFile, outputFile);
		}
	}

}
