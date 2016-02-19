package com.sg.cdf.officeconverter.officeprovider;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class PPTConverter extends AbstractOfficeConverter {

	@Override
	protected ActiveXComponent getActiveXComponent() throws Exception {
		return new ActiveXComponent("PowerPoint.Application");
	}

	@Override
	protected Dispatch openDocument(ActiveXComponent app, String filename)
			throws Exception {
		Dispatch dis = app.getProperty("Presentations").toDispatch();
		System.out.println("打开文档..." + filename);
		return Dispatch.call(dis, "Open", filename, true, true, false)
				.toDispatch();
	}

	@Override
	protected void convert(Dispatch dis, String toFilename) throws Exception {
		System.out.println("转换文档到PDF..." + toFilename);
		Dispatch.call(dis, "SaveAs", toFilename, 32);
	}

	@Override
	protected void dispose(ActiveXComponent app, Dispatch dis) throws Exception {
		if (dis != null) {
			Dispatch.call(dis, "Close");
		}
		if (app != null) {
			app.invoke("Quit");
			app = null;
		}
	}
}
