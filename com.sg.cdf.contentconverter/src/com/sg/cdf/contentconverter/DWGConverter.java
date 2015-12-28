package com.sg.cdf.contentconverter;

import java.io.File;

import com.sg.cdf.core.CDF;

public class DWGConverter {

	private String format;

	public DWGConverter() {
		this("pdf");
	}

	/**
	 * 
	 * @param mediaType
	 *            转换格式: bmp——位图格式(*.bmp), jpg——JPEG格式(*.jpg), gif——GIF格式(*.gif),
	 *            pcx——PCX格式(*.pcx), tiff——TIFF格式(*.tiff), png——PNG格式(*.png),
	 *            tga——TGA格式(*.tga), wmf——WMF格式(*.wmf), svg——SVG格式，(*.svg),
	 *            plt——HPGL PLT格式(*.plt), hgl——HPGL HGL格式(*.hgl),
	 *            pdf——PDF格式(*.pdf), pdfs——将更多的图形文件到一个PDF文件(*.PDF),
	 *            svgz——svgz(压缩可缩放矢量图形),(*.svgz), cgm——CGM(计算机图形元文件(CGM),*.cgm),
	 *            eps——EPS(封装PostScript(EPS),*.eps)
	 */
	public DWGConverter(String mediaType) {
		setFormat(mediaType);
		init();
	}

	public void init() {

	}

	public void setFormat(String mediaType) {
		mediaType = mediaType.toLowerCase();
		if ("bmp".equals(mediaType)) {
			format = "1";
		} else if ("jpg".equals(mediaType)) {
			format = "2";
		} else if ("gif".equals(mediaType)) {
			format = "3";
		} else if ("pcx".equals(mediaType)) {
			format = "4";
		} else if ("tiff".equals(mediaType)) {
			format = "5";
		} else if ("png".equals(mediaType)) {
			format = "6";
		} else if ("tga".equals(mediaType)) {
			format = "7";
		} else if ("wmf".equals(mediaType)) {
			format = "8";
		} else if ("svg".equals(mediaType)) {
			format = "101";
		} else if ("plt".equals(mediaType)) {
			format = "102";
		} else if ("hgl".equals(mediaType)) {
			format = "103";
		} else if ("pdf".equals(mediaType)) {
			format = "104";
		} else if ("pdfs".equals(mediaType)) {
			format = "105";
		} else if ("svgz".equals(mediaType)) {
			format = "106";
		} else if ("cgm".equals(mediaType)) {
			format = "107";
		} else if ("eps".equals(mediaType)) {
			format = "108";
		}
	}

	public void convert(File inputFile, File outputFile) {
		if (CDF.CAD_CONVERTER_PATH != null) {
			String cmdString = CDF.CAD_CONVERTER_PATH
					+ " /r /e /p 1 /b 7 /lw 2 /f " + format + " /ad \""
					+ inputFile.getPath() + "\"";
			try {
				Runtime.getRuntime().exec(cmdString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
