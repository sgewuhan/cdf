package com.sg.cdf.officeconverter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.sg.cdf.officeconverter.officeprovider.AbstractOfficeConverter;
import com.sg.cdf.officeconverter.officeprovider.ExcelConverter;
import com.sg.cdf.officeconverter.officeprovider.FileUtil;
import com.sg.cdf.officeconverter.officeprovider.PPTConverter;
import com.sg.cdf.officeconverter.officeprovider.WordConverter;

public class OfficeActivator implements BundleActivator {

	private static BundleContext context;
	private static WordConverter wordConverter;
	private static ExcelConverter excelConverter;
	private static PPTConverter pptConverter;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		OfficeActivator.context = bundleContext;
		wordConverter = new WordConverter();
		excelConverter = new ExcelConverter();
		pptConverter = new PPTConverter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		OfficeActivator.context = null;
		wordConverter.clear();
		excelConverter.clear();
		pptConverter.clear();
	}

	public static AbstractOfficeConverter getComponent(String filename) {
		int fileType = FileUtil.getFileType(filename);

		if (FileUtil.FILETYPE_WORD_FILE == fileType) {
			return wordConverter;
		}
		if (FileUtil.FILETYPE_EXCEL_FILE == fileType) {
			return excelConverter;
		}
		if (FileUtil.FILETYPE_PPT_FILE == fileType) {
			return pptConverter;
		}

		return null;
	}

}
