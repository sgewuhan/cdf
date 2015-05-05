package com.sg.cdf.contentconverter.contentprovider;

import java.io.File;
import java.net.URL;
import java.util.Random;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sg.cdf.contentconverter.ContentConverter;
import com.sg.cdf.core.CDF;
import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.request.IDistributionJob;

public class ConvertContentProvider extends ContentProvider {

	private static final long serialVersionUID = 1L;

	public String sourceURL;// Դ�ļ���URL��ַ

	public String sourcePath;// Դ�ļ���·��

	public String targetPath;// Ŀ���ļ��Ĵ��·��

	public String targetMediaType;// Ŀ���ļ�����

	public String sourceMediaType;// Դ�ļ�����

	public String downloadContext;// �ṩ���ص�������

	@Override
	public IStatus run(IDistributionJob job) {
		try {
			File srcFile = null;
			if (sourcePath != null) {
				srcFile = new File(sourcePath);
			}

			if (sourceURL != null) {
				URL url = new URL(sourceURL);
				srcFile = new File(url.toURI());
			}

			File targetFile = new File(targetPath);
			if (downloadContext != null) {
				targetFile = new File(CDF.FILE_SERVER_PATH + File.separator
						+ downloadContext + File.separator
						+ targetFile.getName());
			}

			ContentConverter.convert(srcFile, targetFile, targetMediaType,
					sourceMediaType);

			return Status.OK_STATUS;
		} catch (Exception e) {
			return new Status(IStatus.ERROR, "com.sg.cdf.contentconverter",
					IStatus.ERROR, e.getMessage(), e);
		}
	}

	public static String getRandomString(int length, boolean caseIgnore) {

		Random randGen = null;
		char[] numbersAndLetters = null;
		Object initLock = new Object();

		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					if (caseIgnore) {
						numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz") //$NON-NLS-1$
								.toCharArray();
					} else {
						numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" //$NON-NLS-1$
								+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ") //$NON-NLS-1$
								.toCharArray();
					}
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen
					.nextInt(numbersAndLetters.length - 1)];
		}
		return new String(randBuffer);
	}

}
