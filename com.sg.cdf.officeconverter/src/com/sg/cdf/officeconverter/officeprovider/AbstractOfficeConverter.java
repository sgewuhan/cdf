package com.sg.cdf.officeconverter.officeprovider;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;

public abstract class AbstractOfficeConverter {

	public void convert(File inputFile, File outputFile) throws Exception {
		Exception ex = null;
		String filename = inputFile.getPath();
		String toFilename = outputFile.getPath();

		File tofile = new File(toFilename);
		if (tofile.exists()) {
			System.out.println("start ɾ����ʷת���ĵ�");
			tofile.delete();
			System.out.println("finish ɾ����ʷת���ĵ�");
		}

		Dispatch dis = null;
		ActiveXComponent app = null;

		try {
			ComThread.InitSTA();
			app = getActiveXComponent();
			System.out.println("��ʼ�����ĵ�ת��");
			dis = openDocument(app, filename);
			convert(dis, toFilename);
			System.out.println("ת�����");
		} catch (Exception e) {
			ex = e;
		} finally {
			try {
				dispose(app, dis);
			} catch (Exception e) {
				ex = e;
			} finally {
				ComThread.Release();
			}
		}
		if (ex != null) {
			ex.printStackTrace();
			throw ex;
		}
	}

	protected abstract ActiveXComponent getActiveXComponent() throws Exception;

	protected abstract Dispatch openDocument(ActiveXComponent app,
			String filename) throws Exception;

	protected abstract void convert(Dispatch dis, String toFilename)
			throws Exception;

	protected abstract void dispose(ActiveXComponent app, Dispatch dis)
			throws Exception;

}
