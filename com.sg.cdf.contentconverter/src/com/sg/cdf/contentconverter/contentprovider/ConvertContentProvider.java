package com.sg.cdf.contentconverter.contentprovider;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sg.cdf.contentconverter.ContentConverter;
import com.sg.cdf.core.content.ContentProvider;
import com.sg.cdf.core.request.IDistributionJob;

public class ConvertContentProvider extends ContentProvider {

	private static final long serialVersionUID = 1L;

	public String sourceURL;// Դ�ļ���URL��ַ

	public String sourcePath;// Դ�ļ���·��

	public String targetPath;// Ŀ���ļ��Ĵ��·��
	
	public String targetMediaType;//Ŀ���ļ�����

	@Override
	public IStatus run(IDistributionJob job) {
		try {
			File srcFile = null;
			if(sourcePath!=null){
				srcFile = new File(sourcePath);
			}

			if (sourceURL != null) {
				URL url = new URL(sourceURL);
				srcFile = new File(url.toURI());
			}

			File targetFile = new File(targetPath);
			
			ContentConverter.convert(srcFile, targetFile, targetMediaType);
			return Status.OK_STATUS;
		} catch (Exception e) {
			return new Status(IStatus.ERROR, "com.sg.cdf.contentconverter", IStatus.ERROR,
					e.getMessage(), e);
		}
	}
	
}
