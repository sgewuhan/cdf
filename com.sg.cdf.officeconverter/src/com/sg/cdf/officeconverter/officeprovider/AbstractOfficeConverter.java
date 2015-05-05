package com.sg.cdf.officeconverter.officeprovider;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public abstract class AbstractOfficeConverter {
	static final int wdDoNotSaveChanges = 0;// ����������ĸ��ġ�
	static final int wdFormatPDF = 17;// PDF ��ʽ
	private ActiveXComponent app;

	public void convert(File inputFile, File outputFile) throws Exception {
		String filename = inputFile.getPath();
		String toFilename = outputFile.getPath();
		if (app == null) {
			app = new ActiveXComponent(getApplicationName());
		}
		app.setProperty("Visible", false);

		Dispatch docs = app.getProperty("Documents").toDispatch();
		System.out.println("���ĵ�..." + filename);
		final Dispatch doc = Dispatch.call(docs,//
				"Open", //
				filename,// FileName
				false,// ConfirmConversions
				true // ReadOnly
				).toDispatch();

		System.out.println("ת���ĵ���PDF..." + toFilename);
		File tofile = new File(toFilename);
		if (tofile.exists()) {
			tofile.delete();
		}
		
		final Timer timer = new Timer();
        TimerTask tt=new TimerTask() { 
            @Override
            public void run() {
            	Dispatch.call(doc, "Close", false);
//            	app.invoke("Quit", wdDoNotSaveChanges);
                timer.cancel();
            }
        };
		
        timer.schedule(tt, 3000);
		
		Dispatch.call(doc,//
				"SaveAs", //
				toFilename, // FileName
				wdFormatPDF);

		Dispatch.call(doc, "Close", false);
//		app.invoke("Quit", wdDoNotSaveChanges);
		timer.cancel();
	}

	protected abstract String getApplicationName();

	public void clear() {
		if (app != null)
			app.invoke("Quit", wdDoNotSaveChanges);
	}
}
